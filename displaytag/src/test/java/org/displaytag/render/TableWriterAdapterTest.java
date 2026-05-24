/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.render;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TableWriterAdapter}.
 */
class TableWriterAdapterTest {

    @Test
    void testAllAdapterMethodsAreSafeNoOps() {
        final ExposedTableWriterAdapter writer = new ExposedTableWriterAdapter();

        Assertions.assertDoesNotThrow(writer::invokeAll);
    }

    /**
     * Exposes protected methods for direct testing.
     */
    private static class ExposedTableWriterAdapter extends TableWriterAdapter {
        void invokeAll() throws Exception {
            this.writeEmptyListMessage(null);
            this.writeTopBanner(null);
            this.writeTableOpener(null);
            this.writeCaption(null);
            this.writeTableHeader(null);
            this.writePreBodyFooter(null);
            this.writeTableBodyOpener(null);
            this.writeTableBodyCloser(null);
            this.writePostBodyFooter(null);
            this.writeTableCloser(null);
            this.writeBottomBanner(null);
            this.writeDecoratedTableFinish(null);
            this.writeDecoratedRowStart(null);
            this.writeRowOpener(null);
            this.writeColumnOpener(null);
            this.writeColumnValue(null, null);
            this.writeColumnCloser(null);
            this.writeRowWithNoColumns(null);
            this.writeRowCloser(null);
            this.writeDecoratedRowFinish(null);
            this.writeEmptyListRowMessage(null);
        }
    }
}
