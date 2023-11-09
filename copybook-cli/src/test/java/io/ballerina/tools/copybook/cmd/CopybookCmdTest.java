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

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.ballerina.tools.copybook.cmd.TestUtils.assertStringsWithoutWhiteSpace;
import static io.ballerina.tools.copybook.cmd.TestUtils.readContentWithFormat;

public class CopybookCmdTest extends CopybookTest {

    @BeforeTest(description = "This will create a new ballerina project for testing below scenarios.")
    public void setupBallerinaProject() throws IOException {
        super.setup();
    }

    @Test(description = "Test copybook command with help flag")
    public void testCopybookHelpFlag() throws IOException {
        String[] args = {"-h"};
        CopybookCmd copybookCmd = new CopybookCmd(printStream, tmpDir, false);
        new CommandLine(copybookCmd).parseArgs(args);
        copybookCmd.execute();
        String output = readOutput(true);
        String message = "ballerina-copybook - Generate Ballerina types for given cobol copybook definitions";
        assertStringsWithoutWhiteSpace(output, message);
    }

    @Test(description = "Test copybook command without arguments")
    public void testCopybookCmdUsage() throws IOException {
        String[] args = {""};
        CopybookCmd copybookCmd = new CopybookCmd(printStream, tmpDir, false);
        new CommandLine(copybookCmd).parseArgs(args);
        copybookCmd.execute();
        String output = readOutput(true);
        String message = "ballerina-copybook - Generate Ballerina types for given cobol copybook definitions bal" +
                "copybook [-i | --input] <copybook-definition-file-path> [-o | --output] <output-location>";
        assertStringsWithoutWhiteSpace(output, message);
    }

    @Test(description = "Test copybook command with invalid file extension")
    public void testCopybookWithInvalidFileExtension() throws IOException {
        String[] args = {"-i", resourceDir.resolve("expectedGenTypes/copybook.bal").toString()};
        CopybookCmd copybookCmd = new CopybookCmd(printStream, tmpDir, false);
        new CommandLine(copybookCmd).parseArgs(args);
        copybookCmd.execute();
        String output = readOutput(true);
        String message = "Copybook tool support only the Copybook definition files with .cpy or .cob extension." +
                "Please provide the path of the input  file with -i or --input flag.";
        assertStringsWithoutWhiteSpace(output, message);
    }

    @Test(description = "Test copybook command with invalid file")
    public void testCopybookWithInvalidFile() throws IOException {
        String[] args = {"-i", resourceDir.resolve("expectedGenTypes/cob.cpy").toString()};
        CopybookCmd copybookCmd = new CopybookCmd(printStream, tmpDir, false);
        new CommandLine(copybookCmd).parseArgs(args);
        copybookCmd.execute();
        String output = readOutput(true);
        String message = "The copybook definition file does not exist in the given path";
        assertStringsWithoutWhiteSpace(output, message);
    }

    @Test(description = "Test copybook command without arguments")
    public void testCopybookWithoutArguments() throws IOException {
        String[] args = {"-i"};
        CopybookCmd copybookCmd = new CopybookCmd(printStream, tmpDir, false);
        new CommandLine(copybookCmd).parseArgs(args);
        copybookCmd.execute();
        String output = readOutput(true);
        String message = "The input file path argument is missing. Please provide the path of the Copybook definition" +
                " file with -i or --input flag.";
        assertStringsWithoutWhiteSpace(output, message);
    }

    @Test(description = "Test copybook command with help flag")
    public void testCopybookTypeGeneration() throws IOException {
        String[] args = {"-i", resourceDir.resolve("copybookDefinitions/valid/copybook.cob").toString(),
                "-o", tmpDir.toString()};
        CopybookCmd copybookCmd = new CopybookCmd(printStream, tmpDir, false);
        new CommandLine(copybookCmd).parseArgs(args);
        copybookCmd.execute();
        Path expectedSchemaFile = resourceDir.resolve(Paths.get("expectedGenTypes", "copybook.bal"));
        String expectedSchema = readContentWithFormat(expectedSchemaFile);
        Assert.assertTrue(Files.exists(this.tmpDir.resolve("copybook.bal")));
        String generatedSchema = readContentWithFormat(this.tmpDir.resolve("copybook.bal"));
        Assert.assertEquals(expectedSchema, generatedSchema);
        String output = readOutput(true);
        Assert.assertTrue(output.contains("Ballerina record types generated successfully and copied to"));
    }

    @Test(description = "Test copybook type generation with multiple root levels")
    public void testTypeGenerationWithMultipleRootLevels() throws IOException {
        String[] args = {"-i", resourceDir.resolve("copybookDefinitions/valid/hospital.cpy").toString(),
                "-o", tmpDir.toString()};
        CopybookCmd copybookCmd = new CopybookCmd(printStream, tmpDir, false);
        new CommandLine(copybookCmd).parseArgs(args);
        copybookCmd.execute();
        Path expectedSchemaFile = resourceDir.resolve(Paths.get("expectedGenTypes", "hospital.bal"));
        String expectedSchema = readContentWithFormat(expectedSchemaFile);
        Assert.assertTrue(Files.exists(this.tmpDir.resolve("hospital.bal")));
        String generatedSchema = readContentWithFormat(this.tmpDir.resolve("hospital.bal"));
        Assert.assertEquals(expectedSchema, generatedSchema);
        String output = readOutput(true);
        Assert.assertTrue(output.contains("Ballerina record types generated successfully and copied to"));
    }

    @Test(description = "Test copybook type generation with multiple root levels")
    public void testTypeGenerationWithRedefines() throws IOException {
        String[] args = {"-i", resourceDir.resolve("copybookDefinitions/valid/redefine.cpy").toString(),
                "-o", tmpDir.toString()};
        CopybookCmd copybookCmd = new CopybookCmd(printStream, tmpDir, false);
        new CommandLine(copybookCmd).parseArgs(args);
        copybookCmd.execute();
        Path expectedSchemaFile = resourceDir.resolve(Paths.get("expectedGenTypes", "redefine.bal"));
        String expectedSchema = readContentWithFormat(expectedSchemaFile);
        Assert.assertTrue(Files.exists(this.tmpDir.resolve("redefine.bal")));
        String generatedSchema = readContentWithFormat(this.tmpDir.resolve("redefine.bal"));
        Assert.assertEquals(expectedSchema, generatedSchema);
        String output = readOutput(true);
        Assert.assertTrue(output.contains("Ballerina record types generated successfully and copied to"));
    }

    @Test(description = "Test copybook type generation with multiple root levels")
    public void testTypeGenerationForInvalidSchema() throws IOException {
        String[] args = {"-i", resourceDir.resolve("copybookDefinitions/invalid/copybook.cpy").toString(),
                "-o", tmpDir.toString()};
        CopybookCmd copybookCmd = new CopybookCmd(printStream, tmpDir, false);
        new CommandLine(copybookCmd).parseArgs(args);
        copybookCmd.execute();
        String output = readOutput(true);
        String message = "Copybook types generation failed: " +
                "Error at line 3, column 28: Unsupported picture string I(30) found in copybook schema\n" +
                "Error at line 4, column 28: Unsupported picture string I(10) found in copybook schema";
        assertStringsWithoutWhiteSpace(output, message);
    }
}
