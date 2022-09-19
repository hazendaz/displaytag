/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.pagination;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Object representing a page.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
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