package io.ballerina.tools.copybook.generator;

import io.ballerina.lib.copybook.commons.schema.CopyBook;
import io.ballerina.lib.copybook.commons.schema.Schema;
import io.ballerina.tools.copybook.diagnostic.DiagnosticMessages;
import io.ballerina.tools.copybook.exception.CopybookTypeGenerationException;
import org.ballerinalang.formatter.core.FormatterException;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;

import static io.ballerina.tools.copybook.generator.CodeGeneratorUtils.getFileName;
import static io.ballerina.tools.copybook.utils.Utils.createOutputDirectory;
import static io.ballerina.tools.copybook.utils.Utils.resolveSchemaFileName;
import static io.ballerina.tools.copybook.utils.Utils.writeFile;

public abstract class CodeGenerator {

    protected CodeGenerator() {

    }

    public static void generate(Path cbFilePath, Path targetOutputPath, PrintStream outStream)
            throws CopybookTypeGenerationException, FormatterException, IOException {

        Schema schema = CopyBook.parse(cbFilePath.toString());
        if (!schema.getErrors().isEmpty()) {
            List<String> errors = schema.getErrors();
            throw new CopybookTypeGenerationException(DiagnosticMessages.COPYBOOK_TYPE_GEN_102, null,
                    errors.toArray(new String[0]));
        }
        boolean isCreated = createOutputDirectory(targetOutputPath);
        if (!isCreated) {
            throw new CopybookTypeGenerationException(DiagnosticMessages.COPYBOOK_TYPE_GEN_103, null,
                    targetOutputPath.toString());
        }
        CopybookTypeGenerator codeGenerator = new CopybookTypeGenerator(schema);
        String src = codeGenerator.generateSourceCode();
        String fileName = getFileName(cbFilePath.toString());
        String resolvedFileName = resolveSchemaFileName(targetOutputPath, fileName);
        writeFile(targetOutputPath.resolve(resolvedFileName), src);
        outStream.println("Ballerina record types generated successfully and copied to :");
        outStream.println("-- " + resolvedFileName);
    }
}
