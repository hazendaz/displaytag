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
    Map getParameterMap();

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Any parameter already present in the href object is removed.
     * @param parametersMap Map containing parameters
     */
    void setParameterMap(Map parametersMap);

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Parameters in the original href are kept and not overridden.
     * @param parametersMap Map containing parameters
     */
    void addParameterMap(Map parametersMap);

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

    /**
     * toString: output the full url with parameters.
     * @return String
     */
    String toString();

    /**
     * @see java.lang.Object#clone()
     */
    Object clone();

    /**
     * @see java.lang.Object#equals(Object)
     */
    boolean equals(Object object);

}