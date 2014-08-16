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
package org.displaytag.util;

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
