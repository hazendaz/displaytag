package org.displaytag.exception;

/**
 * Exception thrown for problems in loading the (standard or user defined) property file.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class TablePropertiesLoadException extends BaseNestableJspTagException
{

    /**
     * Constructor for TablePropertiesLoadException.
     * @param source Class where the exception is generated
     * @param propertiesFileName properties file name
     * @param cause previous Exception
     */
    public TablePropertiesLoadException(Class source, String propertiesFileName, Throwable cause)
    {
        super(source, "Unable to load file " + propertiesFileName, cause);
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
