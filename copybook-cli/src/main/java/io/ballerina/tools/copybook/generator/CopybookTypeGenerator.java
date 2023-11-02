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
import io.ballerina.compiler.syntax.tree.RecordFieldNode;
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
import java.util.LinkedList;
import java.util.List;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createIdentifierToken;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createNodeList;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createToken;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createMetadataNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createSimpleNameReferenceNode;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createTypeDefinitionNode;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.CLOSE_BRACE_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.OPEN_BRACE_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.PUBLIC_KEYWORD;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.QUESTION_MARK_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.RECORD_KEYWORD;
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

    public String generateSourceCode(String rootName) throws FormatterException {

        List<Node> typeDefinitions = schema.getTypeDefinitions();
        List<TypeDefinitionNode> typeDefinitionList = new ArrayList<>();
        typeDefinitions.forEach(typeDefinition -> {
            typeDefinitionList.add(generateTypeDefNodes(typeDefinition));
        });
        fieldTypeDefinitionList.add(generateRootType(rootName, typeDefinitions));
        fieldTypeDefinitionList.addAll(typeDefinitionList);
        String generatedSyntaxTree = Formatter.format(generateSyntaxTree()).toString();
        String src = Formatter.format(generatedSyntaxTree);
        return src;
    }

    private TypeDefinitionNode generateRootType(String rootName, List<Node> typeDefinitions) {
        List<io.ballerina.compiler.syntax.tree.Node> recordFields = new LinkedList<>();
        recordFields.addAll(addRecordFields(typeDefinitions));
        NodeList<io.ballerina.compiler.syntax.tree.Node> fieldNodes =
                AbstractNodeFactory.createNodeList(recordFields);
        TypeDescriptorNode typeDescriptorNode = NodeFactory.createRecordTypeDescriptorNode(createToken(RECORD_KEYWORD),
                createToken(OPEN_BRACE_TOKEN), fieldNodes, null, createToken(CLOSE_BRACE_TOKEN));
        MetadataNode metadataNode = null;
        IdentifierToken typeName = AbstractNodeFactory.createIdentifierToken(CodeGeneratorUtils.getValidName(rootName));
        return createTypeDefinitionNode(metadataNode, createToken(PUBLIC_KEYWORD), createToken(TYPE_KEYWORD),
                typeName, typeDescriptorNode, createToken(SEMICOLON_TOKEN));
    }

    public List<io.ballerina.compiler.syntax.tree.Node> addRecordFields(List<Node> fields) {

        List<io.ballerina.compiler.syntax.tree.Node> recordFieldList = new ArrayList<>();
        for (Node field : fields) {
            String fieldNameStr = CodeGeneratorUtils.escapeIdentifier(field.getName().trim());
            IdentifierToken fieldName = AbstractNodeFactory.createIdentifierToken(fieldNameStr);
            TypeDescriptorNode typeDescriptorNode = createSimpleNameReferenceNode(createIdentifierToken(fieldNameStr));
            RecordFieldNode recordFieldNode = NodeFactory.createRecordFieldNode(null, null,
                    typeDescriptorNode, fieldName, createToken(QUESTION_MARK_TOKEN), createToken(SEMICOLON_TOKEN));
            recordFieldList.add(recordFieldNode);
        }
        return recordFieldList;
    }

    public TypeDefinitionNode generateTypeDefNodes(Node typeDefinition) {
        if (typeDefinition instanceof GroupItem) {
            return generateTypeDefNode(typeDefinition, true);
        } else {
            // TODO: add a if else and return exception from the else block
            return generateTypeDefNode(typeDefinition, false);
        }
    }

    public TypeDefinitionNode generateTypeDefNode(Node node, boolean isRecordFieldReference) {
        MetadataNode metadataNode = null;
        IdentifierToken typeName = AbstractNodeFactory.createIdentifierToken(CodeGeneratorUtils.getValidName(
                    node.getName().trim()));
        TypeDescriptorNode typeDescriptorNode = getTypeDescriptorNode(node, isRecordFieldReference);
        return createTypeDefinitionNode(metadataNode, createToken(PUBLIC_KEYWORD), createToken(TYPE_KEYWORD),
                typeName, typeDescriptorNode, createToken(SEMICOLON_TOKEN));
    }

    public static TypeDefinitionNode generateFieldTypeDefNode(DataItem node, String extractedTypeName) {
        IdentifierToken typeName;
        List<AnnotationNode> typeAnnotations = new ArrayList<>();
        MetadataNode metadataNode = null;
        typeName = AbstractNodeFactory.createIdentifierToken(CodeGeneratorUtils.getValidName(
                extractTypeReferenceName(node)));
        typeAnnotations.add(CodeGeneratorUtils.generateConstraintNode(node));
        metadataNode = createMetadataNode(null, createNodeList(typeAnnotations));
        TypeDescriptorNode typeDescriptorNode = createSimpleNameReferenceNode(createIdentifierToken(extractedTypeName));
        return createTypeDefinitionNode(metadataNode, createToken(PUBLIC_KEYWORD), createToken(TYPE_KEYWORD),
                typeName, typeDescriptorNode, createToken(SEMICOLON_TOKEN));
    }

    private TypeDescriptorNode getTypeDescriptorNode(Node node, boolean isRecordFieldReference) {

        TypeGenerator typeGenerator = CodeGeneratorUtils.getTypeGenerator(node);
        return typeGenerator.generateTypeDescriptorNode(fieldTypeDefinitionList, isRecordFieldReference);
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

    public static void addToFieldTypeDefinitionList(TypeDefinitionNode node, List<TypeDefinitionNode> typeDefList) {

        boolean isExist = typeDefList.stream().anyMatch(typeDefinitionNode ->
                typeDefinitionNode.typeName().toString().equals(node.typeName().toString()));
        if (!isExist) {
            typeDefList.add(node);
        }
    }
}
