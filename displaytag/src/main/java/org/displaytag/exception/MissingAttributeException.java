/*
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.exception;

import org.apache.commons.lang3.ArrayUtils;
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
    public MissingAttributeException(Class< ? > source, String[] attributeNames)
    {
        super(source, Messages.getString("MissingAttributeException.msg", //$NON-NLS-1$
            new Object[]{ArrayUtils.toString(attributeNames)}));

        // copy attributes to allow them to be retrieved using getAttributeNames()
        this.attributes = ArrayUtils.clone(attributeNames);
    }

    /**
     * @return SeverityEnum.ERROR
     * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
     * @see org.displaytag.exception.SeverityEnum
     */
    @Override
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
        return ArrayUtils.clone(this.attributes);
    }

}