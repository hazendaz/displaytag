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
package org.displaytag.pagination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;


/**
 * @author <a href="mailto:kevin.a.conaway@gmail.com">Kevin Conaway</a>
 * @version $Revision$ ($Author$)
 */
public class PaginationHelperTest extends TestCase
{

    public void testEnsureOnlyListsAreHandled()
    {
        Set data = new HashSet();
        add(data, 2);

        assertEquals(data.iterator(), getIterator(data, 1, 25));
    }

    public void testGetSublistOtherPage()
    {
        List expected = Arrays.asList(new Integer[]{new Integer(4), new Integer(5), new Integer(6)});
        List data = new ArrayList();
        add(data, 7);

        assertEquals(expected.iterator(), getIterator(data, 2, 3));
    }

    public void testGetSublistFirstPage()
    {
        List expected = Arrays.asList(new Integer[]{new Integer(1), new Integer(2), new Integer(3)});
        List data = new ArrayList();
        add(data, 7);

        assertEquals(expected.iterator(), getIterator(data, 1, 3));
    }

    public void testGetSublistLastPage()
    {
        List expected = Arrays.asList(new Integer[]{new Integer(9)});
        List data = new ArrayList();
        add(data, 9);

        assertEquals(expected.iterator(), getIterator(data, 3, 4));
    }

    public void testGetSublistOutOfBounds()
    {
        List expected = Arrays.asList(new Integer[]{
            new Integer(1),
            new Integer(2),
            new Integer(3),
            new Integer(4),
            new Integer(5)});
        List data = new ArrayList();
        add(data, 7);

        // This is out of bounds, it should just take the first page, 1-5
        assertEquals(expected.iterator(), getIterator(data, 3, 5));
    }

    public void testGetSublistPageSizeBiggerThanList()
    {
        List expected = Arrays.asList(new Integer[]{
            new Integer(1),
            new Integer(2),
            new Integer(3),
            new Integer(4),
            new Integer(5)});
        List data = new ArrayList();
        add(data, 5);

        assertEquals(expected.iterator(), getIterator(data, 1, 25));
        assertEquals(expected.iterator(), getIterator(data, 2, 25));
    }

    public void testGetSublistForEntirePage()
    {
        List data = new ArrayList();
        add(data, 5);

        assertEquals(data.iterator(), getIterator(data, 1, 5));
    }

    /**
     * Ensures that the data passed to the helper is cast as an object
     */
    protected Iterator getIterator(Object data, int pageNumber, int pageSize)
    {
        PaginationHelper helper = new PaginationHelper(pageNumber, pageSize);
        return helper.getIterator(data);
    }

    protected void print(Iterator iterator, String message)
    {
        while (iterator.hasNext())
        {
            System.out.println(message + iterator.next());
        }
    }

    protected void add(Collection collection, int number)
    {
        for (int i = 0; i < number; i++)
        {
            collection.add(new Integer(i + 1));
        }
    }

    protected void assertEquals(Iterator expected, Iterator actual)
    {
        while (expected.hasNext())
        {
            assertEquals(expected.next(), actual.next());
        }

        assertFalse(actual.hasNext());
    }

}
