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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.HtmlTagUtil;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.ShortToStringStyle;
import org.displaytag.util.TagConstants;


/**
 * DataObject representing the column header. The header cell contains all the properties common to cells in the same
 * column.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class HeaderCell
{

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

    /**
     * is the column sortable?
     */
    private boolean sortable;

    /**
     * ColumnDecorator.
     */
    private ColumnDecorator columnDecorator;

    /**
     * column number.
     */
    private int columnNumber;

    /**
     * is the column sorted?
     */
    private boolean alreadySorted;

    /**
     * property name to look up in the bean.
     */
    private String beanPropertyName;

    /**
     * show null values?
     */
    private boolean showNulls;

    /**
     * max length of cell content.
     */
    private int maxLength;

    /**
     * max number of words for cell content.
     */
    private int maxWords;

    /**
     * autolink url?
     */
    private boolean autoLink;

    /**
     * group the column?
     */
    private int group;

    /**
     * Name of the non-decorated property used during sorting.
     */
    private String sortPropertyName;

    /**
     * getter for the grouping index.
     * @return 0 if the column is not grouped or the grouping order
     */
    public int getGroup()
    {
        return this.group;
    }

    /**
     * setter for the grouping index.
     * @param groupingOrder int grouping order (>0)
     */
    public void setGroup(int groupingOrder)
    {
        this.group = groupingOrder;
    }

    /**
     * is autolink enabled?
     * @return true if autolink is enabled for the column
     */
    public boolean getAutoLink()
    {
        return this.autoLink;
    }

    /**
     * enable or disable autolink for the column.
     * @param autoLinkEnabled boolean autolink enabled
     */
    public void setAutoLink(boolean autoLinkEnabled)
    {
        this.autoLink = autoLinkEnabled;
    }

    /**
     * getter for the max number of characters to display in the column.
     * @return int number of characters to display in the column
     */
    public int getMaxLength()
    {
        return this.maxLength;
    }

    /**
     * setter for the max number of characters to display in the column.
     * @param numOfChars number of characters to display in the column
     */
    public void setMaxLength(int numOfChars)
    {
        this.maxLength = numOfChars;
    }

    /**
     * getter for the max number of words to display in the column.
     * @return int number of words to display in the column
     */
    public int getMaxWords()
    {
        return this.maxWords;
    }

    /**
     * setter for the max number of words to display in the column.
     * @param numOfWords number of words to display in the column
     */
    public void setMaxWords(int numOfWords)
    {
        this.maxWords = numOfWords;
    }

    /**
     * Should null be displayed?
     * @return true null will be displayed in cell content
     */
    public boolean getShowNulls()
    {
        return this.showNulls;
    }

    /**
     * Enable or disable displaying of null values.
     * @param outputNulls boolean true if null should be displayed
     */
    public void setShowNulls(boolean outputNulls)
    {
        this.showNulls = outputNulls;
    }

    /**
     * Getter for the name of the property to look up in the bean.
     * @return String name of the property to look up in the bean
     */
    public String getBeanPropertyName()
    {
        return this.beanPropertyName;
    }

    /**
     * Setter for the name of the property to look up in the bean.
     * @param propertyName - name of the property to look up in the bean
     */
    public void setBeanPropertyName(String propertyName)
    {
        this.beanPropertyName = propertyName;
    }

    /**
     * Is the column already sorted?
     * @return true if the column already sorted
     */
    public boolean isAlreadySorted()
    {
        return this.alreadySorted;
    }

    /**
     * Setter for the sorted property (the column is actually sorted).
     */
    public void setAlreadySorted()
    {
        this.alreadySorted = true;
    }

    /**
     * Getter for the column number.
     * @return int column number
     */
    public int getColumnNumber()
    {
        return this.columnNumber;
    }

    /**
     * Setter for the column number.
     * @param number - int column number
     */
    public void setColumnNumber(int number)
    {
        this.columnNumber = number;
    }

    /**
     * Returns the columnDecorator object for this column.
     * @return ColumnDecorator
     */
    public ColumnDecorator getColumnDecorator()
    {
        return this.columnDecorator;
    }

    /**
     * Sets the columnDecorator object for this column.
     * @param decorator - the ColumnDecorator
     */
    public void setColumnDecorator(ColumnDecorator decorator)
    {
        this.columnDecorator = decorator;
    }

    /**
     * Is the column sortable?
     * @return true if the column is sortable
     */
    public boolean getSortable()
    {
        return this.sortable;
    }

    /**
     * is the column sortable?
     * @param isSortable - true if the column can be sorted
     */
    public void setSortable(boolean isSortable)
    {
        this.sortable = isSortable;
    }

    /**
     * Gets the column title.
     * @return the column title. If no title is specified the capitalized bean property name is returned
     */
    public String getTitle()
    {
        if (this.title != null)
        {
            return this.title;
        }
        else if (this.beanPropertyName != null)
        {
            return StringUtils.capitalize(this.beanPropertyName);
        }

        return TagConstants.EMPTY_STRING;
    }

    /**
     * Setter for the column title.
     * @param value - the column title
     */
    public void setTitle(String value)
    {
        this.title = value;
    }

    /**
     * Returns the HtmlAttributeMap containg all the html attributes for the <strong>td </strong> tags.
     * @return HtmlAttributeMap with td attributes
     */
    public HtmlAttributeMap getHtmlAttributes()
    {
        return this.htmlAttributes;
    }

    /**
     * Sets the HtmlAttributeMap containg all the html attributes for the <strong>td </strong> tags.
     * @param attributes HtmlAttributeMap
     */
    public void setHtmlAttributes(HtmlAttributeMap attributes)
    {
        this.htmlAttributes = attributes;
    }

    /**
     * returns the HtmlAttributeMap containg all the html attributes for the <strong>th </strong> tag.
     * @return HtmlAttributeMap with th attributes
     */
    public HtmlAttributeMap getHeaderAttributes()
    {
        return this.headerAttributes;
    }

    /**
     * Sets the HtmlAttributeMap containg all the html attributes for the <strong>th </strong> tag.
     * @param attributes HtmlAttributeMap
     */
    public void setHeaderAttributes(HtmlAttributeMap attributes)
    {
        this.headerAttributes = attributes;
    }

    /**
     * Adds a css class to the html "class" attribute.
     * @param cssClass String
     */
    public void addHeaderClass(String cssClass)
    {
        // null safe
        if (StringUtils.isBlank(cssClass))
        {
            return;
        }

        // if headerAttributes has not been set, instantiates a new map
        if (headerAttributes == null)
        {
            headerAttributes = new HtmlAttributeMap();
        }

        Object classAttributes = this.headerAttributes.get(TagConstants.ATTRIBUTE_CLASS);

        // handle multiple values
        if (classAttributes == null)
        {
            this.headerAttributes.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(cssClass));
        }
        else
        {
            ((MultipleHtmlAttribute) classAttributes).addAttributeValue(cssClass);
        }
    }

    /**
     * return the open tag for a cell (td).
     * @return String &lt;td> tag with attributes
     */
    public String getOpenTag()
    {
        return HtmlTagUtil.createOpenTagString(TagConstants.TAGNAME_COLUMN, this.htmlAttributes);
    }

    /**
     * return the open tag for a column header (th).
     * @return String &lt;th&gt; tag with attributes
     */
    public String getHeaderOpenTag()
    {
        return HtmlTagUtil.createOpenTagString(TagConstants.TAGNAME_COLUMN_HEADER, this.headerAttributes);
    }

    /**
     * return the closing tag for a cell (td).
     * @return String &lt;/td&gt;
     */
    public String getCloseTag()
    {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN + TagConstants.TAG_CLOSE;
    }

    /**
     * return the closing tag for a column header (th).
     * @return String &lt;/th&gt;
     */
    public String getHeaderCloseTag()
    {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN_HEADER + TagConstants.TAG_CLOSE;
    }

    /**
     * Setter for the href to be used for dinamic links in cells.
     * @param baseHref base href for links
     */
    public void setHref(Href baseHref)
    {
        this.href = baseHref;
    }

    /**
     * Getter for the href to be used for dinamic links in cells.
     * @return Href base href for links
     */
    public Href getHref()
    {
        return this.href;
    }

    /**
     * Setter for the name of the param to add to links.
     * @param name name of the param
     */
    public void setParamName(String name)
    {
        this.paramName = name;
    }

    /**
     * Getter for the name of the param to add to links.
     * @return String name of the param
     */
    public String getParamName()
    {
        return this.paramName;
    }

    /**
     * Setter for the name of the property to look up in bean to get the param value for links.
     * @param property name of the property to look up in bean to get the param value for links
     */
    public void setParamProperty(String property)
    {
        this.paramProperty = property;
    }

    /**
     * Getter for the name of the property to look up in bean to get the param value for links.
     * @return String name of the property to look up in bean to get the param value for links
     */
    public String getParamProperty()
    {
        return this.paramProperty;
    }

    /**
     * Getter for the name of the property in the bean which will be used for sorting.
     * @return String name of the property in the bean which will be used for sorting
     */
    public String getSortProperty()
    {
        return this.sortPropertyName;
    }

    /**
     * Setter for the name of the property in the bean which will be used for sorting.
     * @param propertyName - name of the property in the bean which will be used for sorting
     */
    public void setSortProperty(String propertyName)
    {
        this.sortPropertyName = propertyName;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ShortToStringStyle.SHORT_STYLE) //
            .append("columnNumber", this.columnNumber) //$NON-NLS-1$
            .append("title", this.title) //$NON-NLS-1$
            .append("beanPropertyName", this.beanPropertyName) //$NON-NLS-1$
            .toString();
    }
}