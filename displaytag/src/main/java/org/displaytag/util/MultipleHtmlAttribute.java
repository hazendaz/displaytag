package org.displaytag.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * Object used to contain html multiple attribute value (for the "class" attribute).
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class MultipleHtmlAttribute implements Cloneable
{

    /**
     * Sets containing splitted attribute values.
     */
    private Set attributeSet;

    /**
     * Constructor for MultipleHtmlAttribute.
     * @param attributeValue String
     */
    public MultipleHtmlAttribute(String attributeValue)
    {

        // split initial attribute
        String[] attributes = StringUtils.split(attributeValue);

        addAllAttributesFromArray(attributes);
    }

    /**
     * Constructor for MultipleHtmlAttribute.
     * @param attributes Object[]
     */
    private MultipleHtmlAttribute(Object[] attributes)
    {

        addAllAttributesFromArray(attributes);
    }

    /**
     * Adds attributes from an array.
     * @param attributes Object[] Array containing attributes
     */
    private void addAllAttributesFromArray(Object[] attributes)
    {

        // number of attributes to add
        int length = attributes.length;

        // create new HashSet with correct size
        this.attributeSet = new HashSet(length);

        // add all the splitted attributes
        for (int j = 0; j < length; j++)
        {

            // don't add if empty
            if (!"".equals(attributes[j]))
            {
                this.attributeSet.add(attributes[j]);
            }

        }
    }

    /**
     * Returns the list of attributes separated by a space.
     * @return String
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        Iterator iterator = this.attributeSet.iterator();

        while (iterator.hasNext())
        {
            // apend next value
            buffer.append(iterator.next());
            if (iterator.hasNext())
            {
                // append a space if there are more
                buffer.append(' ');
            }
        }

        return buffer.toString();
    }

    /**
     * Adds a value to the attribute.
     * @param attributeValue value to add to the attribute
     */
    public void addAttributeValue(String attributeValue)
    {
        // don't add if empty
        if (!"".equals(attributeValue))
        {
            this.attributeSet.add(attributeValue);
        }

    }

    /**
     * @see java.lang.Object#clone()
     */
    protected Object clone()
    {
        // creates a totally new object
        return new MultipleHtmlAttribute(this.attributeSet.toArray());
    }

}