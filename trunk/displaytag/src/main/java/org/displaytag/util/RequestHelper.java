package org.displaytag.util;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>A RequestHelper object is used to read parameters from the request.
 * Main feature are handling of numeric parameters and the ability to create
 * Href objects from the current request
 * </p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 * @see org.displaytag.util.Href
 */
public class RequestHelper
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(RequestHelper.class);

    /**
     * original HttpServletRequest.
     */
    private HttpServletRequest request;

    /**
     * Construct a new RequestHelper for the given request.
     * @param servletRequest HttpServletRequest
     */
    public RequestHelper(HttpServletRequest servletRequest)
    {
        this.request = servletRequest;
    }

    /**
     * Read a String parameter from the request.
     * @param key String parameter name
     * @return String parameter value
     */
    public final String getParameter(String key)
    {
        // actually simply return the parameter, this behaviour could be changed
        return this.request.getParameter(key);
    }

    /**
     * Read a Integer parameter from the request.
     * @param key String parameter name
     * @return Integer parameter value or null if the parameter is not found or it can't be transformed to an Integer
     */
    public final Integer getIntParameter(String key)
    {
        String value = this.request.getParameter(key);

        if (value != null)
        {
            try
            {
                return new Integer(value);
            }
            catch (NumberFormatException e)
            {
                // It's ok to ignore, simply return null
                log.debug("Invalid \"" + key + "\" parameter from request: value=\"" + value + "\"");
            }
        }

        return null;
    }

    /**
     * Returns an HashMap containing all the parameters in the request.
     * @return HashMap
     */
    public final HashMap getParameterMap()
    {

        HashMap map = new HashMap();

        // get the parameters names
        Enumeration parametersName = this.request.getParameterNames();

        while (parametersName.hasMoreElements())
        {
            // ... get the value
            String paramName = (String) parametersName.nextElement();

            // put key/value in the map
            map.put(paramName, this.request.getParameter(paramName));
        }

        // return the Map
        return map;
    }

    /**
     * return the current Href for the request (base url and parameters).
     * @return Href
     */
    public final Href getHref()
    {
        Href href = new Href(this.request.getRequestURI());
        href.setParameterMap(getParameterMap());
        return href;
    }

}