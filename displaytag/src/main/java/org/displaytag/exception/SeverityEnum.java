package org.displaytag.exception;

import org.apache.commons.lang.enum.Enum;

/**
 * <p>Enumeration for logging severities</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public final class SeverityEnum extends Enum
{
    /**
     * Severity FATAL
     */
    public static final SeverityEnum FATAL = new SeverityEnum("fatal");

    /**
     * Severity ERROR
     */
    public static final SeverityEnum ERROR = new SeverityEnum("error");

    /**
     * Severity WARN
     */
    public static final SeverityEnum WARN = new SeverityEnum("warn");

    /**
     * Severity INFO
     */
    public static final SeverityEnum INFO = new SeverityEnum("info");

    /**
     * Severity DEBUG
     */
    public static final SeverityEnum DEBUG = new SeverityEnum("debug");

    /**
     * private constructor. Use only constants
     * @param pSeverity Severity name as String
     */
    private SeverityEnum(String pSeverity)
    {
        super(pSeverity);
    }

}