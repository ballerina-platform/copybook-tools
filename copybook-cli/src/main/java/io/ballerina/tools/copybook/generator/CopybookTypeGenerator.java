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
import io.ballerina.copybook.parser.schema.DataItem;
import io.ballerina.copybook.parser.schema.GroupItem;
import io.ballerina.copybook.parser.schema.Node;
import io.ballerina.copybook.parser.schema.Schema;
import io.ballerina.tools.text.TextDocument;
import io.ballerina.tools.text.TextDocuments;
import org.ballerinalang.formatter.core.Formatter;
import org.ballerinalang.formatter.core.FormatterException;

import java.util.ArrayList;
import java.util.List;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createNodeList;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createToken;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createMetadataNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createTypeDefinitionNode;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.PUBLIC_KEYWORD;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.SEMICOLON_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.TYPE_KEYWORD;
import static io.ballerina.tools.copybook.generator.CodeGeneratorUtils.createImportDeclarationNodes;
import static io.ballerina.tools.copybook.generator.CodeGeneratorUtils.extractTypeReferenceName;

public class CopybookTypeGenerator {

    private final Schema schema;
    private final List<TypeDefinitionNode> fieldTypeDefinitionList;

    public CopybookTypeGenerator(Schema schema) {

        this.schema = schema;
        this.fieldTypeDefinitionList = new ArrayList<>();
    }

    public String generateSourceCode() throws FormatterException {

        List<Node> typeDefinitions = schema.getTypeDefinitions();
        List<TypeDefinitionNode> typeDefinitionList = new ArrayList<>();
        typeDefinitions.forEach(typeDefinition -> {
            generateTypeDefNodes(typeDefinition, typeDefinitionList);
        });
        fieldTypeDefinitionList.addAll(typeDefinitionList);
        String generatedSyntaxTree = Formatter.format(generateSyntaxTree()).toString();
        String src = Formatter.format(generatedSyntaxTree);
        return src;
    }

    public void generateTypeDefNodes(Node typeDefinition, List<TypeDefinitionNode> typeDefinitions) {

        if (typeDefinition instanceof GroupItem) {
            List<TypeDefinitionNode> typeDefNodesOfFields = new ArrayList<>();
            for (Node node : ((GroupItem) typeDefinition).getChildren()) {
                generateTypeDefNodes(node, typeDefNodesOfFields);
            }
            TypeDefinitionNode typeNode = generateTypeDefNode(typeDefinition, true);
            typeDefinitions.add(typeNode);
            addToTypeDefinitionList(typeDefNodesOfFields, typeDefinitions);
        } else {
            addToFieldTypeDefinitionList(generateTypeDefNode(typeDefinition, false));
        }
    }

    public TypeDefinitionNode generateTypeDefNode(Node node, boolean isRecordFieldReference) {

        IdentifierToken typeName;
        List<AnnotationNode> typeAnnotations = new ArrayList<>();
        MetadataNode metadataNode = null;
        if (node instanceof DataItem) {
            typeName = AbstractNodeFactory.createIdentifierToken(CodeGeneratorUtils.getValidName(
                    extractTypeReferenceName((DataItem) node)));
            typeAnnotations.add(CodeGeneratorUtils.generateConstraintNode((DataItem) node));
            metadataNode = createMetadataNode(null, createNodeList(typeAnnotations));
        } else {
            typeName = AbstractNodeFactory.createIdentifierToken(CodeGeneratorUtils.getValidName(
                    node.getName().trim()));
        }
        TypeDescriptorNode typeDescriptorNode = getTypeDescriptorNode(node, isRecordFieldReference);
        return createTypeDefinitionNode(metadataNode, createToken(PUBLIC_KEYWORD), createToken(TYPE_KEYWORD),
                typeName, typeDescriptorNode, createToken(SEMICOLON_TOKEN));
    }

    private TypeDescriptorNode getTypeDescriptorNode(Node node, boolean isRecordFieldReference) {

        TypeGenerator typeGenerator = CodeGeneratorUtils.getTypeGenerator(node, node.getName());
        return typeGenerator.generateTypeDescriptorNode(isRecordFieldReference);
    }

    public SyntaxTree generateSyntaxTree() {

        NodeList<ImportDeclarationNode> imports = createImportDeclarationNodes();
        NodeList<ModuleMemberDeclarationNode> moduleMembers = AbstractNodeFactory.createNodeList(
                fieldTypeDefinitionList.toArray(new TypeDefinitionNode[0]));
        Token eofToken = AbstractNodeFactory.createIdentifierToken("");
        ModulePartNode modulePartNode = NodeFactory.createModulePartNode(imports, moduleMembers, eofToken);
        TextDocument textDocument = TextDocuments.from("");
        SyntaxTree syntaxTree = SyntaxTree.from(textDocument);
        return syntaxTree.modifyWith(modulePartNode);
    }

    private void addToFieldTypeDefinitionList(TypeDefinitionNode node) {

        boolean isExist = fieldTypeDefinitionList.stream().anyMatch(typeDefinitionNode ->
                typeDefinitionNode.typeName().toString().equals(node.typeName().toString()));
        if (!isExist) {
            fieldTypeDefinitionList.add(node);
        }
    }

    private void addToTypeDefinitionList(List<TypeDefinitionNode> nodeList,
                                         List<TypeDefinitionNode> typeDefinitionList) {

        for (TypeDefinitionNode node : nodeList) {
            boolean isExist = typeDefinitionList.stream().anyMatch(typeDefinitionNode ->
                    typeDefinitionNode.typeName().toString().equals(node.typeName().toString()));
            if (!isExist) {
                typeDefinitionList.add(node);
            }
        }
    }
}
