package io.ballerina.tools.copybook.cmd;

import org.testng.annotations.BeforeClass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Copybook command cmd common class to handle temp dirs and outputs.
 */
public abstract class CopybookTest {
    protected Path tmpDir;
    private ByteArrayOutputStream console;
    protected PrintStream printStream;
    protected final Path resourceDir = Paths.get("src/test/resources/").toAbsolutePath();

    @BeforeClass
    public void setup() throws IOException {
        this.tmpDir = Files.createTempDirectory("copybook-cmd-test-out-" + System.nanoTime());
        this.console = new ByteArrayOutputStream();
        this.printStream = new PrintStream(this.console);
    }

    protected String readOutput(boolean enableLog) throws IOException {
        String output = "";
        output = this.console.toString();
        this.console.close();
        this.console = new ByteArrayOutputStream();
        this.printStream = new PrintStream(this.console);
        if (!enableLog) {
            PrintStream out = System.out;
            out.println(output);
        }
        return output;
    }
}

