package org.displaytag.exception;

/**
 * <p>Exception thrown by column decorators. If a decorator need to thrown a checked exception this should be nested in
 * a DecoratorException, </p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class DecoratorException extends BaseNestableJspTagException
{

	/**
	 * Constructor for DecoratorException.
	 * @param pSourceClass Class where the exception is generated
	 * @param pMessage message
	 */
	public DecoratorException(Class pSourceClass, String pMessage)
	{
		super(pSourceClass, pMessage);
	}

	/**
	 * Constructor for DecoratorException.
	 * @param pSourceClass Class where the exception is generated
	 * @param pMessage message
	 * @param pCause previous exception
	 */
	public DecoratorException(Class pSourceClass, String pMessage, Throwable pCause)
	{
		super(pSourceClass, pMessage, pCause);
	}

	/**
	 * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
	 */
	public SeverityEnum getSeverity()
	{
		return SeverityEnum.ERROR;
	}

}
