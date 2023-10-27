package io.ballerina.tools.copybook.cmd;

import io.ballerina.cli.BLauncherCmd;
import io.ballerina.tools.copybook.exception.CmdException;
import io.ballerina.tools.copybook.exception.CodeGenerationException;
import io.ballerina.tools.copybook.generator.CopybookTypeGenerator;
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

import static java.lang.System.out;

/**
 * Main class to implement "copybook" command for Ballerina.
 * Commands for Type generation.
 */
@CommandLine.Command(name = "copybook", description = "Generates Ballerina record types for copybooks definition")
public class CopybookCmd  implements BLauncherCmd {
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

    /**
     * Constructor that initialize with the default values.
     */
    public CopybookCmd() {
        this(System.err, Paths.get(System.getProperty("user.dir")), true);
    }

    /**
     * Constructor override, which takes output stream and execution dir and exits when finish as inputs.
     *
     * @param outStream      output stream from ballerina
     * @param executionDir   defines the directory location of  execution of ballerina command
     * @param exitWhenFinish exit when finish the execution
     */
    public CopybookCmd(PrintStream outStream, Path executionDir, boolean exitWhenFinish) {
        this.outStream = outStream;
        this.executionPath = executionDir;
        this.exitWhenFinish = exitWhenFinish;
    }

    /**
     * Exit with error code 1.
     *
     * @param exit Whether to exit or not.
     */
    private static void exitError(boolean exit) {
        if (exit) {
            Runtime.getRuntime().exit(1);
        }
    }
    @Override
    public void printUsage(StringBuilder stringBuilder) {
    }

    @Override
    public void setParentCmdParser(CommandLine parentCmdParser) {
    }

    @Override
    public void execute() {
        outStream.println("Generate ballerina types from a copybook definition file");
        try {
            if (helpFlag) {
                String commandUsageInfo = BLauncherCmd.getCommandUsageInfo(getName());
                outStream.println(commandUsageInfo);
                exitError(this.exitWhenFinish);
            }
            validateInputFlags();
            executeOperation();
        } catch (CmdException | CodeGenerationException e) {
            outStream.println(e.getMessage());
            exitError(this.exitWhenFinish);
        }
    }


    private void validateInputFlags() throws CmdException {
        // Check if CLI input path flag argument is present
        if (inputPathFlag) {
            // Check if Copybook definition file is provided
            if (argList == null) {
                throw new CmdException("MESSAGE_FOR_MISSING_INPUT_ARGUMENT");
            }
        } else {
            String commandUsageInfo = BLauncherCmd.getCommandUsageInfo(getName());
            outStream.println(commandUsageInfo);
            exitError(this.exitWhenFinish);
        }

        String filePath = argList.get(0);
        if (!validInputFileExtension(filePath)) {
            throw new CmdException("MESSAGE_FOR_INVALID_FILE_EXTENSION");
        }
    }

    private void executeOperation() throws CodeGenerationException, CmdException {
        String filePath = argList.get(0);
        generateType(filePath);
    }

    private void generateType(String filePath) throws CmdException, CodeGenerationException {
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
        CopybookTypeGenerator.generate(copybookFilePath, getTargetOutputPath(), outStream);
    }

    /**
     * Gets the target output path for the code generation.
     *
     * @return the target output path for the code generation
     */
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

    @Override
    public String getName() {
        return CMD_NAME;
    }

    @Override
    public void printLongDesc(StringBuilder stringBuilder) {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("ballerina-copybook.help");
        try (InputStreamReader inputStreamREader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(inputStreamREader)) {
            String content = br.readLine();
            out.append(content);
            while ((content = br.readLine()) != null) {
                out.append('\n').append(content);
            }
        } catch (IOException ignore) {
        }
    }
}
