package org.displaytag.export;

import java.util.Iterator;

import org.apache.commons.collections.IteratorUtils;

/**
 * enumeration for export types
 * @author fgiust
 * @version $Revision: 1 $ ($Author: Fgiust $)
 */
public final class ExportTypeEnum
{

    /**
     * export type CSV = 1
     */
    public static final ExportTypeEnum CSV = new ExportTypeEnum(1, "csv");

    /**
     * export type EXCEL = 2
     */
    public static final ExportTypeEnum EXCEL = new ExportTypeEnum(2, "excel");

    /**
     * export type XML = 3
     */
    public static final ExportTypeEnum XML = new ExportTypeEnum(3, "xml");

    /**
     * array containing all the export types
     */
    private static final ExportTypeEnum[] ALL = { EXCEL, XML, CSV };

    /**
     * code
     */
    private final int enumCode;

    /**
     * description
     */
    private final String enumName;

    /**
      * private constructor. Use only constants
      * @param code int code
      * @param name description of export type
      */
    private ExportTypeEnum(int code, String name)
    {
        this.enumCode = code;
        this.enumName = name;
    }

    /**
     * returns the int code
     * @return int code
     */
    public int getCode()
    {
        return enumCode;
    }

    /**
     * returns the description
     * @return String description of the export type ("excel", "xml", "csv")
     */
    public String getName()
    {
        return this.enumName;
    }

    /**
     * lookup an export type by key
     * @param key int code
     * @return ExportTypeEnum or null if no exportType is found with the given key
     */
    public static ExportTypeEnum fromCode(int key)
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
     * lookup an export type by an Integer key
     * @param key Integer code - null safe: a null key returns a null Enum
     * @return ExportTypeEnum or null if no exportType is found with the given key
     */
    public static ExportTypeEnum fromIntegerCode(Integer key)
    {
        if (key == null)
        {
            return null;
        }
        else
        {
            return fromCode(key.intValue());
        }
    }

    /**
     * returns an iterator on all the export type
     * @return iterator
     */
    public static Iterator iterator()
    {
        return IteratorUtils.arrayIterator(ALL);
    }

    /**
     * returns the export type description
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return getName();
    }

}
