package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.AbstractNodeFactory;
import io.ballerina.compiler.syntax.tree.AnnotationNode;
import io.ballerina.compiler.syntax.tree.IdentifierToken;
import io.ballerina.compiler.syntax.tree.NodeFactory;
import io.ballerina.compiler.syntax.tree.NodeList;
import io.ballerina.compiler.syntax.tree.RecordFieldNode;
import io.ballerina.compiler.syntax.tree.TypeDefinitionNode;
import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;
import io.ballerina.copybook.parser.schema.GroupItem;
import io.ballerina.copybook.parser.schema.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createNodeList;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createToken;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.CLOSE_BRACE_PIPE_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.CLOSE_BRACE_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.OPEN_BRACE_PIPE_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.OPEN_BRACE_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.QUESTION_MARK_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.RECORD_KEYWORD;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.SEMICOLON_TOKEN;

public class RecordTypeGenerator extends TypeGenerator {

    GroupItem groupItemNode;
    String typeName;

    public RecordTypeGenerator(GroupItem groupItemNode, String typeName) {

        this.groupItemNode = groupItemNode;
        this.typeName = typeName;
    }

    @Override public TypeDescriptorNode generateTypeDescriptorNode() {

        List<io.ballerina.compiler.syntax.tree.Node> recordFields = new LinkedList<>();
        // extract metadata with additional properties
        RecordMetadata metadataBuilder = getRecordMetadata();

        if (groupItemNode.getOccurs() < 0) { //check arrays
            List<Node> fields = groupItemNode.getChildren();

            recordFields.addAll(addRecordFields(fields, typeName));
            NodeList<io.ballerina.compiler.syntax.tree.Node> fieldNodes =
                    AbstractNodeFactory.createNodeList(recordFields);
            return NodeFactory.createRecordTypeDescriptorNode(createToken(RECORD_KEYWORD),
                    metadataBuilder.isOpenRecord() ? createToken(OPEN_BRACE_TOKEN) : createToken(OPEN_BRACE_PIPE_TOKEN),
                    fieldNodes, metadataBuilder.getRestDescriptorNode(),
                    metadataBuilder.isOpenRecord() ? createToken(CLOSE_BRACE_TOKEN) :
                            createToken(CLOSE_BRACE_PIPE_TOKEN));
        } else {
            return NodeFactory.createRecordTypeDescriptorNode(createToken(RECORD_KEYWORD),
                    metadataBuilder.isOpenRecord() ? createToken(OPEN_BRACE_TOKEN) : createToken(OPEN_BRACE_PIPE_TOKEN),
                    createNodeList(recordFields), metadataBuilder.getRestDescriptorNode(),
                    metadataBuilder.isOpenRecord() ? createToken(CLOSE_BRACE_TOKEN) :
                            createToken(CLOSE_BRACE_PIPE_TOKEN));
        }
    }

    public List<io.ballerina.compiler.syntax.tree.Node> addRecordFields(List<Node> fields, String recordName) {

        List<io.ballerina.compiler.syntax.tree.Node> recordFieldList = new ArrayList<>();
        for (Node field : fields) {
            String fieldNameStr = CodeGeneratorUtils.escapeIdentifier(field.getName().trim());
            IdentifierToken fieldName = AbstractNodeFactory.createIdentifierToken(fieldNameStr);
            TypeGenerator typeGenerator = CodeGeneratorUtils.getTypeGenerator(field, fieldNameStr);
//            if (typeGenerator instanceof RecordTypeGenerator) {
//                TypeDefinitionNode typeDefinitionNode = getTypeDefinitionNode((GroupItem) field);
//            } else if (typeGenerator instanceof ReferencedTypeGenerator) {
//
//            }
            // generate type definition node for the field if it's a group item or data item
            TypeDescriptorNode fieldTypeName = typeGenerator.generateTypeDescriptorNode();
            RecordFieldNode recordFieldNode = NodeFactory.createRecordFieldNode(null, null,
                    fieldTypeName, fieldName, createToken(QUESTION_MARK_TOKEN), createToken(SEMICOLON_TOKEN));
            recordFieldList.add(recordFieldNode);
        }
        return recordFieldList;
    }

    private TypeDefinitionNode getTypeDefinitionNode(GroupItem typeDefinition) {
        String typeName = typeDefinition.getName();
        IdentifierToken typeNameToken = AbstractNodeFactory.createIdentifierToken(CodeGeneratorUtils.getValidName(
                typeName.trim()));
        RecordTypeGenerator typeGenerator = new RecordTypeGenerator(typeDefinition, typeName);
        List<AnnotationNode> typeAnnotations = new ArrayList<>();
        AnnotationNode constraintNode = CodeGeneratorUtils.generateConstraintNode(typeName);
        if (constraintNode != null) {
            typeAnnotations.add(constraintNode);
        }
        return typeGenerator.generateTypeDefinitionNode(typeNameToken, null, typeAnnotations);
    }

    public RecordMetadata getRecordMetadata() {

        boolean isOpenRecord = true;
        return new RecordMetadata.Builder()
                .withIsOpenRecord(isOpenRecord)
                .withRestDescriptorNode(null).build();
    }
}
