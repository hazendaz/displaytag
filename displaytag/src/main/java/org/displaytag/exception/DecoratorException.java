/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.exception;

/**
 * Exception thrown by column decorators. If a decorator need to throw a checked exception this should be nested in a
 * DecoratorException.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class DecoratorException extends BaseNestableJspTagException {

    /**
     * D1597A17A6.
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
