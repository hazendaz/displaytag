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
 * runtime exception thrown during sorting when a checked exception can't be used.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class RuntimeLookupException extends RuntimeException
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * @param sourceClass class where the exception is thrown
     * @param property object property who caused the exception
     * @param cause previous (checked) exception
     */
    public RuntimeLookupException(Class sourceClass, String property, BaseNestableJspTagException cause)
    {
        super(Messages.getString("RuntimeLookupException.msg", //$NON-NLS-1$
            new Object[]{property, cause.getMessage()}));
    }

}