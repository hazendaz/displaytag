/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.properties;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Properties;

import org.displaytag.decorator.DecoratorFactory;
import org.displaytag.exception.FactoryInstantiationException;
import org.displaytag.localization.I18nResourceProvider;
import org.displaytag.util.RequestHelperFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Additional branch coverage for {@link TableProperties}.
 */
class TablePropertiesAdditionalTest {

    @Test
    void testDefaultFallbackGettersAndBooleanBranches() throws Exception {
        TableProperties.clearProperties();
        TableProperties.setLocaleResolver(null);
        TableProperties.setResourceProvider(null);
        final TableProperties properties = TableProperties.getInstance(null);

        removeProperty(properties, TableProperties.PROPERTY_INT_PAGING_GROUPSIZE);
        removeProperty(properties, TableProperties.PROPERTY_STRING_PAGINATION_SORT_PARAM);
        removeProperty(properties, TableProperties.PROPERTY_STRING_PAGINATION_PAGE_NUMBER_PARAM);
        removeProperty(properties, TableProperties.PROPERTY_STRING_PAGINATION_SORT_DIRECTION_PARAM);
        removeProperty(properties, TableProperties.PROPERTY_STRING_PAGINATION_SEARCH_ID_PARAM);
        removeProperty(properties, TableProperties.PROPERTY_STRING_PAGINATION_ASC_VALUE);
        removeProperty(properties, TableProperties.PROPERTY_STRING_PAGINATION_DESC_VALUE);
        removeProperty(properties, TableProperties.PROPERTY_BOOLEAN_PAGINATION_SKIP_PAGE_NUMBER_IN_SORT);
        removeProperty(properties, TableProperties.PROPERTY_BOOLEAN_LEGACY_FORM_SUBMIT);

        Assertions.assertEquals(8, properties.getPagingGroupSize());
        Assertions.assertEquals("sort", properties.getPaginationSortParam());
        Assertions.assertEquals("page", properties.getPaginationPageNumberParam());
        Assertions.assertEquals("dir", properties.getPaginationSortDirectionParam());
        Assertions.assertEquals("searchid", properties.getPaginationSearchIdParam());
        Assertions.assertEquals("asc", properties.getPaginationAscValue());
        Assertions.assertEquals("desc", properties.getPaginationDescValue());
        Assertions.assertTrue(properties.getPaginationSkipPageNumberInSort());
        Assertions.assertFalse(properties.getUseLegacyFormSubmit());
        Assertions.assertTrue(properties.getExportTypes().length > 0);

        properties.setProperty(TableProperties.PROPERTY_STRING_BANNER_PLACEMENT, "top");
        Assertions.assertTrue(properties.getAddPagingBannerTop());
        Assertions.assertFalse(properties.getAddPagingBannerBottom());

        properties.setProperty(TableProperties.PROPERTY_STRING_BANNER_PLACEMENT, "bottom");
        Assertions.assertFalse(properties.getAddPagingBannerTop());
        Assertions.assertTrue(properties.getAddPagingBannerBottom());

        properties.setProperty(TableProperties.PROPERTY_STRING_EXPORTBANNER_PLACEMENT, "top");
        Assertions.assertTrue(properties.getAddExportBannerTop());
        Assertions.assertFalse(properties.getAddExportBannerBottom());

        properties.setProperty(TableProperties.PROPERTY_STRING_EXPORTBANNER_PLACEMENT, "both");
        Assertions.assertTrue(properties.getAddExportBannerTop());
        Assertions.assertTrue(properties.getAddExportBannerBottom());
    }

