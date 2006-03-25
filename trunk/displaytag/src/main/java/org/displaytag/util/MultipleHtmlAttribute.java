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
package org.displaytag.util;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;


/**
 * Object used to contain html multiple attribute value (for the "class" attribute).
 * @author Fabrizio Giustina
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
        this.attributeSet = new LinkedHashSet();
        addAllAttributesFromArray(StringUtils.split(attributeValue));
    }

    /**
     * Adds attributes from an array.
     * @param attributes Object[] Array containing attributes
     */
    private void addAllAttributesFromArray(String[] attributes)
    {
        if (attributes == null)
        {
            return;
        }

        // number of attributes to add
        int length = attributes.length;

        // add all the splitted attributes
        for (int j = 0; j < length; j++)
        {

            // don't add if empty
            if (!StringUtils.isEmpty(attributes[j]))
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
        if (!StringUtils.isEmpty(attributeValue))
        {
            this.attributeSet.add(attributeValue);
        }

    }

    /**
     * Return true if this MultipleHtmlValue doesn't store any value.
     * @return <code>true</code> if this MultipleHtmlValue doesn't store any value
     */
    public boolean isEmpty()
    {
        return attributeSet.isEmpty();
    }

    /**
     * @see java.lang.Object#clone()
     */
    protected Object clone()
    {
        MultipleHtmlAttribute clone;

        try
        {
            clone = (MultipleHtmlAttribute) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            // should never happen
            throw new UnhandledException(e);
        }

        // copy attributes
        clone.addAllAttributesFromArray((String[]) this.attributeSet.toArray(new String[this.attributeSet.size()]));

        return clone;
    }

}