package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.TypeDefinitionNode;
import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;
import io.ballerina.lib.copybook.commons.schema.DataItem;

import java.util.List;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createIdentifierToken;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createSimpleNameReferenceNode;
import static io.ballerina.tools.copybook.generator.CodeGeneratorUtils.getTypeReferenceName;
import static io.ballerina.tools.copybook.generator.CopybookTypeGenerator.addToFieldTypeDefinitionList;
import static io.ballerina.tools.copybook.generator.CopybookTypeGenerator.generateFieldTypeDefNode;

public class ReferencedTypeGenerator extends TypeGenerator {

    DataItem fieldSchema;

    public ReferencedTypeGenerator(DataItem fieldSchema) {

        this.fieldSchema = fieldSchema;
    }

    /**
     * Generate TypeDescriptorNode for referenced schemas.
     */
    @Override
    public TypeDescriptorNode generateTypeDescriptorNode(List<TypeDefinitionNode> typeDefList) {

        String extractName = getTypeReferenceName(fieldSchema, true);
        String typeName = CodeGeneratorUtils.getValidName(extractName);
        TypeDefinitionNode fieldType = generateFieldTypeDefNode(fieldSchema, getTypeReferenceName(fieldSchema, false));
        addToFieldTypeDefinitionList(fieldType, typeDefList);
        return createSimpleNameReferenceNode(createIdentifierToken(typeName));
    }
}
