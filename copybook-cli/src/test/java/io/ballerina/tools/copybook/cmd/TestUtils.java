package io.ballerina.tools.copybook.cmd;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtils {
    protected static String readContentWithFormat(Path filePath) throws IOException {
        Stream<String> schemaLines = Files.lines(filePath);
        String schemaContent = schemaLines.collect(Collectors.joining(System.getProperty("line.separator")));
        schemaLines.close();
        return schemaContent;
    }
}
