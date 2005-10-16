/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.export;

import java.io.IOException;
import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;


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
            catch (DocumentException e)
            {
            }
            catch (IOException e)
            {
            }
            PdfContentByte cb = writer.getDirectContent();
            cb.saveState();
            // write the headertable
            table.setTotalWidth(document.right() - document.left());
            table.writeSelectedRows(0, -1, document.left(), document.getPageSize().height() - 50, cb);
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
            cb.saveState();
        }
    }
}
