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
import io.ballerina.compiler.syntax.tree.AnnotationNode;
import io.ballerina.compiler.syntax.tree.IdentifierToken;
import io.ballerina.compiler.syntax.tree.ImportDeclarationNode;
import io.ballerina.compiler.syntax.tree.MetadataNode;
import io.ballerina.compiler.syntax.tree.ModuleMemberDeclarationNode;
import io.ballerina.compiler.syntax.tree.ModulePartNode;
import io.ballerina.compiler.syntax.tree.NodeFactory;
import io.ballerina.compiler.syntax.tree.NodeList;
import io.ballerina.compiler.syntax.tree.SyntaxTree;
import io.ballerina.compiler.syntax.tree.Token;
import io.ballerina.compiler.syntax.tree.TypeDefinitionNode;
import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;
import io.ballerina.lib.copybook.commons.schema.CopybookNode;
import io.ballerina.lib.copybook.commons.schema.DataItem;
import io.ballerina.lib.copybook.commons.schema.Schema;
import io.ballerina.tools.text.TextDocument;
import io.ballerina.tools.text.TextDocuments;
import org.ballerinalang.formatter.core.Formatter;
import org.ballerinalang.formatter.core.FormatterException;

import java.util.ArrayList;
import java.util.List;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createIdentifierToken;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createNodeList;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createToken;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createMetadataNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createSimpleNameReferenceNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createTypeDefinitionNode;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.PUBLIC_KEYWORD;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.SEMICOLON_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.TYPE_KEYWORD;
import static io.ballerina.tools.copybook.generator.CodeGeneratorUtils.createImportDeclarationNodes;
import static io.ballerina.tools.copybook.generator.CodeGeneratorUtils.extractTypeReferenceName;

public class CopybookTypeGenerator {

    private final Schema schema;
    private final List<TypeDefinitionNode> fieldTypeDefinitionList = new ArrayList<>();

    public CopybookTypeGenerator(Schema schema) {
        this.schema = schema;
    }

    public String generateSourceCode() throws FormatterException {
        List<CopybookNode> typeDefinitions = schema.getTypeDefinitions();
        List<TypeDefinitionNode> typeDefinitionList  = typeDefinitions.stream().map(this::generateTypeDefNode).toList();
        fieldTypeDefinitionList.addAll(typeDefinitionList);
        String generatedSyntaxTree = Formatter.format(generateSyntaxTree()).toString();
        return Formatter.format(generatedSyntaxTree);
    }

    public TypeDefinitionNode generateTypeDefNode(CopybookNode node) {
        IdentifierToken typeName = AbstractNodeFactory.createIdentifierToken(CodeGeneratorUtils.getValidName(
                node.getName().trim()));
        TypeDescriptorNode typeDescriptorNode = getTypeDescriptorNode(node);
        return createTypeDefinitionNode(null, createToken(PUBLIC_KEYWORD), createToken(TYPE_KEYWORD),
                typeName, typeDescriptorNode, createToken(SEMICOLON_TOKEN));
    }

    public static TypeDefinitionNode generateFieldTypeDefNode(DataItem node, String extractedTypeName) {
        IdentifierToken typeName = AbstractNodeFactory.createIdentifierToken(CodeGeneratorUtils.getValidName(
                extractTypeReferenceName(node)));
        List<AnnotationNode> typeAnnotations = List.of(CodeGeneratorUtils.generateConstraintNode(node));
        MetadataNode metadataNode = createMetadataNode(null, createNodeList(typeAnnotations));
        TypeDescriptorNode typeDescriptorNode = createSimpleNameReferenceNode(createIdentifierToken(extractedTypeName));
        return createTypeDefinitionNode(metadataNode, createToken(PUBLIC_KEYWORD), createToken(TYPE_KEYWORD),
                typeName, typeDescriptorNode, createToken(SEMICOLON_TOKEN));
    }

    private TypeDescriptorNode getTypeDescriptorNode(CopybookNode node) {
        TypeGenerator typeGenerator = CodeGeneratorUtils.getTypeGenerator(node);
        return typeGenerator.generateTypeDescriptorNode(fieldTypeDefinitionList);
    }

    private SyntaxTree generateSyntaxTree() {
        NodeList<ImportDeclarationNode> imports = createImportDeclarationNodes();
        NodeList<ModuleMemberDeclarationNode> moduleMembers = AbstractNodeFactory.createNodeList(
                fieldTypeDefinitionList.toArray(new TypeDefinitionNode[0]));
        Token eofToken = AbstractNodeFactory.createIdentifierToken("");
        ModulePartNode modulePartNode = NodeFactory.createModulePartNode(imports, moduleMembers, eofToken);
        TextDocument textDocument = TextDocuments.from("");
        SyntaxTree syntaxTree = SyntaxTree.from(textDocument);
        return syntaxTree.modifyWith(modulePartNode);
    }

    public static void addToFieldTypeDefinitionList(TypeDefinitionNode node, List<TypeDefinitionNode> typeDefList) {
        boolean isExist = typeDefList.stream().anyMatch(typeDefinitionNode ->
                typeDefinitionNode.typeName().text().equals(node.typeName().text()));
        if (!isExist) {
            typeDefList.add(node);
        }
    }
}
