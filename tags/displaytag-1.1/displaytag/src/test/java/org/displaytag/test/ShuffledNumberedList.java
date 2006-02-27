package org.displaytag.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Test data provider. A list of 4 unordered NumberedItems.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ShuffledNumberedList
{

    /**
     * Returns an iterator on a list made of 4 unordered NumberedItems objects.
     * @return iterator on a list made of 4 unordered NumberedItems objects
     */
    public Iterator iterator()
    {
        List list = new ArrayList();
        list.add(new NumberedItem(1));
        list.add(new NumberedItem(4));
        list.add(new NumberedItem(2));
        list.add(new NumberedItem(3));
        return list.iterator();
    }
}