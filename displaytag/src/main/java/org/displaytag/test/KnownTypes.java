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
package org.displaytag.test;

import java.util.Date;

/**
 * Simple test data provider.
 */
public class KnownTypes {

    /**
     * constant for the "time" property name.
     */
    public static final String TIME_PROPERTY = "time";

    /**
     * constant for the "time" property value. For the curious, evals to Mon Mar 01 18:00:00 CST 3875 (if you are in
     * Chicago).
     */
    public static final Date TIME_VALUE = new Date(60121180800000L);

    /**
     * constant for the "long" property name.
     */
    public static final String LONG_PROPERTY = "long";

    /**
     * constant for the "long" property value.
     */
    public static final Long LONG_VALUE = Long.valueOf(123456);

    /**
     * getter for the <code>TIME_PROPERTY</code> property.
     *
     * @return <code>TIME_VALUE</code>
     */
    public Date getTime() {
        return KnownTypes.TIME_VALUE;
    }

    /**
     * getter for the <code>LONG_PROPERTY</code> property.
     *
     * @return <code>LONG_VALUE</code>
     */
    public Long getLong() {
        return KnownTypes.LONG_VALUE;
    }

    /**
     * getter for a null property.
     *
     * @return <code>null</code>
     */
    public Long getNullValue() {
        return null;
    }

}
