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
