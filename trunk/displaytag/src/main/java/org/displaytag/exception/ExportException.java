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

import org.displaytag.Messages;


/**
 * Exception thrown when displaytag is unable to reset the response during export.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExportException extends BaseNestableJspTagException
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Instantiate a new Exception with a fixed message.
     * @param source Class where the exception is generated
     */
    public ExportException(Class source)
    {
        super(source, Messages.getString("ExportException.msg")); //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
     */
    public SeverityEnum getSeverity()
    {
        return SeverityEnum.WARN;
    }

}