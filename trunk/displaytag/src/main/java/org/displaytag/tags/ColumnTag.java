package org.displaytag.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.decorator.DecoratorFactory;
import org.displaytag.exception.MissingAttributeException;
import org.displaytag.exception.TagStructureException;
import org.displaytag.model.Cell;
import org.displaytag.model.HeaderCell;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;

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
     * Field mStaticContent
     */
    private String staticContent;

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
     * Field mSortable
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
     * Field mHeaderClazz
     */
    private String headerClazz;

    /**
     * a class that should be used to "decorate" the underlying object being displayed. If a decorator
     * is specified for the entire table, then this decorator will decorate that decorator. (optional)
     */
    private String decorator;

    /**
     * Field mDecoratorObject
     */
    private ColumnDecorator decoratorObject;

    /**
     * Field mAlreadySorted
     */
    private boolean alreadySorted = false;

    /**
     * Method getStaticContent
     * @return String
     */
    public String getStaticContent()
    {

        return staticContent;
    }

    /**
     * Method setProperty
     * @param value String
     */
    public void setProperty(String value)
    {
        property = value;
    }

    /**
     * Method setTitle
     * @param value String
     */
    public void setTitle(String value)
    {
        title = value;
    }

    /**
     * Method setNulls
     * @param value String
     */
    public void setNulls(String value)
    {
        if (!Boolean.FALSE.toString().equals(value))
        {
            nulls = true;
        }

    }

    /**
     * Method setSortable
     * @param value String
     */
    public void setSortable(String value)
    {
        if (!Boolean.FALSE.toString().equals(value))
        {
            sortable = true;
        }
    }

    /**
     *
     * @deprecated use setSortable()
     * @param value String
     */
    public void setSort(String value)
    {
        setSortable(value);
    }

    /**
     * Method setAutolink
     * @param value String
     */
    public void setAutolink(String value)
    {
        if (!"false".equals(value))
        {
            autolink = true;
        }
    }

    /**
     * Method setGroup
     * @param value String
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
     * Method setHref
     * @param value String
     */
    public void setHref(String value)
    {
        href = new Href(value);
    }

    /**
     * Method setParamId
     * @param value String
     */
    public void setParamId(String value)
    {
        paramId = value;
    }

    /**
     * Method setParamName
     * @param value String
     */
    public void setParamName(String value)
    {
        paramName = value;
    }

    /**
     * Method setParamProperty
     * @param value String
     */
    public void setParamProperty(String value)
    {
        paramProperty = value;
    }

    /**
     * Method setParamScope
     * @param value String
     */
    public void setParamScope(String value)
    {
        paramScope = value;
    }

    /**
     * Method setMaxLength
     * @param value int
     */
    public void setMaxLength(int value)
    {
        maxLength = value;
    }

    /**
     * Method setMaxWords
     * @param value int
     */
    public void setMaxWords(int value)
    {
        maxWords = value;
    }

    /**
     * Method setWidth
     * @param value String
     */
    public void setWidth(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_WIDTH, value);
        headerAttributeMap.put(TagConstants.ATTRIBUTE_WIDTH, value);
    }

    /**
     * Method setAlign
     * @param value String
     */
    public void setAlign(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_ALIGN, value);
        headerAttributeMap.put(TagConstants.ATTRIBUTE_ALIGN, value);
    }

    /**
     * Method setBackground
     * @param value String
     */
    public void setBackground(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_BACKGROUND, value);
    }

    /**
     * Method setBgcolor
     * @param value String
     */
    public void setBgcolor(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_BGCOLOR, value);
    }

    /**
     * Method setHeight
     * @param value String
     */
    public void setHeight(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_HEIGHT, value);
    }

    /**
     * Method setNowrap
     * @param value String
     */
    public void setNowrap(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_NOWRAP, value);
    }

    /**
     * Method setValign
     * @param value String
     */
    public void setValign(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_VALIGN, value);
    }

    /**
     *
     * @deprecated use setClass()
     * @param value String
     */
    public void setStyleClass(String value)
    {
        setClass(value);
    }

    /**
     * Method setClass
     * @param value String
     */
    public void setClass(String value)
    {
        attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     * Method addClass
     * @param value String
     */
    public void addClass(String value)
    {
        Object lClassAttributes = attributeMap.get(TagConstants.ATTRIBUTE_CLASS);

        if (lClassAttributes == null)
        {
            attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
        }
        else
        {
            ((MultipleHtmlAttribute) lClassAttributes).addAttributeValue(value);
        }
    }

    /**
     * Method setHeaderClass
     * @param value String
     */
    public void setHeaderClass(String value)
    {
        headerAttributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     *
     * @deprecated use setHeaderClass()
     * @param value String
     */
    public void setHeaderStyleClass(String value)
    {
        setHeaderClass(value);
    }

    /**
     * Method setDecorator
     * @param value String
     */
    public void setDecorator(String value)
    {
        decorator = value;
    }

    /**
     * Method getDecoratorObject
     * @return ColumnDecorator
     */
    public ColumnDecorator getDecoratorObject()
    {
        return decoratorObject;
    }

    /**
     * Method setDecoratorObject
     * @param pDecorator ColumnDecorator
     */
    public void setDecoratorObject(ColumnDecorator pDecorator)
    {
        decoratorObject = pDecorator;
    }

    /**
     * Method getProperty
     * @return String
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * Method getTitle
     * @return String
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Method getShowNulls
     * @return boolean
     */
    public boolean getShowNulls()
    {
        return nulls;
    }

    /**
     * Method getAutolink
     * @return boolean
     */
    public boolean getAutolink()
    {
        return autolink;
    }

    /**
     * Method getHref
     * @return Href
     */
    public Href getHref()
    {
        return href;
    }

    /**
     * Method getParamId
     * @return String
     */
    public String getParamId()
    {
        return paramId;
    }

    /**
     * Method getParamName
     * @return String
     */
    public String getParamName()
    {
        return paramName;
    }

    /**
     * Method getParamProperty
     * @return String
     */
    public String getParamProperty()
    {
        return paramProperty;
    }

    /**
     * Method getParamScope
     * @return String
     */
    public String getParamScope()
    {
        return paramScope;
    }

    /**
     * Method getMaxLength
     * @return int
     */
    public int getMaxLength()
    {
        return maxLength;
    }

    /**
     * Method getMaxWords
     * @return int
     */
    public int getMaxWords()
    {
        return maxWords;
    }

    /**
     * Method getHeaderStyleClass
     * @return String
     */
    public String getHeaderStyleClass()
    {
        return headerClazz;
    }

    /**
     * Method isAlreadySorted
     * @return boolean
     */
    public boolean isAlreadySorted()
    {
        return alreadySorted;
    }

    /**
     * Method setAlreadySorted
     */
    public void setAlreadySorted()
    {
        alreadySorted = true;
    }

    /**
     * Method getDecoratorClassName
     * @return String
     */
    public String getDecoratorClassName()
    {
        return decorator;
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

        TableTag lTableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

        // add column header only once
        if (lTableTag.isFirstIteration())
        {

            HeaderCell lHeaderCell = new HeaderCell();
            lHeaderCell.setHeaderAttributes((HtmlAttributeMap) headerAttributeMap.clone());
            lHeaderCell.setHtmlAttributes((HtmlAttributeMap) attributeMap.clone());
            lHeaderCell.setTitle(title);
            lHeaderCell.setSortable(sortable);
            lHeaderCell.setColumnDecorator(DecoratorFactory.loadColumnDecorator(decorator));
            lHeaderCell.setBeanPropertyName(property);
            lHeaderCell.setShowNulls(nulls);
            lHeaderCell.setMaxLength(maxLength);
            lHeaderCell.setMaxWords(maxWords);
            lHeaderCell.setAutoLink(autolink);
            lHeaderCell.setGroup(group);

            // href and parameter, create link
            if (href != null && paramId != null)
            {

                Href lColHref = new Href(href);

                // parameter value is in a different object than the iterated one
                if (paramName != null || paramScope != null)
                {
                    // create a complete string for compatibility with previous version before expression evaluation.
                    // this approach is optimized for new expressions, not for previous property/scope parameters
                    StringBuffer lExpression = new StringBuffer();

                    // append scope
                    if (paramScope != null && !"".equals(paramScope))
                    {
                        lExpression.append(paramScope).append("Scope.");
                    }

                    // base bean name
                    if (paramId != null)
                    {
                        lExpression.append(paramName);
                    }
                    else
                    {
                        lExpression.append(lTableTag.getName());
                    }

                    // append property
                    if (paramProperty != null && !"".equals(paramProperty))
                    {
                        lExpression.append('.').append(property);
                    }

                    // evaluate expression.
                    // note the value is fixed, not based on any object created during iteration
                    // this is here for compatibility with the old version mainly
                    Object lParamValue = lTableTag.evaluateExpression(lExpression.toString());

                    // add parameter
                    lColHref.addParameter(paramId, lParamValue);
                }
                else
                {
                    // lookup value as a property on the list object. This should not be done here to avoid useless
                    // work when only a part of the list is displayed

                    // set id
                    lHeaderCell.setParamName(paramId);

                    // set property
                    lHeaderCell.setParamProperty(paramProperty);

                }

                // sets the base href
                lHeaderCell.setHref(lColHref);

            }

            lTableTag.addColumn(lHeaderCell);
            mLog.debug("columnTag.doEndTag() :: first iteration - adding header" + lHeaderCell);
        }

        Cell lCell;

        if (property == null)
        {

            Object lCellValue;

            if (getBodyContent() != null)
            {
                String lValue = null;
                BodyContent lBodyContent = getBodyContent();
                if (lBodyContent != null)
                {
                    lValue = lBodyContent.getString();
                }

                if (lValue == null && nulls)
                {
                    lValue = "";
                }

                lCellValue = lValue;
            }

            // BodyContent will be null if the body was not eval'd, eg an empty list.
            else if (lTableTag.isEmpty())
            {
                lCellValue = Cell.EMPTY_CELL;
            }
            else
            {
                throw new MissingAttributeException(
                    getClass(),
                    new String[] { "property attribute", "value attribute", "tag body" });
            }
            lCell = new Cell(lCellValue);

        }
        else
        {
            lCell = Cell.EMPTY_CELL;
        }

        lTableTag.addCell(lCell);

        attributeMap.clear();
        headerAttributeMap.clear();
        staticContent = null;
        paramName = null;

        // fix for tag pooling in tomcat
        setBodyContent(null);

        return super.doEndTag();
    }

    /**
     * Returns a String representation of this Tag that is suitable for
     * printing while debugging.  The format of the string is subject to change
     * but it currently:
     *
     * <p><code>ColumnTag([title],[property],[href])</code></p>
     *
     * <p>Where the placeholders in brackets are replaced with their appropriate
     * instance variables.</p>
     * @return String
     **/
    public String toString()
    {
        return "ColumnTag(" + title + "," + property + "," + href + ")";
    }

    /**
     * Method release
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
        TableTag lTableTag = (TableTag) findAncestorWithClass(this, TableTag.class);
        if (lTableTag == null)
        {
            throw new TagStructureException(getClass(), "column", "table");
        }

        // If the list is empty, do not execute the body; may result in NPE
        if (lTableTag.isEmpty())
        {
            mLog.debug("skipping body, empty list");
            return SKIP_BODY;
        }
        else
        {
            return super.doStartTag();
        }
    }

}
