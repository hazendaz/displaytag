package org.displaytag.exception;

/**
 * <p>Exception thrown when a Tag is not properly nested into another one</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class TagStructureException extends BaseNestableJspTagException
{

	/**
	 * Current tag
	 */
	private String mCurrentTag;

	/**
	 * Missing parent tag
	 */
	private String mShoudBeNestedIn;

	/**
	 * Constructor for InvalidTagAttributeValueException.
	 * @param pSourceClass Class where the exception is generated
	 * @param pCurrentTag name of the current tag
	 * @param pShoudBeNestedIn missing parent tag
	 */
	public TagStructureException(Class pSourceClass, String pCurrentTag, String pShoudBeNestedIn)
	{
		super(pSourceClass, "Tag \"" + pCurrentTag + "\" should ne nested in \"" + pShoudBeNestedIn + "\"");
		mCurrentTag = pCurrentTag;
		mShoudBeNestedIn = pShoudBeNestedIn;
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
