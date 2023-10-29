package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.AbstractNodeFactory;
import io.ballerina.compiler.syntax.tree.AnnotationNode;
import io.ballerina.compiler.syntax.tree.IdentifierToken;
import io.ballerina.compiler.syntax.tree.ImportDeclarationNode;
import io.ballerina.compiler.syntax.tree.ImportOrgNameNode;
import io.ballerina.compiler.syntax.tree.Minutiae;
import io.ballerina.compiler.syntax.tree.MinutiaeList;
import io.ballerina.compiler.syntax.tree.Node;
import io.ballerina.compiler.syntax.tree.NodeFactory;
import io.ballerina.compiler.syntax.tree.NodeList;
import io.ballerina.compiler.syntax.tree.SeparatedNodeList;
import io.ballerina.compiler.syntax.tree.Token;
import io.ballerina.copybook.parser.schema.DataItem;
import io.ballerina.copybook.parser.schema.GroupItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static io.ballerina.tools.copybook.generator.GeneratorConstants.BAL_KEYWORDS;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.ESCAPE_PATTERN;

public class CodeGeneratorUtils {

    public static final MinutiaeList SINGLE_WS_MINUTIAE = getSingleWSMinutiae();

    public static TypeGenerator getTypeGenerator(io.ballerina.copybook.parser.schema.Node schemaValue,
                                                 String typeName) {
        if (schemaValue instanceof DataItem) {
            return new ReferencedTypeGenerator((DataItem) schemaValue);
        } else if (schemaValue instanceof GroupItem) {
            return new RecordTypeGenerator((GroupItem) schemaValue, typeName);
        }
        return null;
    }

    public static ImportDeclarationNode getImportDeclarationNode(String orgName, String moduleName) {

        Token importKeyword = AbstractNodeFactory.createIdentifierToken("import", SINGLE_WS_MINUTIAE,
                SINGLE_WS_MINUTIAE);
        Token orgNameToken = AbstractNodeFactory.createIdentifierToken(orgName);
        Token slashToken = AbstractNodeFactory.createIdentifierToken("/");
        ImportOrgNameNode importOrgNameNode = NodeFactory.createImportOrgNameNode(orgNameToken, slashToken);
        Token moduleNameToken = AbstractNodeFactory.createIdentifierToken(moduleName);
        SeparatedNodeList<IdentifierToken> moduleNodeList = AbstractNodeFactory.createSeparatedNodeList(
                moduleNameToken);
        Token semicolon = AbstractNodeFactory.createIdentifierToken(";");

        return NodeFactory.createImportDeclarationNode(importKeyword, importOrgNameNode,
                moduleNodeList, null, semicolon);
    }

    /**
     * This util function is for generating the import node for http module.
     */
    public static NodeList<ImportDeclarationNode> createImportDeclarationNodes() {

        List<ImportDeclarationNode> imports = new ArrayList<>();
        ImportDeclarationNode importForHttp = CodeGeneratorUtils.getImportDeclarationNode(GeneratorConstants.BALLERINA,
                GeneratorConstants.CONSTRAINT);
        imports.add(importForHttp);
        return AbstractNodeFactory.createNodeList(imports);
    }

    private static MinutiaeList getSingleWSMinutiae() {

        Minutiae whitespace = AbstractNodeFactory.createWhitespaceMinutiae(" ");
        return AbstractNodeFactory.createMinutiaeList(whitespace);
    }

    public static void getRecordDocs(List<Node> documentation, List<AnnotationNode> typeAnnotations) {

    }

    public static AnnotationNode generateConstraintNode(String typeName) {
//        if (isConstraintAllowed(typeName, fieldSchema)) {
//            String ballerinaType = convertOpenAPITypeToBallerina(fieldSchema);
//            // For openAPI field schemas having 'string' type, constraints generation will be skipped when
//            // the counterpart Ballerina type is non-string (e.g. for string schemas with format 'binary' or 'byte',
//            // the counterpart ballerina type is 'byte[]', hence any string constraints cannot be applied)
//            if (ballerinaType.equals(STRING)) {
//                // Attributes : maxLength, minLength
//                return generateStringConstraint(fieldSchema);
//            } else if (ballerinaType.equals(DECIMAL) || ballerinaType.equals(FLOAT) || ballerinaType.equals(INT) ||
//                    ballerinaType.equals(INT_SIGNED32)) {
//                // Attribute : minimum, maximum, exclusiveMinimum, exclusiveMaximum
//                return generateNumberConstraint(fieldSchema);
//            } else if (GeneratorUtils.isArraySchema(fieldSchema)) {
//                // Attributes: maxItems, minItems
//                return generateArrayConstraint(fieldSchema);
//            }
//            // Ignore Object, Map and Composed schemas.
//            return null;
//        }
        return null;
    }

