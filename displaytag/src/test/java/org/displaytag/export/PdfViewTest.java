/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.export;

import jakarta.servlet.jsp.JspException;

import java.io.ByteArrayOutputStream;

import org.displaytag.exception.SeverityEnum;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.HtmlAttributeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PdfViewTest {

    @Test
    void testDoExportGeneratesPdf() throws Exception {
        final PdfView view = new PdfView();
        final TableModel model = createModel();
        view.setParameters(model, true, true, true);

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        view.doExport(out);

        Assertions.assertEquals("application/pdf", view.getMimeType());
        Assertions.assertTrue(out.size() > 0);
    }

    @Test
    void testDoExportWrapsFailures() {
        final PdfView view = new PdfView() {
            @Override
            protected void generatePDFTable() throws JspException {
                throw new JspException("boom");
            }
        };

        view.setParameters(createModel(), true, true, true);

        final PdfView.PdfGenerationException ex = Assertions.assertThrows(PdfView.PdfGenerationException.class,
                () -> view.doExport(new ByteArrayOutputStream()));
        Assertions.assertEquals(SeverityEnum.ERROR, ex.getSeverity());
    }

    private static TableModel createModel() {
        final TableProperties properties = TableProperties.getInstance(null);
        final TableModel model = new TableModel(properties, "pdf", null);
        model.setRowListPage(model.getRowListFull());

        final HeaderCell first = new HeaderCell();
        first.setTitle("Visible title");
        first.setBeanPropertyName("first");
        first.setHtmlAttributes(new HtmlAttributeMap());
        model.addColumnHeader(first);

        final HeaderCell second = new HeaderCell();
        second.setTitle(null);
        second.setBeanPropertyName("second");
        second.setHtmlAttributes(new HtmlAttributeMap());
        model.addColumnHeader(second);

        model.addRow(new Row(new PdfBean("left", "right"), 0));
        return model;
    }

    public static class PdfBean {
        private final String first;
        private final String second;

        PdfBean(final String first, final String second) {
            this.first = first;
            this.second = second;
        }

        public String getFirst() {
            return this.first;
        }

        public String getSecond() {
            return this.second;
        }
    }
}
