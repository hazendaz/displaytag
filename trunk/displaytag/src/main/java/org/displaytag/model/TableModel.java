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
    private static Log log = LogFactory.getLog(TableModel.class);

    /**
     * list of HeaderCell
     */
    private List headerCellList;

    /**
     * full list (contains Row objects)
     */
    private List rowListFull;

    /**
     * list of data to be displayed in page
     */
    private List rowListPage;

    /**
     * sort order = ascending?
     */
    private boolean sortOrderAscending = true;

    /**
     * sort full List? (false sort only displayed page)
     */
    private boolean sortFullTable = true;

    /**
     * index of the sorted column (-1 if the table is not sorted)
     */
    private int sortedColumn = -1;

    /**
     * Table decorator
     */
    private TableDecorator tableDecorator;

    /**
     * id inherited from the TableTag (needed only for logging)
     */
    private String id;

    /**
     * Constructor for TableModel
     */
    public TableModel()
    {
        this.rowListFull = new ArrayList(20);
        this.headerCellList = new ArrayList(20);
    }

    /**
     * Setter for the tablemodel id
     * @param tableId same id of table tag, needed for logging
     */
    public void setId(String tableId)
    {
        this.id = tableId;
    }

    /**
     * get the full list
     * @return the full list containing Row objects
     */
    public List getRowListFull()
    {
        return this.rowListFull;
    }

    /**
     * get the partial (paginated) list
     * @return the partial list to display in page (contains Row objects)
     */
    public List getRowListPage()
    {
        return this.rowListPage;
    }

    /**
     * add a Row object to the table
     * @param row Row
     */
    public void addRow(Row row)
    {
        row.setParentTable(this);

        if (log.isDebugEnabled())
        {
            log.debug("[" + id + "] adding row " + row);
        }
        this.rowListFull.add(row);
    }

    /**
     * set the sort full table property. If true the full list is sorted, if false sorting is applied only to the
     * displayed sublist
     * @param sortFull boolean
     */
    public void setSortFullTable(boolean sortFull)
    {
        this.sortFullTable = sortFull;
    }

    /**
     * return the sort full table property
     * @return boolean true if sorting is applied to the full list
     */
    public boolean isSortFullTable()
    {
        return this.sortFullTable;
    }

    /**
     * return the sort order of the page
     * @return true if sort order is ascending
     */
    public boolean isSortOrderAscending()
    {
        return this.sortOrderAscending;

    }

    /**
     * set the sort order of the list
     * @param isSortOrderAscending true to sort in ascending order
     */
    public void setSortOrderAscending(boolean isSortOrderAscending)
    {
        this.sortOrderAscending = isSortOrderAscending;
    }

    /**
     * @param rowList - the new value for this.rowListPage
     */
    public void setRowListPage(List rowList)
    {
        this.rowListPage = rowList;
    }

    /**
     * getter for the Table Decorator
     * @return TableDecorator
     */
    public TableDecorator getTableDecorator()
    {
        return this.tableDecorator;
    }

    /**
     * setter for the table decorator
     * @param decorator - the TableDecorator object
     */
    public void setTableDecorator(TableDecorator decorator)
    {
        this.tableDecorator = decorator;
    }

    /**
     * return true if the table is sorted
     * @return boolean true if the table is sorted
     */
    public boolean isSorted()
    {
        return this.sortedColumn != -1;
    }

    /**
     * return the HeaderCell for the sorted column
     * @return HeaderCell
     */
    public HeaderCell getSortedColumnHeader()
    {
        if (this.sortedColumn < 0 || (this.sortedColumn > (this.headerCellList.size() - 1)))
        {
            return null;
        }
        return (HeaderCell) this.headerCellList.get(this.sortedColumn);
    }

    /**
     * return the number of columns in the table
     * @return int number of columns
     */
    public int getNumberOfColumns()
    {
        return this.headerCellList.size();
    }

    /**
     * return true is the table has no columns
     * @return boolean
     */
    public boolean isEmpty()
    {
        return this.headerCellList.size() == 0;
    }

    /**
     * return the index of the sorted column
     * @return index of the sorted column or -1 if the table is not sorted
     */
    public int getSortedColumnNumber()
    {
        return this.sortedColumn;
    }

    /**
     * set the sorted column index
     * @param sortIndex - the index of the sorted column
     */
    public void setSortedColumnNumber(int sortIndex)
    {
        this.sortedColumn = sortIndex;
    }

    /**
     * Adds a column header (HeaderCell object)
     * @param headerCell HeaderCell
     */
    public void addColumnHeader(HeaderCell headerCell)
    {
        if (this.sortedColumn == this.headerCellList.size())
        {
            headerCell.setAlreadySorted();
        }
        headerCell.setColumnNumber(this.headerCellList.size());

        this.headerCellList.add(headerCell);
    }

    /**
     * List containing headerCell objects
     * @return List containing headerCell objects
     */
    public List getHeaderCellList()
    {
        return this.headerCellList;
    }

    /**
     * returns a RowIterator on the requested (full|page) list
     * @return RowIterator
     * @see org.displaytag.model.RowIterator
     */
    public RowIterator getRowIterator()
    {
        RowIterator iterator = new RowIterator(this.rowListPage, this.headerCellList, this.tableDecorator);
        // copy id for logging
        iterator.setId(id);
        return iterator;
    }

    /**
     * returns a RowIterator on the _full_ list. Needed for export views
     * @return RowIterator
     * @see org.displaytag.model.RowIterator
     */
    public RowIterator getFullListRowIterator()
    {
        RowIterator iterator = new RowIterator(this.rowListFull, this.headerCellList, this.tableDecorator);
        // copy id for logging
        iterator.setId(id);
        return iterator;
    }

    /**
     * sorts the given list of Rows. The method is called internally by sortFullList() and sortPageList()
     * @param list List
     */
    private void sortRowList(List list)
    {
        if (isSorted())
        {
            HeaderCell sortedHeaderCell = getSortedColumnHeader();

            if (sortedHeaderCell != null)
            {
                // If it is an explicit value, then sort by that, otherwise sort by the property...
                if (sortedHeaderCell.getBeanPropertyName() != null
                    || (this.sortedColumn != -1 && this.sortedColumn < this.headerCellList.size()))
                {
                    Collections.sort(
                        list,
                        new RowSorter(
                            this.sortedColumn,
                            sortedHeaderCell.getBeanPropertyName(),
                            getTableDecorator(),
                            this.sortOrderAscending));
                }
            }

        }

    }

    /**
     * sort the list displayed in page
     */
    public void sortPageList()
    {
        if (log.isDebugEnabled())
        {
            log.debug("[" + id + "] sorting page list");
        }
        sortRowList(this.rowListPage);

    }

    /**
     * sort the full list of data
     */
    public void sortFullList()
    {
        if (log.isDebugEnabled())
        {
            log.debug("[" + id + "] sorting full data");
        }
        sortRowList(this.rowListFull);
    }

}