package org.displaytag.sample;

import java.util.List;

/**
 * Simple objects which holds a list
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class ListHolder extends Object
{

    /**
     * contained list
     */
    private List list;

    /**
     * Instantiate a new ListHolder and initialize a TestList with 5 elements
     */
    public ListHolder()
    {
        list = new TestList(15, false);
    }

    /**
     * Returns the contained list
     * @return a TestList with 15 elements
     */
    public final List getList()
    {
        return list;
    }
}
