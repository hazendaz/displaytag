package org.displaytag.exception;

/**
 * Exception thrown when DecoratorFactory is unable to load a Decorator
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class DecoratorInstantiationException extends BaseNestableJspTagException
{

    /**
     * Constructor for DecoratorInstantiationException.
     * @param source Class where the exception is generated
     * @param decorator decorator name
     * @param cause previous Exception
     */
    public DecoratorInstantiationException(Class source, String decorator, Throwable cause)
    {
        super(source, "Unable to load " + decorator, cause);
    }

    /**
     * @return SeverityEnum.ERROR
     * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
     * @see org.displaytag.exception.SeverityEnum
     */
    public SeverityEnum getSeverity()
    {
        return SeverityEnum.ERROR;
    }

}
