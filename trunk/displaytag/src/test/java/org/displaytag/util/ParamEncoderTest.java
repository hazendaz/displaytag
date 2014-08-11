package org.displaytag.util;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;


/**
 * Tests for ParamEncoder.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ParamEncoderTest
{

    /**
     * Test for reported DISPL-12.
     */
    @Test
    public void testOptionAndAnswer()
    {
        Assert.assertFalse("id \"option\" and \"answer\" produce the same parameter", new ParamEncoder("option")
            .encodeParameterName("x")
            .equals(new ParamEncoder("answer").encodeParameterName("x")));
    }

    /**
     * Same parameter produce the same result.
     */
    @Test
    public void testEquals()
    {
        Assert.assertEquals(
            "The same parameter should produce equals key.",
            new ParamEncoder("equals").encodeParameterName("x"),
            (new ParamEncoder("equals").encodeParameterName("x")));
    }

    /**
     * We don't wont param names to be too long.
     */
    @Test
    public void testNotTooLong()
    {
        Assert.assertTrue(new ParamEncoder("averyveryveryveryveryveryverylongidvalue")
            .encodeParameterName("x")
            .length() < 12);
        Assert.assertTrue(new ParamEncoder("test").encodeParameterName("x").length() < 12);
        Assert.assertTrue(new ParamEncoder("a").encodeParameterName("x").length() < 12);
        Assert.assertTrue(new ParamEncoder("xxxxxxxxxxxx").encodeParameterName("x").length() < 12);
        Assert.assertTrue(new ParamEncoder("xxxxxxxxxxxxxxxxxxxxxxxxxxxx").encodeParameterName("x").length() < 12);
    }
}
