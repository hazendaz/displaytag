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
package org.displaytag.decorator;

/**
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public abstract class TableDecorator extends Decorator
{

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
     * return the index in the displayed list.
     * @return int index in the displayed list
     */
    public final int getViewIndex()
    {
        return this.viewIndex;
    }

    /**
     * return the index in the original list.
     * @return int index in the original list
     */
    public final int getListIndex()
    {
        return this.listIndex;
    }

    /**
     * Get the object representing the current row.
     * @return Object
     */
    public final Object getCurrentRowObject()
    {
        return this.currentRowObject;
    }

    /**
     * Initialize the current row. Note this method is also called when sorting a table using a property supplied by the
     * table decorator, so the method could be called multiple times during rendering. When used to initialize sorting
     * the method is always called with 0, 0 as currentViewIndex and currentListIndex.
     * @param rowObject object representing the current row
     * @param currentViewIndex int index in the displayed list
     * @param currentListIndex int index in the original list
     */
    public final void initRow(Object rowObject, int currentViewIndex, int currentListIndex)
    {
        this.currentRowObject = rowObject;
        this.viewIndex = currentViewIndex;
        this.listIndex = currentListIndex;
    }

    /**
     * Called at the beginning of a row. Can be subclassed to provide specific data at the beginning of a row
     * @return null in the default implementation
     */
    public String startRow()
    {
        return null;
    }

    /**
     * Called at the end of a row. Can be subclassed to provide specific data at the end of a row
     * @return null in the default implementation
     */
    public String finishRow()
    {
        return null;
    }

    /**
     * Called at the end of evaluation. Can be subclassed to eventully clean up data. Always remember to also call
     * super.finish()!
     */
    public void finish()
    {
        this.currentRowObject = null;
        super.finish();
    }

}