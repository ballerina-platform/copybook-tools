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

package io.ballerina.tools.copybook.cmd;

import io.ballerina.cli.BLauncherCmd;
import io.ballerina.tools.copybook.diagnostic.Constants;
import io.ballerina.tools.copybook.diagnostic.DiagnosticMessages;
import io.ballerina.tools.copybook.exception.CmdException;
import io.ballerina.tools.copybook.exception.CopybookTypeGenerationException;
import io.ballerina.tools.copybook.generator.CodeGenerator;
import org.ballerinalang.formatter.core.FormatterException;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static io.ballerina.tools.copybook.generator.GeneratorConstants.COBOL_EXTENSION;
import static io.ballerina.tools.copybook.generator.GeneratorConstants.COPYBOOK_EXTENSION;

@CommandLine.Command(
        name = "copybook",
        description = "Generates Ballerina record types for copybooks definition"
)
public class CopybookCmd implements BLauncherCmd {

    private static final String CMD_NAME = "copybook";
    private final PrintStream outStream;
    private final boolean exitWhenFinish;
    private Path executionPath = Paths.get(System.getProperty("user.dir"));

    @CommandLine.Option(names = {"-h", "--help"}, hidden = true)
    private boolean helpFlag;

    @CommandLine.Option(names = {"-i", "--input"},
            description = "File path to the Copybook definition file")
    private boolean inputPathFlag;

    @CommandLine.Option(names = {"-o", "--output"},
            description = "Directory to store the generated Ballerina record types")
    private String outputPath;

    @CommandLine.Parameters
    private List<String> argList;

    public CopybookCmd() {
        this(System.err, Paths.get(System.getProperty("user.dir")), true);
    }

    public CopybookCmd(PrintStream outStream, Path executionDir, boolean exitWhenFinish) {

        this.outStream = outStream;
        this.executionPath = executionDir;
        this.exitWhenFinish = exitWhenFinish;
    }

    private static void exitError(boolean exit) {

        if (exit) {
            Runtime.getRuntime().exit(1);
        }
    }

    @Override
    public void execute() {

        try {
            if (helpFlag) {
                printLongDesc(new StringBuilder());
                outStream.flush();
                exitError(this.exitWhenFinish);
                return;
            }
            validateInputFlags();
            executeOperation();
        } catch (CmdException | CopybookTypeGenerationException | FormatterException | IOException e) {
            outStream.println(e.getMessage());
            exitError(this.exitWhenFinish);
        }
    }

    private void validateInputFlags() throws CmdException, IOException {

        if (inputPathFlag) {
            if (argList == null) {
                throw new CmdException(DiagnosticMessages.COPYBOOK_TYPE_GEN_100, null);
            }
        } else {
            getCommandUsageInfo();
            exitError(this.exitWhenFinish);
            return;
        }

        String filePath = argList.get(0);
        if (!validInputFileExtension(filePath)) {
            throw new CmdException(DiagnosticMessages.COPYBOOK_TYPE_GEN_102, null,
                    String.format(Constants.MESSAGE_FOR_INVALID_FILE_EXTENSION, filePath));
        }
    }

    private void executeOperation() throws CopybookTypeGenerationException, CmdException, FormatterException,
            IOException {
        String filePath = argList.get(0);
        generateType(filePath);
    }

    private void generateType(String filePath)
            throws CmdException, CopybookTypeGenerationException, FormatterException, IOException {

        final File copybookFile = new File(filePath);
        if (!copybookFile.exists()) {
            throw new CmdException(DiagnosticMessages.COPYBOOK_TYPE_GEN_102, null,
                    String.format(Constants.MESSAGE_FOR_INVALID_COPYBOOK_PATH, filePath));
        }
        if (!copybookFile.canRead()) {
            throw new CmdException(DiagnosticMessages.COPYBOOK_TYPE_GEN_102, null,
                    String.format(Constants.MESSAGE_CAN_NOT_READ_COPYBOOK_FILE, filePath));
        }
        Path copybookFilePath = null;
        try {
            copybookFilePath = Paths.get(copybookFile.getCanonicalPath());
        } catch (IOException e) {
            throw new CmdException(DiagnosticMessages.COPYBOOK_TYPE_GEN_102, null, e.toString());
        }
        CodeGenerator.generate(copybookFilePath, getTargetOutputPath(), outStream);
    }

    private Path getTargetOutputPath() {

        Path targetOutputPath = executionPath;
        if (this.outputPath != null) {
            if (Paths.get(outputPath).isAbsolute()) {
                targetOutputPath = Paths.get(outputPath);
            } else {
                targetOutputPath = Paths.get(targetOutputPath.toString(), outputPath);
            }
        }
        return targetOutputPath;
    }

    private boolean validInputFileExtension(String filePath) {
        return filePath.endsWith(COPYBOOK_EXTENSION) || filePath.endsWith(COBOL_EXTENSION);
    }

    private void getCommandUsageInfo() throws IOException {
        String builder = "ballerina-copybook - Generate Ballerina types for given cobol copybook \n" +
                "definitions\n\n" +
                "bal copybook [-i | --input] <copybook-definition-file-path>\n" +
                "             [-o | --output] <output-location>\n\n";
        outStream.println(builder);
    }

    @Override
    public void printUsage(StringBuilder stringBuilder) {
        stringBuilder.append("Ballerina Copybook tools - Code generation\n");
        stringBuilder.append(
                "Ballerina code generation for Copybook schema: bal copybook -i <copybook definition file path>\n");
    }

    @Override
    public void setParentCmdParser(CommandLine parentCmdParser) {
    }

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public void printLongDesc(StringBuilder stringBuilder) {
        Class<CopybookCmd> cmdClass = CopybookCmd.class;
        ClassLoader classLoader = cmdClass.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("ballerina-copybook.help");
        try (InputStreamReader inputStreamREader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(inputStreamREader)) {
            String content = br.readLine();
            outStream.append(content);
            while ((content = br.readLine()) != null) {
                outStream.append('\n').append(content);
            }
        } catch (IOException ignore) {
        }
    }
}
