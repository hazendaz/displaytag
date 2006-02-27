package org.displaytag.sample;

import java.util.ArrayList;
import java.util.Random;

/**
 * Just a utility class for testing out the table and column tags.
 *
 * When this class is created, it loads itself with a number of ListObjects
 * that are shown throughout the various example pages that exercise the table
 * object.  If created via the default constructor, this loads itself with 60
 * ListObjects.
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class TestList extends ArrayList
{

    /**
     * Creats a TestList that is filled with 60 ListObjects suitable for testing
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
     * Creates a TestList that is filled with [size] ListObjects suitable for
     * testing.
     * @param size number of ListObjects contained
     */
    public TestList(int size)
    {
        super();

        for (int j = 0; j < size; j++)
        {
            add(new ListObject());
        }
    }

    /**
     * Constructor for TestList
     * @param duplicates boolean put dusplicates in the list
     * @param size int size of the list
     * @todo the duplicates param has no effect
     */
    public TestList(boolean duplicates, int size)
    {
        super();

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

    /**
     * Returns a ListObject using get(index) from the Array
     * @param index int index of the List object into the array
     * @return ListObject
     */
    public ListObject getItem(int index)
    {
        return (ListObject) super.get(index);
    }

}
