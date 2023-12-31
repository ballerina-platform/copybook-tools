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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtils {

    public static final String WHITESPACE_REGEX = "\\s+";

    private TestUtils() {
    }

    protected static String readContentWithFormat(Path filePath) throws IOException {
        Stream<String> schemaLines = Files.lines(filePath);
        String schemaContent = schemaLines.collect(Collectors.joining(System.getProperty("line.separator")));
        schemaLines.close();
        return schemaContent;
    }

    protected static void assertStringsWithoutWhiteSpace(String output, String expected) {
        // Replace following as Windows environment requirement
        String actualString = output.replaceAll(WHITESPACE_REGEX, "");
        String expectedString = expected.replaceAll(WHITESPACE_REGEX, "");
        Assert.assertTrue(actualString.contains(expectedString));
    }
}
