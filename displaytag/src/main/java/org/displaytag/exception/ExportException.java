package org.displaytag.exception;


/**
 * Exception thrown when displaytag is unable to reset the response during export.
 * @author Fabrizio Giustina
 * @version $Revision $ ($Author $)
 */
public class ExportException extends BaseNestableJspTagException
{

    /**
     * Instantiate a new Exception with a fixed message.
     * @param source Class where the exception is generated
     */
    public ExportException(Class source)
    {
        super(source, "Unable to reset response before returning exported data. "
            + "You are not using an export filter. "
            + "Be sure that no other jsp tags are used before display:table or refer to the displaytag "
            + "documentation on how to configure the export filter (requires j2ee 1.3).");
    }

    /**
     * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
     */
    public SeverityEnum getSeverity()
    {
        return SeverityEnum.WARN;
    }

}