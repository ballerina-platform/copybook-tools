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

package io.ballerina.tools.copybook.diagnostic;

import io.ballerina.tools.diagnostics.DiagnosticSeverity;

public enum DiagnosticMessages {
    COPYBOOK_TYPE_GEN_100("COPYBOOK_TYPE_GEN_100", "Copybook types generation failed: The input file path argument" +
            " is missing. Please provide the path of the Copybook definition file with -i or --input flag.",
            DiagnosticSeverity.ERROR),
    COPYBOOK_TYPE_GEN_102("COPYBOOK_TYPE_GEN_102", "Copybook types generation failed: %s", DiagnosticSeverity.ERROR),
    COPYBOOK_TYPE_GEN_103("COPYBOOK_TYPE_GEN_103", "Failed to create output directory: %s", DiagnosticSeverity.ERROR);
    private final String code;
    private final String description;
    private final DiagnosticSeverity severity;

    DiagnosticMessages(String code, String description, DiagnosticSeverity severity) {
        this.code = code;
        this.description = description;
        this.severity = severity;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public DiagnosticSeverity getSeverity() {
        return severity;
    }
}
