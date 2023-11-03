package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.AbstractNodeFactory;
import io.ballerina.compiler.syntax.tree.IdentifierToken;
import io.ballerina.compiler.syntax.tree.Node;
import io.ballerina.compiler.syntax.tree.NodeFactory;
import io.ballerina.compiler.syntax.tree.NodeList;
import io.ballerina.compiler.syntax.tree.RecordFieldNode;
import io.ballerina.compiler.syntax.tree.TypeDefinitionNode;
import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;
import io.ballerina.lib.copybook.commons.schema.CopybookNode;
import io.ballerina.lib.copybook.commons.schema.GroupItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createToken;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.CLOSE_BRACE_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.OPEN_BRACE_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.QUESTION_MARK_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.RECORD_KEYWORD;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.SEMICOLON_TOKEN;

public class RecordTypeGenerator extends TypeGenerator {

    GroupItem groupItemNode;

    public RecordTypeGenerator(GroupItem groupItemNode) {

        this.groupItemNode = groupItemNode;
    }

    @Override public TypeDescriptorNode generateTypeDescriptorNode(List<TypeDefinitionNode> typeDefList) {

        List<Node> recordFields = new LinkedList<>();
        RecordMetadata metadataBuilder = getRecordMetadata();
        List<CopybookNode> fields = groupItemNode.getChildren();
        recordFields.addAll(addRecordFields(fields, typeDefList));
        NodeList<Node> fieldNodes =
                AbstractNodeFactory.createNodeList(recordFields);
        return NodeFactory.createRecordTypeDescriptorNode(createToken(RECORD_KEYWORD),
                createToken(OPEN_BRACE_TOKEN), fieldNodes, metadataBuilder.getRestDescriptorNode(),
                createToken(CLOSE_BRACE_TOKEN));
    }

    public List<Node> addRecordFields(List<CopybookNode> fields, List<TypeDefinitionNode> typeDefList) {

        List<Node> recordFieldList = new ArrayList<>();
        for (CopybookNode field : fields) {
            String fieldNameStr = CodeGeneratorUtils.escapeIdentifier(field.getName().trim());
            IdentifierToken fieldName = AbstractNodeFactory.createIdentifierToken(fieldNameStr);
            TypeGenerator typeGenerator = CodeGeneratorUtils.getTypeGenerator(field);
            TypeDescriptorNode typeDescriptorNode = typeGenerator.generateTypeDescriptorNode(typeDefList);
            RecordFieldNode recordFieldNode = NodeFactory.createRecordFieldNode(null, null,
                    typeDescriptorNode, fieldName, createToken(QUESTION_MARK_TOKEN), createToken(SEMICOLON_TOKEN));
            recordFieldList.add(recordFieldNode);
        }
        return recordFieldList;
    }

    public RecordMetadata getRecordMetadata() {
        boolean isOpenRecord = true;
        return new RecordMetadata.Builder().withIsOpenRecord(isOpenRecord).withRestDescriptorNode(null).build();
    }
}
