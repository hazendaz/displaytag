package org.displaytag.exception;

import org.apache.commons.lang.enum.Enum;


/**
 * Enumeration for logging severities.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class SeverityEnum extends Enum
{

    /**
     * Severity FATAL.
     */
    public static final SeverityEnum FATAL = new SeverityEnum("fatal"); //$NON-NLS-1$

    /**
     * Severity ERROR.
     */
    public static final SeverityEnum ERROR = new SeverityEnum("error"); //$NON-NLS-1$

    /**
     * Severity WARN.
     */
    public static final SeverityEnum WARN = new SeverityEnum("warn"); //$NON-NLS-1$

    /**
     * Severity INFO.
     */
    public static final SeverityEnum INFO = new SeverityEnum("info"); //$NON-NLS-1$

    /**
     * Severity DEBUG.
     */
    public static final SeverityEnum DEBUG = new SeverityEnum("debug"); //$NON-NLS-1$

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * private constructor. Use only constants
     * @param severity Severity name as String
     */
    private SeverityEnum(String severity)
    {
        super(severity);
    }

}