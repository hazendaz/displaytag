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

import org.apache.commons.lang.ArrayUtils;
import org.displaytag.Messages;


/**
 * Exception thrown when a required attribute is not set. This is thrown when the user is required to set at least one
 * of multiple attributes and the check can't be enforced by the tld.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class MissingAttributeException extends BaseNestableJspTagException
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * list of tag attributes.
     */
    private final String[] attributes;

    /**
     * Constructor for MissingAttributeException.
     * @param source Class where the exception is generated
     * @param attributeNames String attribute name
     */
    public MissingAttributeException(Class source, String[] attributeNames)
    {
        super(source, Messages.getString("MissingAttributeException.msg", //$NON-NLS-1$
            new Object[]{ArrayUtils.toString(attributeNames)}));

        // copy attributes to allow them to be retrieved using getAttributeNames()
        this.attributes = (String[]) ArrayUtils.clone(attributeNames);
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

    /**
     * returns an array containing the names of missing attributes.
     * @return String[] array of missing attributes
     */
    public String[] getAttributeNames()
    {
        return (String[]) ArrayUtils.clone(this.attributes);
    }

}