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
package org.displaytag.properties;

import java.util.Iterator;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Enumeration for sort order.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class SortOrderEnum
{

    /**
     * Sorted in descending order (1, "descending").
     */
    public static final SortOrderEnum DESCENDING = new SortOrderEnum(1, "descending"); //$NON-NLS-1$

    /**
     * Sorted in ascending order (2, "ascending").
     */
    public static final SortOrderEnum ASCENDING = new SortOrderEnum(2, "ascending"); //$NON-NLS-1$

    /**
     * array containing all the export types.
     */
    static final SortOrderEnum[] ALL = {DESCENDING, ASCENDING};

    /**
     * Code; this is the primary key for these objects.
     */
    private final int enumCode;

    /**
     * description.
     */
    private final String enumName;

    /**
     * private constructor. Use only constants.
     * @param code int code
     * @param name description of enumerated type
     */
    private SortOrderEnum(int code, String name)
    {
        this.enumCode = code;
        this.enumName = name;
    }

    /**
     * returns the int code.
     * @return int code
     */
    public int getCode()
    {
        return this.enumCode;
    }

    /**
     * returns the description.
     * @return String description of the sort order ("ascending" or "descending")
     */
    public String getName()
    {
        return this.enumName;
    }

    /**
     * lookup a SortOrderEnum by key.
     * @param key int code
     * @return SortOrderEnum or null if no SortOrderEnum is found with the given key
     */
    public static SortOrderEnum fromCode(int key)
    {
        for (int i = 0; i < ALL.length; i++)
        {
            if (key == ALL[i].getCode())
            {
                return ALL[i];
            }
        }
        // lookup failed
        return null;
    }

    /**
     * lookup a SortOrderEnum by an Integer key.
     * @param key Integer code - null safe: a null key returns a null Enum
     * @return SortOrderEnum or null if no SortOrderEnum is found with the given key
     */
    public static SortOrderEnum fromCode(Integer key)
    {
        if (key == null)
        {
            return null;
        }

        return fromCode(key.intValue());
    }

    /**
     * lookup a SortOrderEnum by an Integer key.
     * @param key Integer code - null safe: a null key returns a null Enum
     * @return SortOrderEnum or null if no SortOrderEnum is found with the given key
     * @deprecated use fromCode(Integer)
     */
    public static SortOrderEnum fromIntegerCode(Integer key)
    {
        return fromCode(key);
    }

    /**
     * Lookup a SortOrderEnum by a String key.
     * @param code String code - null safe: a null key returns a null Enum
     * @return SortOrderEnum or null if no SortOrderEnum is found with the given key
     */
    public static SortOrderEnum fromName(String code)
    {
        for (int i = 0; i < ALL.length; i++)
        {
            if (ALL[i].getName().equals(code))
            {
                return ALL[i];
            }
        }
        // lookup failed
        return null;
    }

    /**
     * returns an iterator on all the enumerated instaces.
     * @return iterator
     */
    public static Iterator iterator()
    {
        return new ArrayIterator(ALL);
    }

    /**
     * returns the enumeration description.
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return getName();
    }

    /**
     * Only a single instance of a specific enumeration can be created, so we can check using ==.
     * @param o the object to compare to
     * @return hashCode
     */
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return new HashCodeBuilder(1123997057, -1289836553).append(this.enumCode).toHashCode();
    }

}