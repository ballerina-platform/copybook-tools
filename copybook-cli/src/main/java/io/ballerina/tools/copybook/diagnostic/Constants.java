package io.ballerina.tools.copybook.diagnostic;

public class Constants {

    public static final String MESSAGE_FOR_INVALID_FILE_EXTENSION = "File \"%s\" is invalid. Copybook tool support" +
            " only the Copybook definition files with .cpy or .cbl extension. %nPlease provide the path of the input " +
            " file with -i or --input flag.%ne.g: bal graphql --input <GraphQL configuration file>";
    public static final String MESSAGE_CAN_NOT_READ_COPYBOOK_FILE =
            "Provided Schema file \"%s\" is not allowed to be read";
    public static final String MESSAGE_FOR_INVALID_COPYBOOK_PATH =
            "The Copybook definition file does not exist in the given path";
}
