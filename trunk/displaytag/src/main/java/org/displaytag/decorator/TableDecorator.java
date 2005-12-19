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


import org.displaytag.model.TableModel;
import org.displaytag.util.TagConstants;
import org.displaytag.render.TableWriterTemplate;

import javax.servlet.jsp.PageContext;

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
     * The associated table model.
     */
    private TableModel tableModel;

    /**
     * Setter for tablemodel
     * @param tableModel
     */
    public void setTableModel(TableModel tableModel)
    {
        this.tableModel = tableModel;
    }

    /**
     * Getter for table model.
     * @return the table model
     */
    public TableModel getTableModel()
    {
        return tableModel;
    }



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
     * Initialize the TableTecorator instance.
     * @param context PageContext
     * @param decorated decorated object (usually a list)
     * @param model the tableModel
     */
    public void init(PageContext context, Object decorated, TableModel model)
    {
        setTableModel(model);
        this.init(context, decorated);
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

    /**
     * Call back to add an additional row class to the current row
     * @return CSS class attribute value for the current row
     */
    public String addRowClass()
    {
        return null;
    }

    /**
     * Call back to allow setting an "id" attribute on a row
     * @return HTML id attribute value for the current row
     */
    public String setRowId()
    {
        return null;
    }

    /**
     * Indicates that we are begining a new group.
     * @param value of the current cell
     * @param group number of the current column
     */
    public void startOfGroup(String value, int group)
    {
    }

    /**
     * Called at the end of a group. Can be subclassed to provide specific data at the end of a row.
     * @param value of the current cell
     * @param groupThatHasEnded number of the current column
     */
    public void endOfGroup(String value, int groupThatHasEnded)
    {
    }

    /**
     * What value should I display in this cell?  The default value for grouped columns is to not display any value if
     * the cellValue has not changed on an interior iteration.  Only invoked for columns that are grouped.
     * @param cellValue
     * @param groupingStatus
     * @return the value to display
     */
    public String displayGroupedValue(String cellValue, short groupingStatus)
    {
        if (groupingStatus == TableWriterTemplate.GROUP_END || groupingStatus == TableWriterTemplate.GROUP_NO_CHANGE)
        {
            return TagConstants.EMPTY_STRING;
        }
        else
        {
            return cellValue;
        }
    }

    public boolean isLastRow()
    {
        return ( getListIndex() == getTableModel().getRowListPage().size() - 1);
    }
}