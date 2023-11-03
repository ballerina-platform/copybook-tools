package io.ballerina.tools.copybook.generator;

import io.ballerina.lib.copybook.commons.schema.CopyBook;
import io.ballerina.lib.copybook.commons.schema.Schema;
import io.ballerina.tools.copybook.diagnostic.DiagnosticMessages;
import io.ballerina.tools.copybook.exception.CopybookTypeGenerationException;
import org.ballerinalang.formatter.core.FormatterException;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

import static io.ballerina.tools.copybook.utils.Utils.createOutputDirectory;
import static io.ballerina.tools.copybook.utils.Utils.resolveSchemaFileName;
import static io.ballerina.tools.copybook.utils.Utils.writeFile;

public abstract class CodeGenerator {

    protected CodeGenerator() {

    }

    public static void generate(Path cbFilePath, String rootName, Path targetOutputPath, PrintStream outStream)
            throws CopybookTypeGenerationException, FormatterException, IOException {

        Schema schema = CopyBook.parse(cbFilePath.toString());
        boolean isCreated = createOutputDirectory(targetOutputPath);
        if (!isCreated) {
            throw new CopybookTypeGenerationException(DiagnosticMessages.COPYBOOK_TYPE_GEN_103, null,
                    targetOutputPath.toString());
        }
        CopybookTypeGenerator codeGenerator = new CopybookTypeGenerator(schema);
        String src = codeGenerator.generateSourceCode(rootName);
        String fileName = resolveSchemaFileName(targetOutputPath, rootName);
        writeFile(targetOutputPath.resolve(fileName), src);
        outStream.println("Ballerina record types generated successfully and copied to :");
        outStream.println("-- " + fileName);
    }
}
