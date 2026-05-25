/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

import org.displaytag.decorator.DefaultDecoratorFactory;
import org.displaytag.model.RowSorter;
import org.displaytag.properties.TableProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Additional coverage tests for exception classes.
 */
class ExceptionCoverageTest {

    @Test
    void testBaseNestableRuntimeExceptionSeverityBranchesAndCause() {
        final RuntimeException cause = new RuntimeException("cause");

        final TestRuntimeException debug = new TestRuntimeException(ExceptionCoverageTest.class, "debug",
                SeverityEnum.DEBUG);
        final TestRuntimeException info = new TestRuntimeException(ExceptionCoverageTest.class, "info",
                SeverityEnum.INFO);
        final TestRuntimeException warn = new TestRuntimeException(ExceptionCoverageTest.class, "warn",
                SeverityEnum.WARN);
        final TestRuntimeException error = new TestRuntimeException(ExceptionCoverageTest.class, "error", cause,
                SeverityEnum.ERROR);

        Assertions.assertNull(debug.getCause());
        Assertions.assertNull(info.getCause());
        Assertions.assertNull(warn.getCause());
        Assertions.assertSame(cause, error.getCause());
        Assertions.assertTrue(error.toString().contains("error"));
        Assertions.assertTrue(error.toString().contains("cause"));
    }

    @Test
    void testJspAndRuntimeConcreteExceptions() {
        final Throwable cause = new IllegalArgumentException("boom");

        final DecoratorException decoratorException = new DecoratorException(ExceptionCoverageTest.class, "decorator");
        final DecoratorInstantiationException instantiationException = new DecoratorInstantiationException(
                DefaultDecoratorFactory.class, "decorator", cause);
        final MissingAttributeException missingAttributeException = new MissingAttributeException(
                ExceptionCoverageTest.class, new String[] { "a", "b" });
        final ObjectLookupException lookupException = new ObjectLookupException(ExceptionCoverageTest.class, this, "x",
                cause);
        final RuntimeLookupException runtimeLookupException = new RuntimeLookupException(RowSorter.class, "x",
                lookupException);
        final TagStructureException tagStructureException = new TagStructureException(ExceptionCoverageTest.class,
                "column", "table");
        final WrappedRuntimeException wrappedRuntimeException = new WrappedRuntimeException(ExceptionCoverageTest.class,
                cause);
        final TablePropertiesLoadException tablePropertiesLoadException = new TablePropertiesLoadException(
                ExceptionCoverageTest.class, "displaytag.properties", cause);
        final FactoryInstantiationException factoryInstantiationException = new FactoryInstantiationException(
                TableProperties.class, "factory.decorator", "x.Factory", cause);

        Assertions.assertEquals(SeverityEnum.ERROR, decoratorException.getSeverity());
        Assertions.assertEquals(SeverityEnum.ERROR, instantiationException.getSeverity());
        Assertions.assertEquals(SeverityEnum.ERROR, missingAttributeException.getSeverity());
        Assertions.assertEquals(SeverityEnum.WARN, lookupException.getSeverity());
        Assertions.assertEquals(SeverityEnum.ERROR, tagStructureException.getSeverity());
        Assertions.assertEquals(SeverityEnum.WARN, wrappedRuntimeException.getSeverity());
        Assertions.assertEquals(SeverityEnum.ERROR, tablePropertiesLoadException.getSeverity());
        Assertions.assertEquals(SeverityEnum.FATAL, factoryInstantiationException.getSeverity());
        Assertions.assertArrayEquals(new String[] { "a", "b" }, missingAttributeException.getAttributeNames());
        Assertions.assertTrue(runtimeLookupException.getMessage().contains("x"));
        Assertions.assertEquals("debug", SeverityEnum.DEBUG.getSeverity());
    }

    private static final class TestRuntimeException extends BaseNestableRuntimeException {
        private static final long serialVersionUID = 1L;
        private final SeverityEnum severity;

        private TestRuntimeException(final Class<?> source, final String message, final SeverityEnum severity) {
            super(source, message);
            this.severity = severity;
        }

        private TestRuntimeException(final Class<?> source, final String message, final Throwable cause,
                final SeverityEnum severity) {
            super(source, message, cause);
            this.severity = severity;
        }

        @Override
        public SeverityEnum getSeverity() {
            return this.severity;
        }
    }
}
