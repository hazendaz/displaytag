package org.displaytag.util;

import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

/**
 * <p>Object used to contain html multiple attribute value (for the "class" attribute)</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class MultipleHtmlAttribute implements Cloneable
{

    /**
     * HashSet containing splitted attribute values
     */
    private HashSet mAttributeSet;

    /**
     * Constructor for MultipleHtmlAttribute
     * @param pAttributeValue String
     */
    public MultipleHtmlAttribute(String pAttributeValue)
    {

        // split initial attribute
        String[] lAttributes = StringUtils.split(pAttributeValue);

        addAllAttributesFromArray(lAttributes);
    }

    /**
     * Constructor for MultipleHtmlAttribute
     * @param pAttributes Object[]
     */
    private MultipleHtmlAttribute(Object[] pAttributes)
    {

        addAllAttributesFromArray(pAttributes);
    }

    /**
     * add attributes from an array
     * @param pAttributes Object[] Array containing attributes
     */
    private void addAllAttributesFromArray(Object[] pAttributes)
    {

        // number of attributes to add
        int lLength = pAttributes.length;

        // create new HashSet with correct size
        mAttributeSet = new HashSet(lLength);

        // add all the splitted attributes
        for (int lCounter = 0; lCounter < lLength; lCounter++)
        {

            // don't add if empty
            if (!"".equals(pAttributes[lCounter]))
            {
                mAttributeSet.add(pAttributes[lCounter]);
            }

        }
    }

    /**
     * return the list of attributes separated by a space
     * @return String
     */
    public String toString()
    {
        StringBuffer lBuffer = new StringBuffer();

        Iterator lIterator = mAttributeSet.iterator();

        while (lIterator.hasNext())
        {
            // apend next value
            lBuffer.append(lIterator.next());
            if (lIterator.hasNext())
            {
                // append a space if there are more
                lBuffer.append(' ');
            }
        }

        return lBuffer.toString();
    }

    /**
     * Method addAttributeValue
     * @param pAttributeValue String
     */
    public void addAttributeValue(String pAttributeValue)
    {
        // don't add if empty
        if (!"".equals(pAttributeValue))
        {
            mAttributeSet.add(pAttributeValue);
        }

    }

    /**
     * Method clone
     * @return Object
     */
    protected Object clone()
    {
        // creates a totally new object
        return new MultipleHtmlAttribute(mAttributeSet.toArray());
    }

}