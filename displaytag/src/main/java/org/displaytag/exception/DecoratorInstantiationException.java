/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

import org.displaytag.Messages;
import org.displaytag.decorator.DefaultDecoratorFactory;
import org.displaytag.util.TagConstants;

/**
 * Exception thrown when DecoratorFactory is unable to load a Decorator.
 */
public class DecoratorInstantiationException extends BaseNestableJspTagException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Constructor for DecoratorInstantiationException.
     *
     * @param source
     *            Class where the exception is generated
     * @param decorator
     *            decorator name
     * @param cause
     *            previous Exception
     */
    public DecoratorInstantiationException(final Class<DefaultDecoratorFactory> source, final String decorator,
            final Throwable cause) {
        super(source, Messages.getString("DecoratorInstantiationException.msg" //$NON-NLS-1$
                , new Object[] { decorator, cause != null ? cause.getClass().getName() : TagConstants.EMPTY_STRING }), //
                cause);
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
