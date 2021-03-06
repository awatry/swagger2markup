/*
 * Copyright 2017 Robert Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.swagger2markup.builder;

import com.google.common.collect.Ordering;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupProperties;
import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.OrderBy;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.BDDAssertions.assertThat;

public class Swagger2MarkupConfigBuilderTest {

    @Test
    public void testConfigOfDefaults() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put(Swagger2MarkupProperties.MARKUP_LANGUAGE, MarkupLanguage.MARKDOWN.toString());
        configMap.put("swagger2markup.extensions.uniqueId1.customProperty1", "123");
        configMap.put("swagger2markup.extensions.uniqueId1.customProperty2", "123");
        configMap.put("swagger2markup.extensions.uniqueId2.customPropertyList1", "123,456");
        configMap.put("swagger2markup.uniqueId1.customProperty1", "123");
        configMap.put("swagger2markup.uniqueId1.customProperty2", "123");

        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder(configMap)
                .build();

        assertThat(config.getAnchorPrefix()).isNull();
        assertThat(config.getDefinitionOrderBy()).isEqualTo(OrderBy.NATURAL);
        assertThat(config.getDefinitionOrdering()).isEqualTo(Ordering.natural());
        assertThat(config.getDefinitionsDocument()).isEqualTo("definitions");
        assertThat(config.isGeneratedExamplesEnabled()).isFalse();
        assertThat(config.isInlineSchemaEnabled()).isEqualTo(true);
        assertThat(config.getInterDocumentCrossReferencesPrefix()).isNull();
        assertThat(config.getMarkupLanguage().name()).isEqualTo(MarkupLanguage.MARKDOWN.name());
        assertThat(config.getOperationOrderBy()).isEqualTo(OrderBy.NATURAL);
        assertThat(config.getOperationOrdering()).isNotNull();
        assertThat(config.getLanguage()).isEqualTo(Language.EN);
        assertThat(config.getOverviewDocument()).isEqualTo("overview");
        assertThat(config.getParameterOrderBy()).isEqualTo(OrderBy.NATURAL);
        assertThat(config.getParameterOrdering()).isEqualTo(Swagger2MarkupConfigBuilder.PARAMETER_IN_NATURAL_ORDERING.compound(Swagger2MarkupConfigBuilder.PARAMETER_NAME_NATURAL_ORDERING));
        assertThat(config.getPathsDocument()).isEqualTo("paths");
        assertThat(config.getPathsGroupedBy()).isEqualTo(GroupBy.AS_IS);
        assertThat(config.getPropertyOrderBy()).isEqualTo(OrderBy.NATURAL);
        assertThat(config.getPropertyOrdering()).isEqualTo(Ordering.natural());
        assertThat(config.getResponseOrderBy()).isEqualTo(OrderBy.NATURAL);
        assertThat(config.getResponseOrdering()).isEqualTo(Ordering.natural());
        assertThat(config.getSecurityDocument()).isEqualTo("security");
        assertThat(config.getSeparatedDefinitionsFolder()).isEqualTo("definitions");
        assertThat(config.getSeparatedOperationsFolder()).isEqualTo("operations");
        assertThat(config.getTagOrderBy()).isEqualTo(OrderBy.NATURAL);
        assertThat(config.getTagOrdering()).isEqualTo(Ordering.natural());
        assertThat(config.isFlatBodyEnabled()).isFalse();
        assertThat(config.isPathSecuritySectionEnabled()).isTrue();
        assertThat(config.isInterDocumentCrossReferencesEnabled()).isFalse();
        assertThat(config.isSeparatedDefinitionsEnabled()).isFalse();
        assertThat(config.isSeparatedOperationsEnabled()).isFalse();
        assertThat(config.getExtensionsProperties().getKeys()).hasSize(3)
        .containsOnly("uniqueId1.customProperty1",
                "uniqueId1.customProperty2",
                "uniqueId2.customPropertyList1"
                );
        assertThat(config.getExtensionsProperties().getString("uniqueId1.customProperty1").get()).isEqualTo("123");
        assertThat(config.getExtensionsProperties().getPathList("uniqueId2.customPropertyList1")).hasSize(1)
        .containsOnly(Paths.get("123,456"));
    }


    @Test
    public void testConfigOfProperties() throws IOException {

        Properties properties = new Properties();
        properties.load(Swagger2MarkupConfigBuilderTest.class.getResourceAsStream("/config/config.properties"));

        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder(properties).build();

        assertThat(config.getAnchorPrefix()).isEqualTo("anchorPrefix");
        assertThat(config.getDefinitionOrderBy()).isEqualTo(OrderBy.AS_IS);
        assertThat(config.getDefinitionOrdering()).isNull();
        assertThat(config.getDefinitionsDocument()).isEqualTo("definitionsTest");
        assertThat(config.isGeneratedExamplesEnabled()).isTrue();
        assertThat(config.isHostnameEnabled()).isEqualTo(false);
        assertThat(config.isInlineSchemaEnabled()).isEqualTo(false);
        assertThat(config.getInterDocumentCrossReferencesPrefix()).isEqualTo("xrefPrefix");
        assertThat(config.getMarkupLanguage().name()).isEqualTo(MarkupLanguage.MARKDOWN.name());
        assertThat(config.getOperationOrderBy()).isEqualTo(OrderBy.NATURAL);
        assertThat(config.getOperationOrdering()).isEqualTo(Swagger2MarkupConfigBuilder.OPERATION_PATH_NATURAL_ORDERING.compound(Swagger2MarkupConfigBuilder.OPERATION_METHOD_NATURAL_ORDERING));
        assertThat(config.getLanguage()).isEqualTo(Language.RU);
        assertThat(config.getOverviewDocument()).isEqualTo("overviewTest");
        assertThat(config.getParameterOrderBy()).isEqualTo(OrderBy.AS_IS);
        assertThat(config.getParameterOrdering()).isNull();
        assertThat(config.getPathsDocument()).isEqualTo("pathsTest");
        assertThat(config.getPathsGroupedBy()).isEqualTo(GroupBy.TAGS);
        assertThat(config.getPropertyOrderBy()).isEqualTo(OrderBy.AS_IS);
        assertThat(config.getPropertyOrdering()).isNull();
        assertThat(config.getResponseOrderBy()).isEqualTo(OrderBy.AS_IS);
        assertThat(config.getResponseOrdering()).isNull();
        assertThat(config.getSecurityDocument()).isEqualTo("securityTest");
        assertThat(config.getSeparatedDefinitionsFolder()).isEqualTo("definitionsTest");
        assertThat(config.getSeparatedOperationsFolder()).isEqualTo("operationsTest");
        assertThat(config.isListDelimiterEnabled()).isEqualTo(true);
        assertThat(config.getListDelimiter()).isEqualTo(Character.valueOf('|'));
        assertThat(config.getTagOrderBy()).isEqualTo(OrderBy.AS_IS);
        assertThat(config.getTagOrdering()).isNull();
        assertThat(config.isFlatBodyEnabled()).isTrue();
        assertThat(config.isPathSecuritySectionEnabled()).isFalse();
        assertThat(config.isInterDocumentCrossReferencesEnabled()).isTrue();
        assertThat(config.isSeparatedDefinitionsEnabled()).isTrue();
        assertThat(config.isSeparatedOperationsEnabled()).isTrue();
        assertThat(config.getExtensionsProperties().getKeys()).hasSize(5)
        .containsOnly("uniqueId1.customProperty1",
                "uniqueId1.customProperty2",
                "uniqueId2.customProperty1",
                "uniqueId2.customProperty2",
                "uniqueId2.customPropertyList1"
                );
        assertThat(config.getExtensionsProperties().getPathList("uniqueId2.customPropertyList1")).hasSize(2)
        .containsOnly(Paths.get("123"), Paths.get("456"));
    }

    @Test
    public void testConfigBuilder() {
        Swagger2MarkupConfigBuilder builder = new Swagger2MarkupConfigBuilder();

        try {
            builder.withTagOrdering(OrderBy.CUSTOM);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("You must provide a custom comparator if orderBy == OrderBy.CUSTOM");
        }
        builder.withTagOrdering(Ordering.<String>natural());

        Swagger2MarkupConfig config = builder.build();
        assertThat(config.getTagOrderBy()).isEqualTo(OrderBy.CUSTOM);
        assertThat(config.getTagOrdering()).isEqualTo(Ordering.natural());

        try {
            builder.withOperationOrdering(OrderBy.CUSTOM);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("You must provide a custom comparator if orderBy == OrderBy.CUSTOM");
        }
        builder.withOperationOrdering(Swagger2MarkupConfigBuilder.OPERATION_PATH_NATURAL_ORDERING);
        config = builder.build();
        assertThat(config.getOperationOrderBy()).isEqualTo(OrderBy.CUSTOM);
        assertThat(config.getOperationOrdering()).isEqualTo(Swagger2MarkupConfigBuilder.OPERATION_PATH_NATURAL_ORDERING);

        try {
            builder.withDefinitionOrdering(OrderBy.CUSTOM);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("You must provide a custom comparator if orderBy == OrderBy.CUSTOM");
        }
        builder.withDefinitionOrdering(Ordering.<String>natural());
        config = builder.build();
        assertThat(config.getDefinitionOrderBy()).isEqualTo(OrderBy.CUSTOM);
        assertThat(config.getDefinitionOrdering()).isEqualTo(Ordering.natural());

        try {
            builder.withParameterOrdering(OrderBy.CUSTOM);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("You must provide a custom comparator if orderBy == OrderBy.CUSTOM");
        }
        builder.withParameterOrdering(Swagger2MarkupConfigBuilder.PARAMETER_NAME_NATURAL_ORDERING);
        config = builder.build();
        assertThat(config.getParameterOrderBy()).isEqualTo(OrderBy.CUSTOM);
        assertThat(config.getParameterOrdering()).isEqualTo(Swagger2MarkupConfigBuilder.PARAMETER_NAME_NATURAL_ORDERING);

        try {
            builder.withPropertyOrdering(OrderBy.CUSTOM);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("You must provide a custom comparator if orderBy == OrderBy.CUSTOM");
        }
        builder.withPropertyOrdering(Ordering.<String>natural());
        config = builder.build();
        assertThat(config.getPropertyOrderBy()).isEqualTo(OrderBy.CUSTOM);
        assertThat(config.getPropertyOrdering()).isEqualTo(Ordering.natural());

        try {
            builder.withResponseOrdering(OrderBy.CUSTOM);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("You must provide a custom comparator if orderBy == OrderBy.CUSTOM");
        }
        builder.withResponseOrdering(Ordering.<String>natural());
        config = builder.build();
        assertThat(config.getResponseOrderBy()).isEqualTo(OrderBy.CUSTOM);
        assertThat(config.getResponseOrdering()).isEqualTo(Ordering.natural());
        
        assertThat(config.isListDelimiterEnabled()).isEqualTo(false);
        builder.withListDelimiter();
        config = builder.build();
        assertThat(config.getListDelimiter()).isEqualTo(Character.valueOf(','));
        assertThat(config.isListDelimiterEnabled()).isEqualTo(true);
        
    }
    
    @Test
    public void testConfigBuilderListDelimiter() {
        Swagger2MarkupConfigBuilder builder = new Swagger2MarkupConfigBuilder();

        builder.withListDelimiter();
        Swagger2MarkupConfig config = builder.build();
        assertThat(config.isListDelimiterEnabled()).isEqualTo(true);
        builder.withListDelimiter(Character.valueOf('|'));
        config = builder.build();
        assertThat(config.getListDelimiter()).isEqualTo(Character.valueOf('|'));
        assertThat(config.isListDelimiterEnabled()).isEqualTo(true);
    }
}
