/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.exception;

import org.displaytag.Messages;
import org.displaytag.model.RowSorter;

/**
 * runtime exception thrown during sorting when a checked exception can't be used.
 */
public class RuntimeLookupException extends RuntimeException {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Instantiates a new runtime lookup exception.
     *
     * @param sourceClass
     *            class where the exception is thrown
     * @param property
     *            object property who caused the exception
     * @param cause
     *            previous (checked) exception
     */
    public RuntimeLookupException(final Class<? extends RowSorter> sourceClass, final String property,
            final BaseNestableJspTagException cause) {
        super(Messages.getString("RuntimeLookupException.msg", //$NON-NLS-1$
                new Object[] { property, cause.getMessage() }));
    }

}
