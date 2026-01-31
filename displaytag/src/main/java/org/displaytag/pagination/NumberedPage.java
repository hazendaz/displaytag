/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.pagination;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Object representing a page.
 */
public class NumberedPage {

    /**
     * page number.
     */
    private final int number;

    /** is the page selected?. */
    private final boolean selected;

    /**
     * Creates a new page with the specified number.
     *
     * @param pageNumber
     *            page number
     * @param isSelected
     *            is the page selected?
     */
    public NumberedPage(final int pageNumber, final boolean isSelected) {
        this.number = pageNumber;
        this.selected = isSelected;
    }

    /**
     * Returns the page number.
     *
     * @return the page number
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * is the page selected?.
     *
     * @return true if the page is slected
     */
    public boolean getSelected() {
        return this.selected;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) //
                .append("selected", this.selected) //$NON-NLS-1$
                .append("number", this.number) //$NON-NLS-1$
                .toString();
    }
}
