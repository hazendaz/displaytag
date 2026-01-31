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
public class TableDecoratorCssRow extends TableDecorator {

    /**
     * Adds the row id.
     *
     * @return the string
     *
     * @see org.displaytag.decorator.TableDecorator#addRowId()
     */
    @Override
    public String addRowId() {
        return "rowid" + this.getViewIndex();
    }

    /**
     * Adds the row class.
     *
     * @return the string
     *
     * @see org.displaytag.decorator.TableDecorator#addRowClass()
     */
    @Override
    public String addRowClass() {
        if (this.getViewIndex() == 2) {
            return "highlighted";
        }
        return null;
    }

}
