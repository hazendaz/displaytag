/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
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
