package org.displaytag.exception;

import javax.servlet.jsp.JspTagException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Base exception: extendes JspTagException providing loggin and exception nesting functionalities
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public abstract class BaseNestableJspTagException extends JspTagException
{

    /**
     * Class where the exception has been generated
     */
    private final Class sourceClass;

    /**
     * previous exception
     */
    private Throwable nestedException;

    /**
     * Instantiate a new BaseNestableJspTagException
     * @param source Class where the exception is generated
     * @param message message
     */
    public BaseNestableJspTagException(Class source, String message)
    {
        super(message);
        this.sourceClass = source;

        // log exception
        Log log = LogFactory.getLog(source);
        log.error(toString());

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
     * Instantiate a new BaseNestableJspTagException
     * @param source Class where the exception is generated
     * @param message message
     * @param cause previous Exception
     */
    public BaseNestableJspTagException(Class source, String message, Throwable cause)
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
     * returns the previous exception
     * @return Throwable previous exception
     */
    public Throwable getCause()
    {
        return this.nestedException;
    }

    /**
     * basic toString. Returns the message plus the previous exception (if a previous exception exists)
     * @return String
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer();

        String className = this.sourceClass.getName();
        className = className.substring(className.lastIndexOf("."));

        buffer.append("Exception: ");
        buffer.append("[").append(className).append("] ");
        buffer.append(getMessage());

        if (this.nestedException != null)
        {
            buffer.append("\nCause:     ");
            buffer.append(this.nestedException.getMessage());
        }

        return buffer.toString();

    }

    /**
     * subclasses need to define the getSeverity method to provide correct severity for logging
     * @return SeverityEnum exception severity
     * @see org.displaytag.exception.SeverityEnum
     */
    public abstract SeverityEnum getSeverity();

}
