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
     * test decorator 3.
     */
    private TableDecorator three;

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
        this.one = new TableDecoratorOne();
        this.two = new TableDecoratorTwo();
        this.three = new TableDecoratorThree();
    }

    /**
     * test that property list is not shared between decorators. (testcase for [840011])
     */
    public void testDecoratorPropertyCache()
    {
        assertTrue("decorator one - property one, expected true", this.one.hasGetterFor("one"));
        assertTrue("decorator one - property one, expected true", this.one.hasGetterFor("one.something"));
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

    /**
     * test for read only properties.
     */
    public void testWriteOnlyProperty()
    {
        assertFalse("Invalid simple property recognized", this.three.hasGetterFor("simple"));
    }

    /**
     * test for read only properties.
     */
    public void testReadOnlyProperty()
    {
        assertTrue("Simple property not recognized", this.two.hasGetterFor("two"));
    }

    /**
     * test for indexed property support. (testcase for [926213])
     */
    public void testReadOnlyIndexedProperty()
    {
        assertFalse("Invalid indexed property recognized", this.three.hasGetterFor("something[0]"));
    }

    /**
     * test for mapped property support. (testcase for [926213])
     */
    public void testReadOnlyMappedProperty()
    {
        assertFalse("Invalid mapped property recognized", this.three.hasGetterFor("something(one)"));
    }

}
