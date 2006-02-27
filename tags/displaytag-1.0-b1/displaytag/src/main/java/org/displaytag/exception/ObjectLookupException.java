package org.displaytag.exception;

/**
 * <p>Exception thrown for errors in accessing bean properties</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class ObjectLookupException extends BaseNestableJspTagException
{

	/**
	 * Instantiate a new ObjectLookupException
	 * @param pSourceClass Class where the exception is generated
	 * @param pBeanObject javabean
	 * @param pBeanProperty name of the property not found in javabean
	 * @param pCause previous Exception
	 */
	public ObjectLookupException(Class pSourceClass, Object pBeanObject, String pBeanProperty, Throwable pCause)
	{
		super(
			pSourceClass,
			"Error looking up property \""
				+ pBeanProperty
				+ "\" in object type \""
				+ ((pBeanObject == null) ? "null" : pBeanObject.getClass().getName())
				+ "\"",
			pCause);
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
