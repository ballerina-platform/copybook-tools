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

import io.ballerina.compiler.syntax.tree.SyntaxInfo;

import java.util.List;

public class GeneratorConstants {

    private GeneratorConstants() {
    }

    public static final String SPECIAL_CHAR_REGEX = "([\\[\\]\\\\?!<>@#&~`*\\=^+();:\\/\\_{}\\s|.$])";
    public static final String ESCAPE_PATTERN = "([\\[\\]\\\\?!<>@#&~`*\\-=^+();:\\/\\_{}\\s|.$])";

    public static final List<String> BAL_KEYWORDS = SyntaxInfo.keywords();
    public static final String BALLERINA = "ballerina";
    public static final String CONSTRAINT = "constraint";

    // Identifiers
    public static final String MAX_DIGITS = "maxDigits";
    public static final String MAX_LENGTH = "maxLength";
    public static final String MIN_VALUE = "minValue";
    public static final String MAX_INTEGER_DIGITS = "maxIntegerDigits";
    public static final String MAX_FRACTION_DIGITS = "maxFractionDigits";
    public static final String COLON = ":";
    public static final String OPEN_BRACE = "{";
    public static final String CLOSE_BRACE = "}";
    public static final String COMMA = ",";
    public static final String CONSTRAINT_STRING = "constraint:String";
    public static final String CONSTRAINT_NUMBER = "constraint:Number";
    public static final String CONSTRAINT_INT = "constraint:Int";

    public static final String SLASH = "/";
    public static final String IMPORT = "import";
    public static final String SEMICOLON = ";";
    public static final String PERIOD = ".";

    // Copybook types
    public static final String SIGNED_DECIMAL_TYPE = "SignedDecimal";
    public static final String UNSIGNED_DECIMAL_TYPE = "UnsignedDecimal";
    public static final String DECIMAL_TYPE = "Decimal";
    public static final String SIGNED_INTEGER_TYPE = "SignedInteger";
    public static final String UNSIGNED_INTEGER_TYPE = "UnsignedInteger";
    public static final String ALPHA_NUMERIC_TYPE = "AlphaNumeric";
    public static final String INTEGER_IN_BINARY_TYPE = "IntegerInBinary";
    public static final String ARRAY_TYPE = "Array";
    public static final String DECIMAL_POINT = "V";
    public static final String NEGATIVE_DECIMAL_PIC = "-9";
    public static final String POSITIVE_DECIMAL_PIC = "+9";
    public static final String COMP_PIC = "COMP";

    // Ballerina types
    public static final String STRING = "string";
    public static final String INT = "int";
    public static final String DECIMAL = "decimal";
    public static final String BYTE_ARRAY = "byte[]";

    // File extensions
    public static final String COPYBOOK_EXTENSION = ".cpy";
    public static final String COBOL_EXTENSION = ".cob";
    public static final String BAL_EXTENSION = ".bal";
}
