package io.ballerina.tools.copybook.utils;

import io.ballerina.tools.copybook.diagnostic.DiagnosticMessages;
import io.ballerina.tools.copybook.exception.CopybookTypeGenerationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;

import static io.ballerina.tools.copybook.generator.GeneratorConstants.PERIOD;

public class Utils {

    public static boolean createOutputDirectory(Path outputPath) {
        File outputDir = new File(outputPath.toString());
        if (!outputDir.exists()) {
            return outputDir.mkdirs();
        }
        return true;
    }

    public static void writeFile(Path filePath, String content) throws CopybookTypeGenerationException {
        try (FileWriter writer = new FileWriter(filePath.toString(), StandardCharsets.UTF_8)) {
            writer.write(content);
        } catch (IOException e) {
            throw new CopybookTypeGenerationException(DiagnosticMessages.COPYBOOK_TYPE_GEN_102, null, e.getMessage());
        }
    }

    public static String resolveSchemaFileName(Path outPath, String fileName) {
        if (outPath != null && Files.exists(outPath)) {
            final File[] listFiles = new File(String.valueOf(outPath)).listFiles();
            if (listFiles != null) {
                fileName = checkAvailabilityOfGivenName(fileName, listFiles);
            }
        }
        return fileName;
    }

    private static String checkAvailabilityOfGivenName(String schemaName, File[] listFiles) {
        for (File file : listFiles) {
            if (System.console() != null && file.getName().equals(schemaName)) {
                String userInput = System.console().readLine("There is already a file named '" + file.getName() +
                        "' in the target location. Do you want to overwrite the file? [y/N] ");
                if (!Objects.equals(userInput.toLowerCase(Locale.ENGLISH), "y")) {
                    schemaName = setGeneratedFileName(listFiles, schemaName);
                }
            }
        }
        return schemaName;
    }

    private static String setGeneratedFileName(File[] listFiles, String fileName) {
        int duplicateCount = 0;
        for (File listFile : listFiles) {
            String listFileName = listFile.getName();
            if (listFileName.contains(".") && ((listFileName.split("\\.")).length >= 2)
                    && (listFileName.split("\\.")[0].equals(fileName.split("\\.")[0]))) {
                duplicateCount++;
            }
        }
        return fileName.split("\\.")[0] + PERIOD + duplicateCount + PERIOD + fileName.split("\\.")[1];
    }
}
