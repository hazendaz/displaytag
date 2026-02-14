/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.export;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import java.io.IOException;
import java.io.OutputStream;

/**
 * PDF exporter using iText.
 */
public class DefaultPdfExportView extends DefaultItextExportView {

    /**
     * Gets the mime type.
     *
     * @return "application/pdf"
     *
     * @see org.displaytag.export.BaseExportView#getMimeType()
     */
    @Override
    public String getMimeType() {
        return "application/pdf"; //$NON-NLS-1$
    }

    /**
     * Initializes the PDF writer this export view uses to write the table document.
     *
     * @param document
     *            The iText document to be written.
     * @param out
     *            The output stream to which the document is written.
     *
     * @throws DocumentException
     *             If something goes wrong during initialization.
     */
    @Override
    protected void initItextWriter(final Document document, final OutputStream out) throws DocumentException {
        PdfWriter.getInstance(document, out).setPageEvent(new PageNumber());
    }

    /**
     * Prints a page number at the bottom of each page. Based on
     * http://itextdocs.lowagie.com/examples/com/lowagie/examples/directcontent/pageevents/PageNumbersWatermark.java
     */
    private static class PageNumber extends PdfPageEventHelper {

        /**
         * On end page.
         *
         * @param writer
         *            the writer
         * @param document
         *            the document
         *
         * @see com.lowagie.text.pdf.PdfPageEventHelper#onEndPage(com.lowagie.text.pdf.PdfWriter,
         *      com.lowagie.text.Document)
         */
        @Override
        public void onEndPage(final PdfWriter writer, final Document document) {
            // The header table
            final PdfPTable table = new PdfPTable(2);
            // A template that will hold the total number of pages.
            final PdfTemplate tpl = writer.getDirectContent().createTemplate(100, 100);
            // The font that will be used.
            BaseFont helv = null;
            try {
                helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
            } catch (DocumentException | IOException e) {
            }
            final PdfContentByte cb = writer.getDirectContent();
            // write the headertable
            table.setTotalWidth(document.right() - document.left());
            table.writeSelectedRows(0, -1, document.left(), document.getPageSize().getHeight() - 50, cb);
            // compose the footer
            final String text = "Page " + writer.getPageNumber();
            final float textSize = helv.getWidthPoint(text, 12);
            final float textBase = document.bottom() - 20;
            cb.beginText();
            cb.setFontAndSize(helv, 12);
            final float adjust = helv.getWidthPoint("0", 12);
            cb.setTextMatrix(document.right() - textSize - adjust, textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(tpl, document.right() - adjust, textBase);
        }
    }
}
