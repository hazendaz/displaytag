/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.test;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.displaytag.model.Row;

/**
 * Simple test objects which wraps an int value.
 */
public class NumberedItem extends Row {

    /**
     * Wrapped int.
     */
    private final int number;

    /**
     * Instantiates a new numbered item.
     *
     * @param num
     *            integer that will be returned by getNumber()
     */
    public NumberedItem(final int num) {
        super("Row" + num, num);
        this.number = num;
    }

    /**
     * Getter for the wrapped int.
     *
     * @return Returns the number.
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * To string.
     *
     * @return the string
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("number", this.number).toString();
    }
}
