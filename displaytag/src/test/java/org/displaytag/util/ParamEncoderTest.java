/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for ParamEncoder.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
class ParamEncoderTest {

    /**
     * Test for reported DISPL-12.
     */
    @Test
    void testOptionAndAnswer() {
        Assertions.assertNotEquals(new ParamEncoder("option")
                .encodeParameterName("x"), new ParamEncoder("answer").encodeParameterName("x"), "id 'option' and 'answer' produce the same parameter");
    }

    /**
     * Same parameter produce the same result.
     */
    @Test
    void testEquals() {
        Assertions.assertEquals(
                new ParamEncoder("equals").encodeParameterName("x"),
                new ParamEncoder("equals").encodeParameterName("x"),
                "The same parameter should produce equals key.");
    }

    /**
     * We don't wont param names to be too long.
     */
    @Test
    void testNotTooLong() {
        Assertions.assertTrue(
                new ParamEncoder("averyveryveryveryveryveryverylongidvalue").encodeParameterName("x").length() < 12);
        Assertions.assertTrue(new ParamEncoder("test").encodeParameterName("x").length() < 12);
        Assertions.assertTrue(new ParamEncoder("a").encodeParameterName("x").length() < 12);
        Assertions.assertTrue(new ParamEncoder("xxxxxxxxxxxx").encodeParameterName("x").length() < 12);
        Assertions.assertTrue(new ParamEncoder("xxxxxxxxxxxxxxxxxxxxxxxxxxxx").encodeParameterName("x").length() < 12);
    }
}
