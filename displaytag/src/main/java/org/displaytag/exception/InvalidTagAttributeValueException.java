/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

import org.displaytag.Messages;

/**
 * Exception thrown when an invalid value is given for a tag attribute.
 */
public class InvalidTagAttributeValueException extends BaseNestableJspTagException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Constructor for InvalidTagAttributeValueException.
     *
     * @param source
     *            Class where the exception is generated
     * @param attributeName
     *            String attribute name
     * @param attributeValue
     *            attribute value (invalid)
     */
    public InvalidTagAttributeValueException(final Class<?> source, final String attributeName,
            final Object attributeValue) {
        super(source, Messages.getString("InvalidTagAttributeValueException.msg", //$NON-NLS-1$
                new Object[] { attributeName, attributeValue }));
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

}
