/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

/**
 * The Class ModelDecorator.
 */
public class ModelDecorator extends TableDecorator {

    /** The Constant DECORATED_VALUE. */
    public static final String DECORATED_VALUE = "decoratedValue";

    /**
     * Gets the decorated value.
     *
     * @return the decorated value
     */
    public String getDecoratedValue() {
        return ModelDecorator.DECORATED_VALUE;
    }

}
