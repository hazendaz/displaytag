/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import java.io.Serializable;

/**
 * Simple utility class for encoding parameter names.
 */
public class ParamEncoder implements Serializable {

    /**
     * Serial ID.
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
