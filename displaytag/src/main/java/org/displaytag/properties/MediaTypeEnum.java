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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Enumeration for media types.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class MediaTypeEnum
{

    /**
     * Array containing all the export types.
     */
    private static final List ALL = new ArrayList();

    /**
     * media type HTML = 0.
     */
    public static final MediaTypeEnum HTML = new MediaTypeEnum(0, "html"); //$NON-NLS-1$

    /**
     * Media type CSV = 1.
     */
    public static final MediaTypeEnum CSV = new MediaTypeEnum(1, "csv"); //$NON-NLS-1$

    /**
     * media type EXCEL = 2.
     */
    public static final MediaTypeEnum EXCEL = new MediaTypeEnum(2, "excel"); //$NON-NLS-1$

    /**
     * media type XML = 3.
     */
    public static final MediaTypeEnum XML = new MediaTypeEnum(3, "xml"); //$NON-NLS-1$

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
     * @param name description of media type
     */
    private MediaTypeEnum(int code, String name)
    {
        this.enumCode = code;
        this.enumName = name;
        ALL.add(this);
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
     * @return String description of the media type ("excel", "xml", "csv", "html")
     */
    public String getName()
    {
        return this.enumName;
    }

    /**
     * lookup a media type by key.
     * @param key int code
     * @return MediaTypeEnum or null if no mediaType is found with the given key
     */
    public static MediaTypeEnum fromCode(int key)
    {
        // @todo optimization needed
        for (int i = 0; i < ALL.size(); i++)
        {
            if (key == ((MediaTypeEnum) ALL.get(i)).getCode())
            {
                return (MediaTypeEnum) ALL.get(i);
            }
        }
        // lookup failed
        return null;
    }

    /**
     * lookup a media type by an Integer key.
     * @param key Integer code - null safe: a null key returns a null Enum
     * @return MediaTypeEnum or null if no mediaType is found with the given key
     */
    public static MediaTypeEnum fromCode(Integer key)
    {
        if (key == null)
        {
            return null;
        }

        return fromCode(key.intValue());
    }

    /**
     * lookup a media type by an Integer key.
     * @param key Integer code - null safe: a null key returns a null Enum
     * @return MediaTypeEnum or null if no mediaType is found with the given key
     * @deprecated use fromCode(Integer)
     */
    public static MediaTypeEnum fromIntegerCode(Integer key)
    {
        return fromCode(key);
    }

    /**
     * Lookup a media type by a String key.
     * @param code String code - null safe: a null key returns a null Enum
     * @return MediaTypeEnum or null if no mediaType is found with the given key
     */
    public static MediaTypeEnum fromName(String code)
    {
        // @todo optimization needed
        for (int i = 0; i < ALL.size(); i++)
        {
            if (((MediaTypeEnum) ALL.get(i)).getName().equals(code))
            {
                return ((MediaTypeEnum) ALL.get(i));
            }
        }
        // lookup failed
        return null;
    }

    /**
     * returns an iterator on all the media type.
     * @return iterator
     */
    public static Iterator iterator()
    {
        return ALL.iterator();
    }

    /**
     * Register a new MediaType. If <code>name</code> is already assigned the existing instance is returned, otherwise
     * a new instance is created.
     * @param name media name
     * @return assigned MediaTypeEnum instance
     */
    public static synchronized MediaTypeEnum registerMediaType(String name)
    {
        MediaTypeEnum existing = fromName(name);
        if (existing == null)
        {
            existing = new MediaTypeEnum(ALL.size() + 1, name);
        }
        return existing;
    }

    /**
     * Returns the number of media type currently loaded.
     * @return number of media types loaded
     */
    public static int getSize()
    {
        return ALL.size();
    }

    /**
     * returns the media type description.
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return getName();
    }

    /**
     * Only a single instance of a specific MediaTypeEnum can be created, so we can check using ==.
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
        return new HashCodeBuilder(1188997057, -1289297553).append(this.enumCode).toHashCode();
    }

}