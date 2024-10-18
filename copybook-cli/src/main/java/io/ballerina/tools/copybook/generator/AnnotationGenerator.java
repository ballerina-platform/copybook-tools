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

import io.ballerina.compiler.syntax.tree.AnnotationNode;
import io.ballerina.compiler.syntax.tree.ExpressionNode;
import io.ballerina.compiler.syntax.tree.MappingConstructorExpressionNode;
import io.ballerina.compiler.syntax.tree.NodeFactory;
import io.ballerina.compiler.syntax.tree.NodeParser;
import io.ballerina.compiler.syntax.tree.SimpleNameReferenceNode;
import io.ballerina.lib.copybook.commons.schema.DataItem;

import java.util.ArrayList;
import java.util.List;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createIdentifierToken;
import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createToken;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createSimpleNameReferenceNode;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.AT_TOKEN;
import static io.ballerina.compiler.syntax.tree.SyntaxKind.MAPPING_CONSTRUCTOR;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.NEGATIVE_SIGN;

public class AnnotationGenerator {

    public static AnnotationNode generateStringConstraint(DataItem node) {
        List<String> fields = getStringAnnotFields(node);
        if (fields.isEmpty()) {
            return null;
        }
        String annotBody = GeneratorConstants.OPEN_BRACE + String.join(GeneratorConstants.COMMA, fields) +
                GeneratorConstants.CLOSE_BRACE;
        return createAnnotationNode(GeneratorConstants.CONSTRAINT_STRING, annotBody);
    }

    public static AnnotationNode generateIntConstraint(DataItem node) {
        List<String> fields = getIntAnnotFields(node);
        if (fields.isEmpty()) {
            return null;
        }
        String annotBody = GeneratorConstants.OPEN_BRACE + String.join(GeneratorConstants.COMMA, fields) +
                GeneratorConstants.CLOSE_BRACE;
        return createAnnotationNode(GeneratorConstants.CONSTRAINT_INT, annotBody);
    }

    public static AnnotationNode generateNumberConstraint(DataItem node) {
        List<String> fields = getNumberAnnotFields(node);
        if (fields.isEmpty()) {
            return null;
        }
        String annotBody = GeneratorConstants.OPEN_BRACE + String.join(GeneratorConstants.COMMA, fields) +
                GeneratorConstants.CLOSE_BRACE;
        return createAnnotationNode(GeneratorConstants.CONSTRAINT_NUMBER, annotBody);
    }

    private static List<String> getStringAnnotFields(DataItem node) {
        List<String> fields = new ArrayList<>();
        int value = node.getReadLength();
        String fieldRef = GeneratorConstants.MAX_LENGTH + GeneratorConstants.COLON + value;
        fields.add(fieldRef);
        return fields;
    }

    private static List<String> getIntAnnotFields(DataItem node) {
        List<String> fields = new ArrayList<>();
        if (!node.isSinged()) {
            int minValue = 0;
            String fieldRef = GeneratorConstants.MIN_VALUE + GeneratorConstants.COLON + minValue;
            fields.add(fieldRef);
        }
        int maxDigits = node.getReadLength();
        String fieldRef = GeneratorConstants.MAX_DIGITS + GeneratorConstants.COLON + maxDigits;
        fields.add(fieldRef);
        return fields;
    }

    private static List<String> getNumberAnnotFields(DataItem node) {
        List<String> fields = new ArrayList<>();
        int maxIntegerDigits;
        if (node.isSinged()) {
            maxIntegerDigits = node.getReadLength() - node.getFloatingPointLength() - 1;
        } else if (node.getPicture().startsWith(NEGATIVE_SIGN) ||
                node.getPicture().startsWith(GeneratorConstants.POSITIVE_SIGN)) {
            maxIntegerDigits = node.getReadLength() - node.getFloatingPointLength() - 2;
        } else {
            int minValue = 0;
            String fieldRef = GeneratorConstants.MIN_VALUE + GeneratorConstants.COLON + minValue;
            fields.add(fieldRef);
            maxIntegerDigits = node.getReadLength() - node.getFloatingPointLength() - 1;
        }
        if (CodeGeneratorUtils.hasImpliedDecimal(node)) {
            // The implied decimal point doesn't include a decimal separator,
            // so add +1 to account for the extra digit removed from the integer part in previous cases.
            maxIntegerDigits += 1;
        }
        int maxFractionDigits = node.getFloatingPointLength();
        String fieldRef = GeneratorConstants.MAX_INTEGER_DIGITS + GeneratorConstants.COLON + maxIntegerDigits;
        fields.add(fieldRef);
        fieldRef = GeneratorConstants.MAX_FRACTION_DIGITS + GeneratorConstants.COLON + maxFractionDigits;
        fields.add(fieldRef);
        return fields;
    }

    public static AnnotationNode createAnnotationNode(String annotationReference, String annotFields) {
        MappingConstructorExpressionNode annotationBody = null;
        SimpleNameReferenceNode annotReference =
                createSimpleNameReferenceNode(createIdentifierToken(annotationReference));
        ExpressionNode expressionNode = NodeParser.parseExpression(annotFields);
        if (expressionNode.kind() == MAPPING_CONSTRUCTOR) {
            annotationBody = (MappingConstructorExpressionNode) expressionNode;
        }
        return NodeFactory.createAnnotationNode(createToken(AT_TOKEN), annotReference, annotationBody);
    }
}
