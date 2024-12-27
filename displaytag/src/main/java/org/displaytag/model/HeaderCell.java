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

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.properties.SortOrderEnum;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.HtmlTagUtil;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;

/**
 * DataObject representing the column header. The header cell contains all the properties common to cells in the same
 * column.
 */
public class HeaderCell {

    /**
     * Map containing the html tag attributes for cells (td).
     */
    private HtmlAttributeMap htmlAttributes;

    /**
     * Map containing the html tag attributes for header cells (td).
     */
    private HtmlAttributeMap headerAttributes;

    /**
     * base href for creating dinamic links.
     */
    private Href href;

    /**
     * param name used in adding a link.
     */
    private String paramName;

    /**
     * property of the object where to get the param value from.
     */
    private String paramProperty;

    /**
     * column title.
     */
    private String title;

    /** is the column sortable?. */
    private boolean sortable;

    /** Name given to the server when sorting this column. */
    private String sortName;

    /**
     * ColumnDecorators.
     */
    private DisplaytagColumnDecorator[] columnDecorators;

    /**
     * column number.
     */
    private int columnNumber;

    /** is the column sorted?. */
    private boolean alreadySorted;

    /**
     * property name to look up in the bean.
     */
    private String beanPropertyName;

    /** show null values?. */
    private boolean showNulls;

    /**
     * max length of cell content.
     */
    private int maxLength;

    /**
     * max number of words for cell content.
     */
    private int maxWords;

    /** group the column?. */
    private int group = -1;

    /**
     * Name of the non-decorated property used during sorting.
     */
    private String sortPropertyName;

    /** Should we be attempting to tabulate the totals?. */
    private boolean totaled;

    /**
     * Defalt sort order for this column.
     */
    private SortOrderEnum defaultSortOrder;

    /**
     * The running total for the column.
     */
    private double total;

    /**
     * Use this comparator for sorting.
     */
    private Comparator<Object> comparator;

    /**
     * getter for the grouping index.
     *
     * @return 0 if the column is not grouped or the grouping order
     */
    public int getGroup() {
        return this.group;
    }

    /**
     * setter for the grouping index.
     *
     * @param groupingOrder
     *            int grouping order (int &gt; 0)
     */
    public void setGroup(final int groupingOrder) {
        this.group = groupingOrder;
    }

    /**
     * getter for the max number of characters to display in the column.
     *
     * @return int number of characters to display in the column
     */
    public int getMaxLength() {
        return this.maxLength;
    }

    /**
     * setter for the max number of characters to display in the column.
     *
     * @param numOfChars
     *            number of characters to display in the column
     */
    public void setMaxLength(final int numOfChars) {
        this.maxLength = numOfChars;
    }

    /**
     * getter for the max number of words to display in the column.
     *
     * @return int number of words to display in the column
     */
    public int getMaxWords() {
        return this.maxWords;
    }

    /**
     * setter for the max number of words to display in the column.
     *
     * @param numOfWords
     *            number of words to display in the column
     */
    public void setMaxWords(final int numOfWords) {
        this.maxWords = numOfWords;
    }

    /**
     * Should null be displayed?.
     *
     * @return true null will be displayed in cell content
     */
    public boolean getShowNulls() {
        return this.showNulls;
    }

    /**
     * Enable or disable displaying of null values.
     *
     * @param outputNulls
     *            boolean true if null should be displayed
     */
    public void setShowNulls(final boolean outputNulls) {
        this.showNulls = outputNulls;
    }

    /**
     * Getter for the name of the property to look up in the bean.
     *
     * @return String name of the property to look up in the bean
     */
    public String getBeanPropertyName() {
        return this.beanPropertyName;
    }

    /**
     * Setter for the name of the property to look up in the bean.
     *
     * @param propertyName
     *            - name of the property to look up in the bean
     */
    public void setBeanPropertyName(final String propertyName) {
        this.beanPropertyName = propertyName;
    }

    /**
     * Is the column already sorted?.
     *
     * @return true if the column already sorted
     */
    public boolean isAlreadySorted() {
        return this.alreadySorted;
    }

    /**
     * Setter for the sorted property (the column is actually sorted).
     */
    public void setAlreadySorted() {
        this.alreadySorted = true;
    }

