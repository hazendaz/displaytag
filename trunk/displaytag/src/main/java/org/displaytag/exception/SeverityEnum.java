package org.displaytag.exception;

import org.apache.commons.lang.enum.Enum;


/**
 * Enumeration for logging severities.
 * @author Fabrizio Giustina
 * @version $Revision $ ($Author $)
 */
public final class SeverityEnum extends Enum
{

    /**
     * Severity FATAL.
     */
    public static final SeverityEnum FATAL = new SeverityEnum("fatal");

    /**
     * Severity ERROR.
     */
    public static final SeverityEnum ERROR = new SeverityEnum("error");

    /**
     * Severity WARN.
     */
    public static final SeverityEnum WARN = new SeverityEnum("warn");

    /**
     * Severity INFO.
     */
    public static final SeverityEnum INFO = new SeverityEnum("info");

    /**
     * Severity DEBUG.
     */
    public static final SeverityEnum DEBUG = new SeverityEnum("debug");

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