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
package io.ballerina.tools.copybook.exception;

import io.ballerina.tools.copybook.diagnostic.DiagnosticMessages;
import io.ballerina.tools.copybook.utils.NullLocation;
import io.ballerina.tools.diagnostics.Diagnostic;
import io.ballerina.tools.diagnostics.DiagnosticFactory;
import io.ballerina.tools.diagnostics.DiagnosticInfo;
import io.ballerina.tools.diagnostics.Location;

public class CopybookTypeGenerationException extends Exception {
    private final Diagnostic diagnostic;

    public CopybookTypeGenerationException(DiagnosticMessages diagnosticMessage, Location location, String... args) {
        super(generateDescription(diagnosticMessage, args));
        this.diagnostic = createDiagnostic(diagnosticMessage, location, args);
    }

    public String getMessage() {
        return this.diagnostic.toString();
    }

    private static String generateDescription(DiagnosticMessages message, String... args) {
        return String.format(message.getDescription(), (Object[]) args);
    }

    private static Diagnostic createDiagnostic(DiagnosticMessages diagnosticMessage, Location location,
                                               String... args) {
        DiagnosticInfo diagnosticInfo = new DiagnosticInfo(diagnosticMessage.getCode(),
                generateDescription(diagnosticMessage, args), diagnosticMessage.getSeverity());
        if (location == null) {
            location = NullLocation.getInstance();
        }
        return DiagnosticFactory.createDiagnostic(diagnosticInfo, location);
    }
}
