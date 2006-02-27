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
     * logger
     */
    private static Log mLog = LogFactory.getLog(RequestHelper.class);

    /**
     * original HttpServletRequest
     */
    private HttpServletRequest mRequest;

    /**
     * Construct a new RequestHelper for the given request
     * @param pRequest HttpServletRequest
     */
    public RequestHelper(HttpServletRequest pRequest)
    {
        mRequest = pRequest;
    }

    /**
     * Read a String parameter from the request
     * @param pKey String parameter name
     * @return String parameter value
     */
    public final String getParameter(String pKey)
    {
        // actually simply return the parameter, this behaviour could be changed
        return mRequest.getParameter(pKey);
    }

    /**
     * Read a Integer parameter from the request
     * @param pKey String parameter name
     * @return Integer parameter value or null if the parameter is not found or it can't be transformed to an Integer
     */
    public final Integer getIntParameter(String pKey)
    {
        String lParamValue = mRequest.getParameter(pKey);

        if (lParamValue != null)
        {
            try
            {
                return new Integer(lParamValue);
            }
            catch (NumberFormatException e)
            {
                // It's ok to ignore, simply return null
                mLog.debug("Invalid \"" + pKey + "\" parameter from request: value=\"" + lParamValue + "\"");
            }
        }

        return null;
    }

    /**
     * Return an HashMap containing all the parameters in the request
     * @return HashMap
     */
    public final HashMap getParameterMap()
    {

        HashMap lMap = new HashMap();

        // get the parameters names
        Enumeration lParametersName = mRequest.getParameterNames();

        while (lParametersName.hasMoreElements())
        {
            // ... get the value
            String lParamName = (String) lParametersName.nextElement();

            // put key/value in the map
            lMap.put(lParamName, mRequest.getParameter(lParamName));
        }

        // return the Map
        return lMap;
    }

    /**
     * return the current Href for the request (base url and parameters)
     * @return Href
     */
    public final Href getHref()
    {
        Href lHref = new Href(mRequest.getRequestURI());
        lHref.setParameterMap(getParameterMap());
        return lHref;
    }

}