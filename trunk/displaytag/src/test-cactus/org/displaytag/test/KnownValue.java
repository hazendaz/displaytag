package org.displaytag.test;


/**
 * Simple test data provider.
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class KnownValue
{
    int position;
    public static String ant = "acolumn";
    public static String bee = "bcolumn";
    public static String camel = "ccolumn";

    public KnownValue(int position)
    {
        this.position = position;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public String getAnt()
    {
        return ant;
    }

    public void setAnt(String aa)
    {
        ant = aa;
    }

    public String getBee()
    {
        return bee;
    }

    public void setBee(String bb)
    {
        bee = bb;
    }

    public String getCamel()
    {
        return camel;
    }

    public void setCamel(String cc)
    {
        camel = cc;
    }
}

