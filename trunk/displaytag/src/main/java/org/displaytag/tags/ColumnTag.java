package org.displaytag.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.DecoratorFactory;
import org.displaytag.exception.MissingAttributeException;
import org.displaytag.exception.TagStructureException;
import org.displaytag.model.Cell;
import org.displaytag.model.HeaderCell;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;
import org.displaytag.export.MediaTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * <p>
 * This tag works hand in hand with the TableTag to display a list of objects.  This describes a column of data in the
 * TableTag.  There can be any number of columns that make up the list.
 * </p>
 * <p>
 * This tag does no work itself, it is simply a container of information.  The TableTag does all the work based on
 * the information provided in the attributes of this tag.
 * <p>
 * @author mraible
 * @version $Revision$ ($Author$)
 */
public class ColumnTag extends BodyTagSupport
{
    /**
     * logger
     */
    private static Log mLog = LogFactory.getLog(ColumnTag.class);

    /**
     * html pass-through attributes for cells
     */
    private HtmlAttributeMap attributeMap = new HtmlAttributeMap();

    /**
     * html pass-through attributes for cell headers
     */
    private HtmlAttributeMap headerAttributeMap = new HtmlAttributeMap();

    /**
     * the property method that is called to retrieve the information to be displayed in this column.
     * This method is called on the current object in the iteration for the given row.
     * The property format is in typical struts format for properties (required)
     */
    private String property;

    /**
     * the title displayed for this column.  if this is omitted then the property name is used
     * for the title of the column (optional)
     */
    private String title;

    /**
     * by default, null values don't appear in the list, by setting viewNulls to 'true', then
     * null values will appear as "null" in the list (mostly useful for debugging) (optional)
     */
    private boolean nulls;

    /**
     * is the column sortable?
     */
    private boolean sortable;

    /**
     * if set to true, then any email addresses and URLs found in the content of the column are
     * automatically converted into a hypertext link.
     */
    private boolean autolink;

    /**
     * the grouping level (starting at 1 and incrementing) of this column (indicates if successive
     * contain the same values, then they should not be displayed).  The level indicates that if a
     * lower level no longer matches, then the matching for this higher level should start over as
     * well. If this attribute is not included, then no grouping is performed. (optional)
     */
    private int group = -1;

    /**
     * if this attribute is provided, then the data that is shown for this column is wrapped inside
     * a &lt;a href&gt; tag with the url provided through this attribute. Typically you would use
     * this attribute along with one of the struts-like param attributes below to create a dynamic
     * link so that each row creates a different URL based on the data that is being viewed. (optional)
     */
    private Href href;

    /**
     * The name of the request parameter that will be dynamically added to the generated href URL.
     * The corresponding value is defined by the paramProperty and (optional) paramName attributes,
     * optionally scoped by the paramScope attribute. (optional)
     */
    private String paramId;

    /**
     * The name of a JSP bean that is a String containing the value for the request parameter named
     * by paramId (if paramProperty is not specified), or a JSP bean whose property getter is called
     * to return a String (if paramProperty is specified). The JSP bean is constrained to the bean
     * scope specified by the paramScope property, if it is specified. If paramName is omitted, then
     * it is assumed that the current object being iterated on is the target bean. (optional)
     */
    private String paramName;

    /**
     * The name of a property of the bean specified by the paramName attribute (or the current object
     * being iterated on if paramName is not provided), whose return value must be a String containing
     * the value of the request parameter (named by the paramId attribute) that will be dynamically
     * added to this href URL. (optional)
     * @deprecated use Expressions in paramName
     */
    private String paramProperty;

    /**
     * The scope within which to search for the bean specified by the paramName attribute. If not
     * specified, all scopes are searched. If paramName is not provided, then the current object
     * being iterated on is assumed to be the target bean. (optional)
     * @deprecated use Expressions in paramName
     */
    private String paramScope;

    /**
     * If this attribute is provided, then the column's displayed is limited to this number of
     * characters.  An elipse (...) is appended to the end if this column is linked, and the user
     * can mouseover the elipse to get the full text. (optional)
     */
    private int maxLength;

