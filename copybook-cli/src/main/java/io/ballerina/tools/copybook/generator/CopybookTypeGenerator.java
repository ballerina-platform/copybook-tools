package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.AbstractNodeFactory;
import io.ballerina.compiler.syntax.tree.AnnotationNode;
import io.ballerina.compiler.syntax.tree.IdentifierToken;
import io.ballerina.compiler.syntax.tree.ImportDeclarationNode;
import io.ballerina.compiler.syntax.tree.ModuleMemberDeclarationNode;
import io.ballerina.compiler.syntax.tree.ModulePartNode;
import io.ballerina.compiler.syntax.tree.NodeFactory;
import io.ballerina.compiler.syntax.tree.NodeList;
import io.ballerina.compiler.syntax.tree.SyntaxTree;
import io.ballerina.compiler.syntax.tree.Token;
import io.ballerina.compiler.syntax.tree.TypeDefinitionNode;
import io.ballerina.copybook.parser.schema.GroupItem;
import io.ballerina.copybook.parser.schema.Node;
import io.ballerina.copybook.parser.schema.Schema;
import io.ballerina.tools.text.TextDocument;
import io.ballerina.tools.text.TextDocuments;

import java.util.ArrayList;
import java.util.List;

import static io.ballerina.tools.copybook.generator.CodeGeneratorUtils.createImportDeclarationNodes;

public class CopybookTypeGenerator {
    private final Schema schema;
    private final List<TypeDefinitionNode> typeDefinitionNodeList;
    private final List<GroupItem> groupItems = new ArrayList<>();

    public CopybookTypeGenerator(Schema schema) {
        this.schema = schema;
        this.typeDefinitionNodeList = new ArrayList<>();
    }

    public SyntaxTree generateSyntaxTree() {
        NodeList<ImportDeclarationNode> imports = createImportDeclarationNodes();
        List<TypeDefinitionNode> typeDefinitionNodeListForSchema = new ArrayList<>();
        List<Node> typeDefinitions = schema.getTypeDefinitions();
        typeDefinitions.forEach(typeDefinition -> {
            TypeDefinitionNode typeDefinitionNode = getTypeDefinitionNode((GroupItem) typeDefinition);
            typeDefinitionNodeListForSchema.add(typeDefinitionNode);
        });
        typeDefinitionNodeList.addAll(typeDefinitionNodeListForSchema);
        // Create module member declaration
        NodeList<ModuleMemberDeclarationNode> moduleMembers = AbstractNodeFactory.createNodeList(
                typeDefinitionNodeList.toArray(new TypeDefinitionNode[typeDefinitionNodeList.size()]));

        Token eofToken = AbstractNodeFactory.createIdentifierToken("");
        ModulePartNode modulePartNode = NodeFactory.createModulePartNode(imports, moduleMembers, eofToken);

        TextDocument textDocument = TextDocuments.from("");
        SyntaxTree syntaxTree = SyntaxTree.from(textDocument);
        return syntaxTree.modifyWith(modulePartNode);

    }

    public TypeDefinitionNode getTypeDefinitionNode(GroupItem typeDefinition) {
        String typeName = typeDefinition.getName();
        IdentifierToken typeNameToken = AbstractNodeFactory.createIdentifierToken(CodeGeneratorUtils.getValidName(
                typeName.trim()));
        RecordTypeGenerator typeGenerator = new RecordTypeGenerator(typeDefinition, typeName);
        List<AnnotationNode> typeAnnotations = new ArrayList<>();
        AnnotationNode constraintNode = CodeGeneratorUtils.generateConstraintNode(typeName);
        if (constraintNode != null) {
            typeAnnotations.add(constraintNode);
        }
        return typeGenerator.generateTypeDefinitionNode(typeNameToken, null, typeAnnotations);
    }
}
