/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
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

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import jakarta.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Base abstract class for simple export views.
 * </p>
 * <p>
 * A class that extends BaseExportView simply need to provide delimiters for rows and columns.
 * </p>
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public abstract class BaseExportView implements TextExportView {

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(BaseExportView.class);

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
     * Sets the parameters.
     *
     * @param tableModel
     *            the table model
     * @param exportFullList
     *            the export full list
     * @param includeHeader
     *            the include header
     * @param decorateValues
     *            the decorate values
     *
     * @see org.displaytag.export.ExportView#setParameters(org.displaytag.model.TableModel, boolean, boolean, boolean)
     */
    @Override
    public void setParameters(final TableModel tableModel, final boolean exportFullList, final boolean includeHeader,
            final boolean decorateValues) {
        this.model = tableModel;
        this.exportFull = exportFullList;
        this.header = includeHeader;
        this.decorated = decorateValues;
    }

    /**
     * String to add before a row.
     *
     * @return String
     */
    protected String getRowStart() {
        return null;
    }

    /**
     * String to add after a row.
     *
     * @return String
     */
    protected String getRowEnd() {
        return null;
    }

    /**
     * String to add before a cell.
     *
     * @return String
     */
    protected String getCellStart() {
        return null;
    }

    /**
     * String to add after a cell.
     *
     * @return String
     */
    protected abstract String getCellEnd();

    /**
     * String to add to the top of document.
     *
     * @return String
     */
    protected String getDocumentStart() {
        return null;
    }

    /**
     * String to add to the end of document.
     *
     * @return String
     */
    protected String getDocumentEnd() {
        return null;
    }

    /**
     * always append cell end string?.
     *
     * @return boolean
     */
    protected abstract boolean getAlwaysAppendCellEnd();

    /**
     * always append row end string?.
     *
     * @return boolean
     */
    protected abstract boolean getAlwaysAppendRowEnd();

    /**
     * can be implemented to escape values for different output.
     *
     * @param value
     *            original column value
     *
     * @return escaped column value
     */
    protected abstract String escapeColumnValue(Object value);

    /**
     * Write table header.
     *
     * @return String rendered header
     */
    protected String doHeaders() {

        final String ROW_START = this.getRowStart();
        final String ROW_END = this.getRowEnd();
        final String CELL_START = this.getCellStart();
        final String CELL_END = this.getCellEnd();
        final boolean ALWAYS_APPEND_CELL_END = this.getAlwaysAppendCellEnd();

        final StringBuilder buffer = new StringBuilder(1000);

        final Iterator<HeaderCell> iterator = this.model.getHeaderCellList().iterator();

        // start row
        if (ROW_START != null) {
            buffer.append(ROW_START);
        }

        while (iterator.hasNext()) {
            final HeaderCell headerCell = iterator.next();

            String columnHeader = headerCell.getTitle();

            if (columnHeader == null) {
                columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
            }

            columnHeader = this.escapeColumnValue(columnHeader);

            if (CELL_START != null) {
                buffer.append(CELL_START);
            }

            if (columnHeader != null) {
                buffer.append(columnHeader);
            }

            if (CELL_END != null && (ALWAYS_APPEND_CELL_END || iterator.hasNext())) {
                buffer.append(CELL_END);
            }
        }

        // end row
        if (ROW_END != null) {
            buffer.append(ROW_END);
        }

        return buffer.toString();

    }

    /**
     * Do export.
     *
     * @param out
     *            the out
     * @param characterEncoding
     *            the character encoding
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws JspException
     *             the jsp exception
     *
     * @see org.displaytag.export.TextExportView#doExport(java.io.Writer,java.lang.String)
     */
    @Override
    public void doExport(final Writer out, final String characterEncoding) throws IOException, JspException {
        if (BaseExportView.log.isDebugEnabled()) {
            BaseExportView.log.debug(this.getClass().getName());
        }

        final String DOCUMENT_START = this.getDocumentStart();
        final String DOCUMENT_END = this.getDocumentEnd();
        final String ROW_START = this.getRowStart();
        final String ROW_END = this.getRowEnd();
        final String CELL_START = this.getCellStart();
        final String CELL_END = this.getCellEnd();
        final boolean ALWAYS_APPEND_CELL_END = this.getAlwaysAppendCellEnd();
        final boolean ALWAYS_APPEND_ROW_END = this.getAlwaysAppendRowEnd();

        // document start
        this.write(out, DOCUMENT_START);

        if (this.header) {
            this.write(out, this.doHeaders());
        }

        // get the correct iterator (full or partial list according to the exportFull field)
        final RowIterator rowIterator = this.model.getRowIterator(this.exportFull);

        // iterator on rows
        while (rowIterator.hasNext()) {
            final Row row = rowIterator.next();

            if (this.model.getTableDecorator() != null) {

                final String stringStartRow = this.model.getTableDecorator().startRow();
                this.write(out, stringStartRow);
            }

            // iterator on columns
            final ColumnIterator columnIterator = row.getColumnIterator(this.model.getHeaderCellList());

            this.write(out, ROW_START);

            while (columnIterator.hasNext()) {
                final Column column = columnIterator.nextColumn();

                // Get the value to be displayed for the column
                final String value = this.escapeColumnValue(column.getValue(this.decorated));

                this.write(out, CELL_START);

                this.write(out, value);

                if (ALWAYS_APPEND_CELL_END || columnIterator.hasNext()) {
                    this.write(out, CELL_END);
                }

            }
            if (ALWAYS_APPEND_ROW_END || rowIterator.hasNext()) {
                this.write(out, ROW_END);
            }
        }

        // document end
        this.write(out, DOCUMENT_END);

    }

    /**
     * Write a String, checking for nulls value.
     *
     * @param out
     *            output writer
     * @param string
     *            String to be written
     *
     * @throws IOException
     *             thrown by out.write
     */
    private void write(final Writer out, final String string) throws IOException {
        if (string != null) {
            out.write(string);
        }
    }

    /**
     * Output page.
     *
     * @return true, if successful
     *
     * @see org.displaytag.export.TextExportView#outputPage()
     */
    @Override
    public boolean outputPage() {
        return false;
    }
}