    /**
     * If this attribute is provided, then the column's displayed is limited to this number of words.
     * An elipse (...) is appended to the end if this column is linked, and the user can mouseover
     * the elipse to get the full text. (optional)
     */
    private int maxWords;

    /**
     * a class that should be used to "decorate" the underlying object being displayed. If a decorator
     * is specified for the entire table, then this decorator will decorate that decorator. (optional)
     */
    private String decorator;

    /**
     * is the column already sorted?
     */
    private boolean alreadySorted = false;

    /**
     * The media supported attribute.
     */
    private List supportedMedia = Arrays.asList(MediaTypeEnum.ALL);

    /**
     * setter for the "property" tag attribute
     * @param value attribute value
     */
    public void setProperty(String value)
    {
        property = value;
    }

    /**
     * setter for the "title" tag attribute
     * @param value attribute value
     */
    public void setTitle(String value)
    {
        title = value;
    }

    /**
     * setter for the "nulls" tag attribute
     * @param value attribute value
     */
    public void setNulls(String value)
    {
        if (!Boolean.FALSE.toString().equals(value))
        {
            nulls = true;
        }

    }

    /**
     * setter for the "sortable" tag attribute
     * @param value attribute value
     */
    public void setSortable(String value)
    {
        if (!Boolean.FALSE.toString().equals(value))
        {
            sortable = true;
        }
    }

    /**
     * @deprecated use setSortable()
     * @param value String
     */
    public void setSort(String value)
    {
        setSortable(value);
    }

    /**
     * setter for the "autolink" tag attribute
     * @param value attribute value
     */
    public void setAutolink(String value)
    {
        if (!Boolean.FALSE.toString().equals(value))
        {
            autolink = true;
        }
    }

    /**
     * setter for the "group" tag attribute
     * @param value attribute value
     */
    public void setGroup(String value)
    {
        try
        {
            group = Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            // ignore?
            mLog.warn("Invalid \"group\" attribute: value=\"" + value + "\"");
        }
    }

    /**
     * setter for the "href" tag attribute
     * @param value attribute value
     */
    public void setHref(String value)
    {
        href = new Href(value);
    }

    /**
     * setter for the "paramId" tag attribute
     * @param value attribute value
     */
    public void setParamId(String value)
    {
        paramId = value;
    }

    /**
     * setter for the "paramName" tag attribute
     * @param value attribute value
     */
    public void setParamName(String value)
    {
        paramName = value;
    }

    /**
     * setter for the "paramProperty" tag attribute
     * @param value attribute value
     */
    public void setParamProperty(String value)
    {
        paramProperty = value;
    }

    /**
     * setter for the "paramScope" tag attribute
     * @param value attribute value
     */
    public void setParamScope(String value)
    {
        paramScope = value;
    }

    /**
     * setter for the "maxLength" tag attribute
     * @param value attribute value
     */
    public void setMaxLength(int value)
    {
        maxLength = value;
    }

    /**
     * setter for the "maxWords" tag attribute
     * @param value attribute value
     */
    public void setMaxWords(int value)
    {
        maxWords = value;
    }

    /**
     * setter for the "width" tag attribute
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setWidth(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_WIDTH, value);
        headerAttributeMap.put(TagConstants.ATTRIBUTE_WIDTH, value);
    }

    /**
     * setter for the "align" tag attribute
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setAlign(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_ALIGN, value);
        headerAttributeMap.put(TagConstants.ATTRIBUTE_ALIGN, value);
    }

    /**
     * setter for the "background" tag attribute
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setBackground(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_BACKGROUND, value);
    }

    /**
     * setter for the "bgcolor" tag attribute
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setBgcolor(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_BGCOLOR, value);
    }

    /**
     * setter for the "height" tag attribute
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setHeight(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_HEIGHT, value);
    }

    /**
     * setter for the "nowrap" tag attribute
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setNowrap(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_NOWRAP, value);
    }

    /**
     * setter for the "valign" tag attribute
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setValign(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_VALIGN, value);
    }

    /**
     * setter for the "class" tag attribute
     * @param value attribute value
     * @deprecated use the "class" attribute
     */
    public void setStyleClass(String value)
    {
        setClass(value);
    }

