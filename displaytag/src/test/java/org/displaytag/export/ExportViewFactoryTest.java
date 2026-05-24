/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.export;

import java.lang.reflect.Field;

import org.displaytag.exception.WrappedRuntimeException;
import org.displaytag.model.TableModel;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ExportViewFactory}.
 */
class ExportViewFactoryTest {

    @Test
    void testRegisterAndGetViewPaths() throws Exception {
        resetSingleton();
        final ExportViewFactory factory = ExportViewFactory.getInstance();

        factory.registerExportView("missing-view", "invalid.missing.ClassName");
        factory.registerExportView("private-view", PrivateCtorExportView.class.getName());
        factory.registerExportView("abstract-view", AbstractTestExportView.class.getName());
        factory.registerExportView("ok-view", TestExportView.class.getName());

        final TableModel model = new TableModel(TableProperties.getInstance(null), "", null);
        final ExportView view = factory.getView(MediaTypeEnum.fromName("ok-view"), model, true, false, true);
        Assertions.assertTrue(view instanceof TestExportView);
        Assertions.assertEquals("test/type", view.getMimeType());
    }

    @Test
    void testGetViewWrapsInstantiationFailure() throws Exception {
        resetSingleton();
        final ExportViewFactory factory = ExportViewFactory.getInstance();

        FlakyExportView.failOnConstruct = false;
        factory.registerExportView("flaky-view", FlakyExportView.class.getName());
        FlakyExportView.failOnConstruct = true;

        final TableModel model = new TableModel(TableProperties.getInstance(null), "", null);
        Assertions.assertThrows(WrappedRuntimeException.class,
                () -> factory.getView(MediaTypeEnum.fromName("flaky-view"), model, true, true, true));
        FlakyExportView.failOnConstruct = false;
    }

    private static void resetSingleton() throws Exception {
        final Field field = ExportViewFactory.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);
    }

    public static class TestExportView extends BaseExportView {
        @Override
        public String getMimeType() {
            return "test/type";
        }

        @Override
        protected String getCellEnd() {
            return ",";
        }

        @Override
        protected boolean getAlwaysAppendCellEnd() {
            return true;
        }

        @Override
        protected boolean getAlwaysAppendRowEnd() {
            return true;
        }

        @Override
        protected String escapeColumnValue(final Object value) {
            return String.valueOf(value);
        }
    }

    public static class FlakyExportView extends BaseExportView {
        private static boolean failOnConstruct;

        public FlakyExportView() {
            if (failOnConstruct) {
                throw new IllegalStateException("boom");
            }
        }

        @Override
        public String getMimeType() {
            return "flaky/type";
        }

        @Override
        protected String getCellEnd() {
            return ",";
        }

        @Override
        protected boolean getAlwaysAppendCellEnd() {
            return true;
        }

        @Override
        protected boolean getAlwaysAppendRowEnd() {
            return true;
        }

        @Override
        protected String escapeColumnValue(final Object value) {
            return String.valueOf(value);
        }
    }

    public static class PrivateCtorExportView extends BaseExportView {
        private PrivateCtorExportView() {
        }

        @Override
        public String getMimeType() {
            return "private/type";
        }

        @Override
        protected String getCellEnd() {
            return ",";
        }

        @Override
        protected boolean getAlwaysAppendCellEnd() {
            return true;
        }

        @Override
        protected boolean getAlwaysAppendRowEnd() {
            return true;
        }

        @Override
        protected String escapeColumnValue(final Object value) {
            return String.valueOf(value);
        }
    }

    public abstract static class AbstractTestExportView extends BaseExportView {
    }
}
