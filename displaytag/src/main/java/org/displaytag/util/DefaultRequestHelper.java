package org.displaytag.util;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Default RequestHelper implementation.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 * @see org.displaytag.util.Href
 * @see org.displaytag.util.RequestHelper
 */
public class DefaultRequestHelper implements RequestHelper
{

    /**
     * j2se 1.4 encode method, used by reflection if available.
     */
    private static Method encodeMethod14;

    static
    {
        // URLEncoder.encode(String) has been deprecated in J2SE 1.4.
        // Take advantage of the new method URLEncoder.encode(String, enc) if J2SE 1.4 is used.
        try
        {
            Class urlEncoderClass = Class.forName("java.net.URLEncoder");
            encodeMethod14 = urlEncoderClass.getMethod("encode", new Class[]{String.class, String.class});
        }
        catch (Throwable ex)
        {
            // encodeMethod14 will be null if exception
        }
    }

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(DefaultRequestHelper.class);

    /**
     * original HttpServletRequest.
     */
    private HttpServletRequest request;

    /**
     * original HttpServletResponse.
     */
    private HttpServletResponse response;

    /**
     * Construct a new RequestHelper for the given request.
     * @param servletRequest HttpServletRequest needed to generate the base href
     * @param servletResponse HttpServletResponse needed to encode generated urls
     */
    public DefaultRequestHelper(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
    {
        this.request = servletRequest;
        this.response = servletResponse;
    }

    /**
     * @see org.displaytag.util.RequestHelper#getHref()
     */
    public Href getHref()
    {
        String requestURI = this.request.getRequestURI();
        // call encodeURL to preserve session id when cookies are disabled
        Href href = new Href(this.response.encodeURL(requestURI));
        href.setParameterMap(getParameterMap());
        return href;
    }

    /**
     * @see org.displaytag.util.RequestHelper#getParameter(java.lang.String)
     */
    public String getParameter(String key)
    {
        // actually simply return the parameter, this behaviour could be changed
        return this.request.getParameter(key);
    }

    /**
     * @see org.displaytag.util.RequestHelper#getIntParameter(java.lang.String)
     */
    public Integer getIntParameter(String key)
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
     * @see org.displaytag.util.RequestHelper#getParameterMap()
     */
    public Map getParameterMap()
    {

        Map map = new HashMap();

        // get the parameters names
        Enumeration parametersName = this.request.getParameterNames();

        while (parametersName.hasMoreElements())
        {
            // ... get the value
            String paramName = (String) parametersName.nextElement();

            // put key/value in the map
            String[] originalValues = this.request.getParameterValues(paramName);
            String[] values = new String[originalValues.length];

            for (int i = 0; i < values.length; i++)
            {
                values[i] = encodeURL(StringUtils.defaultString(originalValues[i]));
            }
            map.put(paramName, values);

        }

        // return the Map
        return map;
    }

    /**
     * Called encodeUrl using j2se 1.4 version by reflection if available, or backward compatible version.
     * @param url url to be encoded
     * @return encoded url.
     */
    private String encodeURL(String url)
    {
        if (encodeMethod14 != null)
        {
            Object[] methodArgs = new Object[2];
            methodArgs[0] = url;
            methodArgs[1] = response.getCharacterEncoding();

            try
            {
                return (String) encodeMethod14.invoke(null, methodArgs);
            }
            catch (Exception ex)
            {
                throw new RuntimeException("System error invoking URLEncoder.encode() by reflection.", ex);
            }
        }
        else
        {
            // must use J2SE 1.3 version
            return URLEncoder.encode(url);
        }
    }

}