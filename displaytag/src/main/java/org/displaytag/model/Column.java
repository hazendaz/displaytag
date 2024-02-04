/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.util.Anchor;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.HtmlTagUtil;
import org.displaytag.util.LookupUtil;
import org.displaytag.util.TagConstants;

/**
 * Represents a column in a table.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class Column {

    /**
     * Row this column belongs to.
     */
    private final Row row;

    /**
     * Header of this column. The header cell contains all the attributes common to all cells in the same column
     */
    private final HeaderCell header;

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
    private final Cell cell;

    /**
     * Constructor for Column.
     *
     * @param headerCell
     *            HeaderCell
     * @param currentCell
     *            Cell
     * @param parentRow
     *            Row
     */
    public Column(final HeaderCell headerCell, final Cell currentCell, final Row parentRow) {
        this.header = headerCell;
        this.row = parentRow;
        this.cell = currentCell;

        // also copy html attributes
        this.htmlAttributes = headerCell.getHtmlAttributes();
    }

    /**
     * Get the header cell for this column.
     *
     * @return the cell
     */
    public HeaderCell getHeaderCell() {
        return this.header;
    }

    /**
     * Gets the value, after calling the table / column decorator is requested.
     *
     * @param decorated
     *            boolean
     *
     * @return Object will never be null if ShowNulls has been set to false
     *
     * @throws ObjectLookupException
     *             for errors in bean property lookup
     * @throws DecoratorException
     *             if a column decorator is used and an exception is thrown during value decoration
     */
    public Object getValue(final boolean decorated) throws ObjectLookupException, DecoratorException {

        Object object = null;

        // a static value has been set?
        if (this.cell.getStaticValue() != null) {
            object = this.cell.getStaticValue();
        } else if (this.header.getBeanPropertyName() != null) {

            // if a decorator has been set, and if decorator has a getter for the requested property only, check
            // decorator
            if (decorated && this.row.getParentTable().getTableDecorator() != null
                    && this.row.getParentTable().getTableDecorator().hasGetterFor(this.header.getBeanPropertyName())) {

                object = LookupUtil.getBeanProperty(this.row.getParentTable().getTableDecorator(),
                        this.header.getBeanPropertyName());
            } else {
                // else check underlining object
                object = LookupUtil.getBeanProperty(this.row.getObject(), this.header.getBeanPropertyName());
            }
        }

        final DisplaytagColumnDecorator[] decorators = this.header.getColumnDecorators();
        if (decorated) {
            for (final DisplaytagColumnDecorator decorator : decorators) {
                object = decorator.decorate(object, this.row.getParentTable().getPageContext(),
                        this.row.getParentTable().getMedia());
            }
        }

        if ((object == null || "null".equals(object)) && !this.header.getShowNulls()) {
            object = TagConstants.EMPTY_STRING;
        }

        return object;
    }

    /**
     * Generates the cell open tag.
     *
     * @return String td open tag
     */
    public String getOpenTag() {
        final HtmlAttributeMap rowAttributes = this.cell.getPerRowAttributes();

        HtmlAttributeMap atts = this.htmlAttributes;
        if (rowAttributes != null) {
            atts = (HtmlAttributeMap) atts.clone();
            atts.putAll(rowAttributes);
        }
        return HtmlTagUtil.createOpenTagString(TagConstants.TAGNAME_COLUMN, atts);
    }

    /**
     * Initialize the cell value.
     *
     * @throws DecoratorException
     *             the decorator exception
     * @throws ObjectLookupException
     *             the object lookup exception
     */
    public void initialize() throws DecoratorException, ObjectLookupException {
        if (this.stringValue == null) {
            this.stringValue = this.createChoppedAndLinkedValue();
        }
    }

    /**
     * Generates the cell close tag (&lt;/td&gt;).
     *
     * @return String td closing tag
     */
    public String getCloseTag() {
        this.stringValue = null;
        return this.header.getCloseTag();
    }

    /**
     * Calculates the cell content, cropping or linking the value as needed.
     *
     * @return String
     *
     * @throws ObjectLookupException
     *             for errors in bean property lookup
     * @throws DecoratorException
     *             if a column decorator is used and an exception is thrown during value decoration
     */
    public String createChoppedAndLinkedValue() throws ObjectLookupException, DecoratorException {

        final String fullValue = Objects.toString(this.getValue(true));
        String choppedValue;

        // trim the string if a maxLength or maxWords is defined
        if (this.header.getMaxLength() > 0) {
            choppedValue = HtmlTagUtil.abbreviateHtmlString(fullValue, this.header.getMaxLength(), false);
        } else if (this.header.getMaxWords() > 0) {
            choppedValue = HtmlTagUtil.abbreviateHtmlString(fullValue, this.header.getMaxWords(), true);
        } else {
            choppedValue = fullValue;
        }

        // chopped content? add the full content to the column "title" attribute
        // note, simply checking that length is less than before can't be enough due to the "..." added if the string is
        // cropped
        if (!Objects.equals(fullValue, choppedValue)) {
            // clone the attribute map, don't want to add title to all the columns
            this.htmlAttributes = (HtmlAttributeMap) this.htmlAttributes.clone();
            // add title
            this.htmlAttributes.put(TagConstants.ATTRIBUTE_TITLE, HtmlTagUtil.stripHTMLTags(fullValue));
        }

        if (this.header.getHref() != null) {
            // generates the href for the link
            final Href colHref = this.getColumnHref(fullValue);
            final Anchor anchor = new Anchor(colHref, choppedValue);
            choppedValue = anchor.toString();
        }

        return choppedValue;
    }

    /**
     * Generates the href for the column using paramName/property/scope.
     *
     * @param columnContent
     *            column body
     *
     * @return generated Href
     *
     * @throws ObjectLookupException
     *             for errors in lookin up object properties
     */
    private Href getColumnHref(final String columnContent) throws ObjectLookupException {
        // copy href
        final Href colHref = (Href) this.header.getHref().clone();

        // do we need to add a param?
        if (this.header.getParamName() != null) {

            Object paramValue;

            if (this.header.getParamProperty() != null) {
                // different property, go get it
                paramValue = LookupUtil.getBeanProperty(this.row.getObject(), this.header.getParamProperty());

            } else {
                // same property as content
                paramValue = columnContent;
            }

            if (paramValue != null) {
                colHref.addParameter(this.header.getParamName(), URLEncoder.encode(paramValue.toString(),
                        Charset.forName(Objects.toString(this.row.getParentTable().getEncoding(), "UTF-8")))); //$NON-NLS-1$
            }
        }
        return colHref;
    }

    /**
     * get the final value to be displayed in the table. This method can only be called after initialize(), where the
     * content is evaluated
     *
     * @return String final value to be displayed in the table
     */
    public String getChoppedAndLinkedValue() {
        return this.stringValue;
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
                .append("cell", this.cell) //$NON-NLS-1$
                .append("header", this.header) //$NON-NLS-1$
                .append("htmlAttributes", this.htmlAttributes) //$NON-NLS-1$
                .append("stringValue", this.stringValue) //$NON-NLS-1$
                .toString();
    }
}
