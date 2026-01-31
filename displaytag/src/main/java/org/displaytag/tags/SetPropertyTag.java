/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.tags;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import jakarta.servlet.jsp.tagext.Tag;
import jakarta.servlet.jsp.tagext.TagSupport;

import org.displaytag.exception.MissingAttributeException;
import org.displaytag.exception.TagStructureException;

/**
 * The Class SetPropertyTag.
 */
public class SetPropertyTag extends BodyTagSupport {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * property name.
     */
    private String name;

    /**
     * property value.
     */
    private String value;

    /** is this the first iteration?. */
    private boolean firstIteration;

    /**
     * Sets the name of the property.
     *
     * @param propertyName
     *            String
     */
    public void setName(final String propertyName) {
        this.name = propertyName;
    }

    /**
     * Sets the value of the property.
     *
     * @param propertyValue
     *            String
     */
    public void setValue(final String propertyValue) {
        this.value = propertyValue;
    }

    /**
     * Do start tag.
     *
     * @return the int
     *
     * @throws JspException
     *             the jsp exception
     *
     * @see jakarta.servlet.jsp.tagext.Tag#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        final TableTag tableTag = (TableTag) TagSupport.findAncestorWithClass(this, TableTag.class);

        if (tableTag == null) {
            throw new TagStructureException(this.getClass(), "setProperty", "table"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        // read body only once
        if (tableTag.isFirstIteration()) {
            this.firstIteration = true;
            // using int to avoid deprecation error in compilation using j2ee 1.3 (EVAL_BODY_TAG)
            return 2;
        }

        this.firstIteration = false;
        return Tag.SKIP_BODY;

    }

    /**
     * Passes attribute information up to the parent TableTag.
     * <p>
     * When we hit the end of the tag, we simply let our parent (which better be a TableTag) know what the user wants to
     * change a property value, and we pass the name/value pair that the user gave us, up to the parent
     * </p>
     *
     * @return <code>TagSupport.EVAL_PAGE</code>
     *
     * @throws MissingAttributeException
     *             if no value or body content has been set
     *
     * @see jakarta.servlet.jsp.tagext.Tag#doEndTag()
     */
    @Override
    public int doEndTag() throws MissingAttributeException {

        if (this.firstIteration) {
            final TableTag tableTag = (TableTag) TagSupport.findAncestorWithClass(this, TableTag.class);

            // tableTag can't be null, it has been checked in doStartTag
            if (this.value == null) {
                if (this.getBodyContent() == null) {
                    throw new MissingAttributeException(this.getClass(), //
                            new String[] { "value", "body content" }); //$NON-NLS-1$ //$NON-NLS-2$
                }
                this.value = this.getBodyContent().getString();
            }

            tableTag.setProperty(this.name, this.value);

            this.name = null;
            this.value = null;
        }
        return Tag.EVAL_PAGE;
    }

}
