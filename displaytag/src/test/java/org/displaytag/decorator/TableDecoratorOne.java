/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

/**
 * Test decorator used in tests.
 */
public class TableDecoratorOne extends TableDecorator {

    /**
     * getter property for "one".
     *
     * @return "one"
     */
    public String getOne() {
        return "one";
    }

    /**
     * getter for a mapped property.
     *
     * @param key
     *            property name
     *
     * @return "mapped property"
     */
    public String getMapped(final String key) {
        return "mapped property";
    }

    /**
     * getter for an indexed property.
     *
     * @param key
     *            property index
     *
     * @return "indexed property"
     */
    public String getIndexed(final int key) {
        return "indexed property";
    }
}
