package org.displaytag.exception;

import org.displaytag.Messages;

/**
 * Exception thrown for errors in accessing bean properties.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ObjectLookupException extends BaseNestableJspTagException
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Instantiate a new ObjectLookupException.
     * @param source Class where the exception is generated
     * @param beanObject javabean
     * @param beanProperty name of the property not found in javabean
     * @param cause previous Exception
     */
    public ObjectLookupException(Class source, Object beanObject, String beanProperty, Throwable cause)
    {
        super(source, Messages.getString("ObjectLookupException.msg" //$NON-NLS-1$
            , new Object[]{beanProperty, ((beanObject == null) ? "null" : beanObject.getClass().getName())}//$NON-NLS-1$
            ), cause);
    }

    /**
     * @return SeverityEnum.WARN
     * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
     * @see org.displaytag.exception.SeverityEnum
     */
    public SeverityEnum getSeverity()
    {
        return SeverityEnum.WARN;
    }

}