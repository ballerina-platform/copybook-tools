package io.ballerina.tools.copybook.cmd;

import io.ballerina.cli.BLauncherCmd;
import io.ballerina.tools.copybook.exception.CmdException;
import io.ballerina.tools.copybook.exception.CodeGenerationException;
import io.ballerina.tools.copybook.generator.CodeGenerator;
import org.ballerinalang.formatter.core.FormatterException;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
                String commandUsageInfo = BLauncherCmd.getCommandUsageInfo(getName());
                outStream.println(commandUsageInfo);
                exitError(this.exitWhenFinish);
            }
            validateInputFlags();
            executeOperation();
        } catch (CmdException | CodeGenerationException | FormatterException e) {
            outStream.println(e.getMessage());
            exitError(this.exitWhenFinish);
        }
    }

    private void validateInputFlags() throws CmdException {
        if (inputPathFlag) {
            if (argList == null) {
                throw new CmdException("MESSAGE_FOR_MISSING_INPUT_ARGUMENT");
            }
        } else {
            getCommandUsageInfo();
            exitError(this.exitWhenFinish);
        }

        String filePath = argList.get(0);
        if (!validInputFileExtension(filePath)) {
            throw new CmdException("MESSAGE_FOR_INVALID_FILE_EXTENSION");
        }
    }

    private void executeOperation() throws CodeGenerationException, CmdException, FormatterException {
        String filePath = argList.get(0);
        generateType(filePath);
    }

    private void generateType(String filePath) throws CmdException, CodeGenerationException, FormatterException {
        final File copybookFile = new File(filePath);
        if (!copybookFile.exists()) {
            throw new CmdException("MESSAGE_MISSING_BAL_FILE");
        }
        if (!copybookFile.canRead()) {
            throw new CmdException("MESSAGE_CANNOT_READ_BAL_FILE");
        }
        Path copybookFilePath = null;
        try {
            copybookFilePath = Paths.get(copybookFile.getCanonicalPath());
        } catch (IOException e) {
            throw new CmdException(e.toString());
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
        return filePath.endsWith("cpy");
    }

    private void getCommandUsageInfo() {
        String builder = "ballerina-copybook - Generate Ballerina types for given cobol copybook definitions\n\n" +
                "bal copybook [-i | --input] <copybook-definition-file-path>\n" +
                "                    [-o | --output] <output-location>\n\n";
        outStream.println(builder);
    }

    @Override
    public void printUsage(StringBuilder stringBuilder) {
        stringBuilder.append("Ballerina Copybook tools - Code generation\n");
        stringBuilder.append(
                "Ballerina code generation for Copybook schema: bal copybook -i <copybook definition file path>\n");
    }

    @Override
    public void setParentCmdParser(CommandLine parentCmdParser) {}

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public void printLongDesc(StringBuilder stringBuilder) {
    }
}
