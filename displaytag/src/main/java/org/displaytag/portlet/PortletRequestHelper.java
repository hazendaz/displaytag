/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.portlet;

import java.util.Map;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.servlet.jsp.PageContext;

import org.displaytag.util.Href;
import org.displaytag.util.RequestHelper;

/**
 * Reads parameters and generates URLs using javax.portlet APIs. The {@link javax.servlet.jsp.PageContext} passed into
 * the constructor must provide the {@link javax.portlet.PortletRequest} via an attribute named
 * {@link #JAVAX_PORTLET_REQUEST} and {@link javax.portlet.RenderResponse} via an attribute named
 * {@link #JAVAX_PORTLET_RESPONSE}. <br>
 * <br>
 * If the pluto portlet container is being used these objects should be setup appropriatly already.
 *
 * @author Eric Dalquist <a href="mailto:dalquist@gmail.com">dalquist@gmail.com</a>
 *
 * @version $Id$
 */
public class PortletRequestHelper implements RequestHelper {

    /** The Constant JAVAX_PORTLET_RESPONSE. */
    public static final String JAVAX_PORTLET_RESPONSE = "javax.portlet.response";

    /** The Constant JAVAX_PORTLET_REQUEST. */
    public static final String JAVAX_PORTLET_REQUEST = "javax.portlet.request";

    /** The portlet request. */
    private final PortletRequest portletRequest;

    /** The render response. */
    private final MimeResponse renderResponse;

    /**
     * Creates a new request helper for the specified PageContext. Retrieves the PortletRequest and RenderResponse from
     * the PageContext.
     *
     * @param pageContext
     *            Current JSP context.
     *
     * @throws IllegalStateException
     *             If the PortletRequest or RenderResponse are not found in the PageContext.
     */
    public PortletRequestHelper(final PageContext pageContext) {
        if (pageContext == null) {
            throw new IllegalArgumentException("pageContext may not be null");
        }

        this.portletRequest = (PortletRequest) pageContext.findAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST);
        if (this.portletRequest == null) {
            throw new IllegalStateException("A PortletRequest could not be found in the PageContext for the key='"
                    + PortletRequestHelper.JAVAX_PORTLET_REQUEST + "'");
        }

        this.renderResponse = (MimeResponse) pageContext.findAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE);
        if (this.portletRequest == null) {
            throw new IllegalStateException("A RenderResponse could not be found in the PageContext for the key='"
                    + PortletRequestHelper.JAVAX_PORTLET_RESPONSE + "'");
        }
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
        final PortletHref href = new PortletHref(this.portletRequest, this.renderResponse);
        href.setParameterMap(this.portletRequest.getParameterMap());

        if (this.portletRequest.isSecure()) {
            href.setRequestedSecure(true);
        }

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
        return this.portletRequest.getParameter(key);
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
        try {
            return Integer.valueOf(this.getParameter(key));
        } catch (final NumberFormatException nfe) {
            return null;
        }
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
        return this.portletRequest.getParameterMap();
    }
}