    //    public static String getValidName(String identifier, boolean isSchema) {
//        //For the flatten enable we need to remove first Part of valid name check
//        // this - > !identifier.matches("\\b[a-zA-Z][a-zA-Z0-9]*\\b") &&
//        if (!identifier.matches("\\b[0-9]*\\b")) {
//            String[] split = identifier.split(GeneratorConstants.ESCAPE_PATTERN);
//            StringBuilder validName = new StringBuilder();
//            for (String part : split) {
//                if (!part.isBlank()) {
//                    if (split.length > 1) {
//                        part = part.substring(0, 1).toUpperCase(Locale.ENGLISH) +
//                                part.substring(1).toLowerCase(Locale.ENGLISH);
//                    }
//                    validName.append(part);
//                }
//            }
//            identifier = validName.toString();
//        }
//        if (isSchema) {
//            return identifier.substring(0, 1).toUpperCase(Locale.ENGLISH) + identifier.substring(1);
//        } else {
//            return escapeIdentifier(identifier.substring(0, 1).toLowerCase(Locale.ENGLISH) + identifier.substring(1));
//        }
//    }
    public static List<Node> getFieldApiDocs() {

        List<Node> schemaDoc = new ArrayList<>();
//    if (field.getDescription() != null) {
//        schemaDoc.addAll(DocCommentsGenerator.createAPIDescriptionDoc(
//                field.getDescription(), false));
//    } else if (field.get$ref() != null) {
//        String[] split = field.get$ref().trim().split("/");
//        String componentName = GeneratorUtils.getValidName(split[split.length - 1], true);
//        OpenAPI openAPI = GeneratorMetaData.getInstance().getOpenAPI();
//        if (openAPI.getComponents().getSchemas().get(componentName) != null) {
//            Schema<?> schema = openAPI.getComponents().getSchemas().get(componentName);
//            if (schema.getDescription() != null) {
//                schemaDoc.addAll(DocCommentsGenerator.createAPIDescriptionDoc(
//                        schema.getDescription(), false));
//            }
//        }
//    }
        return schemaDoc;
    }

    /**
     * This method will return a valid name for records.
     *
     * @param identifier identifier or method name
     * @return valid name for variables
     */
    public static String getValidName(String identifier) {
        // To enable flatten we need to remove first Part of valid name check
        // this - > !identifier.matches("\\b[a-zA-Z][a-zA-Z0-9]*\\b") &&
        if (!identifier.matches("\\b[0-9]*\\b")) {
            String[] split = identifier.split(GeneratorConstants.SPECIAL_CHAR_REGEX);
            StringBuilder validName = new StringBuilder();
            for (String part : split) {
                if (!part.isBlank()) {
                    if (split.length > 1) {
                        part = part.substring(0, 1).toUpperCase(Locale.ENGLISH) +
                                part.substring(1).toLowerCase(Locale.ENGLISH);
                    }
                    validName.append(part);
                }
            }
            identifier = validName.toString();
        }
        return identifier.substring(0, 1).toLowerCase(Locale.ENGLISH) + identifier.substring(1);
    }

    public static String escapeIdentifier(String identifier) {

        if (identifier.matches("\\b[0-9]*\\b")) {
            return "'" + identifier;
        } else if (!identifier.matches("\\b[_a-zA-Z][_a-zA-Z0-9]*\\b")
                || BAL_KEYWORDS.stream().anyMatch(identifier::equals)) {

            // TODO: Remove this `if`. Refer - https://github.com/ballerina-platform/ballerina-lang/issues/23045
            if (identifier.equals("error")) {
                identifier = "_error";
            } else {
                identifier = identifier.replaceAll(ESCAPE_PATTERN, "\\\\$1");
                if (identifier.endsWith("?")) {
                    if (identifier.charAt(identifier.length() - 2) == '\\') {
                        StringBuilder stringBuilder = new StringBuilder(identifier);
                        stringBuilder.deleteCharAt(identifier.length() - 2);
                        identifier = stringBuilder.toString();
                    }
                    if (BAL_KEYWORDS.stream().anyMatch(Optional.ofNullable(identifier)
                            .filter(sStr -> sStr.length() != 0)
                            .map(sStr -> sStr.substring(0, sStr.length() - 1))
                            .orElse(identifier)::equals)) {
                        identifier = "'" + identifier;
                    } else {
                        return identifier;
                    }
                } else {
                    identifier = "'" + identifier;
                }
            }
        }
        return identifier;
    }

}
