package org.displaytag.decorator;


/**
 * Test decorator used in tests.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TableDecoratorOne extends TableDecorator
{

    /**
     * getter property for "one".
     * @return "one"
     */
    public String getOne()
    {
        return "one";
    }

    /**
     * getter for a mapped property.
     * @param key property name
     * @return "mapped property"
     */
    public String getMapped(String key)
    {
        return "mapped property";
    }

    /**
     * getter for an indexed property.
     * @param key property index
     * @return "indexed property"
     */
    public String getIndexed(int key)
    {
        return "indexed property";
    }
}