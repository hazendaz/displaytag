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

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.displaytag.decorator.AutolinkColumnDecorator;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.util.Anchor;
import org.displaytag.util.CompatibleUrlEncoder;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.HtmlTagUtil;
import org.displaytag.util.LookupUtil;
import org.displaytag.util.ShortToStringStyle;
import org.displaytag.util.TagConstants;


/**
 * Represents a column in a table.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Column
{

    /**
     * Row this column belongs to.
     */
    private Row row;

    /**
     * Header of this column. The header cell contains all the attributes common to all cells in the same column
     */
    private HeaderCell header;

    /**
     * copy of the attribute map from the header cell. Needed to change attributes (title) in this cell only
     */
    private HtmlAttributeMap htmlAttributes;

    /**
     * contains the evaluated body value. Filled in getOpenTag.
     */
    private String stringValue;

    /**
     * Cell.
     */
    private Cell cell;

    /**
     * Constructor for Column.
     * @param headerCell HeaderCell
     * @param currentCell Cell
     * @param parentRow Row
     */
    public Column(HeaderCell headerCell, Cell currentCell, Row parentRow)
    {
        this.header = headerCell;
        this.row = parentRow;
        this.cell = currentCell;

        // also copy html attributes
        this.htmlAttributes = headerCell.getHtmlAttributes();
    }

    /**
     * Gets the value, after calling the table / column decorator is requested.
     * @param decorated boolean
     * @return Object
     * @throws ObjectLookupException for errors in bean property lookup
     * @throws DecoratorException if a column decorator is used and an exception is thrown during value decoration
     */
    public Object getValue(boolean decorated) throws ObjectLookupException, DecoratorException
    {

        Object object = null;

        // a static value has been set?
        if (this.cell.getStaticValue() != null)
        {
            object = this.cell.getStaticValue();
        }
        else if (this.header.getBeanPropertyName() != null)
        {
            TableDecorator tableDecorator = this.row.getParentTable().getTableDecorator();

            // if a decorator has been set, and if decorator has a getter for the requested property only, check
            // decorator
            if (decorated && tableDecorator != null && tableDecorator.hasGetterFor(this.header.getBeanPropertyName()))
            {

                object = LookupUtil.getBeanProperty(tableDecorator, this.header.getBeanPropertyName());
            }
            else
            {
                // else check underlining object
                object = LookupUtil.getBeanProperty(this.row.getObject(), this.header.getBeanPropertyName());
            }
        }

        if (decorated && (this.header.getColumnDecorator() != null))
        {
            object = this.header.getColumnDecorator().decorate(object);
        }

        if (object == null || object.equals("null")) //$NON-NLS-1$
        {
            if (!this.header.getShowNulls())
            {
                object = TagConstants.EMPTY_STRING;
            }
        }

        return object;
    }

    /**
     * Generates the cell open tag.
     * @return String td open tag
     * @throws ObjectLookupException for errors in bean property lookup
     * @throws DecoratorException if a column decorator is used and an exception is thrown during value decoration
     */
    public String getOpenTag() throws ObjectLookupException, DecoratorException
    {
        this.stringValue = createChoppedAndLinkedValue();

        return HtmlTagUtil.createOpenTagString(TagConstants.TAGNAME_COLUMN, this.htmlAttributes);
    }

    /**
     * Generates the cell close tag (&lt;/td>).
     * @return String td closing tag
     */
    public String getCloseTag()
    {
        this.stringValue = null;
        return this.header.getCloseTag();
    }

    /**
     * Calculates the cell content, cropping or linking the value as needed.
     * @return String
     * @throws ObjectLookupException for errors in bean property lookup
     * @throws DecoratorException if a column decorator is used and an exception is thrown during value decoration
     */
    public String createChoppedAndLinkedValue() throws ObjectLookupException, DecoratorException
    {

        String fullValue = ObjectUtils.toString(getValue(true));
        String choppedValue;

        // are we supposed to set up a link to the data being displayed in this column?
        if (this.header.getAutoLink())
        {
            fullValue = AutolinkColumnDecorator.INSTANCE.decorate(fullValue);
        }

        // trim the string if a maxLength or maxWords is defined
        if (this.header.getMaxLength() > 0)
        {
            choppedValue = HtmlTagUtil.abbreviateHtmlString(fullValue, this.header.getMaxLength(), false);
        }
        else if (this.header.getMaxWords() > 0)
        {
            choppedValue = HtmlTagUtil.abbreviateHtmlString(fullValue, this.header.getMaxWords(), true);
        }
        else
        {
            choppedValue = fullValue;
        }

        // chopped content? add the full content to the column "title" attribute
        if (choppedValue.length() < fullValue.length())
        {
            // clone the attribute map, don't want to add title to all the columns
            this.htmlAttributes = (HtmlAttributeMap) this.htmlAttributes.clone();
            // add title
            this.htmlAttributes.put(TagConstants.ATTRIBUTE_TITLE, HtmlTagUtil.stripHTMLTags(fullValue));
        }

        if (this.header.getHref() != null)
        {
            // generates the href for the link
            Href colHref = getColumnHref(fullValue);
            Anchor anchor = new Anchor(colHref, choppedValue);
            choppedValue = anchor.toString();
        }

        return choppedValue;
    }

    /**
     * Generates the href for the column using paramName/property/scope.
     * @param columnContent column body
     * @return generated Href
     * @throws ObjectLookupException for errors in lookin up object properties
     */
    private Href getColumnHref(String columnContent) throws ObjectLookupException
    {
        // copy href
        Href colHref = new Href(this.header.getHref());

        // do we need to add a param?
        if (this.header.getParamName() != null)
        {

            Object paramValue;

            if (this.header.getParamProperty() != null)
            {
                // different property, go get it
                paramValue = LookupUtil.getBeanProperty(this.row.getObject(), this.header.getParamProperty());

            }
            else
            {
                // same property as content
                paramValue = columnContent;
            }

            if (paramValue != null)
            {
                colHref.addParameter(this.header.getParamName(), CompatibleUrlEncoder.encode(
                    paramValue.toString(),
                    this.row.getParentTable().getEncoding()));
            }
        }
        return colHref;
    }

    /**
     * get the final value to be displayed in the table. This method can only be called after getOpenTag(), where the
     * content is evaluated
     * @return String final value to be displayed in the table
     */
    public String getChoppedAndLinkedValue()
    {
        return this.stringValue;
    }

    /**
     * returns the grouping order of this column or -1 if the column is not grouped.
     * @return int grouping order of this column or -1 if the column is not grouped
     */
    public int getGroup()
    {
        return this.header.getGroup();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ShortToStringStyle.SHORT_STYLE) //
            .append("cell", this.cell) //$NON-NLS-1$
            .append("header", this.header) //$NON-NLS-1$
            .append("htmlAttributes", this.htmlAttributes) //$NON-NLS-1$
            .append("stringValue", this.stringValue) //$NON-NLS-1$
            .toString();
    }
}