    /**
     * setter for the "class" tag attribute
     * @param value attribute value
     */
    public void setClass(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     * add a css class to the class attribute (html class suports multiple values)
     * @param value attribute value
     */
    public void addClass(String value)
    {
        Object classAttributes = attributeMap.get(TagConstants.ATTRIBUTE_CLASS);

        if (classAttributes == null)
        {
            attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
        }
        else
        {
            ((MultipleHtmlAttribute) classAttributes).addAttributeValue(value);
        }
    }

    /**
     * setter for the "headerClass" tag attribute
     * @param value attribute value
     */
    public void setHeaderClass(String value)
    {
        headerAttributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     * setter for the "headerStyleClass" tag attribute
     * @param value attribute value
     * @deprecated use setHeaderClass()
     */
    public void setHeaderStyleClass(String value)
    {
        setHeaderClass(value);
    }

    /**
     * setter for the "decorator" tag attribute
     * @param value attribute value
     */
    public void setDecorator(String value)
    {
        decorator = value;
    }

    /**
     * Is this column configured for the media type?
     * @param mediaType the currentMedia type
     * @return true if the column should be displayed for this request
     */
    public boolean availableForMedia(MediaTypeEnum mediaType)
    {
        return supportedMedia.contains(mediaType);
    }

    /**
     * Tag setter.
     * @param media the space delimited list of supported types
     */
    public void setMedia(String media)
    {
        if (StringUtils.isBlank(media) || media.toLowerCase().indexOf("all") > -1)
        {
            supportedMedia = Arrays.asList(MediaTypeEnum.ALL);
            return;
        }
        supportedMedia = new ArrayList();
        String[] values = StringUtils.split(media);
        for (int i = 0; i < values.length; i++)
        {
            String value = values[i];
            if (!StringUtils.isBlank(value))
            {
                MediaTypeEnum type = MediaTypeEnum.fromName(value.toLowerCase());
                if (type == null)
                {   // Should be in a tag validator..
                    String msg = "Unknown media type \"" + value
                            + "\"; media must be one or more values, space separated."
                            + " Possible values are:";
                    for (int j = 0; j < MediaTypeEnum.ALL.length; j++)
                    {
                        MediaTypeEnum mediaTypeEnum = MediaTypeEnum.ALL[j];
                        msg += " '" + mediaTypeEnum.getName() + "'";
                    }
                    throw new IllegalArgumentException(msg + ".");
                }
                supportedMedia.add(type);
            }
        }
    }


    /**
     * Passes attribute information up to the parent TableTag.
     *
     * <p>When we hit the end of the tag, we simply let our parent (which better
     * be a TableTag) know what the user wants to do with this column.
     * We do that by simple registering this tag with the parent.  This tag's
     * only job is to hold the configuration information to describe this
     * particular column.  The TableTag does all the work.</p>
     *
     * @return int
     * @throws JspException if this tag is being used outside of a
     *    &lt;display:list...&gt; tag.
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     **/
    public int doEndTag() throws JspException
    {
        MediaTypeEnum currentMediaType = (MediaTypeEnum) pageContext.findAttribute(TableTag.PAGE_ATTRIBUTE_EXPORT);
        if (!availableForMedia(currentMediaType))
        {
            return SKIP_BODY;
        }

        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

        // add column header only once
        if (tableTag.isFirstIteration())
        {

            HeaderCell headerCell = new HeaderCell();
            headerCell.setHeaderAttributes((HtmlAttributeMap) headerAttributeMap.clone());
            headerCell.setHtmlAttributes((HtmlAttributeMap) attributeMap.clone());
            headerCell.setTitle(title);
            headerCell.setSortable(sortable);
            headerCell.setColumnDecorator(DecoratorFactory.loadColumnDecorator(decorator));
            headerCell.setBeanPropertyName(property);
            headerCell.setShowNulls(nulls);
            headerCell.setMaxLength(maxLength);
            headerCell.setMaxWords(maxWords);
            headerCell.setAutoLink(autolink);
            headerCell.setGroup(group);

            // href and parameter, create link
            if (href != null && paramId != null)
            {

                Href colHref = new Href(href);

                // parameter value is in a different object than the iterated one
                if (paramName != null || paramScope != null)
                {
                    // create a complete string for compatibility with previous version before expression evaluation.
                    // this approach is optimized for new expressions, not for previous property/scope parameters
                    StringBuffer expression = new StringBuffer();

                    // append scope
                    if (paramScope != null && !"".equals(paramScope))
                    {
                        expression.append(paramScope).append("Scope.");
                    }

                    // base bean name
                    if (paramId != null)
                    {
                        expression.append(paramName);
                    }
                    else
                    {
                        expression.append(tableTag.getName());
                    }

                    // append property
                    if (paramProperty != null && !"".equals(paramProperty))
                    {
                        expression.append('.').append(property);
                    }

                    // evaluate expression.
                    // note the value is fixed, not based on any object created during iteration
                    // this is here for compatibility with the old version mainly
                    Object paramValue = tableTag.evaluateExpression(expression.toString());

                    // add parameter
                    colHref.addParameter(paramId, paramValue);
                }
                else
                {
                    // lookup value as a property on the list object. This should not be done here to avoid useless
                    // work when only a part of the list is displayed

                    // set id
                    headerCell.setParamName(paramId);

                    // set property
                    headerCell.setParamProperty(paramProperty);

                }

                // sets the base href
                headerCell.setHref(colHref);

            }

            tableTag.addColumn(headerCell);
            mLog.debug("columnTag.doEndTag() :: first iteration - adding header" + headerCell);
        }

        Cell cell;

        if (property == null)
        {

            Object cellValue;

            if (getBodyContent() != null)
            {
                String value = null;
                BodyContent bodyContent = getBodyContent();
                if (bodyContent != null)
                {
                    value = bodyContent.getString();
                }

                if (value == null && nulls)
                {
                    value = "";
                }

                cellValue = value;
            }
            // BodyContent will be null if the body was not eval'd, eg an empty list.
            else if (tableTag.isEmpty())
            {
                cellValue = Cell.EMPTY_CELL;
            }
            else
            {
                throw new MissingAttributeException(
                    getClass(),
                    new String[] { "property attribute", "value attribute", "tag body" });
            }
            cell = new Cell(cellValue);

        }
        else
        {
            cell = Cell.EMPTY_CELL;
        }

        tableTag.addCell(cell);

        attributeMap.clear();
        headerAttributeMap.clear();
        paramName = null;
        decorator = null;

        // fix for tag pooling in tomcat
        setBodyContent(null);

        return super.doEndTag();
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        super.release();
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
        if (tableTag.isEmpty())
        {
            return SKIP_BODY;
        }
        else
        {
            MediaTypeEnum currentMediaType = (MediaTypeEnum) pageContext.findAttribute(TableTag.PAGE_ATTRIBUTE_EXPORT);
            if (!availableForMedia(currentMediaType))
            {
                return SKIP_BODY;
            }
            return super.doStartTag();
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
            .append("bodyContent", this.bodyContent)
            .append("group", this.group)
            .append("maxLength", this.maxLength)
            .append("decorator", this.decorator)
            .append("href", this.href)
            .append("title", this.title)
            .append("paramScope", this.paramScope)
            .append("property", this.property)
            .append("paramProperty", this.paramProperty)
            .append("headerAttributeMap", this.headerAttributeMap)
            .append("paramName", this.paramName)
            .append("autolink", this.autolink)
            .append("nulls", this.nulls)
            .append("maxWords", this.maxWords)
            .append("attributeMap", this.attributeMap)
            .append("sortable", this.sortable)
            .append("paramId", this.paramId)
            .append("alreadySorted", this.alreadySorted)
            .toString();
    }
}
