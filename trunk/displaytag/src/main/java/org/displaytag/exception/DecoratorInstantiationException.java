package org.displaytag.exception;

/**
 * <p>Exception thrown when DecoratorFactory is unable to load a Decorator</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class DecoratorInstantiationException extends BaseNestableJspTagException
{

	/**
	 * name of the decorator
	 */
	private String mDecoratorName;

	/**
	 * Constructor for DecoratorInstantiationException.
	 * @param pSourceClass Class where the exception is generated
	 * @param pDecoratorName decorator name
	 * @param pCause previous Exception
	 */
	public DecoratorInstantiationException(Class pSourceClass, String pDecoratorName, Throwable pCause)
	{
		super(pSourceClass, "Unable to load " + pDecoratorName, pCause);
		mDecoratorName = pDecoratorName;
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
