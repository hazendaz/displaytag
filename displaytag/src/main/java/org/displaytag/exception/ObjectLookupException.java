/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

import org.displaytag.Messages;

/**
 * Exception thrown for errors in accessing bean properties.
 */
public class ObjectLookupException extends BaseNestableJspTagException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Instantiate a new ObjectLookupException.
     *
     * @param source
     *            Class where the exception is generated
     * @param beanObject
     *            javabean
     * @param beanProperty
     *            name of the property not found in javabean
     * @param cause
     *            previous Exception
     */
    public ObjectLookupException(final Class<?> source, final Object beanObject, final String beanProperty,
            final Throwable cause) {
        super(source, Messages.getString("ObjectLookupException.msg" //$NON-NLS-1$
                , new Object[] { beanProperty, beanObject == null ? "null" : beanObject.getClass().getName() }//$NON-NLS-1$
        ), cause);
    }

    /**
     * Gets the severity.
     *
     * @return SeverityEnum.WARN
     *
     * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
     * @see org.displaytag.exception.SeverityEnum
     */
    @Override
    public SeverityEnum getSeverity() {
        return SeverityEnum.WARN;
    }

}
