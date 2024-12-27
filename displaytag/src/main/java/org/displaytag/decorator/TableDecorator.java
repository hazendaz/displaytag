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
package org.displaytag.decorator;

import org.displaytag.exception.ObjectLookupException;
import org.displaytag.render.TableWriterTemplate;
import org.displaytag.util.LookupUtil;
import org.displaytag.util.TagConstants;

/**
 * The Class TableDecorator.
 */
public abstract class TableDecorator extends Decorator {

    /**
     * object representing the current row.
     */
    private Object currentRowObject;

    /**
     * index in displayed list.
     */
    private int viewIndex = -1;

    /**
     * index in original list.
     */
    private int listIndex = -1;

    /**
     * Return the index in the displayed list.
     *
     * @return int index in the displayed list
     */
    public final int getViewIndex() {
        return this.viewIndex;
    }

    /**
     * Return the index in the full list (view index + offset). Note that the index returned if from the
     * <strong>sorted</strong> list, and not from the original one.
     *
     * @return int index in the full list
     */
    public final int getListIndex() {
        return this.listIndex;
    }

    /**
     * Get the object representing the current row.
     *
     * @return Object
     */
    public final Object getCurrentRowObject() {
        return this.currentRowObject;
    }

    /**
     * Initialize the current row. Note this method is also called when sorting a table using a property supplied by the
     * table decorator, so the method could be called multiple times during rendering. When used to initialize sorting
     * the method is always called with 0, 0 as currentViewIndex and currentListIndex.
     *
     * @param rowObject
     *            object representing the current row
     * @param currentViewIndex
     *            int index in the displayed list
     * @param currentListIndex
     *            int index in the original list
     */
    public final void initRow(final Object rowObject, final int currentViewIndex, final int currentListIndex) {
        this.currentRowObject = rowObject;
        this.viewIndex = currentViewIndex;
        this.listIndex = currentListIndex;
    }

    /**
     * Called at the beginning of a row. Can be subclassed to provide specific data at the beginning of a row
     *
     * @return null in the default implementation
     */
    public String startRow() {
        return null;
    }

    /**
     * Called at the end of a row. Can be subclassed to provide specific data at the end of a row
     *
     * @return null in the default implementation
     */
    public String finishRow() {
        return null;
    }

    /**
     * Called at the end of evaluation. Can be subclassed to eventully clean up data. Always remember to also call
     * super.finish()!
     */
    @Override
    public void finish() {
        this.currentRowObject = null;
        super.finish();
    }

    /**
     * Call back to add an additional row class to the current row.
     *
     * @return CSS class attribute value for the current row
     *
     * @since 1.1
     */
    public String addRowClass() {
        return null;
    }

    /**
     * Call back to allow setting an "id" attribute on a row.
     *
     * @return HTML id attribute value for the current row
     *
     * @since 1.1
     */
    public String addRowId() {
        return null;
    }

    /**
     * Indicates that we are begining a new group.
     *
     * @param value
     *            of the current cell
     * @param group
     *            number of the current column
     */
    public void startOfGroup(final String value, final int group) {
    }

    /**
     * Called at the end of a group. Can be subclassed to provide specific data at the end of a row.
     *
     * @param value
     *            of the current cell
     * @param groupThatHasEnded
     *            number of the current column
     */
    public void endOfGroup(final String value, final int groupThatHasEnded) {
    }

    /**
     * What value should I display in this cell? The default value for grouped columns is to not display any value if
     * the cellValue has not changed on an interior iteration. Only invoked for columns that are grouped.
     *
     * @param cellValue
     *            the cell value
     * @param groupingStatus
     *            the grouping status
     * @param columnNumber
     *            the column number
     *
     * @return the value to display
     */
    public String displayGroupedValue(final String cellValue, final short groupingStatus, final int columnNumber) {
        if (groupingStatus == TableWriterTemplate.GROUP_END || groupingStatus == TableWriterTemplate.GROUP_NO_CHANGE) {
            return TagConstants.EMPTY_STRING;
        }
        return cellValue;
    }

    /**
     * Checks if is last row.
     *
     * @return true, if is last row
     */
    public boolean isLastRow() {
        return this.getViewIndex() == this.tableModel.getRowListPage().size() - 1;
    }

    /**
     * Shortcut for evaluating properties in the current row object. Can be useful for implementing anonymous decorators
     * in jsp pages without having to know/import the decorated object Class.
     *
     * @param propertyName
     *            property to lookup in current row object. Can also be a nested or indexed property.
     *
     * @return the object
     *
     * @since 1.1
     */
    protected Object evaluate(final String propertyName) {
        try {
            return LookupUtil.getBeanProperty(this.getCurrentRowObject(), propertyName);
        } catch (final ObjectLookupException e) {
            return null;
        }
    }

}
