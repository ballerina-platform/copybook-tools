package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;
import io.ballerina.copybook.parser.schema.DataItem;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createIdentifierToken;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createSimpleNameReferenceNode;

public class ReferencedTypeGenerator extends TypeGenerator {

    DataItem fieldSchema;

    public ReferencedTypeGenerator(DataItem fieldSchema) {

        this.fieldSchema = fieldSchema;
    }

    /**
     * Generate TypeDescriptorNode for referenced schemas.
     */
    @Override
    public TypeDescriptorNode generateTypeDescriptorNode() {

        String extractName = extractTypeReferenceName(fieldSchema);
        String typeName = CodeGeneratorUtils.getValidName(extractName);
        return createSimpleNameReferenceNode(createIdentifierToken(typeName));
    }

    private String extractTypeReferenceName(DataItem dataItem) {

        String typeName = null;
        if (dataItem.isNumeric()) {
            // handle numeric types
            if (dataItem.getFloatingPointLength() > 0) {
                if (dataItem.isSinged()) {
                    typeName = "SignedFloat" + (dataItem.getReadLength() - dataItem.getFloatingPointLength() - 2) +
                            dataItem.getFloatingPointLength();
                } else {
                    typeName = "Float" + (dataItem.getReadLength() - dataItem.getFloatingPointLength() - 2) +
                            dataItem.getFloatingPointLength();
                }
            } else {
                if (dataItem.isSinged()) {
                    typeName = "SignedInteger" + dataItem.getReadLength();
                } else {
                    typeName = "Integer" + dataItem.getReadLength();
                }
            }
        } else if (dataItem.getPicture().contains("COMP")) {
            // TODO: re-write the logic to handle binary values
            typeName = "IntegerInBinary";
        } else {
            // handle alphanumeric types
            typeName = "AlphaNumeric" + dataItem.getReadLength();
        }
        if (dataItem.getOccurs() > 0) {
            typeName = typeName + "Array" + dataItem.getOccurs();
        }
        return typeName;
    }

}
