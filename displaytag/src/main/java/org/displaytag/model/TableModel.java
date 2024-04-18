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

import jakarta.servlet.jsp.PageContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.render.TableTotaler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Table Model. Holds table data for presentation.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class TableModel {

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(TableModel.class);

    /**
     * list of HeaderCell.
     */
    private final List<HeaderCell> headerCellList;

    /**
     * full list (contains Row objects).
     */
    private final List<Row> rowListFull;

    /**
     * list of data to be displayed in page.
     */
    private List<Row> rowListPage;

    /**
     * Name of the column currently sorted (only used when sort=external).
     */
    private String sortedColumnName;

    /** sort order = ascending?. */
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
    private final TableProperties properties;

    /**
     * Starting offset for elements in the viewable list.
     */
    private int pageOffset;

    /**
     * Response encoding.
     */
    private final String encoding;

    /** Are we sorting locally? (Default True). */
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
    private final PageContext pageContext;

    /**
     * Current media.
     */
    private MediaTypeEnum media;

    /**
     * Uses post for links.
     */
    private String form;

    /** The totaler. */
    private TableTotaler totaler;

    /**
     * Column visibilities to apply sort column properly
     */
    private List<Boolean> columnVisibilities = new ArrayList<>();

    /**
     * Constructor for TableModel.
     *
     * @param tableProperties
     *            table properties
     * @param charEncoding
     *            response encoding
     * @param pageContext
     *            the page context
     */
    public TableModel(final TableProperties tableProperties, final String charEncoding, final PageContext pageContext) {
        this.rowListFull = new ArrayList<>(20);
        this.headerCellList = new ArrayList<>(20);
        this.properties = tableProperties;
        this.encoding = charEncoding;
        this.pageContext = pageContext;
    }

    /**
     * Returns the jsp page context.
     *
     * @return page context
     */
    protected PageContext getPageContext() {
        return this.pageContext;
    }

    /**
     * Gets the current media type.
     *
     * @return current media (html, pdf ...)
     */
    public MediaTypeEnum getMedia() {
        return this.media;
    }

    /**
     * sets the current media type.
     *
     * @param media
     *            current media (html, pdf ...)
     */
    public void setMedia(final MediaTypeEnum media) {
        this.media = media;
    }

    /**
     * Sets whether the table performs local in memory sorting of the data.
     *
     * @param localSort
     *            the new local sort
     */
    public void setLocalSort(final boolean localSort) {
        this.localSort = localSort;
    }

    /**
     * Checks if is local sort.
     *
     * @return sorting in local memory
     */
    public boolean isLocalSort() {
        return this.localSort;
    }

    /**
     * Getter for <code>form</code>.
     *
     * @return Returns the form.
     */
    public String getForm() {
        return this.form;
    }

    /**
     * Setter for <code>form</code>.
     *
     * @param form
     *            The form to set.
     */
    public void setForm(final String form) {
        this.form = form;
    }

    /**
     * Getter for <code>pageOffset</code>.
     *
     * @return Returns the page offset.
     */
    public int getPageOffset() {
        return this.pageOffset;
    }

    /**
     * Sets the starting offset for elements in the viewable list.
     *
     * @param offset
     *            The page offset to set.
     */
    public void setPageOffset(final int offset) {
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
     * get the table id.
     *
     * @return table id
     */
    public String getId() {
        return this.id;
    }

    /**
     * get the full list.
     *
     * @return the full list containing Row objects
     */
    public List<Row> getRowListFull() {
        return this.rowListFull;
    }

    /**
     * gets the partial (paginated) list.
     *
     * @return the partial list to display in page (contains Row objects)
     */
    public List<Row> getRowListPage() {
        return this.rowListPage;
    }

    /**
     * adds a Row object to the table.
     *
     * @param row
     *            Row
     */
    public void addRow(final Row row) {
        row.setParentTable(this);

        if (TableModel.log.isDebugEnabled()) {
            TableModel.log.debug("[{}] adding row {}", this.id, row);
        }
        this.rowListFull.add(row);
    }

    /**
     * sets the name of the currently sorted column.
     *
     * @param sortedColumnName
     *            the new sorted column name
     */
    public void setSortedColumnName(final String sortedColumnName) {
        this.sortedColumnName = sortedColumnName;
    }

    /**
     * sets the sort full table property. If true the full list is sorted, if false sorting is applied only to the
     * displayed sublist.
     *
     * @param sortFull
     *            boolean
     */
    public void setSortFullTable(final boolean sortFull) {
        this.sortFullTable = sortFull;
    }

    /**
     * return the sort full table property.
     *
     * @return boolean true if sorting is applied to the full list
     */
    public boolean isSortFullTable() {
        return this.sortFullTable;
    }

    /**
     * return the sort order of the page.
     *
     * @return true if sort order is ascending
     */
    public boolean isSortOrderAscending() {
        return this.sortOrderAscending;

    }

    /**
     * set the sort order of the list.
     *
     * @param isSortOrderAscending
     *            true to sort in ascending order
     */
    public void setSortOrderAscending(final boolean isSortOrderAscending) {
        this.sortOrderAscending = isSortOrderAscending;
    }

    /**
     * Sets the row list page.
     *
     * @param rowList
     *            - the new value for this.rowListPage
     */
    public void setRowListPage(final List<Row> rowList) {
        this.rowListPage = rowList;
    }

    /**
     * getter for the Table Decorator.
     *
     * @return TableDecorator
     */
    public TableDecorator getTableDecorator() {
        return this.tableDecorator;
    }

    /**
     * setter for the table decorator.
     *
     * @param decorator
     *            - the TableDecorator object
     */
    public void setTableDecorator(final TableDecorator decorator) {
        this.tableDecorator = decorator;
    }

    /**
     * returns true if the table is sorted.
     *
     * @return boolean true if the table is sorted
     */
    public boolean isSorted() {
        return this.sortedColumn != -1;
    }

    /**
     * returns the HeaderCell for the sorted column.
     *
     * @return HeaderCell
     */
    public HeaderCell getSortedColumnHeader() {
        if (this.sortedColumn < 0 || this.sortedColumn > this.headerCellList.size() - 1) {
            return null;
        }
        return this.headerCellList.get(this.sortedColumn);
    }

    /**
     * return the number of columns in the table.
     *
     * @return int number of columns
     */
    public int getNumberOfColumns() {
        return this.headerCellList.size();
    }

    /**
     * return true is the table has no columns.
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return this.headerCellList.isEmpty();
    }

    /**
     * return the index of the sorted column.
     *
     * @return index of the sorted column or -1 if the table is not sorted
     */
    public int getSortedColumnNumber() {
        return this.sortedColumn;
    }

    /**
     * set the sorted column index.
     *
     * @param sortIndex
     *            - the index of the sorted column
     */
    public void setSortedColumnNumber(final int sortIndex) {
        this.sortedColumn = sortIndex;
    }

    /**
     * Adds a column header (HeaderCell object).
     *
     * @param headerCell
     *            HeaderCell
     */
    public void addColumnHeader(final HeaderCell headerCell) {
        if (this.sortedColumnName == null) {
            if (this.sortedColumn == this.headerCellList.size()) {
                headerCell.setAlreadySorted();
            }
        } else // the sorted parameter was a string so try and find that column name and set it as sorted
        if (this.sortedColumnName.equals(headerCell.getSortName())) {
            headerCell.setAlreadySorted();
        }
        headerCell.setColumnNumber(this.headerCellList.size());

        this.headerCellList.add(headerCell);
    }

    /**
     * List containing headerCell objects.
     *
     * @return List containing headerCell objects
     */
    public List<HeaderCell> getHeaderCellList() {
        return this.headerCellList;
    }

    /**
     * returns a RowIterator on the requested (full|page) list.
     *
     * @param full
     *            if <code>true</code> returns an iterator on te full list, if <code>false</code> only on the viewable
     *            part.
     *
     * @return RowIterator
     *
     * @see org.displaytag.model.RowIterator
     */
    public RowIterator getRowIterator(final boolean full) {
        final RowIterator iterator = new RowIterator(full ? this.rowListFull : this.rowListPage, this.headerCellList,
                this.tableDecorator, this.pageOffset);
        // copy id for logging
        iterator.setId(this.id);
        return iterator;
    }

    /**
     * sorts the given list of Rows. The method is called internally by sortFullList() and sortPageList().
     *
     * @param list
     *            List
     */
    private void sortRowList(final List<Row> list) {
        if (this.isSorted()) {
            int oldSortedColumn = this.sortedColumn;
            for (int i = 0; i < oldSortedColumn && i < columnVisibilities.size() && this.sortedColumn > 0; i++) {
                if (!columnVisibilities.get(i)) {
                    this.sortedColumn--;
                }
            }
            try {
                final HeaderCell sortedHeaderCell = this.getSortedColumnHeader();

                // If it is an explicit value, then sort by that, otherwise sort by the property...
                if ((sortedHeaderCell != null) && (sortedHeaderCell.getBeanPropertyName() != null
                        || this.sortedColumn != -1 && this.sortedColumn < this.headerCellList.size())) {
                    final String sorted = sortedHeaderCell.getSortProperty() != null ? sortedHeaderCell.getSortProperty()
                            : sortedHeaderCell.getBeanPropertyName();

                    Collections.sort(list, new RowSorter(this.sortedColumn, sorted, this.getTableDecorator(),
                            this.sortOrderAscending, sortedHeaderCell.getComparator()));
                }
            } finally {
                this.sortedColumn = oldSortedColumn;
            }
        }
    }

    /**
     * sort the list displayed in page.
     */
    public void sortPageList() {
        if (TableModel.log.isDebugEnabled()) {
            TableModel.log.debug("[{}] sorting page list", this.id);
        }
        this.sortRowList(this.rowListPage);

    }

    /**
     * sort the full list of data.
     */
    public void sortFullList() {
        if (TableModel.log.isDebugEnabled()) {
            TableModel.log.debug("[{}] sorting full data", this.id);
        }
        this.sortRowList(this.rowListFull);
    }

    /**
     * Returns the table properties.
     *
     * @return the configured table properties.
     */
    public TableProperties getProperties() {
        return this.properties;
    }

    /**
     * Getter for character encoding.
     *
     * @return Returns the encoding used for response.
     */
    public String getEncoding() {
        return this.encoding;
    }

    /**
     * Obtain this table's caption.
     *
     * @return This table's caption.
     */
    public String getCaption() {
        return this.caption;
    }

    /**
     * Set this table's caption.
     *
     * @param caption
     *            This table's caption.
     */
    public void setCaption(final String caption) {
        this.caption = caption;
    }

    /**
     * Obtain this table's footer.
     *
     * @return This table's footer.
     */
    public String getFooter() {
        return this.footer;
    }

    /**
     * Set this table's footer.
     *
     * @param footer
     *            This table's footer.
     */
    public void setFooter(final String footer) {
        this.footer = footer;
    }

    /**
     * To string.
     *
     * @return the string
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
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
                .append("caption", this.caption) // $NON-NLS-1
                .append("footer", this.footer) // $NON-NLS-1
                .append("media", this.media) // $NON-NLS-1
                .toString();
    }

    /**
     * Gets the totaler.
     *
     * @return the totaler
     */
    public TableTotaler getTotaler() {
        return this.totaler;
    }

    /**
     * Sets the totaler.
     *
     * @param totaler
     *            the new totaler
     */
    public void setTotaler(final TableTotaler totaler) {
        this.totaler = totaler;
    }

    /**
     * Reset.
     */
    public void reset() {
        this.totaler.reset();
        this.totaler.init(this);
    }

    /**
     * Returns the column visibilities to apply sort column properly.
     *
     * @return The column visibilities.
     */
    public List<Boolean> getColumnVisibilities() {
        return this.columnVisibilities;
    }
}
