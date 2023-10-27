package io.ballerina.tools.copybook.exception;

/**
 * Exception type definition for Copybook command related errors.
 */
public class CmdException extends Exception {
    private final String message;

    public CmdException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public CmdException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return getDiagnosticMessage();
    }

    public String getDiagnosticMessage() {
//        GraphqlDiagnostic graphqlDiagnostic = Utils.constructGraphqlDiagnostic(
//                DiagnosticMessages.GRAPHQL_CLI_100.getCode(),
//                this.message, DiagnosticSeverity.ERROR, null);
        return "Invalid input flag";
    }
}
