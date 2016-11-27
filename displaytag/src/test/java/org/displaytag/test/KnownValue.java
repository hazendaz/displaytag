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
package org.displaytag.test;

import java.util.Date;


/**
 * Simple test data provider.
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class KnownValue
{

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
    public static final Date MAY = new Date(12934123434l);

    /** The ant value. */
    public String antValue = ANT;

    /** The bee value. */
    public String beeValue = BEE;

    /** The two value. */
    public int twoValue = TWO;

    /** The camel value. */
    public String camelValue = CAMEL;

    /** The date. */
    public Date date = MAY;

    /**
     * constant for the "two" property (this is both the property name and value).
     */
    public static final int TWO = 2;

    /**
     * getter for the "ant" property.
     * @return ANT
     */
    public String getAnt()
    {
        return this.antValue;
    }

    /**
     * getter for the "bee" property.
     * @return BEE
     */
    public String getBee()
    {
        return this.beeValue;
    }

    /**
     * getter for the "camel" property.
     * @return CAMEL
     */
    public String getCamel()
    {
        return this.camelValue;
    }

    /**
     * getter for the "two" property.
     * @return TWO
     */
    public int getTwo()
    {
        return this.twoValue;
    }

    /**
     * getter for the "date" property.
     * @return date
     */
    public Date getDate()
    {
        return this.date;
    }
}
