package org.displaytag.exception;

import org.displaytag.Messages;
import org.displaytag.util.TagConstants;


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
        super(source, Messages.getString("DecoratorInstantiationException.msg" //$NON-NLS-1$
            , new Object[]{decorator, (cause != null ? cause.getClass().getName() : TagConstants.EMPTY_STRING)}), cause); //$NON-NLS-1$
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