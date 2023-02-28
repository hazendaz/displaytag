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
package org.displaytag.tags;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.displaytag.decorator.AutolinkColumnDecorator;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.decorator.EscapeXmlColumnDecorator;
import org.displaytag.decorator.MessageFormatColumnDecorator;
import org.displaytag.exception.DecoratorInstantiationException;
import org.displaytag.exception.InvalidTagAttributeValueException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.exception.TagStructureException;
import org.displaytag.model.Cell;
import org.displaytag.model.HeaderCell;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.SortOrderEnum;
import org.displaytag.util.DefaultHref;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MediaUtil;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This tag works hand in hand with the TableTag to display a list of objects. This describes a column of data in the
 * TableTag. There can be any number of columns that make up the list.
 * <p>
 * This tag does no work itself, it is simply a container of information. The TableTag does all the work based on the
 * information provided in the attributes of this tag.
 *
 * @author mraible
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class ColumnTag extends BodyTagSupport implements MediaUtil.SupportsMedia {

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(ColumnTag.class);

    /**
     * html pass-through attributes for cells.
     */
    private final HtmlAttributeMap attributeMap = new HtmlAttributeMap();

    /**
     * html pass-through attributes for cell headers.
     */
    private final HtmlAttributeMap headerAttributeMap = new HtmlAttributeMap();

    /**
     * the property method that is called to retrieve the information to be displayed in this column. This method is
     * called on the current object in the iteration for the given row. The property format is in typical struts format
     * for properties (required)
     */
    private String property;

    /**
     * the title displayed for this column. if this is omitted then the property name is used for the title of the
     * column (optional).
     */
    private String title;

    /**
     * by default, null values don't appear in the list, by setting viewNulls to 'true', then null values will appear as
     * "null" in the list (mostly useful for debugging) (optional).
     */
    private boolean nulls;

    /** is the column sortable?. */
    private boolean sortable;

    /**
     * Name given to the server when sorting this column.
     */
    private String sortName;

    /**
     * Defalt sort order for this column.
     */
    private SortOrderEnum defaultorder;

    /**
     * The comparator to use when sorting this column.
     */
    private transient Comparator<Object> comparator;

    /**
     * if set to true, then any email addresses and URLs found in the content of the column are automatically converted
     * into a hypertext link.
     */
    private boolean autolink;

    /**
     * Automatically escape column content for html and xml media.
     */
    private Boolean escapeXml;

    /**
     * A MessageFormat patter that will be used to decorate objects in the column. Can be used as a "shortcut" for
     * simple column decorations.
     */
    private String format;

    /**
     * the grouping level (starting at 1 and incrementing) of this column (indicates if successive contain the same
     * values, then they should not be displayed). The level indicates that if a lower level no longer matches, then the
     * matching for this higher level should start over as well. If this attribute is not included, then no grouping is
     * performed. (optional)
     */
    private int group = -1;

    /**
     * if this attribute is provided, then the data that is shown for this column is wrapped inside a &lt;a href&gt; tag
     * with the url provided through this attribute. Typically you would use this attribute along with one of the
     * struts-like param attributes below to create a dynamic link so that each row creates a different URL based on the
     * data that is being viewed. (optional)
     */
    private Href href;

    /**
     * The name of the request parameter that will be dynamically added to the generated href URL. The corresponding
     * value is defined by the paramProperty and (optional) paramName attributes, optionally scoped by the paramScope
     * attribute. (optional)
     */
    private String paramId;

    /**
     * The name of a JSP bean that is a String containing the value for the request parameter named by paramId (if
     * paramProperty is not specified), or a JSP bean whose property getter is called to return a String (if
     * paramProperty is specified). The JSP bean is constrained to the bean scope specified by the paramScope property,
     * if it is specified. If paramName is omitted, then it is assumed that the current object being iterated on is the
     * target bean. (optional)
     */
    private String paramName;

    /**
     * The name of a property of the bean specified by the paramName attribute (or the current object being iterated on
     * if paramName is not provided), whose return value must be a String containing the value of the request parameter
     * (named by the paramId attribute) that will be dynamically added to this href URL. (optional)
     *
     * @deprecated use Expressions in paramName
     */
    @Deprecated
    private String paramProperty;

    /**
     * If this attribute is provided, then the column's displayed is limited to this number of characters. An elipse
     * (...) is appended to the end if this column is linked, and the user can mouseover the elipse to get the full
     * text. (optional)
     */
    private int maxLength;

    /**
     * If this attribute is provided, then the column's displayed is limited to this number of words. An elipse (...) is
     * appended to the end if this column is linked, and the user can mouseover the elipse to get the full text.
     * (optional)
     */
    private int maxWords;

    /**
     * a class that should be used to "decorate" the underlying object being displayed. If a decorator is specified for
     * the entire table, then this decorator will decorate that decorator. (optional)
     */
    private String decorator;

    /** is the column already sorted?. */
    private boolean alreadySorted;

    /**
     * The media supported attribute.
     */
    private transient List<MediaTypeEnum> supportedMedia;

    /**
     * Property in a resource bundle to be used as the title for the column.
     */
    private String titleKey;

    /**
     * The name of the bean property if a decorator is used and sorting need to be still on on the property itself.
     * Useful for displaying data with links but sorting on original value.
     */
    private String sortProperty;

    /**
     * Should the value of the column be summed? Requires that the value of the column be convertible to a Number.
     */
    private boolean totaled;

    /**
     * Static value for this cell, equivalent to column body.
     */
    private transient Object value;

    /**
     * Setter for totals.
     *
     * @param totals
     *            the value
     */
    public void setTotal(final boolean totals) {
        this.totaled = totals;
    }

    /**
     * setter for the "property" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setProperty(final String value) {
        this.property = value;
    }

    /**
     * setter for the "value" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setValue(final Object value) {
        this.value = value;
    }

    /**
     * Set the comparator, classname or object.
     *
     * @param comparatorObj
     *            the comparator, classname or object
     */
    public void setComparator(final Object comparatorObj) {
        // @todo don't do this! Setters should remains simple setters and any evaluation should be done in doEndTag()!
        if (comparatorObj instanceof Comparator) {
            this.comparator = (Comparator<Object>) comparatorObj;
        } else if (comparatorObj instanceof String) {
            final String comparatorClassname = (String) comparatorObj;
            Class<Comparator<Object>> compClass;
            try {
                compClass = (Class<Comparator<Object>>) Thread.currentThread().getContextClassLoader()
                        .loadClass(comparatorClassname);
            } catch (final ClassNotFoundException e) {
                throw new RuntimeException("InstantiationException setting column comparator as " + comparatorClassname
                        + ": " + e.getMessage(), e);
            }
            try {
                this.comparator = compClass.getDeclaredConstructor().newInstance();
            } catch (final InstantiationException e) {
                throw new RuntimeException("InstantiationException setting column comparator as " + comparatorClassname
                        + ": " + e.getMessage(), e);
            } catch (final IllegalAccessException e) {
                throw new RuntimeException("IllegalAccessException setting column comparator as " + comparatorClassname
                        + ": " + e.getMessage(), e);
            } catch (IllegalArgumentException e) {
              throw new RuntimeException("IllegalArgumentException setting column comparator as " + comparatorClassname
                  + ": " + e.getMessage(), e);
            } catch (InvocationTargetException e) {
              throw new RuntimeException("InvocationTargetException setting column comparator as " + comparatorClassname
                  + ": " + e.getMessage(), e);
            } catch (NoSuchMethodException e) {
              throw new RuntimeException("NoSuchMethodException setting column comparator as " + comparatorClassname
                  + ": " + e.getMessage(), e);
            } catch (SecurityException e) {
              throw new RuntimeException("SecurityException setting column comparator as " + comparatorClassname
                  + ": " + e.getMessage(), e);
            }
        } else {
            throw new IllegalArgumentException(
                    "Value for comparator: " + comparatorObj + " of type " + comparatorObj.getClass().getName());
        }
    }

    /**
     * setter for the "title" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setTitle(final String value) {
        this.title = value;
    }

    /**
     * setter for the "format" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setFormat(final String value) {
        this.format = value;
    }

    /**
     * setter for the "nulls" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setNulls(final boolean value) {
        this.nulls = value;
    }

    /**
     * setter for the "sortable" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setSortable(final boolean value) {
        this.sortable = value;
    }

    /**
     * setter for the "autolink" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setAutolink(final boolean value) {
        this.autolink = value;
    }

    /**
     * setter for the "escapeXml" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setEscapeXml(final boolean value) {
        this.escapeXml = value;
    }

    /**
     * setter for the "group" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setGroup(final int value) {
        this.group = value;
    }

    /**
     * setter for the "titleKey" tag attribute.
     *
     * @param value
     *            property name
     */
    public void setTitleKey(final String value) {
        this.titleKey = value;
    }

    /**
     * setter for the "href" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setHref(final String value) {
        // call encodeURL to preserve session id when cookies are disabled
        final String encodedHref = ((HttpServletResponse) this.pageContext.getResponse())
                .encodeURL(StringUtils.defaultString(value));
        this.href = new DefaultHref(encodedHref);
    }

    /**
     * setter for the "url" tag attribute. This has the same meaning of href, but prepends the context path to the given
     * URI.
     *
     * @param value
     *            attribute value
     */
    public void setUrl(final String value) {
        final HttpServletRequest req = (HttpServletRequest) this.pageContext.getRequest();
        // call encodeURL to preserve session id when cookies are disabled
        final String encodedHref = ((HttpServletResponse) this.pageContext.getResponse())
                .encodeURL(StringUtils.defaultString(req.getContextPath() + value));
        this.href = new DefaultHref(encodedHref);
    }

    /**
     * setter for the "paramId" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setParamId(final String value) {
        this.paramId = value;
    }

    /**
     * setter for the "paramName" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setParamName(final String value) {
        this.paramName = value;
    }

    /**
     * setter for the "paramProperty" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setParamProperty(final String value) {
        this.paramProperty = value;
    }

    /**
     * setter for the "scope" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setScope(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_SCOPE, value);
    }

    /**
     * setter for the "headerScope" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setHeaderScope(final String value) {
        this.headerAttributeMap.put(TagConstants.ATTRIBUTE_SCOPE, value);
    }

    /**
     * setter for the "maxLength" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setMaxLength(final int value) {
        this.maxLength = value;
    }

    /**
     * setter for the "maxWords" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setMaxWords(final int value) {
        this.maxWords = value;
    }

    /**
     * setter for the "style" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setStyle(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_STYLE, value);
    }

    /**
     * setter for the "headerStyle" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setHeaderStyle(final String value) {
        this.headerAttributeMap.put(TagConstants.ATTRIBUTE_STYLE, value);
    }

    /**
     * setter for the "class" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setClass(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     * setter for the "headerClass" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setHeaderClass(final String value) {
        this.headerAttributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     * setter for the "decorator" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setDecorator(final String value) {
        this.decorator = value;
    }

    /**
     * setter for the "sortProperty" tag attribute.
     *
     * @param value
     *            attribute value
     */
    public void setSortProperty(final String value) {
        this.sortProperty = value;
    }

    /**
     * Looks up the parent table tag.
     *
     * @return a table tag instance.
     */
    protected TableTag getTableTag() {
        return (TableTag) TagSupport.findAncestorWithClass(this, TableTag.class);
    }

    /**
     * Tag setter.
     *
     * @param media
     *            the space delimited list of supported types
     */
    public void setMedia(final String media) {
        MediaUtil.setMedia(this, media);
    }

    /**
     * Sets the supported media.
     *
     * @param media
     *            the new supported media
     *
     * @see org.displaytag.util.MediaUtil.SupportsMedia#setSupportedMedia(java.util.List)
     */
    @Override
    public void setSupportedMedia(final List<MediaTypeEnum> media) {
        this.supportedMedia = media;
    }

    /**
     * Gets the supported media.
     *
     * @return the supported media
     *
     * @see org.displaytag.util.MediaUtil.SupportsMedia#getSupportedMedia()
     */
    @Override
    public List<MediaTypeEnum> getSupportedMedia() {
        return this.supportedMedia;
    }

    /**
     * sets the name given to the server when sorting this column.
     *
     * @param sortName
     *            name given to the server to sort this column
     */
    public void setSortName(final String sortName) {
        this.sortName = sortName;
    }

    /**
     * sets the sorting order for the sorted column.
     *
     * @param value
     *            "ascending" or "descending"
     *
     * @throws InvalidTagAttributeValueException
     *             if value is not one of "ascending" or "descending"
     */
    public void setDefaultorder(final String value) throws InvalidTagAttributeValueException {
        this.defaultorder = SortOrderEnum.fromName(value);
        if (this.defaultorder == null) {
            throw new InvalidTagAttributeValueException(this.getClass(), "defaultorder", value); //$NON-NLS-1$
        }
    }

    /**
     * Passes attribute information up to the parent TableTag.
     * <p>
     * When we hit the end of the tag, we simply let our parent (which better be a TableTag) know what the user wants to
     * do with this column. We do that by simple registering this tag with the parent. This tag's only job is to hold
     * the configuration information to describe this particular column. The TableTag does all the work.
     * </p>
     *
     * @return int
     *
     * @throws JspException
     *             if this tag is being used outside of a &lt;display:list...&gt; tag.
     *
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    @Override
    public int doEndTag() throws JspException {
        final TableTag tableTag = this.getTableTag();

        final MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext
                .findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
        if (currentMediaType != null && !MediaUtil.availableForMedia(this, currentMediaType)) {
            if (ColumnTag.log.isDebugEnabled()) {
                ColumnTag.log.debug("skipping column body, currentMediaType={}", currentMediaType);
            }
            return Tag.SKIP_BODY;
        }

        // add column header only once
        if (tableTag.isFirstIteration()) {
            this.addHeaderToTable(tableTag);
        }

        if (!tableTag.isIncludedRow()) {
            return super.doEndTag();
        }

        Cell cell = null;
        if (this.property == null && this.value != null) {
            cell = new Cell(this.value);
        } else if (this.property == null && this.bodyContent != null) {
            cell = new Cell(this.bodyContent.getString());
        }

        final Object rowStyle = this.attributeMap.get(TagConstants.ATTRIBUTE_STYLE);
        final Object rowClass = this.attributeMap.get(TagConstants.ATTRIBUTE_CLASS);
        if (rowStyle != null || rowClass != null) {
            final HtmlAttributeMap perRowValues = new HtmlAttributeMap();
            if (rowStyle != null) {
                perRowValues.put(TagConstants.ATTRIBUTE_STYLE, rowStyle);
            }
            if (rowClass != null) {
                perRowValues.put(TagConstants.ATTRIBUTE_CLASS, rowClass);
            }
            if (cell == null) {
                cell = new Cell(null);
            }
            cell.setPerRowAttributes(perRowValues);
        }

        tableTag.addCell(cell != null ? cell : Cell.EMPTY_CELL);

        // cleanup non-attribute variables
        this.alreadySorted = false;

        return super.doEndTag();
    }

    /**
     * Adds the current header to the table model calling addColumn in the parent table tag. This method should be
     * called only at first iteration.
     *
     * @param tableTag
     *            parent table tag
     *
     * @throws DecoratorInstantiationException
     *             for error during column decorator instantiation
     * @throws ObjectLookupException
     *             for errors in looking up values
     */
    private void addHeaderToTable(final TableTag tableTag)
            throws DecoratorInstantiationException, ObjectLookupException {
        // don't modify "title" directly
        String evalTitle = this.title;

        // title has precedence over titleKey
        if (evalTitle == null && (this.titleKey != null || this.property != null)) {
            // handle title i18n
            evalTitle = tableTag.getProperties().geResourceProvider().getResource(this.titleKey, this.property,
                    tableTag, this.pageContext);
        }

        final HeaderCell headerCell = new HeaderCell();
        headerCell.setHeaderAttributes((HtmlAttributeMap) this.headerAttributeMap.clone());
        headerCell.setHtmlAttributes((HtmlAttributeMap) this.attributeMap.clone());
        headerCell.setTitle(evalTitle);
        headerCell.setSortable(this.sortable);

        final List<DisplaytagColumnDecorator> decorators = new ArrayList<>();

        // handle multiple chained decorators, whitespace separated
        if (StringUtils.isNotEmpty(this.decorator)) {
            final String[] decoratorNames = StringUtils.split(this.decorator);
            for (final String decoratorName : decoratorNames) {
                decorators.add(tableTag.getProperties().getDecoratorFactoryInstance()
                        .loadColumnDecorator(this.pageContext, decoratorName));
            }
        }

        // "special" decorators
        if (this.escapeXml != null && this.escapeXml) {
            decorators.add(EscapeXmlColumnDecorator.INSTANCE);
        }
        if (this.autolink) {
            decorators.add(AutolinkColumnDecorator.INSTANCE);
        }
        if (StringUtils.isNotBlank(this.format)) {
            decorators.add(new MessageFormatColumnDecorator(this.format, tableTag.getProperties().getLocale()));
        }

        headerCell.setColumnDecorators(decorators.toArray(new DisplaytagColumnDecorator[decorators.size()]));

        headerCell.setBeanPropertyName(this.property);
        headerCell.setShowNulls(this.nulls);
        headerCell.setMaxLength(this.maxLength);
        headerCell.setMaxWords(this.maxWords);
        headerCell.setGroup(this.group);
        headerCell.setSortProperty(this.sortProperty);
        headerCell.setTotaled(this.totaled);

        final Comparator<Object> headerComparator = this.comparator != null ? this.comparator
                : tableTag.getProperties().getDefaultComparator();

        headerCell.setComparator(headerComparator);
        headerCell.setDefaultSortOrder(this.defaultorder);
        headerCell.setSortName(this.sortName);

        // href and parameter, create link
        if (this.href != null) {
            Href colHref;

            // empty base url, use href with parameters from parent table
            if (StringUtils.isEmpty(this.href.getBaseUrl())) {
                colHref = (Href) tableTag.getBaseHref().clone();
            } else {
                colHref = (Href) this.href.clone();
            }

            if (this.paramId != null) {
                // parameter value is in a different object than the iterated one
                if (this.paramName != null) {
                    // create a complete string for compatibility with previous version before expression evaluation.
                    // this approach is optimized for new expressions, not for previous property/scope parameters
                    final StringBuilder expression = new StringBuilder();

                    // base bean name
                    if (this.paramId != null) {
                        expression.append(this.paramName);
                    } else {
                        expression.append(tableTag.getName());
                    }

                    // append property
                    if (StringUtils.isNotBlank(this.paramProperty)) {
                        expression.append('.').append(this.paramProperty);
                    }

                    // evaluate expression.
                    // note the value is fixed, not based on any object created during iteration
                    // this is here for compatibility with the old version mainly
                    final Object paramValue = tableTag.evaluateExpression(expression.toString());

                    // add parameter
                    colHref.addParameter(this.paramId, paramValue);
                } else {
                    // set id
                    headerCell.setParamName(this.paramId);

                    // set property
                    headerCell.setParamProperty(this.paramProperty);
                }
            }

            // sets the base href
            headerCell.setHref(colHref);

        }

        tableTag.addColumn(headerCell);

        if (ColumnTag.log.isDebugEnabled()) {
            ColumnTag.log.debug("columnTag.addHeaderToTable() :: first iteration - adding header {}", headerCell);
        }
    }

    /**
     * Release.
     *
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    @Override
    public void release() {
        super.release();
        this.attributeMap.clear();
        this.autolink = false;
        this.decorator = null;
        this.group = -1;
        this.headerAttributeMap.clear();
        this.href = null;
        this.maxLength = 0;
        this.maxWords = 0;
        this.nulls = false;
        this.paramId = null;
        this.paramName = null;
        this.paramProperty = null;
        this.property = null;
        this.sortable = false;
        this.sortName = null;
        this.supportedMedia = null;
        this.title = null;
        this.titleKey = null;
        this.sortProperty = null;
        this.comparator = null;
        this.defaultorder = null;
        this.escapeXml = null;
        this.format = null;
        this.value = null;
        this.totaled = false;
    }

    /**
     * Do start tag.
     *
     * @return the int
     *
     * @throws JspException
     *             the jsp exception
     *
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        final TableTag tableTag = this.getTableTag();
        if (tableTag == null) {
            throw new TagStructureException(this.getClass(), "column", "table");
        }

        // If the list is empty, do not execute the body; may result in NPE
        if (tableTag.isEmpty() || !tableTag.isIncludedRow()) {
            return Tag.SKIP_BODY;
        }

        final MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext
                .findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
        if (!MediaUtil.availableForMedia(this, currentMediaType)) {
            return Tag.SKIP_BODY;
        }
        
        // Configure escapeXml default value from properties
        if (this.escapeXml == null) {
        	this.escapeXml = tableTag.getProperties().getEscapeXmlDefault();
        }

        return super.doStartTag();
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
                .append("bodyContent", this.bodyContent) //$NON-NLS-1$
                .append("group", this.group) //$NON-NLS-1$
                .append("maxLength", this.maxLength) //$NON-NLS-1$
                .append("decorator", this.decorator) //$NON-NLS-1$
                .append("href", this.href) //$NON-NLS-1$
                .append("title", this.title) //$NON-NLS-1$
                .append("property", this.property) //$NON-NLS-1$
                .append("paramProperty", this.paramProperty) //$NON-NLS-1$
                .append("headerAttributeMap", this.headerAttributeMap) //$NON-NLS-1$
                .append("paramName", this.paramName) //$NON-NLS-1$
                .append("autolink", this.autolink) //$NON-NLS-1$
                .append("format", this.format) //$NON-NLS-1$
                .append("nulls", this.nulls) //$NON-NLS-1$
                .append("maxWords", this.maxWords) //$NON-NLS-1$
                .append("attributeMap", this.attributeMap) //$NON-NLS-1$
                .append("sortable", this.sortable) //$NON-NLS-1$
                .append("paramId", this.paramId) //$NON-NLS-1$
                .append("alreadySorted", this.alreadySorted) //$NON-NLS-1$
                .append("sortProperty", this.sortProperty) //$NON-NLS-1$
                .append("defaultSortOrder", this.defaultorder) //$NON-NLS-1$
                .toString();
    }

}
