package org.displaytag.decorator;

import junit.framework.TestCase;


/**
 * Test for TableDecorator.
 * @author Fabrizio Giustina
 * @version $Revision: 1 $ ($Author: Fgiust $)
 */
public class TableDecoratorTest extends TestCase
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
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return getClass().getName() + "." + super.getName();
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        this.one = DecoratorFactory.loadTableDecorator(TableDecoratorOne.class.getName());
        this.two = DecoratorFactory.loadTableDecorator(TableDecoratorTwo.class.getName());
    }

    /**
     * test that property list is not shared between decorators. (testcase for [840011])
     */
    public void testDecoratorPropertyCache()
    {
        assertTrue("decorator one - property one, expected true", this.one.hasGetterFor("one"));
        assertTrue("decorator two - property two, expected true", this.two.hasGetterFor("two"));

        assertFalse("decorator one - property two, expected false", this.one.hasGetterFor("two"));
        assertFalse("decorator two - property one, expected false", this.two.hasGetterFor("one"));
    }

    /**
     * test for mapped property support. (testcase for [926213])
     */
    public void testMappedProperty()
    {
        assertTrue("mapped property not recognized", this.one.hasGetterFor("mapped(one)"));
    }

    /**
     * test for mapped property support. (testcase for [926213])
     */
    public void testNotExistingMappedProperty()
    {
        assertFalse("Invalid mapped property recognized", this.one.hasGetterFor("something(one)"));
    }

    /**
     * test for indexed property support. (testcase for [926213])
     */
    public void testIndexedProperty()
    {
        assertTrue("indexed property not recognized", this.one.hasGetterFor("indexed[0]"));
    }

    /**
     * test for indexed property support. (testcase for [926213])
     */
    public void testNotExistingIndexedProperty()
    {
        assertFalse("Invalid indexed property recognized", this.one.hasGetterFor("something[0]"));
    }

}
