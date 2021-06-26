/*
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.io.OutputStream;


/**
 * PDF exporter using iText.
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 */
public class DefaultPdfExportView extends DefaultItextExportView
{

    /**
     * @see org.displaytag.export.BaseExportView#getMimeType()
     * @return "application/pdf"
     */
    @Override
    public String getMimeType()
    {
        return "application/pdf"; //$NON-NLS-1$
    }

    /**
     * Initializes the PDF writer this export view uses to write the table document.
     * @param document The iText document to be written.
     * @param out The output stream to which the document is written.
     * @throws DocumentException If something goes wrong during initialization.
     */
    @Override
    protected void initItextWriter(Document document, OutputStream out) throws DocumentException
    {
        PdfWriter.getInstance(document, out).setPageEvent(new PageNumber());
    }

    /**
     * Prints a page number at the bottom of each page. Based on
     * http://itextdocs.lowagie.com/examples/com/lowagie/examples/directcontent/pageevents/PageNumbersWatermark.java
     * @author Jorge L. Barroso
     * @version $Revision$ ($Author$)
     */
    private static class PageNumber extends PdfPageEventHelper
    {

        /**
         * @see com.lowagie.text.pdf.PdfPageEventHelper#onEndPage(com.lowagie.text.pdf.PdfWriter,
         * com.lowagie.text.Document)
         */
        @Override
        public void onEndPage(PdfWriter writer, Document document)
        {
            /** The headertable. */
            PdfPTable table = new PdfPTable(2);
            /** A template that will hold the total number of pages. */
            PdfTemplate tpl = writer.getDirectContent().createTemplate(100, 100);
            /** The font that will be used. */
            BaseFont helv = null;
            try
            {
                helv = BaseFont.createFont("Helvetica", BaseFont.WINANSI, false);
            }
            catch (DocumentException | IOException e)
            {
            }
            PdfContentByte cb = writer.getDirectContent();
            // write the headertable
            table.setTotalWidth(document.right() - document.left());
            table.writeSelectedRows(0, -1, document.left(), document.getPageSize().getHeight() - 50, cb);
            // compose the footer
            String text = "Page " + writer.getPageNumber();
            float textSize = helv.getWidthPoint(text, 12);
            float textBase = document.bottom() - 20;
            cb.beginText();
            cb.setFontAndSize(helv, 12);
            float adjust = helv.getWidthPoint("0", 12);
            cb.setTextMatrix(document.right() - textSize - adjust, textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(tpl, document.right() - adjust, textBase);
        }
    }
}
