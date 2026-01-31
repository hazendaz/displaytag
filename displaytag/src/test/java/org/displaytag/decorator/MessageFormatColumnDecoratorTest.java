/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for MessageFormatColumnDecoratorTest.
 */
class MessageFormatColumnDecoratorTest {

    /**
     * Test with <code>day is {0, date, EEEE}</code>.
     */
    @Test
    void testDate() {
        final Object result = new MessageFormatColumnDecorator("day is {0,date,EEEE}", Locale.ENGLISH)
                .decorate(new Date(0), null, null);
        Assertions.assertEquals("day is " + new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date(0)), result);
    }

    /**
     * Test with <code>day is {0, date, EEEE}</code>.
     */
    @Test
    void testWrongDate() {
        final Object result = new MessageFormatColumnDecorator("day is {0,date,EEEE}", Locale.ENGLISH).decorate("abc",
                null, null);
        Assertions.assertEquals("abc", result);
    }

}
