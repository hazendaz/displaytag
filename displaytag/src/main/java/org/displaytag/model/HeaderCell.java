package org.displaytag.model;

import org.apache.commons.lang.StringUtils;
import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.HtmlTagUtil;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;

/**
 * <p>DataObject representing the column header</p>
 * <p>The header cell contains all the properties common to cells in the same column</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class HeaderCell
{

    /**
     * Map containing the html tag attributes for cells (td)
     */
    private HtmlAttributeMap mHtmlAttributes;

    /**
     * Map containing the html tag attributes for header cells (td)
     */
    private HtmlAttributeMap mHeaderAttributes;

    /**
     * base href for creating dinamic links
     */
    private Href mHref;

    /**
     * param name used in adding a link
     */
    private String mParamName;

    /**
     * property of the object where to get the param value from
     */
    private String mParamProperty;

    /**
     * column title
     */
    private String mTitle;

    /**
     * is the column sortable?
     */
    private boolean mSortable;

    /**
     * ColumnDecorator
     */
    private ColumnDecorator mColumnDecorator;

    /**
     * column number
     */
    private int mColumnNumber;

    /**
     * is the column sorted?
     */
    private boolean mAlreadySorted;

    /**
     * property name to look up in the bean
     */
    private String mBeanPropertyName;

    /**
     * show null values?
     */
    private boolean mShowNulls;

    /**
     * max length of cell content
     */
    private int mMaxLength;

    /**
     * max number of words for cell content
     */
    private int mMaxWords;

    /**
     * autolink url?
     */
    private boolean mAutoLink;

    /**
     * group the column?
     */
    private int mGroup;

    /**
     * getter for the grouping index
     * @return 0 if the column is not grouped or the grouping order
     */
    public int getGroup()
    {
        return mGroup;
    }

    /**
     * setter for the grouping index
     * @param pGroup int grouping order (>0)
     */
    public void setGroup(int pGroup)
    {
        mGroup = pGroup;
    }

    /**
     * is autolink enabled?
     * @return true if autolink is enabled for the column
     */
    public boolean getAutoLink()
    {
        return mAutoLink;
    }

    /**
     * enable or disable autolink for the column
     * @param pAutoLink boolean autolink enabled
     */
    public void setAutoLink(boolean pAutoLink)
    {
        mAutoLink = pAutoLink;
    }

    /**
     * getter for the max number of characters to display in the column
     * @return int number of characters to display in the column
     */
    public int getMaxLength()
    {
        return mMaxLength;
    }

    /**
     * setter for the max number of characters to display in the column
     * @param pMaxLength number of characters to display in the column
     */
    public void setMaxLength(int pMaxLength)
    {
        mMaxLength = pMaxLength;
    }

    /**
     * getter for the max number of words to display in the column
     * @return int number of words to display in the column
     */
    public int getMaxWords()
    {
        return mMaxWords;
    }

    /**
     * setter for the max number of words to display in the column
     * @param pMaxWords number of words to display in the column
     */
    public void setMaxWords(int pMaxWords)
    {
        mMaxWords = pMaxWords;
    }

    /**
     * should null be displayed?
     * @return true null will be displayed in cell content
     */
    public boolean getShowNulls()
    {
        return mShowNulls;
    }

    /**
     * enable or disable displaying of null values
     * @param pShowNulls boolean true if null should be displayed
     */
    public void setShowNulls(boolean pShowNulls)
    {
        mShowNulls = pShowNulls;
    }

    /**
     * getter for the name of the property to look up in the bean
     * @return String name of the property to look up in the bean
     */
    public String getBeanPropertyName()
    {
        return mBeanPropertyName;
    }

    /**
     * setter for the name of the property to look up in the bean
     * @param pBeanPropertyName - name of the property to look up in the bean
     */
    public void setBeanPropertyName(String pBeanPropertyName)
    {
        mBeanPropertyName = pBeanPropertyName;
    }

    /**
     * is the column already sorted?
     * @return true if the column already sorted
     */
    public boolean isAlreadySorted()
    {
        return mAlreadySorted;
    }

    /**
     * setter for the sorted property (the column is actually sorted)
     */
    public void setAlreadySorted()
    {
        mAlreadySorted = true;
    }

    /**
     * getter for the column number
     * @return int column number
     */
    public int getColumnNumber()
    {
        return mColumnNumber;
    }

    /**
     * setter for the column number
     * @param pColumnNumber - int column number
     */
    public void setColumnNumber(int pColumnNumber)
    {
        mColumnNumber = pColumnNumber;
    }

    /**
     * return the columnDecorator object for this column
     * @return ColumnDecorator
     */
    public ColumnDecorator getColumnDecorator()
    {
        return mColumnDecorator;
    }

    /**
     * st the columnDecorator object for this column
     * @param pColumnDecorator - the ColumnDecorator
     */
    public void setColumnDecorator(ColumnDecorator pColumnDecorator)
    {
        mColumnDecorator = pColumnDecorator;
    }

    /**
     * is the column sortable?
     * @return true if the column is sortable
     */
    public boolean getSortable()
    {
        return mSortable;
    }

    /**
     * is the column sortable?
     * @param pSortable - true if the column can be sorted
     */
    public void setSortable(boolean pSortable)
    {
        mSortable = pSortable;
    }

    /**
     * get the column title
     * @return the column title. If no title is specified the capitalized bean property name is returned
     */
    public String getTitle()
    {
        if (mTitle != null)
        {
            return mTitle;
        }
        else if (mBeanPropertyName != null)
        {
            return StringUtils.capitalise(mBeanPropertyName);
        }

        return "";
    }

    /**
     * setter for the column title
     * @param pValue - the column title
     */
    public void setTitle(String pValue)
    {
        mTitle = pValue;
    }

    /**
     * returns the HtmlAttributeMap containg all the html attributes for the <strong>td</strong> tags
     * @return HtmlAttributeMap with td attributes
     */
    public HtmlAttributeMap getHtmlAttributes()
    {
        return mHtmlAttributes;
    }

    /**
     * set the HtmlAttributeMap containg all the html attributes for the <strong>td</strong> tags
     * @param pHtmlAttributes - HtmlAttributeMap
     */
    public void setHtmlAttributes(HtmlAttributeMap pHtmlAttributes)
    {
        mHtmlAttributes = pHtmlAttributes;
    }

    /**
     * returns the HtmlAttributeMap containg all the html attributes for the <strong>th</strong> tag
     * @return HtmlAttributeMap with th attributes
     */
    public HtmlAttributeMap getHeaderAttributes()
    {
        return mHeaderAttributes;
    }

    /**
     * set the HtmlAttributeMap containg all the html attributes for the <strong>th</strong> tag
     * @param pHeaderAttributes - HtmlAttributeMap
     */
    public void setHeaderAttributes(HtmlAttributeMap pHeaderAttributes)
    {
        mHeaderAttributes = pHeaderAttributes;
    }

    /**
     * add a css class to the html "class" attribute
     * @param pClass String
     */
    public void addHeaderClass(String pClass)
    {
        Object lClassAttributes = mHeaderAttributes.get(TagConstants.ATTRIBUTE_CLASS);

        // handle multiple values
        if (lClassAttributes == null)
        {
            mHeaderAttributes.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(pClass));
        }
        else
        {
            ((MultipleHtmlAttribute) lClassAttributes).addAttributeValue(pClass);
        }
    }

    /**
     * return the open tag for a cell (td)
     * @return String &lt;td&gt; tag with attributes
     */
    public String getOpenTag()
    {
        return HtmlTagUtil.createOpenTagString(TagConstants.TAGNAME_COLUMN, mHtmlAttributes);
    }

    /**
     * return the open tag for a column header (th)
     * @return String &lt;th&gt; tag with attributes
     */
    public String getHeaderOpenTag()
    {
        return HtmlTagUtil.createOpenTagString(TagConstants.TAGNAME_COLUMN_HEADER, mHeaderAttributes);
    }

    /**
     * return the closing tag for a cell (td)
     * @return String &lt;/td&gt;
     */
    public String getCloseTag()
    {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN + TagConstants.TAG_CLOSE;
    }

    /**
     * return the closing tag for a column header (th)
     * @return String &lt;/th&gt;
     */
    public String getHeaderCloseTag()
    {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN_HEADER + TagConstants.TAG_CLOSE;
    }

    /**
     * simple toString with title and bean property name
     * @return String in the follwing format: "[HeaderCell title=" + mTitle + " property=" + mBeanPropertyName + "]"
     */
    public String toString()
    {
        return "[HeaderCell title=" + mTitle + " property=" + mBeanPropertyName + "]";
    }

    /**
     * Sets the href to be used for dinamic links in cells
     * @param pHref base href for links
     */
    public void setHref(Href pHref)
    {
        mHref = pHref;
    }

    /**
     * Returns the href to be used for dinamic links in cells
     * @return Href base href for links
     */
    public Href getHref()
    {
        return mHref;
    }

    /**
     * Sets the name of the param to add to links
     * @param pParamName name of the param
     */
    public void setParamName(String pParamName)
    {
        mParamName = pParamName;
    }

    /**
     * Returns the name of the param to add to links
     * @return String name of the param
     */
    public String getParamName()
    {
        return mParamName;
    }

    /**
     * Sets the name of the property to look up in bean to get the param value for links
     * @param pParamProperty name of the property to look up in bean to get the param value for links
     */
    public void setParamProperty(String pParamProperty)
    {
        mParamProperty = pParamProperty;
    }

    /**
     * Returns the name of the property to look up in bean to get the param value for links
     * @return String name of the property to look up in bean to get the param value for links
     */
    public String getParamProperty()
    {
        return mParamProperty;
    }

}