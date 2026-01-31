/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

import org.displaytag.Messages;

/**
 * Exception thrown when a Tag is not properly nested into another one.
 */
public class TagStructureException extends BaseNestableJspTagException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Constructor for InvalidTagAttributeValueException.
     *
     * @param source
     *            Class where the exception is generated
     * @param currentTag
     *            name of the current tag
     * @param shoudBeNestedIn
     *            missing parent tag
     */
    public TagStructureException(final Class<?> source, final String currentTag, final String shoudBeNestedIn) {
        super(source, Messages.getString("TagStructureException.msg", //$NON-NLS-1$
                new Object[] { currentTag, shoudBeNestedIn }));
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
