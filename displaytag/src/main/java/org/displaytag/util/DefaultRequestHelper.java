/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.displaytag.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default RequestHelper implementation.
 *
 * @see org.displaytag.util.Href
 * @see org.displaytag.util.RequestHelper
 */
public class DefaultRequestHelper implements RequestHelper {

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(DefaultRequestHelper.class);

    /**
     * original HttpServletRequest.
     */
    private final HttpServletRequest request;

    /**
     * original HttpServletResponse.
     */
    private final HttpServletResponse response;

    /**
     * Construct a new RequestHelper for the given request.
     *
     * @param servletRequest
     *            HttpServletRequest needed to generate the base href
     * @param servletResponse
     *            HttpServletResponse needed to encode generated urls
     */
    public DefaultRequestHelper(final HttpServletRequest servletRequest, final HttpServletResponse servletResponse) {
        this.request = servletRequest;
        this.response = servletResponse;
    }

    /**
     * Gets the href.
     *
     * @return the href
     *
     * @see org.displaytag.util.RequestHelper#getHref()
     */
    @Override
    public Href getHref() {
        final String requestURI = this.request.getRequestURI();
        // call encodeURL to preserve session id when cookies are disabled
        final Href href = new DefaultHref(this.response.encodeURL(requestURI));
        href.setParameterMap(this.getParameterMap());
        return href;
    }

    /**
     * Gets the parameter.
     *
     * @param key
     *            the key
     *
     * @return the parameter
     *
     * @see org.displaytag.util.RequestHelper#getParameter(java.lang.String)
     */
    @Override
    public String getParameter(final String key) {
        // actually simply return the parameter, this behaviour could be changed
        return this.request.getParameter(key);
    }

    /**
     * Gets the int parameter.
     *
     * @param key
     *            the key
     *
     * @return the int parameter
     *
     * @see org.displaytag.util.RequestHelper#getIntParameter(java.lang.String)
     */
    @Override
    public Integer getIntParameter(final String key) {
        final String value = this.request.getParameter(key);

        if (value != null) {
            try {
                return Integer.valueOf(value);
            } catch (final NumberFormatException e) {
                // It's ok to ignore, simply return null
                DefaultRequestHelper.log.debug(Messages.getString("RequestHelper.invalidparameter", //$NON-NLS-1$
                        new Object[] { key, value }));
            }
        }

        return null;
    }

    /**
     * Gets the parameter map.
     *
     * @return the parameter map
     *
     * @see org.displaytag.util.RequestHelper#getParameterMap()
     */
    @Override
    public Map<String, String[]> getParameterMap() {

        final Map<String, String[]> map = new HashMap<>();

        final Enumeration<String> parametersName = this.request.getParameterNames();

        while (parametersName.hasMoreElements()) {
            // ... get the value
            final String paramName = (String) parametersName.nextElement();

            this.request.getParameter(paramName);
            // put key/value in the map
            final String[] originalValues = ObjectUtils.getIfNull(this.request.getParameterValues(paramName),
                    new String[0]);
            final String[] values = new String[originalValues.length];

            for (int i = 0; i < values.length; i++) {
                values[i] = URLEncoder.encode(StringUtils.defaultString(originalValues[i]),
                        Charset.forName(Objects.toString(this.response.getCharacterEncoding(), "UTF8"))); //$NON-NLS-1$
            }
            map.put(paramName, values);

        }

        // return the Map
        return map;
    }

}
