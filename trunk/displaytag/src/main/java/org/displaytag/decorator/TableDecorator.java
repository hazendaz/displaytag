package org.displaytag.decorator;

/**
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public abstract class TableDecorator extends Decorator
{
    /**
     * object representing the current row
     */
    private Object currentRowObject;

    /**
     * index in displayed list
     */
    private int viewIndex = -1;

    /**
     * index in original list
     */
    private int listIndex = -1;

    /**
     * return the index in the displayed list
     * @return int index in the displayed list
     */
    public final int getViewIndex()
    {
        return viewIndex;
    }

    /**
     * return the index in the original list
     * @return int index in the original list
     */
    public final int getListIndex()
    {
        return listIndex;
    }

    /**
     * Returns the object representing the current row
     * @return Object
     */
    public final Object getCurrentRowObject()
    {
        return currentRowObject;
    }

    /**
     * Initialize the current row.
     * Note this method is also called when sorting a table using a property supplied by the table decorator, so the
     * method could be called multiple times during rendering. When used to initialize sorting the method is
     * always called with 0, 0 as currentViewIndex and currentListIndex.
     * @param rowObject object representing the current row
     * @param currentViewIndex int index in the displayed list
     * @param currentListIndex int index in the original list
     */
    public final void initRow(Object rowObject, int currentViewIndex, int currentListIndex)
    {
        currentRowObject = rowObject;
        viewIndex = currentViewIndex;
        listIndex = currentListIndex;
    }

    /**
     * Method called at the beginning of a row. Can be subclassed to provide specific data at
     * the beginning of a row
     * @return empty String in the default implementation
     */
    public String startRow()
    {
        return "";
    }

    /**
     * Method called at the end of a row. Can be subclassed to provide specific data at
     * the end of a row
     * @return empty String in the default implementation
     */
    public String finishRow()
    {
        return "";
    }

    /**
     * Method called at the end of evaluation. Can be subclassed to eventully clean up
     * data. Always remember to also call super.finish()!
     */
    public void finish()
    {
        currentRowObject = null;
        super.finish();
    }

}
