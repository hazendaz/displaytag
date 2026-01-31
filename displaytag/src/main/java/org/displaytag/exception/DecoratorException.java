/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

/**
 * Exception thrown by column decorators. If a decorator need to throw a checked exception this should be nested in a
 * DecoratorException.
 */
public class DecoratorException extends BaseNestableJspTagException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Constructor for DecoratorException.
     *
     * @param source
     *            Class where the exception is generated
     * @param message
     *            message
     */
    public DecoratorException(final Class<?> source, final String message) {
        super(source, message);
    }

    /**
     * Constructor for DecoratorException.
     *
     * @param source
     *            Class where the exception is generated
     * @param message
     *            message
     * @param cause
     *            previous exception
     */
    public DecoratorException(final Class<?> source, final String message, final Throwable cause) {
        super(source, message, cause);
    }

    /**
     * Gets the severity.
     *
     * @return the severity
     *
     * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
     */
    @Override
    public SeverityEnum getSeverity() {
        return SeverityEnum.ERROR;
    }

}
