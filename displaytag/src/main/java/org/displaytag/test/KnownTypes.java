/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
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
