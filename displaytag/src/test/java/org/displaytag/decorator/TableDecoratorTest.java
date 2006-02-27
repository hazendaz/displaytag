package org.displaytag.decorator;

import junit.framework.TestCase;

/**
 * test for TableDecorator.
 * @author fgiust
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
        this.one = DecoratorFactory.loadTableDecorator(TableDecoratorOne.class.getName());
        this.two = DecoratorFactory.loadTableDecorator(TableDecoratorTwo.class.getName());
    }

    /**
     * test that property list is not shared between decorators.
     * (testcase for [840011])
     */
    public void testDecoratorPropertyCache()
    {
        assertTrue("decorator one - property one, expected true", this.one.hasGetterFor("one"));
        assertTrue("decorator two - property two, expected true", this.two.hasGetterFor("two"));

        assertFalse("decorator one - property two, expected false", this.one.hasGetterFor("two"));
        assertFalse("decorator two - property one, expected false", this.two.hasGetterFor("one"));
    }

}

/**
 * test decorator one.
 * @author fgiust
 * @version $Revision: 1 $ ($Author: Fgiust $)
 */
class TableDecoratorOne extends TableDecorator
{

    /**
     * getter property for "one".
     * @return "one"
     */
    public String getOne()
    {
        return "one";
    }
}

/**
 * test decorator two.
 * @author fgiust
 * @version $Revision: 1 $ ($Author: Fgiust $)
 */
class TableDecoratorTwo extends TableDecorator
{

    /**
     * getter property for "two".
     * @return "two"
     */
    public String getTwo()
    {
        return "two";
    }
}
