/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for ParamEncoder.
 */
class ParamEncoderTest {

    /**
     * Test for reported DISPL-12.
     */
    @Test
    void testOptionAndAnswer() {
        Assertions.assertNotEquals(new ParamEncoder("option").encodeParameterName("x"),
                new ParamEncoder("answer").encodeParameterName("x"),
                "id 'option' and 'answer' produce the same parameter");
    }

    /**
     * Same parameter produce the same result.
     */
    @Test
    void testEquals() {
        Assertions.assertEquals(new ParamEncoder("equals").encodeParameterName("x"),
                new ParamEncoder("equals").encodeParameterName("x"), "The same parameter should produce equals key.");
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
