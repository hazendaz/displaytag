/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.model;

import java.text.Collator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.displaytag.util.HtmlAttributeMap;

/**
 * Represents a table cell.
 * <p>
 * A cell is used only when the content is placed as content of the column tag and need to be evaluated during
 * iteration.
 */
public class Cell implements Comparable<Cell> {

    /**
     * empty cell object. Use as placeholder for empty cell to avoid useless object creation.
     */
    public static final Cell EMPTY_CELL = new Cell();

    /**
     * content of the cell.
     */
    private Object staticValue;

    /**
     * Per row html attributes (style, class).
     */
    private HtmlAttributeMap attributes;

    /**
     * Creates a new empty cell. This should never be done, use EMPTY_CELL instead.
     */
    private Cell() {
    }

    /**
     * Creates a cell with a static value.
     *
     * @param value
     *            Object value of the Cell object
     */
    public Cell(final Object value) {
        this.staticValue = value;
    }

    /**
     * get the static value for the cell.
     *
     * @return the Object value of this.staticValue.
     */
    public Object getStaticValue() {
        return this.staticValue;
    }

    /**
     * Compare the Cell value to another Cell.
     *
     * @param obj
     *            Object to compare this cell to
     * @param collator
     *            Collator to use for the comparison of the other object is a Cell holding a String
     *
     * @return int
     *
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(final Object obj, final Collator collator) {
        if (this.staticValue == null) {
            return -1;
        }
        if (obj instanceof Cell) {
            final Object otherStatic = ((Cell) obj).getStaticValue();
            if (otherStatic == null) {
                return 1;
            }
            if (collator != null && this.staticValue instanceof String && otherStatic instanceof String) {
                final String a = (String) this.staticValue;
                final String b = (String) otherStatic;
                return collator.compare(a, b);
            }
            return ((Comparable<Object>) this.staticValue).compareTo(otherStatic);
        }
        return ((Comparable<Object>) this.staticValue).compareTo(obj);
    }

    /**
     * Compare the Cell value to another Cell.
     *
     * @param obj
     *            Object to compare this cell to
     *
     * @return int
     *
     * @see java.lang.Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(final Cell obj) {
        return this.compareTo(obj, null);
    }

    /**
     * Simple toString which output the static value.
     *
     * @return String representation of the cell
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) //
                .append("staticValue", this.staticValue).toString(); //$NON-NLS-1$
    }

    /**
     * Sets the per row attributes.
     *
     * @param perRowValues
     *            the new per row attributes
     */
    public void setPerRowAttributes(final HtmlAttributeMap perRowValues) {
        this.attributes = perRowValues;
    }

    /**
     * Gets the per row attributes.
     *
     * @return the per row attributes
     */
    public HtmlAttributeMap getPerRowAttributes() {
        return this.attributes;
    }

}
