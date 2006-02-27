package org.displaytag.exception;

import javax.servlet.jsp.JspTagException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Base exception: extendes JspTagException providing loggin and exception nesting functionalities</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public abstract class BaseNestableJspTagException extends JspTagException
{

    /**
     * Class where the exception has been generated
     */
    private Class mSourceClass;

    /**
     * previous exception
     */
    private Throwable mCause;

    /**
     * Instantiate a new BaseNestableJspTagException
     * @param pSourceClass Class where the exception is generated
     * @param pMessage message
     */
    public BaseNestableJspTagException(Class pSourceClass, String pMessage)
    {
        super(pMessage);
        mSourceClass = pSourceClass;

        // log exception
        Log lLog = LogFactory.getLog(pSourceClass);
        lLog.error(toString());

        // choose appropriate logging method
        if (getSeverity() == SeverityEnum.DEBUG)
        {
            lLog.debug(toString());
        }
        else if (getSeverity() == SeverityEnum.INFO)
        {
            lLog.info(toString());
        }
        else if (getSeverity() == SeverityEnum.WARN)
        {
            lLog.warn(toString());
        }
        else
        {
            // error - default
            lLog.error(toString());
        }

    }

    /**
     * Instantiate a new BaseNestableJspTagException
     * @param pSourceClass Class where the exception is generated
     * @param pMessage message
     * @param pCause previous Exception
     */
    public BaseNestableJspTagException(Class pSourceClass, String pMessage, Throwable pCause)
    {
        super(pMessage);
        mSourceClass = pSourceClass;
        mCause = pCause;

        // log exception
        Log lLog = LogFactory.getLog(pSourceClass);

        // choose appropriate logging method
        if (getSeverity() == SeverityEnum.DEBUG)
        {
            lLog.debug(toString(), pCause);
        }
        else if (getSeverity() == SeverityEnum.INFO)
        {
            lLog.info(toString(), pCause);
        }
        else if (getSeverity() == SeverityEnum.WARN)
        {
            lLog.warn(toString(), pCause);
        }
        else
        {
            // error - default
            lLog.error(toString(), pCause);
        }

    }

    /**
     * returns the previous exception
     * @return Throwable previous exception
     */
    public Throwable getCause()
    {
        return mCause;
    }

    /**
     * basic toString. Returns the message plus the previous exception (if a previous exception exists)
     * @return String
     */
    public String toString()
    {
        StringBuffer lBuffer = new StringBuffer();

        String lClassName = mSourceClass.getName();
        lClassName = lClassName.substring(lClassName.lastIndexOf("."));

        lBuffer.append("Exception: ");
        lBuffer.append("[").append(lClassName).append("] ");
        lBuffer.append(getMessage());

        if (mCause != null)
        {
            lBuffer.append("\nCause:     ");
            lBuffer.append(mCause.getMessage());
        }

        return lBuffer.toString();

    }

    /**
     * subclasses need to define the getSeverity method to provide correct severity for logging
     * @return SeverityEnum exception severity
     * @see org.displaytag.exception.SeverityEnum
     */
    public abstract SeverityEnum getSeverity();

}
