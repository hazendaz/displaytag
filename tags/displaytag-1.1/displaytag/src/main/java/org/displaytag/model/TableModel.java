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
package org.displaytag.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;


/**
 * Table Model. Holds table data for presentation.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TableModel
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(TableModel.class);

    /**
     * list of HeaderCell.
     */
    private List headerCellList;

    /**
     * full list (contains Row objects).
     */
    private List rowListFull;

    /**
     * list of data to be displayed in page.
     */
    private List rowListPage;

    /**
     * Name of the column currently sorted (only used when sort=external).
     */
    private String sortedColumnName;

    /**
     * sort order = ascending?
     */
    private boolean sortOrderAscending = true;

    /**
     * sort full List? (false sort only displayed page).
     */
    private boolean sortFullTable = true;

    /**
     * index of the sorted column (-1 if the table is not sorted).
     */
    private int sortedColumn = -1;

    /**
     * Table decorator.
     */
    private TableDecorator tableDecorator;

    /**
     * id inherited from the TableTag (needed only for logging).
     */
    private String id;

    /**
     * configurable table properties.
     */
    private TableProperties properties;

    /**
     * Starting offset for elements in the viewable list.
     */
    private int pageOffset;

    /**
     * Response encoding.
     */
    private String encoding;

    /**
     * Are we sorting locally? (Default True)
     */
    private boolean localSort = true;

    /**
     * Table caption.
     */
    private String caption;

    /**
     * Table footer.
     */
    private String footer;

    /**
     * Jsp page context.
     */
    private PageContext pageContext;

    /**
     * Current media.
     */
    private MediaTypeEnum media;

    /**
     * Constructor for TableModel.
     * @param tableProperties table properties
     * @param charEncoding response encoding
     */
    public TableModel(TableProperties tableProperties, String charEncoding, PageContext pageContext)
    {
        this.rowListFull = new ArrayList(20);
        this.headerCellList = new ArrayList(20);
        this.properties = tableProperties;
        this.encoding = charEncoding;
        this.pageContext = pageContext;
    }

    /**
     * Returns the jsp page context.
     * @return page context
     */
    protected PageContext getPageContext()
    {
        return this.pageContext;
    }

    /**
     * Gets the current media type.
     * @return current media (html, pdf ...)
     */
    public MediaTypeEnum getMedia()
    {
        return this.media;
    }

    /**
     * sets the current media type.
     * @param media current media (html, pdf ...)
     */
    public void setMedia(MediaTypeEnum media)
    {
        this.media = media;
    }

    /**
     * Sets whether the table performs local in memory sorting of the data.
     * @param localSort
     */
    public void setLocalSort(boolean localSort)
    {
        this.localSort = localSort;
    }

    /**
     * @return sorting in local memory
     */
    public boolean isLocalSort()
    {
        return localSort;
    }

    /**
     * Sets the starting offset for elements in the viewable list.
     * @param offset The page offset to set.
     */
    public void setPageOffset(int offset)
    {
        this.pageOffset = offset;
    }

    /**
     * Setter for the tablemodel id.
     * @param tableId same id of table tag, needed for logging
     */
    public void setId(String tableId)
    {
        this.id = tableId;
    }

    /**
     * get the table id.
     * @return table id
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * get the full list.
     * @return the full list containing Row objects
     */
    public List getRowListFull()
    {
        return this.rowListFull;
    }

    /**
     * gets the partial (paginated) list.
     * @return the partial list to display in page (contains Row objects)
     */
    public List getRowListPage()
    {
        return this.rowListPage;
    }

    /**
     * adds a Row object to the table.
     * @param row Row
     */
    public void addRow(Row row)
    {
        row.setParentTable(this);

        if (log.isDebugEnabled())
        {
            log.debug("[" + this.id + "] adding row " + row);
        }
        this.rowListFull.add(row);
    }

    /**
     * sets the name of the currently sorted column
     * @param sortedColumnName
     */
    public void setSortedColumnName(String sortedColumnName)
    {
        this.sortedColumnName = sortedColumnName;
    }

    /**
     * sets the sort full table property. If true the full list is sorted, if false sorting is applied only to the
     * displayed sublist.
     * @param sortFull boolean
     */
    public void setSortFullTable(boolean sortFull)
    {
        this.sortFullTable = sortFull;
    }

    /**
     * return the sort full table property.
     * @return boolean true if sorting is applied to the full list
     */
    public boolean isSortFullTable()
    {
        return this.sortFullTable;
    }

    /**
     * return the sort order of the page.
     * @return true if sort order is ascending
     */
    public boolean isSortOrderAscending()
    {
        return this.sortOrderAscending;

    }

    /**
     * set the sort order of the list.
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
     * getter for the Table Decorator.
     * @return TableDecorator
     */
    public TableDecorator getTableDecorator()
    {
        return this.tableDecorator;
    }

    /**
     * setter for the table decorator.
     * @param decorator - the TableDecorator object
     */
    public void setTableDecorator(TableDecorator decorator)
    {
        this.tableDecorator = decorator;
    }

    /**
     * returns true if the table is sorted.
     * @return boolean true if the table is sorted
     */
    public boolean isSorted()
    {
        return this.sortedColumn != -1;
    }

    /**
     * returns the HeaderCell for the sorted column.
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
     * return the number of columns in the table.
     * @return int number of columns
     */
    public int getNumberOfColumns()
    {
        return this.headerCellList.size();
    }

    /**
     * return true is the table has no columns.
     * @return boolean
     */
    public boolean isEmpty()
    {
        return this.headerCellList.size() == 0;
    }

    /**
     * return the index of the sorted column.
     * @return index of the sorted column or -1 if the table is not sorted
     */
    public int getSortedColumnNumber()
    {
        return this.sortedColumn;
    }

    /**
     * set the sorted column index.
     * @param sortIndex - the index of the sorted column
     */
    public void setSortedColumnNumber(int sortIndex)
    {
        this.sortedColumn = sortIndex;
    }

    /**
     * Adds a column header (HeaderCell object).
     * @param headerCell HeaderCell
     */
    public void addColumnHeader(HeaderCell headerCell)
    {
        if (this.sortedColumnName == null)
        {
            if (this.sortedColumn == this.headerCellList.size())
            {
                headerCell.setAlreadySorted();
            }
        }
        else
        {
            // the sorted parameter was a string so try and find that column name and set it as sorted
            if (this.sortedColumnName.equals(headerCell.getSortName()))
            {
                headerCell.setAlreadySorted();
            }
        }
        headerCell.setColumnNumber(this.headerCellList.size());

        this.headerCellList.add(headerCell);
    }

    /**
     * List containing headerCell objects.
     * @return List containing headerCell objects
     */
    public List getHeaderCellList()
    {
        return this.headerCellList;
    }

    /**
     * returns a RowIterator on the requested (full|page) list.
     * @return RowIterator
     * @param full if <code>true</code> returns an iterator on te full list, if <code>false</code> only on the
     * viewable part.
     * @see org.displaytag.model.RowIterator
     */
    public RowIterator getRowIterator(boolean full)
    {
        RowIterator iterator = new RowIterator(
            full ? this.rowListFull : this.rowListPage,
            this.headerCellList,
            this.tableDecorator,
            this.pageOffset);
        // copy id for logging
        iterator.setId(this.id);
        return iterator;
    }

    /**
     * sorts the given list of Rows. The method is called internally by sortFullList() and sortPageList().
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

                    String sorted = (sortedHeaderCell.getSortProperty() != null)
                        ? sortedHeaderCell.getSortProperty()
                        : sortedHeaderCell.getBeanPropertyName();

                    Collections.sort(list, new RowSorter(
                        this.sortedColumn,
                        sorted,
                        getTableDecorator(),
                        this.sortOrderAscending,
                        sortedHeaderCell.getComparator()));
                }
            }

        }

    }

    /**
     * sort the list displayed in page.
     */
    public void sortPageList()
    {
        if (log.isDebugEnabled())
        {
            log.debug("[" + this.id + "] sorting page list");
        }
        sortRowList(this.rowListPage);

    }

    /**
     * sort the full list of data.
     */
    public void sortFullList()
    {
        if (log.isDebugEnabled())
        {
            log.debug("[" + this.id + "] sorting full data");
        }
        sortRowList(this.rowListFull);
    }

    /**
     * Returns the table properties.
     * @return the configured table properties.
     */
    public TableProperties getProperties()
    {
        return this.properties;
    }

    /**
     * Getter for character encoding.
     * @return Returns the encoding used for response.
     */
    public String getEncoding()
    {
        return encoding;
    }

    /**
     * Obtain this table's caption.
     * @return This table's caption.
     */
    public String getCaption()
    {
        return this.caption;
    }

    /**
     * Set this table's caption.
     * @param caption This table's caption.
     */
    public void setCaption(String caption)
    {
        this.caption = caption;
    }

    /**
     * Obtain this table's footer.
     * @return This table's footer.
     */
    public String getFooter()
    {
        return this.footer;
    }

    /**
     * Set this table's footer.
     * @param footer This table's footer.
     */
    public void setFooter(String footer)
    {
        this.footer = footer;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) //
            .append("rowListFull", this.rowListFull) //$NON-NLS-1$
            .append("rowListPage", this.rowListPage) //$NON-NLS-1$
            .append("properties", this.properties) //$NON-NLS-1$
            .append("empty", this.isEmpty()) //$NON-NLS-1$
            .append("encoding", this.encoding) //$NON-NLS-1$
            .append("numberOfColumns", this.getNumberOfColumns()) //$NON-NLS-1$
            .append("headerCellList", this.headerCellList) //$NON-NLS-1$
            .append("sortFullTable", this.sortFullTable) //$NON-NLS-1$
            .append("sortedColumnNumber", this.getSortedColumnNumber()) //$NON-NLS-1$
            .append("sortOrderAscending", this.sortOrderAscending) //$NON-NLS-1$
            .append("sortedColumnHeader", this.getSortedColumnHeader()) //$NON-NLS-1$
            .append("sorted", this.isSorted()) //$NON-NLS-1$
            .append("tableDecorator", this.tableDecorator) //$NON-NLS-1$
            .append("caption", this.caption) //$NON-NLS-1
            .append("footer", this.footer) //$NON-NLS-1
            .append("media", this.media) //$NON-NLS-1
            .toString();
    }

}