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
package org.displaytag.util;

import java.io.Serializable;


/**
 * Simple utility class for encoding parameter names.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ParamEncoder implements Serializable
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Unique identifier for a tag with the given id/name.
     */
    private String parameterIdentifier;

    /**
     * Generates a new parameter encoder for the table with the given id.
     * @param idAttribute value of "id" attribute
     */
    public ParamEncoder(String idAttribute)
    {
        // use name and id to get the unique identifier
        String stringIdentifier = "x-" + idAttribute; //$NON-NLS-1$

        // get the array
        char[] charArray = stringIdentifier.toCharArray();

        // calculate a simple checksum-like value
        int checkSum = 17;

        for (int j = 0; j < charArray.length; j++)
        {
            checkSum = 3 * checkSum + charArray[j];
        }

        // keep it positive
        checkSum &= 0x7fffff;

        // this is the full identifier used for all the parameters
        this.parameterIdentifier = "d-" + checkSum + "-"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * encode a parameter name prepending calculated <code>parameterIdentifier</code>.
     * @param paramName parameter name
     * @return encoded parameter name in the form <code>d-<em>XXXX</em>-<em>name</em></code>
     */
    public String encodeParameterName(String paramName)
    {
        return this.parameterIdentifier + paramName;
    }

    /**
     * Check if the given parameter has been encoded using paramEncoder. It actually check if the parameter name starts
     * with the calculated <code>parameterIdentifier</code>. Null safe (a null string returns <code>false</code>).
     * @param paramName parameter name
     * @return <code>true</code> if the given parameter as been encoded using this param encoder
     */
    public boolean isParameterEncoded(String paramName)
    {
        return paramName != null && paramName.startsWith(this.parameterIdentifier);
    }

}