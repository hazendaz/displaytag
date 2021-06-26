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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.displaytag.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    private static Logger log = LoggerFactory.getLogger(DefaultRequestHelper.class);

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
    @Override
    public Href getHref()
    {
        String requestURI = this.request.getRequestURI();
        // call encodeURL to preserve session id when cookies are disabled
        Href href = new DefaultHref(this.response.encodeURL(requestURI));
        href.setParameterMap(getParameterMap());
        return href;
    }

    /**
     * @see org.displaytag.util.RequestHelper#getParameter(java.lang.String)
     */
    @Override
    public String getParameter(String key)
    {
        // actually simply return the parameter, this behaviour could be changed
        return this.request.getParameter(key);
    }

    /**
     * @see org.displaytag.util.RequestHelper#getIntParameter(java.lang.String)
     */
    @Override
    public Integer getIntParameter(String key)
    {
        String value = this.request.getParameter(key);

        if (value != null)
        {
            try
            {
                return Integer.valueOf(value);
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
    @Override
    public Map<String, String[]> getParameterMap()
    {

        Map<String, String[]> map = new HashMap<>();

        // Note: Don't type enumeration so servlet 2.5 is still possible
        // get the parameters names
        Enumeration parametersName = this.request.getParameterNames();

        while (parametersName.hasMoreElements())
        {
            // ... get the value
            String paramName = (String) parametersName.nextElement();

            this.request.getParameter(paramName);
            // put key/value in the map
            String[] originalValues = ObjectUtils.defaultIfNull(
                this.request.getParameterValues(paramName),
                new String[0]);
            String[] values = new String[originalValues.length];

            for (int i = 0; i < values.length; i++)
            {
                try
                {
                    values[i] = URLEncoder.encode(
                        StringUtils.defaultString(originalValues[i]),
                        StringUtils.defaultString(this.response.getCharacterEncoding(), "UTF8")); //$NON-NLS-1$
                }
                catch (UnsupportedEncodingException e)
                {
                    throw new RuntimeException(e);
                }
            }
            map.put(paramName, values);

        }

        // return the Map
        return map;
    }

}