    /**
     * Getter for the column number.
     *
     * @return int column number
     */
    public int getColumnNumber() {
        return this.columnNumber;
    }

    /**
     * Setter for the column number.
     *
     * @param number
     *            - int column number
     */
    public void setColumnNumber(final int number) {
        this.columnNumber = number;
    }

    /**
     * Returns the columnDecorator object for this column.
     *
     * @return DisplaytagColumnDecorator
     */
    public DisplaytagColumnDecorator[] getColumnDecorators() {
        return this.columnDecorators != null ? this.columnDecorators : new DisplaytagColumnDecorator[0];
    }

    /**
     * Sets the columnDecorator object for this column.
     *
     * @param decorator
     *            - the DisplaytagColumnDecorator
     */
    public void setColumnDecorators(final DisplaytagColumnDecorator[] decorator) {
        this.columnDecorators = decorator;
    }

    /**
     * Is the column sortable?.
     *
     * @return true if the column is sortable
     */
    public boolean getSortable() {
        return this.sortable;
    }

    /**
     * is the column sortable?.
     *
     * @param isSortable
     *            - true if the column can be sorted
     */
    public void setSortable(final boolean isSortable) {
        this.sortable = isSortable;
    }

    /**
     * Get name given to server for sorting this column.
     *
     * @return name given to server for sorting this column
     */
    public String getSortName() {
        return this.sortName;
    }

    /**
     * Set name given to server for sorting this column.
     *
     * @param sortName
     *            name given to server for sorting this column
     */
    public void setSortName(final String sortName) {
        this.sortName = sortName;
    }

    /**
     * Gets the column title.
     *
     * @return the column title. If no title is specified the capitalized bean property name is returned
     */
    public String getTitle() {
        if (this.title != null) {
            return this.title;
        }
        if (this.beanPropertyName != null) {
            return StringUtils.capitalize(this.beanPropertyName);
        }

        return TagConstants.EMPTY_STRING;
    }

    /**
     * Setter for the column title.
     *
     * @param value
     *            - the column title
     */
    public void setTitle(final String value) {
        this.title = value;
    }

    /**
     * Returns the HtmlAttributeMap containg all the html attributes for the <strong>td </strong> tags.
     *
     * @return HtmlAttributeMap with td attributes
     */
    public HtmlAttributeMap getHtmlAttributes() {
        return this.htmlAttributes;
    }

    /**
     * Sets the HtmlAttributeMap containg all the html attributes for the <strong>td </strong> tags.
     *
     * @param attributes
     *            HtmlAttributeMap
     */
    public void setHtmlAttributes(final HtmlAttributeMap attributes) {
        this.htmlAttributes = attributes;
    }

    /**
     * returns the HtmlAttributeMap containg all the html attributes for the <strong>th </strong> tag.
     *
     * @return HtmlAttributeMap with th attributes
     */
    public HtmlAttributeMap getHeaderAttributes() {
        return this.headerAttributes;
    }

    /**
     * Sets the HtmlAttributeMap containg all the html attributes for the <strong>th </strong> tag.
     *
     * @param attributes
     *            HtmlAttributeMap
     */
    public void setHeaderAttributes(final HtmlAttributeMap attributes) {
        this.headerAttributes = attributes;
    }

