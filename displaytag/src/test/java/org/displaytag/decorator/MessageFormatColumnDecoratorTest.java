package org.displaytag.decorator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test case for MessageFormatColumnDecoratorTest.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class MessageFormatColumnDecoratorTest
{

    /**
     * Test with <code>day is {0, date, EEEE}</code>.
     */
    @Test
    public void testDate()
    {
        Object result = new MessageFormatColumnDecorator("day is {0,date,EEEE}", Locale.ENGLISH).decorate(
            new Date(0),
            null,
            null);
        Assert.assertEquals("day is " + new SimpleDateFormat("EEEE").format(new Date(0)), result);
    }

    /**
     * Test with <code>day is {0, date, EEEE}</code>.
     */
    @Test
    public void testWrongDate()
    {
        Object result = new MessageFormatColumnDecorator("day is {0,date,EEEE}", Locale.ENGLISH).decorate(
            "abc",
            null,
            null);
        Assert.assertEquals("abc", result);
    }

}