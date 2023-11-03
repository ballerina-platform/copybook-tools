package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.ArrayDimensionNode;
import io.ballerina.compiler.syntax.tree.BasicLiteralNode;
import io.ballerina.compiler.syntax.tree.SyntaxKind;
import io.ballerina.compiler.syntax.tree.TypeDefinitionNode;
import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;
import io.ballerina.lib.copybook.commons.schema.CopybookNode;
import io.ballerina.lib.copybook.commons.schema.DataItem;
import io.ballerina.lib.copybook.commons.schema.GroupItem;

import java.util.List;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createEmptyMinutiaeList;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createIdentifierToken;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createLiteralValueToken;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createNodeList;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createToken;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createArrayDimensionNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createArrayTypeDescriptorNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createBasicLiteralNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createSimpleNameReferenceNode;
import static io.ballerina.tools.copybook.generator.CodeGeneratorUtils.getTypeReferenceName;
import static io.ballerina.tools.copybook.generator.CopybookTypeGenerator.addToFieldTypeDefinitionList;
import static io.ballerina.tools.copybook.generator.CopybookTypeGenerator.generateFieldTypeDefNode;

public class ArrayTypeGenerator extends TypeGenerator {

    CopybookNode schemaValue;

    public ArrayTypeGenerator(CopybookNode groupItemNode) {
        this.schemaValue = groupItemNode;
    }

    @Override public TypeDescriptorNode generateTypeDescriptorNode(List<TypeDefinitionNode> typeDefList) {

        if (schemaValue instanceof GroupItem) {
            return getGroputItemDescriptorNode(typeDefList);
        } else {
            return getDataItemDescriptorNode(typeDefList);
        }
    }

    private TypeDescriptorNode getGroputItemDescriptorNode(List<TypeDefinitionNode> typeDefList) {

        BasicLiteralNode length = createBasicLiteralNode(SyntaxKind.NUMERIC_LITERAL,
                createLiteralValueToken(SyntaxKind.DECIMAL_INTEGER_LITERAL_TOKEN,
                        String.valueOf(schemaValue.getOccurringCount()),
                        createEmptyMinutiaeList(), createEmptyMinutiaeList()));
        ArrayDimensionNode arrayDimension =
                createArrayDimensionNode(createToken(SyntaxKind.OPEN_BRACKET_TOKEN), length,
                        createToken(SyntaxKind.CLOSE_BRACKET_TOKEN));
        TypeGenerator typeGenerator = new RecordTypeGenerator((GroupItem) schemaValue);
        TypeDescriptorNode wrappedType =
                typeGenerator.generateTypeDescriptorNode(typeDefList);
        return createArrayTypeDescriptorNode(wrappedType, createNodeList(arrayDimension));
    }

    private TypeDescriptorNode getDataItemDescriptorNode(List<TypeDefinitionNode> typeDefList) {

        TypeDefinitionNode fieldType = generateFieldTypeDefNode(
                (DataItem) schemaValue, getTypeReferenceName(schemaValue, false));
        addToFieldTypeDefinitionList(fieldType, typeDefList);
        String extractName;
        if (schemaValue.getOccurringCount() > 0) {
            extractName = getTypeReferenceName(schemaValue, true);
        } else {
            extractName = getTypeReferenceName(schemaValue, false);
        }
        String typeName = CodeGeneratorUtils.getValidName(extractName);
        BasicLiteralNode length = createBasicLiteralNode(SyntaxKind.NUMERIC_LITERAL,
                createLiteralValueToken(SyntaxKind.DECIMAL_INTEGER_LITERAL_TOKEN,
                        String.valueOf(schemaValue.getOccurringCount()),
                        createEmptyMinutiaeList(), createEmptyMinutiaeList()));
        ArrayDimensionNode arrayDimension =
                createArrayDimensionNode(createToken(SyntaxKind.OPEN_BRACKET_TOKEN), length,
                        createToken(SyntaxKind.CLOSE_BRACKET_TOKEN));
        TypeDescriptorNode wrappedType = createSimpleNameReferenceNode(createIdentifierToken(typeName));
        return createArrayTypeDescriptorNode(wrappedType, createNodeList(arrayDimension));
    }
}
