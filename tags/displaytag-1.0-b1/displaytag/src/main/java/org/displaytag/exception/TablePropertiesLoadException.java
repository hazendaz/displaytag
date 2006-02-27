package org.displaytag.exception;

/**
 * <p>Exception thrown for problems in loading the (standard or user defined) property file</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class TablePropertiesLoadException extends BaseNestableJspTagException
{

	/**
	 * name of the properties file
	 */
	private String mPropertiesFileName;

	/**
	 * Constructor for TablePropertiesLoadException
	 * @param pSourceClass Class where the exception is generated
	 * @param pPropertiesFileName properties file name
	 * @param pCause previous Exception
	 */
	public TablePropertiesLoadException(Class pSourceClass, String pPropertiesFileName, Throwable pCause)
	{
		super(pSourceClass, "Unable to load file " + pPropertiesFileName, pCause);
		mPropertiesFileName = pPropertiesFileName;
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
