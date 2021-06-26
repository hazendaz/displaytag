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
package org.displaytag.model;

import java.util.Iterator;
import java.util.List;


/**
 * Iterator on columns.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ColumnIterator
{

    /**
     * current row.
     */
    private Row parentRow;

    /**
     * Internal iterator on header cells.
     */
    private Iterator<HeaderCell> headerIterator;

    /**
     * Internal iterator on cells.
     */
    private Iterator<Cell> cellIterator;

    /**
     * Creates a new ColumnIterator given a list of column and a row.
     * @param columns List containing column objects
     * @param row current Row
     */
    public ColumnIterator(List<HeaderCell> columns, Row row)
    {
        this.headerIterator = columns.iterator();
        this.cellIterator = row.getCellList().iterator();
        this.parentRow = row;
    }

    /**
     * Are there more columns?.
     *
     * @return boolean <code>true</code> if there are more columns
     */
    public boolean hasNext()
    {
        return this.headerIterator.hasNext();
    }

    /**
     * Returns the next column.
     * @return Column next column
     */
    public Column nextColumn()
    {
        HeaderCell header = this.headerIterator.next();

        Cell cell = Cell.EMPTY_CELL;

        // if cells is not present simply return an empty cell.
        // this is needed for automatic properties discovery
        if (this.cellIterator.hasNext())
        {
            cell = this.cellIterator.next();
        }

        // create a new column using the next value in the header and cell iterators and returns it
        return new Column(header, cell, this.parentRow);
    }

}