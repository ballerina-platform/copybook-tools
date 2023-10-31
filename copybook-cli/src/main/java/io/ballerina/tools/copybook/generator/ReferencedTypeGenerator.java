package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.ArrayDimensionNode;
import io.ballerina.compiler.syntax.tree.BasicLiteralNode;
import io.ballerina.compiler.syntax.tree.SyntaxKind;
import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;
import io.ballerina.copybook.parser.schema.DataItem;
import io.ballerina.copybook.parser.schema.GroupItem;
import io.ballerina.copybook.parser.schema.Node;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createEmptyMinutiaeList;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createIdentifierToken;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createLiteralValueToken;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createNodeList;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createToken;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createArrayDimensionNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createArrayTypeDescriptorNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createBasicLiteralNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createSimpleNameReferenceNode;
import static io.ballerina.tools.copybook.generator.CodeGeneratorUtils.extractTypeReferenceName;

public class ReferencedTypeGenerator extends TypeGenerator {

    Node fieldSchema;

    public ReferencedTypeGenerator(Node fieldSchema) {

        this.fieldSchema = fieldSchema;
    }

    /**
     * Generate TypeDescriptorNode for referenced schemas.
     */
    @Override
    public TypeDescriptorNode generateTypeDescriptorNode(boolean isRecordFieldReference) {
        String extractName = getTypeReferenceName(fieldSchema, isRecordFieldReference);
        String typeName = CodeGeneratorUtils.getValidName(extractName);
        if (fieldSchema instanceof GroupItem && fieldSchema.getOccurs() > 0) {
            BasicLiteralNode length = createBasicLiteralNode(SyntaxKind.NUMERIC_LITERAL,
                createLiteralValueToken(SyntaxKind.DECIMAL_INTEGER_LITERAL_TOKEN,
                        String.valueOf(fieldSchema.getOccurs()),
                        createEmptyMinutiaeList(), createEmptyMinutiaeList()));
            ArrayDimensionNode arrayDimension =
                    createArrayDimensionNode(createToken(SyntaxKind.OPEN_BRACKET_TOKEN), length,
                            createToken(SyntaxKind.CLOSE_BRACKET_TOKEN));
            TypeDescriptorNode wrappedType = createSimpleNameReferenceNode(createIdentifierToken(typeName));
            return createArrayTypeDescriptorNode(wrappedType, createNodeList(arrayDimension));
        } else if (fieldSchema instanceof DataItem && fieldSchema.getOccurs() > 0 && isRecordFieldReference) {
            BasicLiteralNode length = createBasicLiteralNode(SyntaxKind.NUMERIC_LITERAL,
                    createLiteralValueToken(SyntaxKind.DECIMAL_INTEGER_LITERAL_TOKEN,
                            String.valueOf(fieldSchema.getOccurs()),
                            createEmptyMinutiaeList(), createEmptyMinutiaeList()));
            ArrayDimensionNode arrayDimension =
                    createArrayDimensionNode(createToken(SyntaxKind.OPEN_BRACKET_TOKEN), length,
                            createToken(SyntaxKind.CLOSE_BRACKET_TOKEN));
            TypeDescriptorNode wrappedType = createSimpleNameReferenceNode(createIdentifierToken(typeName));
            return createArrayTypeDescriptorNode(wrappedType, createNodeList(arrayDimension));
        }
        return createSimpleNameReferenceNode(createIdentifierToken(typeName));
    }

    private String getTypeReferenceName(Node dataItem, boolean isRecordFieldReference) {

        if (dataItem instanceof DataItem) {
            if (!isRecordFieldReference) {
                if (((DataItem) dataItem).isNumeric()) {
                    if (((DataItem) dataItem).getFloatingPointLength() > 0) {
                        return "decimal";
                    }
                    return "int";
                } else if (((DataItem) dataItem).getPicture().contains("COMP")) {
                    return "byte[]";
                } else {
                    return "string";
                }
            } else {
                return extractTypeReferenceName((DataItem) dataItem);
            }
        }
        return dataItem.getName();
    }

}
