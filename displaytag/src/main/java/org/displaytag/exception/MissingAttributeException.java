/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

import org.apache.commons.lang3.ArrayUtils;
import org.displaytag.Messages;

/**
 * Exception thrown when a required attribute is not set. This is thrown when the user is required to set at least one
 * of multiple attributes and the check can't be enforced by the tld.
 */
public class MissingAttributeException extends BaseNestableJspTagException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * list of tag attributes.
     */
    private final String[] attributes;

    /**
     * Constructor for MissingAttributeException.
     *
     * @param source
     *            Class where the exception is generated
     * @param attributeNames
     *            String attribute name
     */
    public MissingAttributeException(final Class<?> source, final String[] attributeNames) {
        super(source, Messages.getString("MissingAttributeException.msg", //$NON-NLS-1$
                new Object[] { ArrayUtils.toString(attributeNames) }));

        // copy attributes to allow them to be retrieved using getAttributeNames()
        this.attributes = ArrayUtils.clone(attributeNames);
    }

    /**
     * Gets the severity.
     *
     * @return SeverityEnum.ERROR
     *
     * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
     * @see org.displaytag.exception.SeverityEnum
     */
    @Override
    public SeverityEnum getSeverity() {
        return SeverityEnum.ERROR;
    }

    /**
     * returns an array containing the names of missing attributes.
     *
     * @return String[] array of missing attributes
     */
    public String[] getAttributeNames() {
        return ArrayUtils.clone(this.attributes);
    }

}
