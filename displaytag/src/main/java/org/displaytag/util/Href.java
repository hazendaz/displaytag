/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import java.io.Serializable;
import java.util.Map;

/**
 * Interface representing an URI (the href parameter of an &lt;a&gt; tag). Provides methods to insert new parameters. It
 * doesn't support multiple parameter values
 */
public interface Href extends Cloneable, Serializable {

    /**
     * Adds a parameter to the href.
     *
     * @param name
     *            String
     * @param value
     *            Object
     *
     * @return this Href instance, useful for concatenation.
     */
    Href addParameter(String name, Object value);

    /**
     * Removes a parameter from the href.
     *
     * @param name
     *            String
     */
    void removeParameter(String name);

    /**
     * Adds an int parameter to the href.
     *
     * @param name
     *            String
     * @param value
     *            int
     *
     * @return this Href instance, useful for concatenation.
     */
    Href addParameter(String name, int value);

    /**
     * Getter for the map containing link parameters. The returned map is always a copy and not the original instance.
     *
     * @return parameter Map (copy)
     */
    Map<String, String[]> getParameterMap();

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Any parameter already present in the href object is removed.
     *
     * @param parametersMap
     *            Map containing parameters
     */
    void setParameterMap(Map<String, String[]> parametersMap);

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Parameters in the original href are kept and not overridden.
     *
     * @param parametersMap
     *            Map containing parameters
     */
    void addParameterMap(Map<String, String[]> parametersMap);

    /**
     * Getter for the base url (without parameters).
     *
     * @return String
     */
    String getBaseUrl();

    /**
     * Set the full url, overriding any existing parameter.
     *
     * @param url
     *            full url
     */
    void setFullUrl(String url);

    /**
     * Returns the URI anchor.
     *
     * @return anchor or <code>null</code> if no anchor has been set.
     */
    String getAnchor();

    /**
     * Setter for the URI anchor.
     *
     * @param name
     *            string to be used as anchor name (without #).
     */
    void setAnchor(String name);

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    String toString();

    /**
     * Clone.
     *
     * @return the object
     *
     * @see java.lang.Object#clone()
     */
    Object clone();

    /**
     * Equals.
     *
     * @param object
     *            the object
     *
     * @return true, if successful
     */
    @Override
    boolean equals(Object object);

}
