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
package org.displaytag.tags;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.DecoratorFactory;
import org.displaytag.exception.DecoratorInstantiationException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.exception.TagStructureException;
import org.displaytag.model.Cell;
import org.displaytag.model.HeaderCell;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.ShortToStringStyle;
import org.displaytag.util.TagConstants;


/**
 * <p>
 * This tag works hand in hand with the TableTag to display a list of objects. This describes a column of data in the
 * TableTag. There can be any number of columns that make up the list.
 * </p>
 * <p>
 * This tag does no work itself, it is simply a container of information. The TableTag does all the work based on the
 * information provided in the attributes of this tag.
 * <p>
 * @author mraible
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ColumnTag extends BodyTagSupport
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(ColumnTag.class);

    /**
     * html pass-through attributes for cells.
     */
    private HtmlAttributeMap attributeMap = new HtmlAttributeMap();

    /**
     * html pass-through attributes for cell headers.
     */
    private HtmlAttributeMap headerAttributeMap = new HtmlAttributeMap();

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

    /**
     * is the column sortable?
     */
    private boolean sortable;

    /**
     * if set to true, then any email addresses and URLs found in the content of the column are automatically converted
     * into a hypertext link.
     */
    private boolean autolink;

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
     * @deprecated use Expressions in paramName
     */
    private String paramProperty;

    /**
     * The scope within which to search for the bean specified by the paramName attribute. If not specified, all scopes
     * are searched. If paramName is not provided, then the current object being iterated on is assumed to be the target
     * bean. (optional)
     * @deprecated use Expressions in paramName
     */
    private String paramScope;

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

    /**
     * is the column already sorted?
     */
    private boolean alreadySorted;

    /**
     * The media supported attribute.
     */
    private List supportedMedia;

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
     * setter for the "property" tag attribute.
     * @param value attribute value
     */
    public void setProperty(String value)
    {
        this.property = value;
    }

    /**
     * setter for the "title" tag attribute.
     * @param value attribute value
     */
    public void setTitle(String value)
    {
        this.title = value;
    }

    /**
     * setter for the "nulls" tag attribute.
     * @param value attribute value
     */
    public void setNulls(boolean value)
    {
        this.nulls = value;
    }

    /**
     * setter for the "sortable" tag attribute.
     * @param value attribute value
     */
    public void setSortable(boolean value)
    {
        this.sortable = value;
    }

    /**
     * setter for the "autolink" tag attribute.
     * @param value attribute value
     */
    public void setAutolink(boolean value)
    {
        this.autolink = value;
    }

    /**
     * setter for the "group" tag attribute.
     * @param value attribute value
     */
    public void setGroup(int value)
    {
        this.group = value;
    }

    /**
     * setter for the "titleKey" tag attribute.
     * @param value property name
     */
    public void setTitleKey(String value)
    {
        this.titleKey = value;
    }

    /**
     * setter for the "href" tag attribute.
     * @param value attribute value
     */
    public void setHref(String value)
    {
        // call encodeURL to preserve session id when cookies are disabled
        String encodedHref = ((HttpServletResponse) this.pageContext.getResponse()).encodeURL(StringUtils
            .defaultString(value));
        this.href = new Href(encodedHref);
    }

    /**
     * setter for the "url" tag attribute. This has the same meaning of href, but prepends the context path to the given
     * URI.
     * @param value attribute value
     */
    public void setUrl(String value)
    {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        // call encodeURL to preserve session id when cookies are disabled
        String encodedHref = ((HttpServletResponse) this.pageContext.getResponse()).encodeURL(StringUtils
            .defaultString(req.getContextPath() + value));
        this.href = new Href(encodedHref);
    }

    /**
     * setter for the "paramId" tag attribute.
     * @param value attribute value
     */
    public void setParamId(String value)
    {
        this.paramId = value;
    }

    /**
     * setter for the "paramName" tag attribute.
     * @param value attribute value
     */
    public void setParamName(String value)
    {
        this.paramName = value;
    }

    /**
     * setter for the "paramProperty" tag attribute.
     * @param value attribute value
     */
    public void setParamProperty(String value)
    {
        this.paramProperty = value;
    }

    /**
     * setter for the "paramScope" tag attribute.
     * @param value attribute value
     */
    public void setParamScope(String value)
    {
        this.paramScope = value;
    }

    /**
     * setter for the "maxLength" tag attribute.
     * @param value attribute value
     */
    public void setMaxLength(int value)
    {
        this.maxLength = value;
    }

    /**
     * setter for the "maxWords" tag attribute.
     * @param value attribute value
     */
    public void setMaxWords(int value)
    {
        this.maxWords = value;
    }

    /**
     * setter for the "width" tag attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setWidth(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_WIDTH, value);
        this.headerAttributeMap.put(TagConstants.ATTRIBUTE_WIDTH, value);
    }

    /**
     * setter for the "align" tag attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setAlign(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_ALIGN, value);
        this.headerAttributeMap.put(TagConstants.ATTRIBUTE_ALIGN, value);
    }

    /**
     * setter for the "background" tag attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setBackground(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_BACKGROUND, value);
    }

    /**
     * setter for the "bgcolor" tag attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setBgcolor(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_BGCOLOR, value);
    }

    /**
     * setter for the "height" tag attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setHeight(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_HEIGHT, value);
    }

    /**
     * setter for the "nowrap" tag attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setNowrap(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_NOWRAP, "nowrap");
    }

    /**
     * setter for the "valign" tag attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setValign(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_VALIGN, value);
    }

    /**
     * setter for the "style" tag attribute.
     * @param value attribute value
     */
    public void setStyle(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_STYLE, value);
    }

    /**
     * setter for the "class" tag attribute.
     * @param value attribute value
     */
    public void setClass(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     * Adds a css class to the class attribute (html class suports multiple values).
     * @param value attribute value
     */
    public void addClass(String value)
    {
        Object classAttributes = this.attributeMap.get(TagConstants.ATTRIBUTE_CLASS);

        if (classAttributes == null)
        {
            this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
        }
        else
        {
            ((MultipleHtmlAttribute) classAttributes).addAttributeValue(value);
        }
    }

    /**
     * setter for the "headerClass" tag attribute.
     * @param value attribute value
     */
    public void setHeaderClass(String value)
    {
        this.headerAttributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     * setter for the "decorator" tag attribute.
     * @param value attribute value
     */
    public void setDecorator(String value)
    {
        this.decorator = value;
    }

    /**
     * setter for the "sortProperty" tag attribute.
     * @param value attribute value
     */
    public void setSortProperty(String value)
    {
        this.sortProperty = value;
    }

    /**
     * Is this column configured for the media type?
     * @param mediaType the currentMedia type
     * @return true if the column should be displayed for this request
     */
    public boolean availableForMedia(MediaTypeEnum mediaType)
    {
        if (supportedMedia == null)
        {
            return true;
        }

        return this.supportedMedia.contains(mediaType);
    }

    /**
     * Tag setter.
     * @param media the space delimited list of supported types
     */
    public void setMedia(String media)
    {
        if (StringUtils.isBlank(media) || media.toLowerCase().indexOf("all") > -1)
        {
            this.supportedMedia = null;
            return;
        }
        this.supportedMedia = new ArrayList();
        String[] values = StringUtils.split(media);
        for (int i = 0; i < values.length; i++)
        {
            String value = values[i];
            if (!StringUtils.isBlank(value))
            {
                MediaTypeEnum type = MediaTypeEnum.fromName(value.toLowerCase());
                if (type == null)
                {
                    log.warn("Unrecognized value for attribute \"media\" value=\"" + value + "\"");
                }
                else
                {
                    this.supportedMedia.add(type);
                }
            }
        }
    }

    /**
     * Passes attribute information up to the parent TableTag.
     * <p>
     * When we hit the end of the tag, we simply let our parent (which better be a TableTag) know what the user wants to
     * do with this column. We do that by simple registering this tag with the parent. This tag's only job is to hold
     * the configuration information to describe this particular column. The TableTag does all the work.
     * </p>
     * @return int
     * @throws JspException if this tag is being used outside of a &lt;display:list...&gt; tag.
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException
    {
        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

        MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext.findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
        if (currentMediaType != null && !availableForMedia(currentMediaType))
        {
            if (log.isDebugEnabled())
            {
                log.debug("skipping column body, currentMediaType=" + currentMediaType);
            }
            return SKIP_BODY;
        }

        // add column header only once
        if (tableTag.isFirstIteration())
        {
            addHeaderToTable(tableTag);
        }

        if (!tableTag.isIncludedRow())
        {
            return super.doEndTag();
        }

        Cell cell;
        if (this.property == null)
        {

            Object cellValue;

            if (this.bodyContent != null)
            {
                String value = this.bodyContent.getString();

                if (value == null && this.nulls)
                {
                    value = TagConstants.EMPTY_STRING;
                }

                cellValue = value;
            }
            // BodyContent will be null if the body was not eval'd, eg an empty list.
            else
            {
                cellValue = Cell.EMPTY_CELL;
            }
            cell = new Cell(cellValue);

        }
        else
        {
            cell = Cell.EMPTY_CELL;
        }

        tableTag.addCell(cell);

        // cleanup non-attribute variables
        this.alreadySorted = false;

        return super.doEndTag();
    }

    /**
     * Adds the current header to the table model calling addColumn in the parent table tag. This method should be
     * called only at first iteration.
     * @param tableTag parent table tag
     * @throws DecoratorInstantiationException for error during column decorator instantiation
     * @throws ObjectLookupException for errors in looking up values
     */
    private void addHeaderToTable(TableTag tableTag) throws DecoratorInstantiationException, ObjectLookupException
    {
        // don't modify "title" directly
        String evalTitle = this.title;

        // title has precedence over titleKey
        if (evalTitle == null && (this.titleKey != null || this.property != null))
        {
            // handle title i18n
            evalTitle = tableTag.getProperties().geResourceProvider().getResource(
                this.titleKey,
                this.property,
                tableTag,
                this.pageContext);
        }

        HeaderCell headerCell = new HeaderCell();
        headerCell.setHeaderAttributes((HtmlAttributeMap) this.headerAttributeMap.clone());
        headerCell.setHtmlAttributes((HtmlAttributeMap) this.attributeMap.clone());
        headerCell.setTitle(evalTitle);
        headerCell.setSortable(this.sortable);
        headerCell.setColumnDecorator(DecoratorFactory.loadColumnDecorator(this.decorator));
        headerCell.setBeanPropertyName(this.property);
        headerCell.setShowNulls(this.nulls);
        headerCell.setMaxLength(this.maxLength);
        headerCell.setMaxWords(this.maxWords);
        headerCell.setAutoLink(this.autolink);
        headerCell.setGroup(this.group);
        headerCell.setSortProperty(this.sortProperty);

        // href and parameter, create link
        if (this.href != null)
        {
            Href colHref;

            // empty base url, use href with parameters from parent table
            if (StringUtils.isEmpty(this.href.getBaseUrl()))
            {
                colHref = new Href(tableTag.getBaseHref());
            }
            else
            {
                colHref = new Href(this.href);
            }

            if (this.paramId != null)
            {
                // parameter value is in a different object than the iterated one
                if (this.paramName != null || this.paramScope != null)
                {
                    // create a complete string for compatibility with previous version before expression evaluation.
                    // this approach is optimized for new expressions, not for previous property/scope parameters
                    StringBuffer expression = new StringBuffer();

                    // append scope
                    if (StringUtils.isNotBlank(this.paramScope))
                    {
                        expression.append(this.paramScope).append("Scope.");
                    }

                    // base bean name
                    if (this.paramId != null)
                    {
                        expression.append(this.paramName);
                    }
                    else
                    {
                        expression.append(tableTag.getName());
                    }

                    // append property
                    if (StringUtils.isNotBlank(this.paramProperty))
                    {
                        expression.append('.').append(this.paramProperty);
                    }

                    // evaluate expression.
                    // note the value is fixed, not based on any object created during iteration
                    // this is here for compatibility with the old version mainly
                    Object paramValue = tableTag.evaluateExpression(expression.toString());

                    // add parameter
                    colHref.addParameter(this.paramId, paramValue);
                }
                else
                {
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

        if (log.isDebugEnabled())
        {
            log.debug("columnTag.addHeaderToTable() :: first iteration - adding header " + headerCell);
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
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
        this.paramScope = null;
        this.property = null;
        this.sortable = false;
        this.supportedMedia = null;
        this.title = null;
        this.titleKey = null;
        this.sortProperty = null;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
        if (tableTag == null)
        {
            throw new TagStructureException(getClass(), "column", "table");
        }

        // If the list is empty, do not execute the body; may result in NPE
        if (tableTag.isEmpty() || !tableTag.isIncludedRow())
        {
            return SKIP_BODY;
        }

        MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext.findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
        if (!availableForMedia(currentMediaType))
        {
            return SKIP_BODY;
        }

        return super.doStartTag();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ShortToStringStyle.SHORT_STYLE) //
            .append("bodyContent", this.bodyContent) //$NON-NLS-1$
            .append("group", this.group) //$NON-NLS-1$
            .append("maxLength", this.maxLength) //$NON-NLS-1$
            .append("decorator", this.decorator) //$NON-NLS-1$
            .append("href", this.href) //$NON-NLS-1$
            .append("title", this.title) //$NON-NLS-1$
            .append("paramScope", this.paramScope) //$NON-NLS-1$
            .append("property", this.property) //$NON-NLS-1$
            .append("paramProperty", this.paramProperty) //$NON-NLS-1$
            .append("headerAttributeMap", this.headerAttributeMap) //$NON-NLS-1$
            .append("paramName", this.paramName) //$NON-NLS-1$
            .append("autolink", this.autolink) //$NON-NLS-1$
            .append("nulls", this.nulls) //$NON-NLS-1$
            .append("maxWords", this.maxWords) //$NON-NLS-1$
            .append("attributeMap", this.attributeMap) //$NON-NLS-1$
            .append("sortable", this.sortable) //$NON-NLS-1$
            .append("paramId", this.paramId) //$NON-NLS-1$
            .append("alreadySorted", this.alreadySorted) //$NON-NLS-1$
            .append("sortProperty", this.sortProperty) //$NON-NLS-1$
            .toString();
    }
}