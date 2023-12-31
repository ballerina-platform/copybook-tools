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

package io.ballerina.tools.copybook.generator;

import io.ballerina.compiler.syntax.tree.TypeDefinitionNode;
import io.ballerina.compiler.syntax.tree.TypeDescriptorNode;
import io.ballerina.lib.copybook.commons.schema.DataItem;

import java.util.List;

import static io.ballerina.compiler.syntax.tree.AbstractNodeFactory.createIdentifierToken;
import static io.ballerina.compiler.syntax.tree.NodeFactory.createSimpleNameReferenceNode;
import static io.ballerina.tools.copybook.generator.CodeGeneratorUtils.getTypeReferenceName;
import static io.ballerina.tools.copybook.generator.CopybookTypeGenerator.addToFieldTypeDefinitionList;
import static io.ballerina.tools.copybook.generator.CopybookTypeGenerator.generateFieldTypeDefNode;

public class ReferencedTypeGenerator extends TypeGenerator {

    DataItem fieldSchema;

    public ReferencedTypeGenerator(DataItem fieldSchema) {
        this.fieldSchema = fieldSchema;
    }

    /**
     * Generate TypeDescriptorNode for referenced schemas.
     */
    @Override
    public TypeDescriptorNode generateTypeDescriptorNode(List<TypeDefinitionNode> typeDefList) {
        String extractName = getTypeReferenceName(fieldSchema, true);
        String typeName = CodeGeneratorUtils.getValidName(extractName);
        TypeDefinitionNode fieldType = generateFieldTypeDefNode(fieldSchema, getTypeReferenceName(fieldSchema, false));
        addToFieldTypeDefinitionList(fieldType, typeDefList);
        return createSimpleNameReferenceNode(createIdentifierToken(typeName));
    }
}
