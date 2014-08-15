/**
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
import java.util.Map;


/**
 * Interface representing an URI (the href parameter of an &lt;a> tag). Provides methods to insert new parameters. It
 * doesn't support multiple parameter values
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public interface Href extends Cloneable, Serializable
{

    /**
     * Adds a parameter to the href.
     * @param name String
     * @param value Object
     * @return this Href instance, useful for concatenation.
     */
    Href addParameter(String name, Object value);

    /**
     * Removes a parameter from the href.
     * @param name String
     */
    void removeParameter(String name);

    /**
     * Adds an int parameter to the href.
     * @param name String
     * @param value int
     * @return this Href instance, useful for concatenation.
     */
    Href addParameter(String name, int value);

    /**
     * Getter for the map containing link parameters. The returned map is always a copy and not the original instance.
     * @return parameter Map (copy)
     */
    Map<String, String[]> getParameterMap();

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Any parameter already present in the href object is removed.
     * @param parametersMap Map containing parameters
     */
    void setParameterMap(Map<String, String[]> parametersMap);

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Parameters in the original href are kept and not overridden.
     * @param parametersMap Map containing parameters
     */
    void addParameterMap(Map<String, String[]> parametersMap);

    /**
     * Getter for the base url (without parameters).
     * @return String
     */
    String getBaseUrl();

    /**
     * Set the full url, overriding any existing parameter.
     * @param url full url
     */
    void setFullUrl(String url);

    /**
     * Returns the URI anchor.
     * @return anchor or <code>null</code> if no anchor has been set.
     */
    String getAnchor();

    /**
     * Setter for the URI anchor.
     * @param name string to be used as anchor name (without #).
     */
    void setAnchor(String name);

    @Override
    /**
     * toString: output the full url with parameters.
     * @return String
     */
    String toString();

    /**
     * @see java.lang.Object#clone()
     */
    Object clone();

    @Override
    /**
     * @see java.lang.Object#equals(Object)
     */
    boolean equals(Object object);

}