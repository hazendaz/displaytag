package org.displaytag.exception;

import org.displaytag.Messages;

/**
 * Exception thrown when displaytag is unable to instantiate a class specified by the user in the properties file.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class FactoryInstantiationException extends BaseNestableJspTagException
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Instantiate a new FactoryInstantiationException.
     * @param source Class where the exception is generated
     * @param propertyName name of the property
     * @param propertyValue value for the property (class name)
     * @param cause previous exception
     */
    public FactoryInstantiationException(Class source, String propertyName, String propertyValue, Throwable cause)
    {
        super(source, Messages.getString("FactoryInstantiationException.msg", //$NON-NLS-1$
            new Object[]{propertyValue, propertyName}), cause);
    }

    /**
     * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
     */
    public SeverityEnum getSeverity()
    {
        return SeverityEnum.FATAL;
    }

}