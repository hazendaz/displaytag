package org.displaytag.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.TableDecorator;

/**
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class TableModel
{

    /**
     * logger
     */
    private static Log mLog = LogFactory.getLog(TableModel.class);

    /** list of HeaderCell **/
    private List mHeaderCellList;

    /** full list (contains Row objects) **/
    private List mRowListFull;

    /** list of data to be displayed in page **/
    private List mRowListPage;

    /** sort order = ascending? **/
    private boolean mSortOrderAscending = true;

    /** sort full List? (false sort only displayed page) **/
    private boolean mSortFullTable = true;

    /**
     * index of the sorted column (-1 if the table is not sorted)
     */
    private int mSortColumn = -1;

    /** Table decorator **/
    private TableDecorator mTableDecorator;

    /**
     * Constructor for TableModel
     */
    public TableModel()
    {
        mRowListFull = new ArrayList(20);
        mHeaderCellList = new ArrayList(20);
    }

    /**
     * get the full list
     * @return the full list containing Row objects
     */
    public List getRowListFull()
    {
        return mRowListFull;
    }

    /**
     * get the partial (paginated) list
     * @return the partial list to display in page (contains Row objects)
     */
    public List getRowListPage()
    {
        return mRowListPage;
    }

    /**
     * add a Row object to the table
     * @param pRow Row
     */
    public void addRow(Row pRow)
    {
        pRow.setParentTable(this);

        mLog.debug("adding row");

        mRowListFull.add(pRow);
    }

    /**
     * set the sort full table property. If true the full list is sorted,
     * if false sorting is applied only to the displayed sublist
     * @param pSortFullTable boolean
     */
    public void setSortFullTable(boolean pSortFullTable)
    {
        mSortFullTable = pSortFullTable;
    }

    /**
     * return the sort full table property
     * @return boolean true if sorting is applied to the full list
     */
    public boolean isSortFullTable()
    {
        return mSortFullTable;
    }

    /**
     * return the sort order of the page
     * @return true if sort order is ascending
     */
    public boolean isSortOrderAscending()
    {
        return mSortOrderAscending;

    }

    /**
     * set the sort order of the list
     * @param pSortOrderAscending true to sort in ascending order
     */
    public void setSortOrderAscending(boolean pSortOrderAscending)
    {
        mSortOrderAscending = pSortOrderAscending;
    }

    /**
     *
     * @param pRowListPage - the new value for mRowListPage
     */
    public void setRowListPage(List pRowListPage)
    {

        mRowListPage = pRowListPage;
    }

    /**
     * getter for the Table Decorator
     * @return TableDecorator
     */
    public TableDecorator getTableDecorator()
    {
        return mTableDecorator;
    }

    /**
     * setter for the table decorator
     * @param pTableDecorator - the TableDecorator object
     */
    public void setTableDecorator(TableDecorator pTableDecorator)
    {
        mTableDecorator = pTableDecorator;
    }

    /**
     * return true if the table is sorted
     * @return boolean true if the table is sorted
     */
    public boolean isSorted()
    {
        return mSortColumn != -1;
    }

    /**
     * return the HeaderCell for the sorted column
     * @return HeaderCell
     */
    public HeaderCell getSortedColumnHeader()
    {
        if (mSortColumn < 0 || (mSortColumn > (mHeaderCellList.size() - 1)))
        {
            return null;
        }
        return (HeaderCell) mHeaderCellList.get(mSortColumn);
    }

    /**
     * return the number of columns in the table
     * @return int number of columns
     */
    public int getNumberOfColumns()
    {
        return mHeaderCellList.size();
    }

    /**
     * return true is the table has no columns
     * @return boolean
     */
    public boolean isEmpty()
    {
        return mHeaderCellList.size() == 0;
    }

    /**
     * return the index of the sorted column
     * @return index of the sorted column or -1 if the table is not sorted
     */
    public int getSortedColumnNumber()
    {
        return mSortColumn;
    }

    /**
     * set the sorted column index
     * @param pSortColumn - the index of the sorted column
     */
    public void setSortedColumnNumber(int pSortColumn)
    {
        mSortColumn = pSortColumn;
    }

    /**
     * Method addColumnHeader
     * @param pHeaderCell HeaderCell
     */
    public void addColumnHeader(HeaderCell pHeaderCell)
    {
        if (mSortColumn == mHeaderCellList.size())
        {
            pHeaderCell.setAlreadySorted();
        }
        pHeaderCell.setColumnNumber(mHeaderCellList.size());

        mHeaderCellList.add(pHeaderCell);
    }

    /**
     * Method getHeaderCellList
     * @return List
     */
    public List getHeaderCellList()
    {
        return mHeaderCellList;
    }

    /**
     * returns a RowIterator on the requested (full|page) list
     * @return RowIterator
     * @see org.displaytag.model.RowIterator
     */
    public RowIterator getRowIterator()
    {
        return new RowIterator(mRowListPage, mHeaderCellList, mTableDecorator);
    }

    /**
     * returns a RowIterator on the _full_ list. Needed for export views
     * @return RowIterator
     * @see org.displaytag.model.RowIterator
     */
    public RowIterator getFullListRowIterator()
    {
        return new RowIterator(mRowListFull, mHeaderCellList, mTableDecorator);
    }

    /**
     * Method sortRowList
     * @param pList List
     */
    private void sortRowList(List pList)
    {
        mLog.debug("sortRowList()");

        if (isSorted())
        {
            HeaderCell lSortedHeaderCell = getSortedColumnHeader();

            if (lSortedHeaderCell != null)
            {
                // If it is an explicit value, then sort by that, otherwise sort by the property...
                if (lSortedHeaderCell.getBeanPropertyName() != null
                    || (mSortColumn != -1 && mSortColumn < mHeaderCellList.size()))
                {
                    Collections.sort(
                        pList,
                        new RowSorter(
                            mSortColumn,
                            lSortedHeaderCell.getBeanPropertyName(),
                            getTableDecorator(),
                            mSortOrderAscending));
                }
            }

        }

    }

    /**
     * sort the list displayed in page
     */
    public void sortPageList()
    {
        mLog.debug("sortFullData()");
        sortRowList(mRowListPage);

    }

    /**
     * sort the full list of data
     */
    public void sortFullList()
    {
        mLog.debug("sort FullData");
        sortRowList(mRowListFull);
    }

}