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
     * Media type CSV = 1.
     */
    public static final MediaTypeEnum CSV = new MediaTypeEnum("csv");

    /**
     * media type EXCEL = 2.
     */
    public static final MediaTypeEnum EXCEL = new MediaTypeEnum("excel");

    /**
     * media type XML = 3.
     */
    public static final MediaTypeEnum XML = new MediaTypeEnum("xml");

    /**
     * media type HTML = 4.
     */
    public static final MediaTypeEnum HTML = new MediaTypeEnum("html");

    /**
     * array containing all the export types.
     */
    private static final List MEDIA = new ArrayList();

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
     * @param name description of media type
     */
    private MediaTypeEnum(String name)
    {
        this.enumName = name;
        this.enumCode = MediaTypeEnum.MEDIA.size();
        MediaTypeEnum.MEDIA.add(this);
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
        if (key >= 0 && key < MEDIA.size())
        {
            return (MediaTypeEnum) MEDIA.get(key);
        }

        // lookup failed
        return null;
    }

    /**
     * lookup a media type by an Integer key.
     * @param key Integer code - null safe: a null key returns a null Enum
     * @return MediaTypeEnum or null if no mediaType is found with the given key
     */
    public static MediaTypeEnum fromIntegerCode(Integer key)
    {
        if (key == null)
        {
            return null;
        }
        return fromCode(key.intValue());
    }

    /**
     * Lookup a media type by a String key.
     * @param code String code - null safe: a null key returns a null Enum
     * @return MediaTypeEnum or null if no mediaType is found with the given key
     */
    public static MediaTypeEnum fromName(String code)
    {
        for (Iterator it = MEDIA.iterator(); it.hasNext();)
        {
            MediaTypeEnum mediaType = (MediaTypeEnum) it.next();
            if (mediaType.getName().equals(code))
            {
                return mediaType;
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
        return MEDIA.iterator();
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