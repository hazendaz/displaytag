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
package org.displaytag.decorator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for TableDecorator.
 * @author Fabrizio Giustina
 * @version $Revision: 1 $ ($Author: Fgiust $)
 */
public class TableDecoratorTest
{

    /**
     * test decorator one.
     */
    private TableDecorator one;

    /**
     * test decorator 2.
     */
    private TableDecorator two;

    /**
     * test decorator 3.
     */
    private TableDecorator three;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     * @see TestCase#setUp()
     */
    @Before
    public void setUp() throws Exception
    {
        this.one = new TableDecoratorOne();
        this.two = new TableDecoratorTwo();
        this.three = new TableDecoratorThree();
    }

    /**
     * test that property list is not shared between decorators. (testcase for [840011])
     */
    @Test
    public void testDecoratorPropertyCache()
    {
        Assert.assertTrue("decorator one - property one, expected true", this.one.hasGetterFor("one"));
        Assert.assertTrue("decorator one - property one, expected true", this.one.hasGetterFor("one.something"));
        Assert.assertTrue("decorator two - property two, expected true", this.two.hasGetterFor("two"));

        Assert.assertFalse("decorator one - property two, expected false", this.one.hasGetterFor("two"));
        Assert.assertFalse("decorator two - property one, expected false", this.two.hasGetterFor("one"));
    }

    /**
     * test for mapped property support. (testcase for [926213])
     */
    @Test
    public void testMappedProperty()
    {
        Assert.assertTrue("mapped property not recognized", this.one.hasGetterFor("mapped(one)"));
    }

    /**
     * test for mapped property support. (testcase for [926213])
     */
    @Test
    public void testNotExistingMappedProperty()
    {
        Assert.assertFalse("Invalid mapped property recognized", this.one.hasGetterFor("something(one)"));
    }

    /**
     * test for indexed property support. (testcase for [926213])
     */
    @Test
    public void testIndexedProperty()
    {
        Assert.assertTrue("indexed property not recognized", this.one.hasGetterFor("indexed[0]"));
    }

    /**
     * test for indexed property support. (testcase for [926213])
     */
    @Test
    public void testNotExistingIndexedProperty()
    {
        Assert.assertFalse("Invalid indexed property recognized", this.one.hasGetterFor("something[0]"));
    }

    /**
     * test for read only properties.
     */
    @Test
    public void testWriteOnlyProperty()
    {
        Assert.assertFalse("Invalid simple property recognized", this.three.hasGetterFor("simple"));
    }

    /**
     * test for read only properties.
     */
    @Test
    public void testReadOnlyProperty()
    {
        Assert.assertTrue("Simple property not recognized", this.two.hasGetterFor("two"));
    }

    /**
     * test for indexed property support. (testcase for [926213])
     */
    @Test
    public void testReadOnlyIndexedProperty()
    {
        Assert.assertFalse("Invalid indexed property recognized", this.three.hasGetterFor("something[0]"));
    }

    /**
     * test for mapped property support. (testcase for [926213])
     */
    @Test
    public void testReadOnlyMappedProperty()
    {
        Assert.assertFalse("Invalid mapped property recognized", this.three.hasGetterFor("something(one)"));
    }

}
