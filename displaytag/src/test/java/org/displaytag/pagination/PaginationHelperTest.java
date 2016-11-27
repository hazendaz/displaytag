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
package org.displaytag.pagination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;


/**
 * The Class PaginationHelperTest.
 *
 * @author <a href="mailto:kevin.a.conaway@gmail.com">Kevin Conaway</a>
 * @version $Revision$ ($Author$)
 */
public class PaginationHelperTest
{

    /**
     * Test ensure only lists are handled.
     */
    @Test
    public void testEnsureOnlyListsAreHandled()
    {
        Set<Integer> data = new HashSet<Integer>();
        add(data, 2);

        this.assertEquals(data.iterator(), getIterator(data, 1, 25));
    }

    /**
     * Test get sublist other page.
     */
    @Test
    public void testGetSublistOtherPage()
    {
        List<Integer> expected = Arrays.asList(new Integer[]{new Integer(4), new Integer(5), new Integer(6)});
        List<Integer> data = new ArrayList<Integer>();
        add(data, 7);

        this.assertEquals(expected.iterator(), getIterator(data, 2, 3));
    }

    /**
     * Test get sublist first page.
     */
    @Test
    public void testGetSublistFirstPage()
    {
        List<Integer> expected = Arrays.asList(new Integer[]{new Integer(1), new Integer(2), new Integer(3)});
        List<Integer> data = new ArrayList<Integer>();
        add(data, 7);

        this.assertEquals(expected.iterator(), getIterator(data, 1, 3));
    }

    /**
     * Test get sublist last page.
     */
    @Test
    public void testGetSublistLastPage()
    {
        List<Integer> expected = Arrays.asList(new Integer[]{new Integer(9)});
        List<Integer> data = new ArrayList<Integer>();
        add(data, 9);

        this.assertEquals(expected.iterator(), getIterator(data, 3, 4));
    }

    /**
     * Test get sublist out of bounds.
     */
    @Test
    public void testGetSublistOutOfBounds()
    {
        List<Integer> expected = Arrays.asList(new Integer[]{
            new Integer(1),
            new Integer(2),
            new Integer(3),
            new Integer(4),
            new Integer(5)});
        List<Integer> data = new ArrayList<Integer>();
        add(data, 7);

        // This is out of bounds, it should just take the first page, 1-5
        this.assertEquals(expected.iterator(), getIterator(data, 3, 5));
    }

    /**
     * Test get sublist page size bigger than list.
     */
    @Test
    public void testGetSublistPageSizeBiggerThanList()
    {
        List<Integer> expected = Arrays.asList(new Integer[]{
            new Integer(1),
            new Integer(2),
            new Integer(3),
            new Integer(4),
            new Integer(5)});
        List<Integer> data = new ArrayList<Integer>();
        add(data, 5);

        this.assertEquals(expected.iterator(), getIterator(data, 1, 25));
        this.assertEquals(expected.iterator(), getIterator(data, 2, 25));
    }

    /**
     * Test get sublist for entire page.
     */
    @Test
    public void testGetSublistForEntirePage()
    {
        List<Integer> data = new ArrayList<Integer>();
        add(data, 5);

        this.assertEquals(data.iterator(), getIterator(data, 1, 5));
    }

    /**
     * Ensures that the data passed to the helper is cast as an object.
     *
     * @param data the data
     * @param pageNumber the page number
     * @param pageSize the page size
     * @return the iterator
     */
    protected Iterator<Object> getIterator(Object data, int pageNumber, int pageSize)
    {
        PaginationHelper helper = new PaginationHelper(pageNumber, pageSize);
        return helper.getIterator(data);
    }

    /**
     * Prints the.
     *
     * @param iterator the iterator
     * @param message the message
     */
    protected void print(Iterator< ? extends Object> iterator, String message)
    {
        while (iterator.hasNext())
        {
            System.out.println(message + iterator.next());
        }
    }

    /**
     * Adds the.
     *
     * @param collection the collection
     * @param number the number
     */
    protected void add(Collection<Integer> collection, int number)
    {
        for (int i = 0; i < number; i++)
        {
            collection.add(new Integer(i + 1));
        }
    }

    /**
     * Assert equals.
     *
     * @param expected the expected
     * @param actual the actual
     */
    protected void assertEquals(Iterator< ? extends Object> expected, Iterator< ? extends Object> actual)
    {
        while (expected.hasNext())
        {
            Assert.assertEquals(expected.next(), actual.next());
        }

        Assert.assertFalse(actual.hasNext());
    }

}
