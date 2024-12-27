/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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

import java.io.Serializable;
import java.util.Iterator;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Enumeration for sort order.
 */
public final class SortOrderEnum implements Serializable {

    /**
     * Stable serialVersionUID.
     */
    private static final long serialVersionUID = 42L;

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
    static final SortOrderEnum[] ALL = { SortOrderEnum.DESCENDING, SortOrderEnum.ASCENDING };

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
     *
     * @param code
     *            int code
     * @param name
     *            description of enumerated type
     */
    private SortOrderEnum(final int code, final String name) {
        this.enumCode = code;
        this.enumName = name;
    }

    /**
     * returns the int code.
     *
     * @return int code
     */
    public int getCode() {
        return this.enumCode;
    }

    /**
     * returns the description.
     *
     * @return String description of the sort order ("ascending" or "descending")
     */
    public String getName() {
        return this.enumName;
    }

    /**
     * lookup a SortOrderEnum by key.
     *
     * @param key
     *            int code
     *
     * @return SortOrderEnum or null if no SortOrderEnum is found with the given key
     */
    public static SortOrderEnum fromCode(final int key) {
        for (final SortOrderEnum element : SortOrderEnum.ALL) {
            if (key == element.getCode()) {
                return element;
            }
        }
        // lookup failed
        return null;
    }

    /**
     * lookup a SortOrderEnum by an Integer key.
     *
     * @param key
     *            Integer code - null safe: a null key returns a null Enum
     *
     * @return SortOrderEnum or null if no SortOrderEnum is found with the given key
     */
    public static SortOrderEnum fromCode(final Integer key) {
        if (key == null) {
            return null;
        }

        return SortOrderEnum.fromCode(key.intValue());
    }

    /**
     * lookup a SortOrderEnum by an Integer key.
     *
     * @param key
     *            Integer code - null safe: a null key returns a null Enum
     *
     * @return SortOrderEnum or null if no SortOrderEnum is found with the given key
     *
     * @deprecated use fromCode(Integer)
     */
    @Deprecated
    public static SortOrderEnum fromIntegerCode(final Integer key) {
        return SortOrderEnum.fromCode(key);
    }

    /**
     * Lookup a SortOrderEnum by a String key.
     *
     * @param code
     *            String code - null safe: a null key returns a null Enum
     *
     * @return SortOrderEnum or null if no SortOrderEnum is found with the given key
     */
    public static SortOrderEnum fromName(final String code) {
        for (final SortOrderEnum element : SortOrderEnum.ALL) {
            if (element.getName().equals(code)) {
                return element;
            }
        }
        // lookup failed
        return null;
    }

    /**
     * returns an iterator on all the enumerated instances.
     *
     * @return iterator
     */
    public static Iterator<SortOrderEnum> iterator() {
        return new ArrayIterator(SortOrderEnum.ALL);
    }

    /**
     * returns the enumeration description.
     *
     * @return the string
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.getName();
    }

    /**
     * Only a single instance of a specific enumeration can be created, so we can check using ==.
     *
     * @param o
     *            the object to compare to
     *
     * @return hashCode
     */
    @Override
    public boolean equals(final Object o) {
        return this == o;
    }

    /**
     * Hash code.
     *
     * @return the int
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(1123997057, -1289836553).append(this.enumCode).toHashCode();
    }

}
