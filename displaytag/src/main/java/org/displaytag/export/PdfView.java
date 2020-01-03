/**
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.Messages;
import org.displaytag.exception.BaseNestableJspTagException;
import org.displaytag.exception.SeverityEnum;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;
import org.displaytag.util.TagConstants;


/**
 * PDF exporter using IText. This class is provided more as an example than as a "production ready" class: users
 * probably will need to write a custom export class with a specific layout.
 * @author Ivan Markov
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PdfView implements BinaryExportView
{

    /**
     * TableModel to render.
     */
    private TableModel model;

    /** export full list?. */
    private boolean exportFull;

    /** include header in export?. */
    private boolean header;

    /** decorate export?. */
    private boolean decorated;

    /**
     * This is the table, added as an Element to the PDF document. It contains all the data, needed to represent the
     * visible table into the PDF
     */
    private PdfPTable tablePDF;

    /**
     * The default font used in the document.
     */
    private Font smallFont;

    /**
     * @see org.displaytag.export.ExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
    @Override
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader,
        boolean decorateValues)
    {
        this.model = tableModel;
        this.exportFull = exportFullList;
        this.header = includeHeader;
        this.decorated = decorateValues;
    }

    /**
     * Initialize the main info holder table.
     */
    protected void initTable()
    {
        this.tablePDF = new PdfPTable(this.model.getNumberOfColumns());
        this.tablePDF.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        this.tablePDF.setWidthPercentage(100);

        this.smallFont = FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, new BaseColor(0, 0, 0));

    }

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
     * The overall PDF table generator.
     * @throws JspException for errors during value retrieving from the table model
     */
    protected void generatePDFTable() throws JspException
    {
        if (this.header)
        {
            generateHeaders();
        }
        generateRows();
    }

    /**
     * @see org.displaytag.export.BinaryExportView#doExport(OutputStream)
     */
    @Override
    public void doExport(OutputStream out) throws JspException
    {
        try
        {
            // Initialize the table with the appropriate number of columns
            initTable();

            // Initialize the Document and register it with PdfWriter listener and the OutputStream
            Document document = new Document(PageSize.A4.rotate(), 60, 60, 40, 40);
            document.addCreationDate();

            PdfWriter writer = PdfWriter.getInstance(document, out);
            writer.setPageEvent(new PdfPageEventHelper()
            {

                @Override
                public void onEndPage(PdfWriter writer, Document document)
                {

                    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(
                        TagConstants.EMPTY_STRING,
                        PdfView.this.smallFont), (document.left() + document.right()) / 2, document.bottom() - 18, 0);
                }

            });

            // Fill the virtual PDF table with the necessary data
            generatePDFTable();

            document.open();
            document.add(this.tablePDF);
            document.close();

        }
        catch (Exception e)
        {
            throw new PdfGenerationException(e);
        }
    }

    /**
     * Generates the header cells, which persist on every page of the PDF document.
     */
    protected void generateHeaders()
    {
        Iterator<HeaderCell> iterator = this.model.getHeaderCellList().iterator();

        while (iterator.hasNext())
        {
            HeaderCell headerCell = iterator.next();

            String columnHeader = headerCell.getTitle();

            if (columnHeader == null)
            {
                columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
            }

            PdfPCell hdrCell = getCell(columnHeader);
            hdrCell.setGrayFill(0.9f);
            this.tablePDF.addCell(hdrCell);

        }
        this.tablePDF.setHeaderRows(1);
    }

    /**
     * Generates all the row cells.
     * @throws JspException for errors during value retrieving from the table model
     */
    protected void generateRows() throws JspException
    {
        // get the correct iterator (full or partial list according to the exportFull field)
        RowIterator rowIterator = this.model.getRowIterator(this.exportFull);
        // iterator on rows
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();

            // iterator on columns
            ColumnIterator columnIterator = row.getColumnIterator(this.model.getHeaderCellList());

            while (columnIterator.hasNext())
            {
                Column column = columnIterator.nextColumn();

                // Get the value to be displayed for the column
                Object value = column.getValue(this.decorated);

                PdfPCell cell = getCell(value != null ? value.toString() : StringUtils.EMPTY);
                this.tablePDF.addCell(cell);
            }
        }
    }

    /**
     * Returns a formatted cell for the given value.
     * @param value cell value
     * @return Cell
     */
    private PdfPCell getCell(String value)
    {
        PdfPCell cell = new PdfPCell(new Phrase(new Chunk(StringUtils.trimToEmpty(value), this.smallFont)));
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setLeading(8, 0);
        cell.setPadding(2);
        return cell;
    }

    /**
     * Wraps IText-generated exceptions.
     * @author Fabrizio Giustina
     * @version $Revision$ ($Author$)
     */
    static class PdfGenerationException extends BaseNestableJspTagException
    {

        /**
         * D1597A17A6.
         */
        private static final long serialVersionUID = 899149338534L;

        /**
         * Instantiate a new PdfGenerationException with a fixed message and the given cause.
         * @param cause Previous exception
         */
        public PdfGenerationException(Throwable cause)
        {
            super(PdfView.class, Messages.getString("PdfView.errorexporting"), cause); //$NON-NLS-1$
        }

        /**
         * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
         */
        @Override
        public SeverityEnum getSeverity()
        {
            return SeverityEnum.ERROR;
        }
    }
}