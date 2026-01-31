/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

import org.displaytag.Messages;

/**
 * Runtime exception thrown for problems in loading the (standard or user defined) property file.
 */
public class TablePropertiesLoadException extends BaseNestableRuntimeException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Constructor for TablePropertiesLoadException.
     *
     * @param source
     *            Class where the exception is generated
     * @param propertiesFileName
     *            properties file name
     * @param cause
     *            previous Exception
     */
    public TablePropertiesLoadException(final Class<?> source, final String propertiesFileName, final Throwable cause) {
        super(source, Messages.getString("TablePropertiesLoadException.msg", //$NON-NLS-1$
                new Object[] { propertiesFileName }), cause);
    }

    /**
     * Gets the severity.
     *
     * @return SeverityEnum.ERROR
     *
     * @see org.displaytag.exception.BaseNestableRuntimeException#getSeverity()
     * @see org.displaytag.exception.SeverityEnum
     */
    @Override
    public SeverityEnum getSeverity() {
        return SeverityEnum.ERROR;
    }

}
