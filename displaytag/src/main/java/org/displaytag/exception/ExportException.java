/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

import org.displaytag.Messages;

/**
 * Exception thrown when displaytag is unable to reset the response during export.
 */
public class ExportException extends BaseNestableJspTagException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Instantiate a new Exception with a fixed message.
     *
     * @param source
     *            Class where the exception is generated
     */
    public ExportException(final Class<?> source) {
        super(source, Messages.getString("ExportException.msg")); //$NON-NLS-1$
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
        return SeverityEnum.WARN;
    }

}
