/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
