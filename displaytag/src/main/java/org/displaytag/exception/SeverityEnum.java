/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.exception;

/**
 * Enumeration for logging severities.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public enum SeverityEnum {

    FATAL("fatal"), ERROR("error"), WARN("warn"), INFO("info"), DEBUG("debug");

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    private String severity;

    /**
     * private constructor. Use only constants
     * @param severity Severity name as String
     */
    private SeverityEnum(String severity)
    {
        this.severity = severity;
    }

}