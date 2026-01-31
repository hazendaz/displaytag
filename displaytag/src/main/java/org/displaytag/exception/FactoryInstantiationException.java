/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

import org.displaytag.Messages;
import org.displaytag.properties.TableProperties;

/**
 * Exception thrown when displaytag is unable to instantiate a class specified by the user in the properties file.
 */
public class FactoryInstantiationException extends BaseNestableRuntimeException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Instantiate a new FactoryInstantiationException.
     *
     * @param source
     *            Class where the exception is generated
     * @param propertyName
     *            name of the property
     * @param propertyValue
     *            value for the property (class name)
     * @param cause
     *            previous exception
     */
    public FactoryInstantiationException(final Class<? extends TableProperties> source, final String propertyName,
            final String propertyValue, final Throwable cause) {
        super(source, Messages.getString("FactoryInstantiationException.msg", //$NON-NLS-1$
                new Object[] { propertyValue, propertyName }), cause);
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
        return SeverityEnum.FATAL;
    }

}
