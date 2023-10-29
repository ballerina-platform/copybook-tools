package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.SyntaxInfo;

import java.util.List;

public class GeneratorConstants {

    public static final String SPECIAL_CHAR_REGEX = "([\\[\\]\\\\?!<>@#&~`*\\-=^+();:\\/\\_{}\\s|.$])";
    public static final String ESCAPE_PATTERN = "([\\[\\]\\\\?!<>@#&~`*\\-=^+();:\\/\\_{}\\s|.$])";

    public static final List<String> BAL_KEYWORDS = SyntaxInfo.keywords();
    public static final String BALLERINA = "ballerina";
    public static final String CONSTRAINT = "constraint";
}
