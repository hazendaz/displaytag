/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import java.util.Date;

/**
 * Test decorator used in tests.
 */
public class TableDecoratorDate extends TableDecorator {

    /**
     * getter property for "decoratorDate".
     *
     * @return a fixed date
     */
    public Date getDecoratorDate() {
        return new Date(60121180800000L);
    }
}
