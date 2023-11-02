package io.ballerina.tools.copybook.generator;

import io.ballerina.copybook.parser.schema.CopyBookErrorListener;
import io.ballerina.copybook.parser.schema.CopyBookLexer;
import io.ballerina.copybook.parser.schema.CopyBookParser;
import io.ballerina.copybook.parser.schema.Schema;
import io.ballerina.copybook.parser.schema.SchemaBuilder;
import io.ballerina.tools.copybook.exception.CopybookTypeGenerationException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.ballerinalang.formatter.core.FormatterException;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

import static io.ballerina.copybook.parser.schema.CopyBookPreprocessor.chopCopybookColumn;
import static io.ballerina.tools.copybook.utils.Utils.createOutputDirectory;
import static io.ballerina.tools.copybook.utils.Utils.resolveSchemaFileName;
import static io.ballerina.tools.copybook.utils.Utils.writeFile;

/**
 * This class implements the GraphQL code generator tool.
 */
public abstract class CodeGenerator {

    protected CodeGenerator() {
    }

    public static void generate(Path cbFilePath, String rootName, Path targetOutputPath, PrintStream outStream)
            throws CopybookTypeGenerationException, FormatterException {
        int columnsToChop = 6;
        String copyBookString;
        try {
            copyBookString = chopCopybookColumn(cbFilePath.toString(), columnsToChop).toString();
        } catch (IOException e) {
            return; // TODO: fix this
        }
        CopyBookLexer lexer = new CopyBookLexer(CharStreams.fromString(copyBookString));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CopyBookParser parser = new CopyBookParser(tokens);
        parser.removeErrorListeners();
        CopyBookErrorListener errorListener = new CopyBookErrorListener();
        parser.addErrorListener(errorListener);
        CopyBookParser.StartRuleContext startRule = parser.startRule();
        SchemaBuilder visitor = new SchemaBuilder();
        startRule.accept(visitor);
        Schema schema = visitor.getSchema();

        CopybookTypeGenerator codeGenerator = new CopybookTypeGenerator(schema);
        String src = codeGenerator.generateSourceCode(rootName);
        String fileName = resolveSchemaFileName(targetOutputPath, rootName);
        createOutputDirectory(targetOutputPath);
        writeFile(targetOutputPath.resolve(fileName), src);
        outStream.println("Ballerina record types generated successfully and copied to :");
        outStream.println("-- " + fileName);
    }
}