    @Test
    void testFactoryAndComparatorLoadingBranches() {
        TableProperties.clearProperties();
        TableProperties.setLocaleResolver(null);
        TableProperties.setResourceProvider(null);
        final TableProperties properties = TableProperties.getInstance(null);

        properties.setProperty(TableProperties.PROPERTY_CLASS_REQUESTHELPERFACTORY, InvalidClass.class.getName());
        Assertions.assertThrows(FactoryInstantiationException.class, properties::getRequestHelperFactoryInstance);

        properties.setProperty(TableProperties.PROPERTY_CLASS_REQUESTHELPERFACTORY,
                TestRequestHelperFactory.class.getName());
        final RequestHelperFactory helperFactory = properties.getRequestHelperFactoryInstance();
        Assertions.assertTrue(helperFactory instanceof TestRequestHelperFactory);

        properties.setProperty(TableProperties.PROPERTY_CLASS_DECORATORFACTORY, TestDecoratorFactory.class.getName());
        final DecoratorFactory decoratorFactory = properties.getDecoratorFactoryInstance();
        Assertions.assertTrue(decoratorFactory instanceof TestDecoratorFactory);

        properties.setProperty(TableProperties.PROPERTY_DEFAULT_COMPARATOR, TestComparator.class.getName());
        final Comparator<Object> comparator = properties.getDefaultComparator();
        Assertions.assertTrue(comparator instanceof TestComparator);

        properties.setProperty(TableProperties.PROPERTY_CLASS_LOCALEPROVIDER, TestResourceProvider.class.getName());
        final I18nResourceProvider provider = properties.geResourceProvider();
        Assertions.assertTrue(provider instanceof TestResourceProvider);
    }

    @Test
    void testFallbackFactoriesAndProviderWhenNotConfigured() throws Exception {
        TableProperties.clearProperties();
        TableProperties.setLocaleResolver(null);
        TableProperties.setResourceProvider(null);
        final TableProperties properties = TableProperties.getInstance(null);

        removeProperty(properties, TableProperties.PROPERTY_CLASS_REQUESTHELPERFACTORY);
        removeProperty(properties, TableProperties.PROPERTY_CLASS_DECORATORFACTORY);
        removeProperty(properties, TableProperties.PROPERTY_CLASS_LOCALEPROVIDER);

        Assertions.assertTrue(properties
                .getRequestHelperFactoryInstance() instanceof org.displaytag.util.DefaultRequestHelperFactory);
        Assertions.assertTrue(
                properties.getDecoratorFactoryInstance() instanceof org.displaytag.decorator.DefaultDecoratorFactory);
        Assertions.assertNotNull(properties.geResourceProvider());
    }

    @Test
    void testInvalidClassAndInvalidNumericConfigurationBranches() {
        TableProperties.clearProperties();
        TableProperties.setLocaleResolver(null);
        TableProperties.setResourceProvider(null);
        final TableProperties properties = TableProperties.getInstance(null);

        properties.setProperty(TableProperties.PROPERTY_CLASS_DECORATORFACTORY, String.class.getName());
        Assertions.assertThrows(FactoryInstantiationException.class, properties::getDecoratorFactoryInstance);

        properties.setProperty(TableProperties.PROPERTY_DEFAULT_COMPARATOR, "missing.Comparator");
        Assertions.assertFalse(properties.getDefaultComparator() instanceof TestComparator);

        properties.setProperty(TableProperties.PROPERTY_INT_PAGING_GROUPSIZE, "invalid");
        Assertions.assertEquals(8, properties.getPagingGroupSize());
    }

    private static void removeProperty(final TableProperties properties, final String key) throws Exception {
        final Field field = TableProperties.class.getDeclaredField("properties");
        field.setAccessible(true);
        final Properties raw = (Properties) field.get(properties);
        raw.remove(key);
    }

    public static class InvalidClass {
    }

    public static class TestRequestHelperFactory extends org.displaytag.util.DefaultRequestHelperFactory {
    }

    public static class TestDecoratorFactory extends org.displaytag.decorator.DefaultDecoratorFactory {
    }

    public static class TestComparator implements Comparator<Object> {
        @Override
        public int compare(final Object left, final Object right) {
            return String.valueOf(left).compareTo(String.valueOf(right));
        }
    }

    public static class TestResourceProvider implements I18nResourceProvider {
        @Override
        public String getResource(final String resourceKey, final String defaultValue,
                final jakarta.servlet.jsp.tagext.Tag tag, final jakarta.servlet.jsp.PageContext pageContext) {
            return resourceKey != null ? resourceKey : defaultValue;
        }
    }
}
