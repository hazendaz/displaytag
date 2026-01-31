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
public class TableDecoratorPageContext extends TableDecorator {

    /**
     * Gets the use page context.
     *
     * @return the use page context
     */
    public String getUsePageContext() {
        return this.getPageContext() != null ? "OK" : "ko";
    }

}
