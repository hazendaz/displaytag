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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.Messages;


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
                log.debug(Messages.getString("RequestHelper.invalidparameter", //$NON-NLS-1$
                    new Object[]{key, value}));
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
                values[i] = CompatibleUrlEncoder.encode(StringUtils.defaultString(originalValues[i]), response
                    .getCharacterEncoding());
            }
            map.put(paramName, values);

        }

        // return the Map
        return map;
    }

}