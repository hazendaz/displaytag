package org.displaytag.export;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;

/**
 * <p>
 * Base abstract class for simple export views
 * </p>
 * <p>
 * A class wich extends BaseExportView simple need to provide delimiters for rows and columns
 * </p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public abstract class BaseExportView
{

    /**
     * logger
     */
    private static Log log = LogFactory.getLog(BaseExportView.class);

    /**
     * TableModel to render
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
     * Constructor for BaseExportView
     * @param tableModel TableModel to render
     * @param exportFullList boolean export full list?
     * @param includeHeader should header be included in export?
     */
    public BaseExportView(TableModel tableModel, boolean exportFullList, boolean includeHeader)
    {
        this.model = tableModel;
        this.exportFull = exportFullList;
        this.header = includeHeader;
    }

    /**
     * String to add before a row
     * @return String
     */
    protected abstract String getRowStart();

    /**
     * String to add after a row
     * @return String
     */
    protected abstract String getRowEnd();

    /**
     * String to add before a cell
     * @return String
     */
    protected abstract String getCellStart();

    /**
     * String to add after a cell
     * @return String
     */
    protected abstract String getCellEnd();

    /**
     * String to add to the top of document
     * @return String
     */
    protected abstract String getDocumentStart();

    /**
     * String to add to the end of document
     * @return String
     */
    protected abstract String getDocumentEnd();

    /**
     * always append cell end string?
     * @return boolean
     */
    protected abstract boolean getAlwaysAppendCellEnd();

    /**
     * always append row end string?
     * @return boolean
     */
    protected abstract boolean getAlwaysAppendRowEnd();

    /**
     * MimeType to return
     * @return String myme type
     */
    public abstract String getMimeType();

    /**
     * Write table header
     * @return String rendered header
     */
    protected String doHeaders()
    {

        final String ROW_START = getRowStart();
        final String ROW_END = getRowEnd();
        final String CELL_START = getCellStart();
        final String CELL_END = getCellEnd();
        final boolean ALWAYS_APPEND_CELL_END = getAlwaysAppendCellEnd();

        StringBuffer buffer = new StringBuffer(1000);

        Iterator iterator = this.model.getHeaderCellList().iterator();

        // start row
        buffer.append(ROW_START);

        while (iterator.hasNext())
        {
            log.debug("iterator.hasNext()");
            HeaderCell headerCell = (HeaderCell) iterator.next();

            String columnHeader = headerCell.getTitle();

            if (columnHeader == null)
            {
                columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
            }

            buffer.append(CELL_START);

            buffer.append(escapeColumnValue(columnHeader));

            if (ALWAYS_APPEND_CELL_END || iterator.hasNext())
            {
                buffer.append(CELL_END);
            }
        }

        // end row
        buffer.append(ROW_END);

        return buffer.toString();

    }

    /**
     * Write the rendered table
     * @return String rendered table body
     * @throws JspException for errors during cell value lookup
     */
    public String doExport() throws JspException
    {

        StringBuffer buffer = new StringBuffer(8000);

        final String DOCUMENT_START = getDocumentStart();
        final String DOCUMENT_END = getDocumentEnd();
        final String ROW_START = getRowStart();
        final String ROW_END = getRowEnd();
        final String CELL_START = getCellStart();
        final String CELL_END = getCellEnd();
        final boolean ALWAYS_APPEND_CELL_END = getAlwaysAppendCellEnd();
        final boolean ALWAYS_APPEND_ROW_END = getAlwaysAppendRowEnd();

        // document start
        buffer.append(DOCUMENT_START);

        if (this.header)
        {
            buffer.append(doHeaders());
        }

        //get the correct iterator (full or partial list according to the mExportFullList field)
        RowIterator rowIterator = this.exportFull ? this.model.getFullListRowIterator() : this.model.getRowIterator();

        // iterator on rows
        while (rowIterator.hasNext())
        {
            log.debug("lRowIterator.hasNext()");
            Row row = rowIterator.next();
            log.debug("lRow=" + row);

            if (this.model.getTableDecorator() != null)
            {

                String stringStartRow = this.model.getTableDecorator().startRow();
                if (stringStartRow != null)
                {
                    buffer.append(stringStartRow);
                }

            }

            // iterator on columns
            ColumnIterator columnIterator = row.getColumnIterator(this.model.getHeaderCellList());

            buffer.append(ROW_START);

            while (columnIterator.hasNext())
            {
                Column column = columnIterator.nextColumn();

                Object value;

                // Get the value to be displayed for the column
                value = column.getValue(true);

                buffer.append(CELL_START);
                buffer.append(escapeColumnValue(value));

                if (ALWAYS_APPEND_CELL_END || columnIterator.hasNext())
                {
                    buffer.append(CELL_END);
                }

            }
            if (ALWAYS_APPEND_ROW_END || rowIterator.hasNext())
            {
                buffer.append(ROW_END);
            }
        }

        // document start
        buffer.append(DOCUMENT_END);

        return buffer.toString();

    }

    /**
     * can be implemented to escape values for different output
     * @param value original column value
     * @return escaped column value
     */
    protected abstract Object escapeColumnValue(Object value);

}