package org.displaytag.exception;

/**
 * Exception thrown when DecoratorFactory is unable to load a Decorator.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DecoratorInstantiationException extends BaseNestableJspTagException
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Constructor for DecoratorInstantiationException.
     * @param source Class where the exception is generated
     * @param decorator decorator name
     * @param cause previous Exception
     */
    public DecoratorInstantiationException(Class source, String decorator, Throwable cause)
    {
        super(source, "Unable to load "
            + decorator
            + (cause != null ? " due to a " + cause.getClass().getName() + " exception" : ""), cause);
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