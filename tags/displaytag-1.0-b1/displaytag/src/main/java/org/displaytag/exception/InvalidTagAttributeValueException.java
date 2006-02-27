package org.displaytag.exception;

/**
 * <p>Exception thrown when an invalid value is given for a tag attribute</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class InvalidTagAttributeValueException extends BaseNestableJspTagException
{

	/**
	 * tag attribute name
	 */
	private String mAttributeName;

	/**
	 * tag attribute value
	 */
	private String mAttributeValue;

	/**
	 * Constructor for InvalidTagAttributeValueException.
	 * @param pSourceClass Class where the exception is generated
	 * @param pAttributeName String attribute name
	 * @param pAttributeValue String attribute value (invalid)
	 */
	public InvalidTagAttributeValueException(Class pSourceClass, String pAttributeName, String pAttributeValue)
	{
		super(pSourceClass, "Invalid value for attribute \"" + pAttributeName + "\" value=\"" + pAttributeValue + "\"");
		mAttributeName = pAttributeName;
		mAttributeValue = pAttributeValue;
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
