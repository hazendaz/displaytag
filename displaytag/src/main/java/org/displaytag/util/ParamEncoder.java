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
package org.displaytag.util;

import java.io.Serializable;

/**
 * Simple utility class for encoding parameter names.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class ParamEncoder implements Serializable {

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Unique identifier for a tag with the given id/name.
     */
    private final String parameterIdentifier;

    /**
     * Generates a new parameter encoder for the table with the given id.
     *
     * @param idAttribute
     *            value of "id" attribute
     */
    public ParamEncoder(final String idAttribute) {
        // use name and id to get the unique identifier
        final String stringIdentifier = "x-" + idAttribute; //$NON-NLS-1$

        // get the array
        final char[] charArray = stringIdentifier.toCharArray();

        // calculate a simple checksum-like value
        int checkSum = 17;

        for (final char element : charArray) {
            checkSum = 3 * checkSum + element;
        }

        // keep it positive
        checkSum &= 0x7fffff;

        // this is the full identifier used for all the parameters
        this.parameterIdentifier = "d-" + checkSum + "-"; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * encode a parameter name prepending calculated <code>parameterIdentifier</code>.
     *
     * @param paramName
     *            parameter name
     *
     * @return encoded parameter name in the form <code>d-<em>XXXX</em>-<em>name</em></code>
     */
    public String encodeParameterName(final String paramName) {
        return this.parameterIdentifier + paramName;
    }

    /**
     * Check if the given parameter has been encoded using paramEncoder. It actually check if the parameter name starts
     * with the calculated <code>parameterIdentifier</code>. Null safe (a null string returns <code>false</code>).
     *
     * @param paramName
     *            parameter name
     *
     * @return <code>true</code> if the given parameter as been encoded using this param encoder
     */
    public boolean isParameterEncoded(final String paramName) {
        return paramName != null && paramName.startsWith(this.parameterIdentifier);
    }

}