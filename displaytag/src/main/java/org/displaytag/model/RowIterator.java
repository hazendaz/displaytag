/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.model;

import java.util.Iterator;
import java.util.List;

import org.displaytag.decorator.TableDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Iterator on table rows.
 */
public class RowIterator {

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(RowIterator.class);

    /**
     * internal iterator for Rows.
     */
    private final Iterator<Row> iterator;

    /**
     * row number counter.
     */
    private int rowNumber;

    /**
     * reference to the table TableDecorator.
     */
    private final TableDecorator decorator;

    /**
     * id inherited from the TableTag (needed only for logging).
     */
    private String id;

    /**
     * Starting offset for items n the current page. Needed to calculare the index in the original list
     */
    private final int pageOffset;

    /**
     * Constructor for RowIterator.
     *
     * @param rowList
     *            List containing Row objects
     * @param columnList
     *            List containing CellHeader objects
     * @param tableDecorator
     *            TableDecorator
     * @param offset
     *            Starting offset for items n the current page
     */
    protected RowIterator(final List<Row> rowList, final List<HeaderCell> columnList,
            final TableDecorator tableDecorator, final int offset) {
        this.iterator = rowList.iterator();
        this.rowNumber = 0;
        this.decorator = tableDecorator;
        this.pageOffset = offset;
    }

    /**
     * Setter for the tablemodel id.
     *
     * @param tableId
     *            same id of table tag, needed for logging
     */
    public void setId(final String tableId) {
        this.id = tableId;
    }

    /**
     * Check if a next row exist.
     *
     * @return boolean true if a new row
     */
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    /**
     * Gets the page offset.
     *
     * @return the page offset
     */
    public int getPageOffset() {
        return this.pageOffset;
    }

    /**
     * Returns the next row object.
     *
     * @return Row
     */
    public Row next() {

        final int currentRowNumber = this.rowNumber++;

        if (RowIterator.log.isDebugEnabled()) {
            RowIterator.log.debug("[{}] rowIterator.next() row number={}", this.id, currentRowNumber);
        }

        final Row row = this.iterator.next();

        row.setRowNumber(currentRowNumber);

        if (this.decorator != null) {
            this.decorator.initRow(row.getObject(), currentRowNumber, currentRowNumber + this.getPageOffset());
        }

        return row;

    }

}
