package io.ballerina.tools.copybook.exception;

public class CodeGenerationException extends Exception {

    private final String message;

    public CodeGenerationException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public CodeGenerationException(String message) {
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
        return "Code generation failed";
    }
}
