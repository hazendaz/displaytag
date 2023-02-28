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
package org.displaytag.decorator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for MessageFormatColumnDecoratorTest.
 *
 * @author Fabrizio Giustina
 *
 * @version $Id$
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
