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
 * @author <a href="mailto:kevin.a.conaway@gmail.com">Kevin Conaway</a>
 * @version $Revision$ ($Author$)
 */
public class PaginationHelperTest
{

    @Test
    public void testEnsureOnlyListsAreHandled()
    {
        Set<Integer> data = new HashSet<Integer>();
        add(data, 2);

        this.assertEquals(data.iterator(), getIterator(data, 1, 25));
    }

    @Test
    public void testGetSublistOtherPage()
    {
        List<Integer> expected = Arrays.asList(new Integer[]{new Integer(4), new Integer(5), new Integer(6)});
        List<Integer> data = new ArrayList<Integer>();
        add(data, 7);

        this.assertEquals(expected.iterator(), getIterator(data, 2, 3));
    }

    @Test
    public void testGetSublistFirstPage()
    {
        List<Integer> expected = Arrays.asList(new Integer[]{new Integer(1), new Integer(2), new Integer(3)});
        List<Integer> data = new ArrayList<Integer>();
        add(data, 7);

        this.assertEquals(expected.iterator(), getIterator(data, 1, 3));
    }

    @Test
    public void testGetSublistLastPage()
    {
        List<Integer> expected = Arrays.asList(new Integer[]{new Integer(9)});
        List<Integer> data = new ArrayList<Integer>();
        add(data, 9);

        this.assertEquals(expected.iterator(), getIterator(data, 3, 4));
    }

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

    @Test
    public void testGetSublistForEntirePage()
    {
        List<Integer> data = new ArrayList<Integer>();
        add(data, 5);

        this.assertEquals(data.iterator(), getIterator(data, 1, 5));
    }

    /**
     * Ensures that the data passed to the helper is cast as an object
     */
    protected Iterator<Object> getIterator(Object data, int pageNumber, int pageSize)
    {
        PaginationHelper helper = new PaginationHelper(pageNumber, pageSize);
        return helper.getIterator(data);
    }

    protected void print(Iterator< ? extends Object> iterator, String message)
    {
        while (iterator.hasNext())
        {
            System.out.println(message + iterator.next());
        }
    }

    protected void add(Collection<Integer> collection, int number)
    {
        for (int i = 0; i < number; i++)
        {
            collection.add(new Integer(i + 1));
        }
    }

    protected void assertEquals(Iterator< ? extends Object> expected, Iterator< ? extends Object> actual)
    {
        while (expected.hasNext())
        {
            Assert.assertEquals(expected.next(), actual.next());
        }

        Assert.assertFalse(actual.hasNext());
    }

}
