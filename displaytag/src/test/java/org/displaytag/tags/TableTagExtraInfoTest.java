/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.tags;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for org.displaytag.tags.TableTagExtraInfo.
 */
public class TableTagExtraInfoTest {

    /**
     * Test for isJavaId().
     */
    @Test
    void testIsJavaIdValid() {
        Assertions.assertTrue(TableTagExtraInfo.isJavaId("table"));
    }

    /**
     * Test for isJavaId().
     */
    @Test
    void testIsJavaIdEnum() {
        Assertions.assertFalse(TableTagExtraInfo.isJavaId("enum"));
    }

    /**
     * Test for isJavaId().
     */
    @Test
    void testIsJavaIdSpace() {
        Assertions.assertFalse(TableTagExtraInfo.isJavaId("invalid x"));
    }

    /**
     * Test for isJavaId().
     */
    @Test
    void testIsJavaIdEsclamationMark() {
        Assertions.assertFalse(TableTagExtraInfo.isJavaId("invalid!"));
    }

}
