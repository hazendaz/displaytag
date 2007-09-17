package org.displaytag.decorator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;


/**
 * Test case for MessageFormatColumnDecoratorTest.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class MessageFormatColumnDecoratorTest extends TestCase
{

    /**
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return getClass().getName() + "." + super.getName();
    }

    /**
     * Test with <code>day is {0, date, EEEE}</code>.
     */
    public void testDate()
    {
        Object result = new MessageFormatColumnDecorator("day is {0,date,EEEE}", Locale.ENGLISH).decorate(
            new Date(0),
            null,
            null);
        assertEquals("day is " + new SimpleDateFormat("EEEE").format(new Date(0)), result);
    }

    /**
     * Test with <code>day is {0, date, EEEE}</code>.
     */
    public void testWrongDate()
    {
        Object result = new MessageFormatColumnDecorator("day is {0,date,EEEE}", Locale.ENGLISH).decorate(
            "abc",
            null,
            null);
        assertEquals("abc", result);
    }

}