/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.sample;

import java.util.ArrayList;
import java.util.Random;


/**
 * Just a utility class for testing out the table and column tags. When this class is created, it loads itself with a
 * number of ListObjects that are shown throughout the various example pages that exercise the table object. If created
 * via the default constructor, this loads itself with 60 ListObjects.
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TestList extends ArrayList
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Creats a TestList that is filled with 60 ListObjects suitable for testing.
     */
    public TestList()
    {
        super();

        for (int j = 0; j < 60; j++)
        {
            add(new ListObject());
        }
    }

    /**
     * Creates a TestList that is filled with [size] ListObjects suitable for testing.
     * @param size int size of the list
     * @param duplicates boolean put duplicates in the list
     */
    public TestList(int size, boolean duplicates)
    {
        if (duplicates)
        {
            // generate a random number between 1 and 3 and duplicate that many number of times.
            for (int j = 0; j < size; j++)
            {

                ListObject object1 = new ListObject();
                ListObject object2 = new ListObject();
                ListObject object3 = new ListObject();

                int random = new Random().nextInt(3);
                for (int k = 0; k <= random; k++)
                {
                    add(object1);
                }

                object1.setId(object2.getId());

                random = new Random().nextInt(3);
                for (int k = 0; k <= random; k++)
                {
                    add(object1);
                    add(object2);
                }

                object1.setEmail(object3.getEmail());

                random = new java.util.Random().nextInt(3);
                for (int k = 0; k <= random; k++)
                {
                    add(object1);
                }
            }
        }
        else
        {
            for (int j = 0; j < size; j++)
            {
                add(new ListObject());
            }
        }
    }

    /**
     * Returns a ListObject using get(index) from the Array.
     * @param index int index of the List object into the array
     * @return ListObject
     */
    public ListObject getItem(int index)
    {
        return (ListObject) super.get(index);
    }

}
