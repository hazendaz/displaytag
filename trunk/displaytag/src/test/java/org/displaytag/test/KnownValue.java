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

    public String antValue = ANT;
    public String beeValue = BEE;
    public int twoValue = TWO;
    public String camelValue = CAMEL;
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
        return antValue;
    }

    /**
     * getter for the "bee" property.
     * @return BEE
     */
    public String getBee()
    {
        return beeValue;
    }

    /**
     * getter for the "camel" property.
     * @return CAMEL
     */
    public String getCamel()
    {
        return camelValue;
    }

    /**
     * getter for the "two" property.
     * @return TWO
     */
    public int getTwo()
    {
        return twoValue;
    }

    /**
     * getter for the "date" property.
     * @return date
     */
    public Date getDate()
    {
        return date;
    }
}
