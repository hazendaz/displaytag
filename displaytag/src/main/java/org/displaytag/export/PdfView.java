package org.displaytag.export;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.Messages;
import org.displaytag.exception.BaseNestableJspTagException;
import org.displaytag.exception.SeverityEnum;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;


/**
 * PDF exporter using IText. This class is provided more as an example than as a "production ready" class: users
 * probably will need to write a custom export class with a specific layout.
 * @author Ivan Markov
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PdfView implements ExportView
{

    /**
     * TableModel to render.
     */
    private TableModel model;

    /**
     * export full list?
     */
    private boolean exportFull;

    /**
     * include header in export?
     */
    private boolean header;

    /**
     * decorate export?
     */
    private boolean decorated;

    /**
     * This is the table, added as an Element to the PDF document. It contains all the data, needed to represent the
     * visible table into the PDF
     */
    private Table tablePDF;

    /**
     * @see org.displaytag.export.ExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
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
     * @throws BadElementException for errors during table initialization
     */
    protected void initTable() throws BadElementException
    {
        tablePDF = new Table(this.model.getNumberOfColumns());
        tablePDF.setPadding(5);
        tablePDF.setDefaultVerticalAlignment(Element.ALIGN_MIDDLE);
        tablePDF.setCellsFitPage(true);

        tablePDF.setPadding(4);
        tablePDF.setSpacing(0);
    }

    /**
     * @see org.displaytag.export.BaseExportView#getMimeType()
     * @return "application/pdf"
     */
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
        tablePDF.endHeaders();

        generateRows();
    }

    /**
     * @see org.displaytag.export.ExportView#doExport(java.io.Writer)
     */
    public void doExport(Writer out) throws JspException
    {
        try
        {
            // Initialize the table with the appropriate number of columns
            initTable();

            // Initialize the OutputStream to which the PDF file is written
            WriterOutputStream stream = new WriterOutputStream(out, this.model.getEncoding());

            // Initialize the Document and register it with PdfWriter listener and the OutputStream
            Document document = new Document(PageSize.A4.rotate(), 36, 36, 72, 72);
            PdfWriter.getInstance(document, stream);

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
        Iterator iterator = this.model.getHeaderCellList().iterator();

        while (iterator.hasNext())
        {
            HeaderCell headerCell = (HeaderCell) iterator.next();

            String columnHeader = headerCell.getTitle();

            if (columnHeader == null)
            {
                columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
            }

            Cell hdrCell = new Cell(columnHeader);
            hdrCell.setGrayFill(0.9f);
            tablePDF.addCell(hdrCell);
        }
    }

    /**
     * Generates all the row cells.
     * @throws JspException for errors during value retrieving from the table model
     */
    protected void generateRows() throws JspException
    {
        //get the correct iterator (full or partial list according to the exportFull field)
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

                Cell cell = new Cell(ObjectUtils.toString(value));

                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablePDF.addCell(cell);
            }
        }
    }

    /**
     * @see org.displaytag.export.ExportView#outputPage()
     */
    public boolean outputPage()
    {
        return false;
    }

    /**
     * The opposite of java.io.OutputStreamWriter: allow a stream to be written to a writer. (this is really a bad
     * design, but needed if <code>getOut()</code> has already been called in response).
     * @author Fabrizio Giustina
     * @version $Revision$ ($Author$)
     */
    private static class WriterOutputStream extends OutputStream
    {

        /**
         * Wrapped writer.
         */
        private Writer writer;

        /**
         * Response encoding.
         */
        private String encoding;

        /**
         * Instantiate a new outputStream which writes to <code>writer</code>.
         * @param out Writer (usually a JspWriter)
         * @param charEncoding response encoding
         */
        public WriterOutputStream(Writer out, String charEncoding)
        {
            this.writer = out;
            this.encoding = charEncoding;
        }

        /**
         * Write a byte to the jsp writer.
         * @see java.io.OutputStream#write(int)
         */
        public void write(int b) throws IOException
        {
            writer.write(new String(new byte[]{(byte) b}, encoding));
        }
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
        public SeverityEnum getSeverity()
        {
            return SeverityEnum.ERROR;
        }
    }
}
