package org.displaytag.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.decorator.DecoratorFactory;
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
    private String mStaticContent;

    /**
     * html pass-through attributes for cells
     */
    private HtmlAttributeMap mAttributeMap = new HtmlAttributeMap();

    /**
     * html pass-through attributes for cell headers
     */
    private HtmlAttributeMap mHeaderAttributeMap = new HtmlAttributeMap();

    /**
     * the property method that is called to retrieve the information to be displayed in this column.
     * This method is called on the current object in the iteration for the given row.
     * The property format is in typical struts format for properties (required)
     */
    private String mProperty;

    /**
     * the title displayed for this column.  if this is omitted then the property name is used
     * for the title of the column (optional)
     */
    private String mTitle;

    /**
     * by default, null values don't appear in the list, by setting viewNulls to 'true', then
     * null values will appear as "null" in the list (mostly useful for debugging) (optional)
     */
    private boolean mNulls;

    /**
     * Field mSortable
     */
    private boolean mSortable;

    /**
     * if set to true, then any email addresses and URLs found in the content of the column are
     * automatically converted into a hypertext link.
     */
    private boolean mAutolink;

    /**
     * the grouping level (starting at 1 and incrementing) of this column (indicates if successive
     * contain the same values, then they should not be displayed).  The level indicates that if a
     * lower level no longer matches, then the matching for this higher level should start over as
     * well. If this attribute is not included, then no grouping is performed. (optional)
     */
    private int mGroup = -1;

    /**
     * if this attribute is provided, then the data that is shown for this column is wrapped inside
     * a &lt;a href&gt; tag with the url provided through this attribute. Typically you would use
     * this attribute along with one of the struts-like param attributes below to create a dynamic
     * link so that each row creates a different URL based on the data that is being viewed. (optional)
     */
    private Href mHref;

    /**
     * The name of the request parameter that will be dynamically added to the generated href URL.
     * The corresponding value is defined by the paramProperty and (optional) paramName attributes,
     * optionally scoped by the paramScope attribute. (optional)
     */
    private String mParamId;

    /**
     * The name of a JSP bean that is a String containing the value for the request parameter named
     * by paramId (if paramProperty is not specified), or a JSP bean whose property getter is called
     * to return a String (if paramProperty is specified). The JSP bean is constrained to the bean
     * scope specified by the paramScope property, if it is specified. If paramName is omitted, then
     * it is assumed that the current object being iterated on is the target bean. (optional)
     */
    private String mParamName;

    /**
     * The name of a property of the bean specified by the paramName attribute (or the current object
     * being iterated on if paramName is not provided), whose return value must be a String containing
     * the value of the request parameter (named by the paramId attribute) that will be dynamically
     * added to this href URL. (optional)
     * @deprecated use Expressions in paramName
     */
    private String mParamProperty;

    /**
     * The scope within which to search for the bean specified by the paramName attribute. If not
     * specified, all scopes are searched. If paramName is not provided, then the current object
     * being iterated on is assumed to be the target bean. (optional)
     * @deprecated use Expressions in paramName
     */
    private String mParamScope;

    /**
     * If this attribute is provided, then the column's displayed is limited to this number of
     * characters.  An elipse (...) is appended to the end if this column is linked, and the user
     * can mouseover the elipse to get the full text. (optional)
     */
    private int mMaxLength;

    /**
     * If this attribute is provided, then the column's displayed is limited to this number of words.
     * An elipse (...) is appended to the end if this column is linked, and the user can mouseover
     * the elipse to get the full text. (optional)
     */
    private int mMaxWords;

    /**
     * Field mHeaderClazz
     */
    private String mHeaderClazz;

    /**
     * static value if no property attribute is provided
     */
    private Object mValue;

    /** a class that should be used to "decorate" the underlying object being displayed. If a decorator
     * is specified for the entire table, then this decorator will decorate that decorator. (optional)
     */
    private String mDecorator;

    /**
     * Field mDecoratorObject
     */
    private ColumnDecorator mDecoratorObject;

    /**
     * Method getStaticContent
     * @return String
     */
    public String getStaticContent()
    {

        return mStaticContent;
    }

    /**
     * Method setProperty
     * @param pAttributeValue String
     */
    public void setProperty(String pAttributeValue)
    {
        mProperty = pAttributeValue;
    }

    /**
     * Method setTitle
     * @param pAttributeValue String
     */
    public void setTitle(String pAttributeValue)
    {
        mTitle = pAttributeValue;
    }

    /**
     * Method setNulls
     * @param pAttributeValue String
     */
    public void setNulls(String pAttributeValue)
    {
        if (!Boolean.FALSE.toString().equals(pAttributeValue))
        {
            mNulls = true;
        }

    }

    /**
     * Method setSortable
     * @param pAttributeValue String
     */
    public void setSortable(String pAttributeValue)
    {
        if (!Boolean.FALSE.toString().equals(pAttributeValue))
        {
            mSortable = true;
        }
    }

    /**
     *
     * @deprecated use setSortable()
     * @param pAttributeValue String
     */
    public void setSort(String pAttributeValue)
    {
        setSortable(pAttributeValue);
    }

    /**
     * Method setAutolink
     * @param pAttributeValue String
     */
    public void setAutolink(String pAttributeValue)
    {
        if (!"false".equals(pAttributeValue))
        {
            mAutolink = true;
        }
    }

    /**
     * Method setGroup
     * @param pAttributeValue String
     */
    public void setGroup(String pAttributeValue)
    {
        try
        {
            mGroup = Integer.parseInt(pAttributeValue);
        }
        catch (NumberFormatException e)
        {
            // ignore?
            mLog.warn("Invalid \"group\" attribute: value=\"" + pAttributeValue + "\"");
        }
    }

    /**
     * Method setHref
     * @param pAttributeValue String
     */
    public void setHref(String pAttributeValue)
    {
        mHref = new Href(pAttributeValue);
    }

    /**
     * Method setParamId
     * @param pAttributeValue String
     */
    public void setParamId(String pAttributeValue)
    {
        mParamId = pAttributeValue;
    }

    /**
     * Method setParamName
     * @param pAttributeValue String
     */
    public void setParamName(String pAttributeValue)
    {
        mParamName = pAttributeValue;
    }

    /**
     * Method setParamProperty
     * @param pAttributeValue String
     */
    public void setParamProperty(String pAttributeValue)
    {
        mParamProperty = pAttributeValue;
    }

    /**
     * Method setParamScope
     * @param pAttributeValue String
     */
    public void setParamScope(String pAttributeValue)
    {
        mParamScope = pAttributeValue;
    }

    /**
     * Method setMaxLength
     * @param pAttributeValue int
     */
    public void setMaxLength(int pAttributeValue)
    {
        mMaxLength = pAttributeValue;
    }

    /**
     * Method setMaxWords
     * @param pAttributeValue int
     */
    public void setMaxWords(int pAttributeValue)
    {
        mMaxWords = pAttributeValue;
    }

    /**
     * Method setWidth
     * @param pAttributeValue String
     */
    public void setWidth(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_WIDTH, pAttributeValue);
        mHeaderAttributeMap.put(TagConstants.ATTRIBUTE_WIDTH, pAttributeValue);
    }

    /**
     * Method setAlign
     * @param pAttributeValue String
     */
    public void setAlign(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_ALIGN, pAttributeValue);
        mHeaderAttributeMap.put(TagConstants.ATTRIBUTE_ALIGN, pAttributeValue);
    }

    /**
     * Method setBackground
     * @param pAttributeValue String
     */
    public void setBackground(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_BACKGROUND, pAttributeValue);
    }

    /**
     * Method setBgcolor
     * @param pAttributeValue String
     */
    public void setBgcolor(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_BGCOLOR, pAttributeValue);
    }

    /**
     * Method setHeight
     * @param pAttributeValue String
     */
    public void setHeight(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_HEIGHT, pAttributeValue);
    }

    /**
     * Method setNowrap
     * @param pAttributeValue String
     */
    public void setNowrap(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_NOWRAP, pAttributeValue);
    }

    /**
     * Method setValign
     * @param pAttributeValue String
     */
    public void setValign(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_VALIGN, pAttributeValue);
    }

    /**
     *
     * @deprecated use setClass()
     * @param pAttributeValue String
     */
    public void setStyleClass(String pAttributeValue)
    {
        setClass(pAttributeValue);
    }

    /**
     * Method setClass
     * @param pAttributeValue String
     */
    public void setClass(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(pAttributeValue));
    }

    /**
     * Method addClass
     * @param pAttributeValue String
     */
    public void addClass(String pAttributeValue)
    {
        Object lClassAttributes = mAttributeMap.get(TagConstants.ATTRIBUTE_CLASS);

        if (lClassAttributes == null)
        {
            mAttributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(pAttributeValue));
        }
        else
        {
            ((MultipleHtmlAttribute) lClassAttributes).addAttributeValue(pAttributeValue);
        }
    }

    /**
     * Method setHeaderClass
     * @param pAttributeValue String
     */
    public void setHeaderClass(String pAttributeValue)
    {
        mHeaderAttributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(pAttributeValue));
    }

    /**
     *
     * @deprecated use setHeaderClass()
     * @param pAttributeValue String
     */
    public void setHeaderStyleClass(String pAttributeValue)
    {
        setHeaderClass(pAttributeValue);
    }

    /**
     * Method setValue
     * @param pAttributeValue Object
     */
    public void setValue(Object pAttributeValue)
    {
        mValue = pAttributeValue;
    }

    /**
     * Method setDecorator
     * @param pAttributeValue String
     */
    public void setDecorator(String pAttributeValue)
    {
        mDecorator = pAttributeValue;
    }

    /**
     * Method getDecoratorObject
     * @return ColumnDecorator
     */
    public ColumnDecorator getDecoratorObject()
    {
        return mDecoratorObject;
    }

    /**
     * Method setDecoratorObject
     * @param pDecorator ColumnDecorator
     */
    public void setDecoratorObject(ColumnDecorator pDecorator)
    {
        mDecoratorObject = pDecorator;
    }

    /**
     * Method getProperty
     * @return String
     */
    public String getProperty()
    {
        return mProperty;
    }

    /**
     * Method getTitle
     * @return String
     */
    public String getTitle()
    {
        return mTitle;
    }

    /**
     * Method getShowNulls
     * @return boolean
     */
    public boolean getShowNulls()
    {
        return mNulls;
    }

    /**
     * Method getAutolink
     * @return boolean
     */
    public boolean getAutolink()
    {
        return mAutolink;
    }

    /**
     * Method getHref
     * @return Href
     */
    public Href getHref()
    {
        return mHref;
    }

    /**
     * Method getParamId
     * @return String
     */
    public String getParamId()
    {
        return mParamId;
    }

    /**
     * Method getParamName
     * @return String
     */
    public String getParamName()
    {
        return mParamName;
    }

    /**
     * Method getParamProperty
     * @return String
     */
    public String getParamProperty()
    {
        return mParamProperty;
    }

    /**
     * Method getParamScope
     * @return String
     */
    public String getParamScope()
    {
        return mParamScope;
    }

    /**
     * Method getMaxLength
     * @return int
     */
    public int getMaxLength()
    {
        return mMaxLength;
    }

    /**
     * Method getMaxWords
     * @return int
     */
    public int getMaxWords()
    {
        return mMaxWords;
    }

    /**
     * Method getHeaderStyleClass
     * @return String
     */
    public String getHeaderStyleClass()
    {
        return mHeaderClazz;
    }

    /**
     * Method getValue
     * @return Object
     */
    public Object getValue()
    {
        return mValue;
    }

    /**
     * Method getDecoratorClassName
     * @return String
     */
    public String getDecoratorClassName()
    {
        return mDecorator;
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

        if (lTableTag == null)
        {
            throw new TagStructureException(getClass(), "column", "table");
        }

        // add column header only once
        if (lTableTag.isFirstIteration())
        {

            HeaderCell lHeaderCell = new HeaderCell();
            lHeaderCell.setHeaderAttributes((HtmlAttributeMap) mHeaderAttributeMap.clone());
            lHeaderCell.setHtmlAttributes((HtmlAttributeMap) mAttributeMap.clone());
            lHeaderCell.setTitle(mTitle);
            lHeaderCell.setSortable(mSortable);
            lHeaderCell.setColumnDecorator(DecoratorFactory.loadColumnDecorator(mDecorator));
            lHeaderCell.setBeanPropertyName(mProperty);
            lHeaderCell.setShowNulls(mNulls);
            lHeaderCell.setMaxLength(mMaxLength);
            lHeaderCell.setMaxWords(mMaxWords);
            lHeaderCell.setAutoLink(mAutolink);
            lHeaderCell.setGroup(mGroup);

            // href and parameter, create link
            if (mHref != null && mParamId != null)
            {

                Href lColHref = new Href(mHref);

                // parameter value is in a different object than the iterated one
                if (mParamName != null || mParamScope != null)
                {
                    // create a complete string for compatibility with previous version before expression evaluation.
                    // this approach is optimized for new expressions, not for previous property/scope parameters
                    StringBuffer lExpression = new StringBuffer();

                    // append scope
                    if (mParamScope != null && !"".equals(mParamScope))
                    {
                        lExpression.append(mParamScope).append("Scope.");
                    }

                    // base bean name
                    if (mParamId != null)
                    {
                        lExpression.append(mParamName);
                    }
                    else
                    {
                        lExpression.append(lTableTag.getName());
                    }

                    // append property
                    if (mParamProperty != null && !"".equals(mParamProperty))
                    {
                        lExpression.append('.').append(mProperty);
                    }

                    // evaluate expression.
                    // note the value is fixed, not based on any object created during iteration
                    // this is here for compatibility with the old version mainly
                    Object lParamValue = lTableTag.evaluateExpression(lExpression.toString());

                    // add parameter
                    lColHref.addParameter(mParamId, lParamValue);
                }
                else
                {
                    // lookup value as a property on the list object. This should not be done here to avoid useless
                    // work when only a part of the list is displayed

                    // set id
                    lHeaderCell.setParamName(mParamId);

                    // set property
                    lHeaderCell.setParamProperty(mParamProperty);

                }

                // sets the base href
                lHeaderCell.setHref(lColHref);

            }

            lTableTag.addColumn(lHeaderCell);
            mLog.debug("columnTag.doEndTag() :: first iteration - adding header" + lHeaderCell);
        }

        Cell lCell;

        if (mProperty == null)
        {

            Object lCellValue;

            if (mValue != null)
            {
                lCellValue = mValue;
            }
            else if (getBodyContent() != null)
            {
                String lValue = null;
                BodyContent lBodyContent = getBodyContent();
                if (lBodyContent != null)
                {
                    lValue = lBodyContent.getString();
                }

                if (lValue == null && mNulls)
                {
                    lValue = "";
                }

                lCellValue = lValue;
            }
            else
            {
                mLog.error("Column tag: you must specify a property or value attribute, or a body");
                throw new JspException("Column tag: you must specify a property or value attribute, or a body");
            }
            lCell = new Cell(lCellValue);

        }
        else
        {
            lCell = Cell.EMPTY_CELL;
        }

        lTableTag.addCell(lCell);

        mAttributeMap.clear();
        mHeaderAttributeMap.clear();
        mStaticContent = null;
        mParamName = null;

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
        return "ColumnTag(" + mTitle + "," + mProperty + "," + mHref + ")";
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
     * Field mColumnNumber
     */
    private int mColumnNumber;

    /**
     * Method setColumnNumber
     * @param pColumnNumber int
     */
    public void setColumnNumber(int pColumnNumber)
    {
        mColumnNumber = pColumnNumber;
    }

    /**
     * Method getColumnNumber
     * @return int
     */
    public int getColumnNumber()
    {
        return mColumnNumber;
    }

    /**
     * Field mAlreadySorted
     */
    private boolean mAlreadySorted = false;

    /**
     * Method isAlreadySorted
     * @return boolean
     */
    public boolean isAlreadySorted()
    {
        return mAlreadySorted;
    }

    /**
     * Method setAlreadySorted
     */
    public void setAlreadySorted()
    {
        mAlreadySorted = true;
    }

}
