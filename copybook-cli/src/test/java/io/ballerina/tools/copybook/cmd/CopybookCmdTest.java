package io.ballerina.tools.copybook.cmd;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import picocli.CommandLine;

import java.io.IOException;

public class CopybookCmdTest extends CopybookTest {
    @BeforeTest(description = "This will create a new ballerina project for testing below scenarios.")
    public void setupBallerinaProject() throws IOException {
        super.setup();
    }

    @Test(description = "Test copybook command with help flag")
    public void testOpenAPICmdHelp() throws IOException {
        String[] args = {"-h"};
        CopybookCmd copybookCmd = new CopybookCmd(printStream, tmpDir, false);
        new CommandLine(copybookCmd).parseArgs(args);
        copybookCmd.execute();
        String output = readOutput(true);
        Assert.assertTrue(output.contains("Generate ballerina types from a copybook definition file"));
    }
}
