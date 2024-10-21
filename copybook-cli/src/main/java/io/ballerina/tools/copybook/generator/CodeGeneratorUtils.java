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
import java.util.List;

import static io.ballerina.tools.copybook.generator.AnnotationGenerator.generateIntConstraint;
import static io.ballerina.tools.copybook.generator.AnnotationGenerator.generateNumberConstraint;
import static io.ballerina.tools.copybook.generator.AnnotationGenerator.generateStringConstraint;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.ALPHA_NUMERIC_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.ARRAY_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.BALLERINA;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.BAL_EXTENSION;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.BAL_KEYWORDS;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.CONSTRAINT;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.DECIMAL;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.DECIMAL_POINT;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.ERROR;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.ESCAPE_PATTERN;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.IMPORT;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.INT;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.NEGATIVE_SIGN;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.POSITIVE_SIGN;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.SEMICOLON;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.SIGNED_DECIMAL_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.SIGNED_INTEGER_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.SINGLE_QUOTE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.SLASH;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.SPECIAL_CHAR_REGEX;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.STRING;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.UNSIGNED_DECIMAL_TYPE;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.UNSIGNED_INTEGER_TYPE;

public class CodeGeneratorUtils {

    public static final MinutiaeList SINGLE_WS_MINUTIAE = getSingleWSMinutiae();

    private CodeGeneratorUtils() {
    }

    public static TypeGenerator getTypeGenerator(CopybookNode schemaValue) {
        if (schemaValue.getOccurringCount() > 0) {
            return new ArrayTypeGenerator(schemaValue);
        }
        if (schemaValue instanceof DataItem dataItem) {
            return new ReferencedTypeGenerator(dataItem);
        }
        return new RecordTypeGenerator((GroupItem) schemaValue);
    }

    public static String getTypeReferenceName(CopybookNode copybookNode, boolean isRecordFieldReference) {
        if (copybookNode instanceof DataItem dataItem) {
            if (isRecordFieldReference) {
                return extractTypeReferenceName(dataItem);
            }
            if (dataItem.isNumeric()) {
                return dataItem.getFloatingPointLength() > 0 ? DECIMAL : INT;
            }
            return STRING;
        }
        return copybookNode.getName();
    }

    public static ImportDeclarationNode getImportDeclarationNode(String orgName, String moduleName) {
        Token importKeyword = AbstractNodeFactory.createIdentifierToken(IMPORT, SINGLE_WS_MINUTIAE, SINGLE_WS_MINUTIAE);
        Token orgNameToken = AbstractNodeFactory.createIdentifierToken(orgName);
        Token slashToken = AbstractNodeFactory.createIdentifierToken(SLASH);
        ImportOrgNameNode importOrgNameNode = NodeFactory.createImportOrgNameNode(orgNameToken, slashToken);
        Token moduleNameToken = AbstractNodeFactory.createIdentifierToken(moduleName);
        SeparatedNodeList<IdentifierToken> moduleNodes = AbstractNodeFactory.createSeparatedNodeList(moduleNameToken);
        Token semicolon = AbstractNodeFactory.createIdentifierToken(SEMICOLON);
        return NodeFactory.createImportDeclarationNode(importKeyword, importOrgNameNode, moduleNodes, null, semicolon);
    }

    public static NodeList<ImportDeclarationNode> createImportDeclarationNodes() {
        ImportDeclarationNode importForHttp = CodeGeneratorUtils.getImportDeclarationNode(BALLERINA, CONSTRAINT);
        return AbstractNodeFactory.createNodeList(List.of(importForHttp));
    }

    private static MinutiaeList getSingleWSMinutiae() {
        Minutiae whitespace = AbstractNodeFactory.createWhitespaceMinutiae(" ");
        return AbstractNodeFactory.createMinutiaeList(whitespace);
    }

    public static AnnotationNode generateConstraintNode(DataItem dataItem) {
        String ballerinaType = getConstraintType(dataItem);
        return switch (ballerinaType) {
            case DECIMAL -> generateNumberConstraint(dataItem);
            case INT -> generateIntConstraint(dataItem);
            default -> generateStringConstraint(dataItem);
        };
    }

    private static String getConstraintType(DataItem dataItem) {
        if (dataItem.isNumeric() && dataItem.getFloatingPointLength() > 0) {
            return DECIMAL;
        }
        return dataItem.isNumeric() ? INT : STRING;
    }

    public static String extractTypeReferenceName(DataItem dataItem) {
        String typeName;
        if (dataItem.isNumeric()) {
            if (dataItem.getFloatingPointLength() > 0) {
                int wholeNumberLength = dataItem.getReadLength() - dataItem.getFloatingPointLength();
                if (!hasImpliedDecimal(dataItem)) {
                    wholeNumberLength -= 1;
                }
                if (dataItem.getPicture().startsWith(NEGATIVE_SIGN)
                        || dataItem.getPicture().startsWith(POSITIVE_SIGN)) {
                    wholeNumberLength -= 1;
                }
                boolean isSignedDecimal = dataItem.isSinged() || dataItem.getPicture().startsWith(NEGATIVE_SIGN) ||
                        dataItem.getPicture().startsWith(POSITIVE_SIGN);
                typeName = isSignedDecimal ? SIGNED_DECIMAL_TYPE : UNSIGNED_DECIMAL_TYPE;
                typeName += wholeNumberLength + DECIMAL_POINT + dataItem.getFloatingPointLength();
            } else {
                typeName = dataItem.isSinged() ? SIGNED_INTEGER_TYPE : UNSIGNED_INTEGER_TYPE;
                typeName += dataItem.getReadLength();
            }
        } else {
            typeName = ALPHA_NUMERIC_TYPE + dataItem.getReadLength();
        }
        return dataItem.getOccurringCount() > 0 ? typeName + ARRAY_TYPE : typeName;
    }

    public static String getValidName(String identifier) {
        if (!identifier.matches("\\b[0-9]*\\b")) {
            String[] split = identifier.split(SPECIAL_CHAR_REGEX);
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
            return SINGLE_QUOTE + identifier;
        }
        boolean isValidIdentifier = identifier.matches("\\b[_a-zA-Z][_a-zA-Z0-9]*\\b");
        boolean isKeyword = BAL_KEYWORDS.contains(identifier);
        if (!isValidIdentifier || isKeyword) {
            if (ERROR.equals(identifier)) {
                return '_' + ERROR;
            }
            identifier = identifier.replaceAll(ESCAPE_PATTERN, "\\\\$1");
            if (identifier.endsWith("?")) {
                int length = identifier.length();
                if (length > 1 && identifier.charAt(length - 2) == '\\') {
                    identifier = identifier.substring(0, length - 2) + "?";
                }
                String strippedIdentifier = identifier.substring(0, length - 1);
                return BAL_KEYWORDS.contains(strippedIdentifier) ? SINGLE_QUOTE + identifier : identifier;
            }
        }
        return identifier;
    }

    public static String getFileName(String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
        fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        return String.join("", fileName, BAL_EXTENSION);
    }

    public static boolean hasImpliedDecimal(DataItem dataItem) {
        return dataItem.getPicture().contains(DECIMAL_POINT);
    }
}
