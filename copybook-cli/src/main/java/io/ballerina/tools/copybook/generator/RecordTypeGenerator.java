/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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

    @Override
    public TypeDescriptorNode generateTypeDescriptorNode(List<TypeDefinitionNode> typeDefList) {
        List<CopybookNode> fields = groupItemNode.getChildren();
        List<Node> recordFields = getFieldNodes(fields, typeDefList);
        NodeList<Node> fieldNodes = AbstractNodeFactory.createNodeList(recordFields);
        return NodeFactory.createRecordTypeDescriptorNode(createToken(RECORD_KEYWORD),
                createToken(OPEN_BRACE_TOKEN), fieldNodes, null, createToken(CLOSE_BRACE_TOKEN));
    }

    private List<Node> getFieldNodes(List<CopybookNode> fields, List<TypeDefinitionNode> typeDefList) {
        List<Node> recordFieldList = new ArrayList<>();
        for (CopybookNode field : fields) {
            String fieldName = CodeGeneratorUtils.escapeIdentifier(field.getName().trim());
            IdentifierToken fieldNameIdentifier = AbstractNodeFactory.createIdentifierToken(fieldName);
            TypeGenerator typeGenerator = CodeGeneratorUtils.getTypeGenerator(field);
            TypeDescriptorNode typeDescriptorNode = typeGenerator.generateTypeDescriptorNode(typeDefList);
            RecordFieldNode recordFieldNode = NodeFactory.createRecordFieldNode(null, null,
                    typeDescriptorNode, fieldNameIdentifier, createToken(QUESTION_MARK_TOKEN),
                    createToken(SEMICOLON_TOKEN));
            recordFieldList.add(recordFieldNode);
        }
        return recordFieldList;
    }
}
