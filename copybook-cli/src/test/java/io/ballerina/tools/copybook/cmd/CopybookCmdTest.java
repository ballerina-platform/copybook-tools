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

import static io.ballerina.tools.copybook.cmd.TestUtils.readContentWithFormat;

public class CopybookCmdTest extends CopybookTest {
    @BeforeTest(description = "This will create a new ballerina project for testing below scenarios.")
    public void setupBallerinaProject() throws IOException {
        super.setup();
    }

    @Test(description = "Test copybook command with help flag")
    public void testCopybookTypeGeneration() throws IOException {
        String[] args = {"-i", resourceDir.resolve("copybookDefinitions/copybook.cob").toString(),
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
        String[] args = {"-i", resourceDir.resolve("copybookDefinitions/hospital.cpy").toString(),
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
        String[] args = {"-i", resourceDir.resolve("copybookDefinitions/redefine.cpy").toString(),
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
}
