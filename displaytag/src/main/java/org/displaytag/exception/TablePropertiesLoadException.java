package org.displaytag.exception;

/**
 * Runtime exception thrown for problems in loading the (standard or user defined) property file.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TablePropertiesLoadException extends BaseNestableRuntimeException
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
     * @see org.displaytag.exception.BaseNestableRuntimeException#getSeverity()
     * @see org.displaytag.exception.SeverityEnum
     */
    public SeverityEnum getSeverity()
    {
        return SeverityEnum.ERROR;
    }

}
