package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.TypeDefinitionNode;
import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;

import java.util.List;

public abstract class TypeGenerator {

    public abstract TypeDescriptorNode generateTypeDescriptorNode(List<TypeDefinitionNode> typeDefList);

}
