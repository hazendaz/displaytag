package org.displaytag.util;

import junit.framework.TestCase;


/**
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ParamEncoderTest extends TestCase
{

    /**
     * Test for reported DISPL-12
     */
    public void testOptionAndAnswer()
    {
        assertFalse("id \"option\" and \"answer\" produce the same parameter", new ParamEncoder("option")
            .encodeParameterName("x")
            .equals(new ParamEncoder("answer").encodeParameterName("x")));
    }

    /**
     * Same parameter produce the same result.
     */
    public void testEquals()
    {
        assertEquals("The same parameter should produce equals key.", new ParamEncoder("equals")
            .encodeParameterName("x"), (new ParamEncoder("equals").encodeParameterName("x")));
    }

    /**
     * We don't wont param names to be too long.
     */
    public void testNotTooLong()
    {
        assertTrue(new ParamEncoder("averyveryveryveryveryveryverylongidvalue").encodeParameterName("x").length() < 12);
        assertTrue(new ParamEncoder("test").encodeParameterName("x").length() < 12);
        assertTrue(new ParamEncoder("a").encodeParameterName("x").length() < 12);
        assertTrue(new ParamEncoder("xxxxxxxxxxxx").encodeParameterName("x").length() < 12);
        assertTrue(new ParamEncoder("xxxxxxxxxxxxxxxxxxxxxxxxxxxx").encodeParameterName("x").length() < 12);
    }
}
