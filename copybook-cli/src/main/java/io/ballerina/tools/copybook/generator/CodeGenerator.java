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
