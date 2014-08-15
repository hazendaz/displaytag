/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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

import javax.servlet.jsp.JspTagException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.Messages;


/**
 * Base exception: extendes JspTagException providing logging and exception nesting functionalities.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public abstract class BaseNestableJspTagException extends JspTagException
{

    /**
     * Stable serialVersionUID.
     */
    private static final long serialVersionUID = 42L;

    /**
     * Class where the exception has been generated.
     */
    private final Class< ? > sourceClass;

    /**
     * previous exception.
     */
    private Throwable nestedException;

    /**
     * Instantiate a new BaseNestableJspTagException.
     * @param source Class where the exception is generated
     * @param message message
     */
    public BaseNestableJspTagException(Class< ? > source, String message)
    {
        super(message);
        this.sourceClass = source;

        // log exception
        Log log = LogFactory.getLog(source);

        // choose appropriate logging method
        if (getSeverity() == SeverityEnum.DEBUG)
        {
            log.debug(toString());
        }
        else if (getSeverity() == SeverityEnum.INFO)
        {
            log.info(toString());
        }
        else if (getSeverity() == SeverityEnum.WARN)
        {
            log.warn(toString());
        }
        else
        {
            // error - default
            log.error(toString());
        }

    }

    /**
     * Instantiate a new BaseNestableJspTagException.
     * @param source Class where the exception is generated
     * @param message message
     * @param cause previous Exception
     */
    public BaseNestableJspTagException(Class< ? > source, String message, Throwable cause)
    {
        super(message);
        this.sourceClass = source;
        this.nestedException = cause;

        // log exception
        Log log = LogFactory.getLog(source);

        // choose appropriate logging method
        if (getSeverity() == SeverityEnum.DEBUG)
        {
            log.debug(toString(), cause);
        }
        else if (getSeverity() == SeverityEnum.INFO)
        {
            log.info(toString(), cause);
        }
        else if (getSeverity() == SeverityEnum.WARN)
        {
            log.warn(toString(), cause);
        }
        else
        {
            // error - default
            log.error(toString(), cause);
        }

    }

    /**
     * returns the previous exception.
     * @return Throwable previous exception
     */
    @Override
    public Throwable getCause()
    {
        return this.nestedException;
    }

    /**
     * basic toString. Returns the message plus the previous exception (if a previous exception exists).
     * @return String
     */
    @Override
    public String toString()
    {
        String className = this.sourceClass.getName();
        className = className.substring(className.lastIndexOf(".")); //$NON-NLS-1$

        if (this.nestedException == null)
        {
            return Messages.getString("NestableException.msg", //$NON-NLS-1$
                new Object[]{className, getMessage()});
        }

        return Messages.getString("NestableException.msgcause", //$NON-NLS-1$
            new Object[]{className, getMessage(), this.nestedException.getMessage()});
    }

    /**
     * subclasses need to define the getSeverity method to provide correct severity for logging.
     * @return SeverityEnum exception severity
     * @see org.displaytag.exception.SeverityEnum
     */
    public abstract SeverityEnum getSeverity();

}