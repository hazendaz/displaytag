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
public class KnownValue {

    /**
     * constant for the "ant" property (this is both the property name and value).
     */
    public static final String ANT = "ant";

    /**
     * constant for the "bee" property (this is both the property name and value).
     */
    public static final String BEE = "bee";

    /**
     * constant for the "camel" property (this is both the property name and value).
     */
    public static final String CAMEL = "camel";

    /**
     * constant for the "date" property (this is both the property name and value).
     */
    public static final Date MAY = new Date(12934123434L);

    /** The ant value. */
    public String antValue = KnownValue.ANT;

    /** The bee value. */
    public String beeValue = KnownValue.BEE;

    /** The two value. */
    public int twoValue = KnownValue.TWO;

    /** The camel value. */
    public String camelValue = KnownValue.CAMEL;

    /** The date. */
    public Date date = KnownValue.MAY;

    /**
     * constant for the "two" property (this is both the property name and value).
     */
    public static final int TWO = 2;

    /**
     * getter for the "ant" property.
     *
     * @return ANT
     */
    public String getAnt() {
        return this.antValue;
    }

    /**
     * getter for the "bee" property.
     *
     * @return BEE
     */
    public String getBee() {
        return this.beeValue;
    }

    /**
     * getter for the "camel" property.
     *
     * @return CAMEL
     */
    public String getCamel() {
        return this.camelValue;
    }

    /**
     * getter for the "two" property.
     *
     * @return TWO
     */
    public int getTwo() {
        return this.twoValue;
    }

    /**
     * getter for the "date" property.
     *
     * @return date
     */
    public Date getDate() {
        return this.date;
    }
}
