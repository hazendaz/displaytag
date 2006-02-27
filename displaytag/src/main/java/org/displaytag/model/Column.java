package org.displaytag.model;

import java.util.StringTokenizer;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.util.Anchor;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.HtmlTagUtil;
import org.displaytag.util.LinkUtil;
import org.displaytag.util.LookupUtil;
import org.displaytag.util.TagConstants;

/**
 * <p>Represents a column in a table</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class Column
{

    /**
     * Row this column belongs to
     */
    private Row mParentRow;

    /**
     * Header of this column. The header cell contains all the attributes common to all cells in the same column
     */
    private HeaderCell mHeaderCell;

    /**
     * copy of the attribute map from the header cell. Needed to change attributes (title) in this cell only
     */
    private HtmlAttributeMap mHtmlAttributes;

    /**
     * contains the evaluated body value. Filled in getOpenTag
     */
    private String mStringValue;

    /**
     * Field mCell
     */
    private Cell mCell;

    /**
     * Constructor for Column
     * @param pHeaderCell HeaderCell
     * @param pCell Cell
     * @param pParentRow Row
     */
    public Column(HeaderCell pHeaderCell, Cell pCell, Row pParentRow)
    {
        mHeaderCell = pHeaderCell;
        mParentRow = pParentRow;
        mCell = pCell;

        // also copy html attributes
        mHtmlAttributes = pHeaderCell.getHtmlAttributes();
    }

    /**
     * Method getValue
     * @param pDecorated boolean
     * @return Object
     * @throws ObjectLookupException for errors in bean property lookup
     * @throws DecoratorException if a column decorator is used and an exception is thrown during value decoration
     */
    public Object getValue(boolean pDecorated) throws ObjectLookupException, DecoratorException
    {
        // a static value has been set?
        if (mCell.getStaticValue() != null)
        {
            return mCell.getStaticValue();
        }

        Object lObject = null;
        TableDecorator lTableDecorator = mParentRow.getParentTable().getTableDecorator();

        // if a decorator has been set, and if decorator has a getter for the requested property only, check decorator
        if (pDecorated && lTableDecorator != null && lTableDecorator.hasGetterFor(mHeaderCell.getBeanPropertyName()))
        {

            lObject = LookupUtil.getBeanProperty(lTableDecorator, mHeaderCell.getBeanPropertyName());
        }
        else
        {
            // else check underlining oblject
            lObject = LookupUtil.getBeanProperty(mParentRow.getObject(), mHeaderCell.getBeanPropertyName());
        }

        if (pDecorated && (mHeaderCell.getColumnDecorator() != null))
        {
            lObject = mHeaderCell.getColumnDecorator().decorate(lObject);
        }

        if (lObject == null || lObject.equals("null"))
        {
            if (!mHeaderCell.getShowNulls())
            {
                lObject = "";
            }
        }

        return lObject;
    }

    /**
     * Method getOpenTag
     * @return String
     * @throws ObjectLookupException for errors in bean property lookup
     * @throws DecoratorException if a column decorator is used and an exception is thrown during value decoration
     */
    public String getOpenTag() throws ObjectLookupException, DecoratorException
    {
        mStringValue = createChoppedAndLinkedValue();

        return HtmlTagUtil.createOpenTagString(TagConstants.TAGNAME_COLUMN, mHtmlAttributes);
    }

    /**
     * Method getCloseTag
     * @return String
     */
    public String getCloseTag()
    {
        mStringValue = null;
        return mHeaderCell.getCloseTag();
    }

    /**
     * Method createChoppedAndLinkedValue
     * @return String
     * @throws ObjectLookupException for errors in bean property lookup
     * @throws DecoratorException if a column decorator is used and an exception is thrown during value decoration
     */
    public String createChoppedAndLinkedValue() throws ObjectLookupException, DecoratorException
    {

        Object lChoppedValue = getValue(true);

        boolean lChopped = false;
        String lFullValue = "";
        if (lChoppedValue != null)
        {
            lFullValue = lChoppedValue.toString();
        }

        // trim the string if a maxLength or maxWords is defined
        if (mHeaderCell.getMaxLength() > 0 && lFullValue.length() > mHeaderCell.getMaxLength())
        {
            lChoppedValue = lFullValue.substring(0, mHeaderCell.getMaxLength()) + "...";
            lChopped = true;
        }
        else if (mHeaderCell.getMaxWords() > 0)
        {
            StringBuffer lBuffer = new StringBuffer();
            StringTokenizer lTokenizer = new StringTokenizer(lFullValue);
            int lTokensNum = lTokenizer.countTokens();
            if (lTokensNum > mHeaderCell.getMaxWords())
            {
                int lWordsCount = 0;
                while (lTokenizer.hasMoreTokens() && (lWordsCount < mHeaderCell.getMaxWords()))
                {
                    lBuffer.append(lTokenizer.nextToken() + " ");
                    lWordsCount++;
                }
                lBuffer.append("...");
                lChoppedValue = lBuffer;
                lChopped = true;
            }
        }

        // chopped content? add the full content to the column "title" attribute
        if (lChopped)
        {
            // clone the attribute map, don't want to add title to all the columns
            mHtmlAttributes = (HtmlAttributeMap) mHtmlAttributes.clone();
            // add title
            mHtmlAttributes.put(TagConstants.ATTRIBUTE_TITLE, lFullValue);
        }

        // Are we supposed to set up a link to the data being displayed in this column...
        if (mHeaderCell.getAutoLink())
        {
            lChoppedValue = LinkUtil.autoLink(lChoppedValue.toString());
        }
        else if (mHeaderCell.getHref() != null) // add link?
        {
            // copy href
            Href lColHref = new Href(mHeaderCell.getHref());

            // do we need to add a param?
            if (mHeaderCell.getParamName() != null)
            {

                Object lParamValue;

                if (mHeaderCell.getParamProperty() != null)
                {
                    // different property, go get it
                    lParamValue = LookupUtil.getBeanProperty(mParentRow.getObject(), mHeaderCell.getParamProperty());

                }
                else
                {
                    // same property as content
                    lParamValue = lFullValue;
                }

                lColHref.addParameter(mHeaderCell.getParamName(), lParamValue);

            }
            Anchor lAtag = new Anchor(lColHref, lChoppedValue.toString());

            lChoppedValue = lAtag.toString();
        }

        if (lChoppedValue != null)
        {
            return lChoppedValue.toString();
        }
        return null;
    }

    /**
     * get the final value to be displayed in the table. This method can only be called after getOpenTag(), where the
     * content is evaluated
     * @return String final value to be displayed in the table
     */
    public String getChoppedAndLinkedValue()
    {
        return mStringValue;
    }

    /**
     * returns the grouping order of this column or -1 if the column is not grouped
     * @return int grouping order of this column or -1 if the column is not grouped
     */
    public int getGroup()
    {
        return mHeaderCell.getGroup();
    }

    /**
     * Method toString
     * @return String
     */
    public String toString()
    {
        return new ToStringBuilder(this).append("headerCell", mHeaderCell).append("cell", mCell).toString();
    }

}