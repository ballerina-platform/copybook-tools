package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.SyntaxInfo;

import java.util.List;

public class GeneratorConstants {

    public static final String SPECIAL_CHAR_REGEX = "([\\[\\]\\\\?!<>@#&~`*\\=^+();:\\/\\_{}\\s|.$])";
    public static final String ESCAPE_PATTERN = "([\\[\\]\\\\?!<>@#&~`*\\-=^+();:\\/\\_{}\\s|.$])";

    public static final List<String> BAL_KEYWORDS = SyntaxInfo.keywords();
    public static final String BALLERINA = "ballerina";
    public static final String CONSTRAINT = "constraint";

    // Identifiers
    public static final String STRING = "string";
    public static final String INT = "int";
    public static final String DECIMAL = "decimal";
    public static final String MAX_DIGITS = "maxDigits";
    public static final String MAX_LENGTH = "maxLength";
    public static final String MIN_VALUE = "minValue";
    public static final String MAX_VALUE_EXCLUSIVE = "maxValueExclusive";
    public static final String MIN_VALUE_EXCLUSIVE = "minValueExclusive";
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
    public static final String NEGATIVE_DECIMAL_TYPE = "NegativeDecimal";
    public static final String DECIMAL_TYPE = "Decimal";
    public static final String SIGNED_INTEGER_TYPE = "SignedInteger";
    public static final String INTEGER_TYPE = "Integer";
    public static final String ALPHA_NUMERIC_TYPE = "AlphaNumeric";
    public static final String INTEGER_IN_BINARY_TYPE = "IntegerInBinary";
    public static final String ARRAY_TYPE = "Array";
    public static final String FLOATING_POINT = "V";
    public static final String COMP = "COMP";

    // File extensions
    public static final String COPYBOOK_EXTENSION = ".cpy";
    public static final String COBOL_EXTENSION = ".cob";
}
