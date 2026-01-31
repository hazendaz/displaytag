/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

/**
 * Basic wrapper for checked exceptions.
 */
public class WrappedRuntimeException extends BaseNestableRuntimeException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Instantiate a new WrappedRuntimeException.
     *
     * @param source
     *            Class where the exception is generated
     * @param cause
     *            Original exception
     */
    public WrappedRuntimeException(final Class<?> source, final Throwable cause) {
        super(source, cause.getMessage(), cause);
    }

    /**
     * Gets the severity.
     *
     * @return the severity
     *
     * @see org.displaytag.exception.BaseNestableRuntimeException#getSeverity()
     */
    @Override
    public SeverityEnum getSeverity() {
        return SeverityEnum.WARN;
    }

}
