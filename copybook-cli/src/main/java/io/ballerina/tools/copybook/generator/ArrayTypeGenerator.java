package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.ArrayDimensionNode;
import io.ballerina.compiler.syntax.tree.SyntaxKind;
import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;
import io.ballerina.copybook.parser.schema.GroupItem;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createIdentifierToken;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createNodeList;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createToken;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createArrayDimensionNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createArrayTypeDescriptorNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createSimpleNameReferenceNode;

public class ArrayTypeGenerator extends TypeGenerator {
    GroupItem groupItemNode;
    public ArrayTypeGenerator(GroupItem groupItemNode) {
        this.groupItemNode = groupItemNode;
    }

    /**
     * @return 
     */
    @Override public TypeDescriptorNode generateTypeDescriptorNode(boolean isRecordFieldReference)  {
        ArrayDimensionNode arrayDimension =
                createArrayDimensionNode(createToken(SyntaxKind.OPEN_BRACKET_TOKEN), null,
                        createToken(SyntaxKind.CLOSE_BRACKET_TOKEN));
        TypeDescriptorNode wrappedType = createSimpleNameReferenceNode(createIdentifierToken(groupItemNode.getName()));
        return createArrayTypeDescriptorNode(wrappedType, createNodeList(arrayDimension));
    }
}
