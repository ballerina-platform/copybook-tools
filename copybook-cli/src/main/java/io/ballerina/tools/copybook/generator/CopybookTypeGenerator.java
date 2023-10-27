package io.ballerina.tools.copybook.generator;

import io.ballerina.copybook.parser.schema.Schema;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.tools.copybook.exception.CodeGenerationException;

import java.io.PrintStream;
import java.nio.file.Path;

import static io.ballerina.copybook.parser.convertor.Utils.parseSchemaFile;

public class CopybookTypeGenerator extends CodeGenerator {

    public static void generate(Path cbFilePath, Path targetOutputPath, PrintStream outStream)
            throws CodeGenerationException {

        BObject cbSchema = parseSchemaFile(StringUtils.fromString(cbFilePath.toString()));
        Schema schema = (Schema) cbSchema.getNativeData("native");
        outStream.println(schema.toString());
//        final List<Node> typeDefinitions = schema.getTypeDefinitions();
    }
}
