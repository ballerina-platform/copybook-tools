package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.AbstractNodeFactory;
import io.ballerina.compiler.syntax.tree.AnnotationNode;
import io.ballerina.compiler.syntax.tree.IdentifierToken;
import io.ballerina.compiler.syntax.tree.ImportDeclarationNode;
import io.ballerina.compiler.syntax.tree.ImportOrgNameNode;
import io.ballerina.compiler.syntax.tree.Minutiae;
import io.ballerina.compiler.syntax.tree.MinutiaeList;
import io.ballerina.compiler.syntax.tree.NodeFactory;
import io.ballerina.compiler.syntax.tree.NodeList;
import io.ballerina.compiler.syntax.tree.SeparatedNodeList;
import io.ballerina.compiler.syntax.tree.Token;
import io.ballerina.lib.copybook.commons.schema.CopybookNode;
import io.ballerina.lib.copybook.commons.schema.DataItem;
import io.ballerina.lib.copybook.commons.schema.GroupItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.ballerina.tools.copybook.generator.AnnotationGenerator.generateIntConstraint;
import static io.ballerina.tools.copybook.generator.AnnotationGenerator.generateNumberConstraint;
import static io.ballerina.tools.copybook.generator.AnnotationGenerator.generateStringConstraint;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.ALPHA_NUMERIC_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.ARRAY_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.BAL_EXTENSION;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.BAL_KEYWORDS;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.BYTE_ARRAY;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.COMP_PIC;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.DECIMAL;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.DECIMAL_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.ESCAPE_PATTERN;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.FLOATING_POINT;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.IMPORT;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.INT;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.INTEGER_IN_BINARY_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.INTEGER_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.NEGATIVE_DECIMAL_PIC;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.NEGATIVE_DECIMAL_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.SEMICOLON;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.SIGNED_DECIMAL_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.SIGNED_INTEGER_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.SLASH;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.STRING;

public class CodeGeneratorUtils {

    public static final MinutiaeList SINGLE_WS_MINUTIAE = getSingleWSMinutiae();

    public static TypeGenerator getTypeGenerator(CopybookNode schemaValue) {

        if (schemaValue.getOccurringCount() > 0) {
            return new ArrayTypeGenerator(schemaValue);
        } else if (schemaValue instanceof DataItem dataItem) {
            return new ReferencedTypeGenerator(dataItem);
        } else {
            return new RecordTypeGenerator((GroupItem) schemaValue);
        }
    }

    public static String getTypeReferenceName(CopybookNode copybookNode, boolean isRecordFieldReference) {
        if (copybookNode instanceof DataItem dataItem) {
            if (isRecordFieldReference) {
                return extractTypeReferenceName(dataItem);
            } else {
                if (dataItem.isNumeric()) {
                    if (dataItem.getFloatingPointLength() > 0) {
                        return DECIMAL;
                    }
                    return INT;
                } else if (dataItem.getPicture().contains(COMP_PIC)) {
                    return BYTE_ARRAY;
                } else {
                    return STRING;
                }
            }
        }
        return copybookNode.getName();
    }

    public static ImportDeclarationNode getImportDeclarationNode(String orgName, String moduleName) {

        Token importKeyword = AbstractNodeFactory.createIdentifierToken(IMPORT, SINGLE_WS_MINUTIAE,
                SINGLE_WS_MINUTIAE);
        Token orgNameToken = AbstractNodeFactory.createIdentifierToken(orgName);
        Token slashToken = AbstractNodeFactory.createIdentifierToken(SLASH);
        ImportOrgNameNode importOrgNameNode = NodeFactory.createImportOrgNameNode(orgNameToken, slashToken);
        Token moduleNameToken = AbstractNodeFactory.createIdentifierToken(moduleName);
        SeparatedNodeList<IdentifierToken> moduleNodeList = AbstractNodeFactory.createSeparatedNodeList(
                moduleNameToken);
        Token semicolon = AbstractNodeFactory.createIdentifierToken(SEMICOLON);
        return NodeFactory.createImportDeclarationNode(importKeyword, importOrgNameNode,
                moduleNodeList, null, semicolon);
    }

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

    public static AnnotationNode generateConstraintNode(DataItem dataItem) {

        String ballerinaType = getConstraintType(dataItem);
        if (ballerinaType.equals(DECIMAL)) {
            return generateNumberConstraint(dataItem);
        } else if (ballerinaType.equals(INT)) {
            return generateIntConstraint(dataItem);
        } else {
            return generateStringConstraint(dataItem);
        }
    }

    private static String getConstraintType(DataItem dataItem) {

        if (dataItem.isNumeric() && dataItem.getFloatingPointLength() > 0) {
            return DECIMAL;
        } else if (dataItem.isNumeric()) {
            return INT;
        }
        return STRING;
    }

    public static String extractTypeReferenceName(DataItem dataItem) {

        String typeName = null;
        if (dataItem.isNumeric()) {
            if (dataItem.getFloatingPointLength() > 0) {
                if (dataItem.isSinged()) {
                    typeName = SIGNED_DECIMAL_TYPE + (dataItem.getReadLength() - dataItem.getFloatingPointLength() - 2)
                            + FLOATING_POINT + dataItem.getFloatingPointLength();
                } else {
                    if (dataItem.getPicture().startsWith(NEGATIVE_DECIMAL_PIC)) {
                        typeName = NEGATIVE_DECIMAL_TYPE +
                                (dataItem.getReadLength() - dataItem.getFloatingPointLength() - 2)
                                + FLOATING_POINT + dataItem.getFloatingPointLength();
                    } else {
                        typeName = DECIMAL_TYPE + (dataItem.getReadLength() - dataItem.getFloatingPointLength() - 1) +
                                FLOATING_POINT + dataItem.getFloatingPointLength();
                    }
                }
            } else {
                if (dataItem.isSinged()) {
                    typeName = SIGNED_INTEGER_TYPE + dataItem.getReadLength();
                } else {
                    typeName = INTEGER_TYPE + dataItem.getReadLength();
                }
            }
        } else if (dataItem.getPicture().contains(COMP_PIC)) {
            // TODO: implement the binary values handling part
            typeName = INTEGER_IN_BINARY_TYPE;
        } else {
            typeName = ALPHA_NUMERIC_TYPE + dataItem.getReadLength();
        }
        if (dataItem.getOccurringCount() > 0) {
            typeName = typeName + ARRAY_TYPE;
        }
        return typeName;
    }

    public static String getValidName(String identifier) {

        if (!identifier.matches("\\b[0-9]*\\b")) {
            String[] split = identifier.split(GeneratorConstants.SPECIAL_CHAR_REGEX);
            StringBuilder validName = new StringBuilder();
            for (String part : split) {
                if (!part.isBlank()) {
                    validName.append(part);
                }
            }
            identifier = validName.toString();
        }
        return escapeIdentifier(identifier);
    }

    public static String escapeIdentifier(String identifier) {

        if (identifier.matches("\\b[0-9]*\\b")) {
            return "'" + identifier;
        } else if (!identifier.matches("\\b[_a-zA-Z][_a-zA-Z0-9]*\\b")
                || BAL_KEYWORDS.stream().anyMatch(identifier::equals)) {
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
                }
            }
        }
        return identifier;
    }

    public static String getFileName(String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
        fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        return String.join("", fileName, BAL_EXTENSION);
    }

}
