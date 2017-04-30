/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.properties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;


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
    private static final List<MediaTypeEnum> ALL = new ArrayList<MediaTypeEnum>();

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
     * media type XML = 3.
     */
    public static final MediaTypeEnum PDF = new MediaTypeEnum(4, "pdf"); //$NON-NLS-1$

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
     * @return String description of the media type ("excel", "xml", "csv", "html", "pdf")
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
            if (key == ALL.get(i).getCode())
            {
                return ALL.get(i);
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
    @Deprecated
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
            if (ALL.get(i).getName().equals(code))
            {
                return ALL.get(i);
            }
        }
        // lookup failed
        return null;
    }

    /**
     * returns an iterator on all the media type.
     * @return iterator
     */
    public static Iterator<MediaTypeEnum> iterator()
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
    @Override
    public String toString()
    {
        return getName();
    }

    /**
     * Only a single instance of a specific MediaTypeEnum can be created, so we can check using ==.
     * @param o the object to compare to
     * @return hashCode
     */
    @Override
    public boolean equals(Object o)
    {
        return this == o;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(1188997057, -1289297553).append(this.enumCode).toHashCode();
    }

}