    /**
     * Adds a css class to the html "class" attribute.
     *
     * @param cssClass
     *            String
     */
    public void addHeaderClass(final String cssClass) {
        // null safe
        if (StringUtils.isBlank(cssClass)) {
            return;
        }

        // if headerAttributes has not been set, instantiates a new map
        if (this.headerAttributes == null) {
            this.headerAttributes = new HtmlAttributeMap();
        }

        final Object classAttributes = this.headerAttributes.get(TagConstants.ATTRIBUTE_CLASS);

        // handle multiple values
        if (classAttributes == null) {
            this.headerAttributes.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(cssClass));
        } else {
            ((MultipleHtmlAttribute) classAttributes).addAttributeValue(cssClass);
        }
    }

    /**
     * return the open tag for a column header (th).
     *
     * @return String &lt;th&gt; tag with attributes
     */
    public String getHeaderOpenTag() {
        return HtmlTagUtil.createOpenTagString(TagConstants.TAGNAME_COLUMN_HEADER, this.headerAttributes);
    }

    /**
     * return the closing tag for a cell (td).
     *
     * @return String &lt;/td&gt;
     */
    public String getCloseTag() {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN + TagConstants.TAG_CLOSE;
    }

    /**
     * return the closing tag for a column header (th).
     *
     * @return String &lt;/th&gt;
     */
    public String getHeaderCloseTag() {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN_HEADER + TagConstants.TAG_CLOSE;
    }

    /**
     * Setter for the href to be used for dinamic links in cells.
     *
     * @param baseHref
     *            base href for links
     */
    public void setHref(final Href baseHref) {
        this.href = baseHref;
    }

    /**
     * Getter for the href to be used for dinamic links in cells.
     *
     * @return Href base href for links
     */
    public Href getHref() {
        return this.href;
    }

    /**
     * Setter for the name of the param to add to links.
     *
     * @param name
     *            name of the param
     */
    public void setParamName(final String name) {
        this.paramName = name;
    }

    /**
     * Getter for the name of the param to add to links.
     *
     * @return String name of the param
     */
    public String getParamName() {
        return this.paramName;
    }

    /**
     * Setter for the name of the property to look up in bean to get the param value for links.
     *
     * @param property
     *            name of the property to look up in bean to get the param value for links
     */
    public void setParamProperty(final String property) {
        this.paramProperty = property;
    }

    /**
     * Getter for the name of the property to look up in bean to get the param value for links.
     *
     * @return String name of the property to look up in bean to get the param value for links
     */
    public String getParamProperty() {
        return this.paramProperty;
    }

    /**
     * Getter for the name of the property in the bean which will be used for sorting.
     *
     * @return String name of the property in the bean which will be used for sorting
     */
    public String getSortProperty() {
        return this.sortPropertyName;
    }

    /**
     * Setter for the name of the property in the bean which will be used for sorting.
     *
     * @param propertyName
     *            - name of the property in the bean which will be used for sorting
     */
    public void setSortProperty(final String propertyName) {
        this.sortPropertyName = propertyName;
    }

    /**
     * Sets the default sort order for this column.
     *
     * @return default order
     */
    public SortOrderEnum getDefaultSortOrder() {
        return this.defaultSortOrder;
    }

    /**
     * Gets the default sort order for this column.
     *
     * @param order
     *            default order
     */
    public void setDefaultSortOrder(final SortOrderEnum order) {
        this.defaultSortOrder = order;
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
                .append("columnNumber", this.columnNumber) //$NON-NLS-1$
                .append("title", this.title) //$NON-NLS-1$
                .append("beanPropertyName", this.beanPropertyName) //$NON-NLS-1$
                .toString();
    }

    /**
     * Set the column comparator.
     *
     * @param columnComparator
     *            the value
     */
    public void setComparator(final Comparator<Object> columnComparator) {
        this.comparator = columnComparator;
    }

    /**
     * Get the comparator for sorting this column.
     *
     * @return the comparator
     */
    public Comparator<Object> getComparator() {
        return this.comparator;
    }

    /**
     * Will we be keeping a total for this column?.
     *
     * @return true if we are totaling
     */
    public boolean isTotaled() {
        return this.totaled;
    }

    /**
     * Setter for totaled.
     *
     * @param isTotaled
     *            the value
     */
    public void setTotaled(final boolean isTotaled) {
        this.totaled = isTotaled;
    }

    /**
     * Add the value of this parameter to the column total. The param will be converted to a number via a property
     * Converter.
     *
     * @param value
     *            the value
     *
     * @see Converter#convert(Class, Object)
     */
    private void addToTotal(final Object value) {
        if (value instanceof Number) {
            this.total = this.total + ((Number) value).doubleValue();
        }
    }

    /**
     * Get the current total.
     *
     * @return the current total.
     */
    public double getTotal() {
        return this.total;
    }

    /**
     * Add a new cell to this column.
     *
     * @param column
     *            the value
     */
    public void addCell(final Column column) {
        // Not actually going to hold a reference to the added cell - we just need access for the totals
        if (this.totaled) {
            try {
                final Object val = column.getValue(false);
                this.addToTotal(val);
            } catch (ObjectLookupException | DecoratorException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
