package org.displaytag.util;

import java.util.Map;


/**
 * A RequestHelper object is used to read parameters from the request. Main features are handling of numeric parameters
 * and the ability to create Href objects from the current request.
 * @author Fabrizio Giustina
 * @version $Revision $ ($Author $)
 */
public interface RequestHelper
{

    /**
     * return the current Href for the request (base url and parameters).
     * @return Href
     */
    Href getHref();

    /**
     * Reads a String parameter from the request.
     * @param key String parameter name
     * @return String parameter value
     */
    String getParameter(String key);

    /**
     * Reads an Integer parameter from the request.
     * @param key String parameter name
     * @return Integer parameter value or null if the parameter is not found or it can't be transformed to an Integer
     */
    Integer getIntParameter(String key);

    /**
     * Returns a Map containing all the parameters in the request.
     * @return Map
     */
    Map getParameterMap();

}
