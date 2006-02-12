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
import org.displaytag.util.TagConstants;


/**
 * Exception thrown when DecoratorFactory is unable to load a Decorator.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DecoratorInstantiationException extends BaseNestableJspTagException
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Constructor for DecoratorInstantiationException.
     * @param source Class where the exception is generated
     * @param decorator decorator name
     * @param cause previous Exception
     */
    public DecoratorInstantiationException(Class source, String decorator, Throwable cause)
    {
        super(source, Messages.getString("DecoratorInstantiationException.msg" //$NON-NLS-1$
            , new Object[]{decorator, (cause != null ? cause.getClass().getName() : TagConstants.EMPTY_STRING)}), //
            cause);
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