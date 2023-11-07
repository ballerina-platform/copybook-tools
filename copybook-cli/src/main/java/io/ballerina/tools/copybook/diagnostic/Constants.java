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

public class Constants {

    public static final String MESSAGE_FOR_INVALID_FILE_EXTENSION = "File \"%s\" is invalid. Copybook tool support" +
            " only the Copybook definition files with .cpy or .cob extension. %nPlease provide the path of the input " +
            " file with -i or --input flag.%ne.g: bal copybook --input <Copybook Definition File>";
    public static final String MESSAGE_CAN_NOT_READ_COPYBOOK_FILE =
            "Provided Definition file \"%s\" is not allowed to be read";
    public static final String MESSAGE_FOR_INVALID_COPYBOOK_PATH =
            "The Copybook definition file does not exist in the given path";
}
