package org.displaytag.decorator;

import junit.framework.TestCase;

/**
 * test for TableDecorator
 * @author fgiust
 * @version $Revision: $ ($Author: $)
 */
public class TableDecoratorTest extends TestCase
{
    /**
     * test decorator one
     */
    private TableDecorator one;

    /**
     * test decorator 2
     */
    private TableDecorator two;

    /**
     * Constructor for TableDecoratorTest.
     * @param name test name
     */
    public TableDecoratorTest(String name)
    {
        super(name);
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        one = DecoratorFactory.loadTableDecorator(TableDecoratorOne.class.getName());
        two = DecoratorFactory.loadTableDecorator(TableDecoratorTwo.class.getName());
    }

    /**
     * test that property list is not shared between decorators
     * (testcase for [840011])
     */
    public void testDecoratorPropertyCache()
    {
        assertTrue("decorator one - property one, expected true", one.hasGetterFor("one"));
        assertTrue("decorator two - property two, expected true", two.hasGetterFor("two"));

        assertFalse("decorator one - property two, expected false", one.hasGetterFor("two"));
        assertFalse("decorator two - property one, expected false", two.hasGetterFor("one"));
    }

}

/**
 * test decorator one
 * @author fgiust
 * @version $Revision: $ ($Author: $)
 */
class TableDecoratorOne extends TableDecorator
{

    /**
     * getter property for "one"
     * @return "one"
     */
    public String getOne()
    {
        return "one";
    }
}

/**
 * test decorator two
 * @author fgiust
 * @version $Revision: $ ($Author: $)
 */
class TableDecoratorTwo extends TableDecorator
{

    /**
     * getter property for "two"
     * @return "two"
     */
    public String getTwo()
    {
        return "two";
    }
}
