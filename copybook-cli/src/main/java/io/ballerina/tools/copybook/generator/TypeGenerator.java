package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;

public abstract class TypeGenerator {

    public abstract TypeDescriptorNode generateTypeDescriptorNode(boolean isRecordFieldReference);

}
