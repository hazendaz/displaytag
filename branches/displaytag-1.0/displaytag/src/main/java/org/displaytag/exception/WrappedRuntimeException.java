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
 * Basic wrapper for checked exceptions.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class WrappedRuntimeException extends BaseNestableRuntimeException
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Instantiate a new WrappedRuntimeException.
     * @param source Class where the exception is generated
     * @param cause Original exception
     */
    public WrappedRuntimeException(Class source, Throwable cause)
    {
        super(source, cause.getMessage(), cause);
    }

    /**
     * @see org.displaytag.exception.BaseNestableRuntimeException#getSeverity()
     */
    public SeverityEnum getSeverity()
    {
        return SeverityEnum.WARN;
    }

}
