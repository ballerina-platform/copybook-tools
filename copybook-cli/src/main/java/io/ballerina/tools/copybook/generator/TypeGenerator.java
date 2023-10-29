package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.AnnotationNode;
import io.ballerina.compiler.syntax.tree.IdentifierToken;
import io.ballerina.compiler.syntax.tree.Node;
import io.ballerina.compiler.syntax.tree.TypeDefinitionNode;
import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;

import java.util.List;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createToken;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createTypeDefinitionNode;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.PUBLIC_KEYWORD;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.SEMICOLON_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.TYPE_KEYWORD;

public abstract class TypeGenerator {

    public TypeDefinitionNode generateTypeDefinitionNode(IdentifierToken typeName, List<Node> schemaDoc,
                                                         List<AnnotationNode> typeAnnotations) {

//        for (AnnotationNode annotation : typeAnnotations) {
//            String annotationRef = annotation.annotReference().toString();
//            if (annotationRef.startsWith(CONSTRAINT) && !nullable) {
//            ImportDeclarationNode constraintImport = GeneratorUtils.getImportDeclarationNode(BALLERINA, CONSTRAINT);
//                //Here we are unable to add ImportDeclarationNode since newly generated node has different hashcode.
//                imports.add(constraintImport.toSourceCode());
//            }
//        }

//        MarkdownDocumentationNode documentationNode = createMarkdownDocumentationNode(createNodeList(schemaDoc));
//        MetadataNode metadataNode = createMetadataNode(documentationNode, createNodeList(typeAnnotations));
        return createTypeDefinitionNode(null, createToken(PUBLIC_KEYWORD), createToken(TYPE_KEYWORD),
                typeName, generateTypeDescriptorNode(), createToken(SEMICOLON_TOKEN));
    }

    public abstract TypeDescriptorNode generateTypeDescriptorNode();

}
