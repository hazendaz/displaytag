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
 * @author Eric Dalquist <a href="mailto:dalquist@gmail.com">dalquist@gmail.com</a>
 * @version $Id$
 */
public class PortletRequestHelper implements RequestHelper
{

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
     * @param pageContext Current JSP context.
     * @throws IllegalStateException If the PortletRequest or RenderResponse are not found in the PageContext.
     */
    public PortletRequestHelper(PageContext pageContext)
    {
        if (pageContext == null)
        {
            throw new IllegalArgumentException("pageContext may not be null");
        }

        this.portletRequest = (PortletRequest) pageContext.findAttribute(JAVAX_PORTLET_REQUEST);
        if (this.portletRequest == null)
        {
            throw new IllegalStateException("A PortletRequest could not be found in the PageContext for the key='"
                + JAVAX_PORTLET_REQUEST
                + "'");
        }

        this.renderResponse = (MimeResponse) pageContext.findAttribute(JAVAX_PORTLET_RESPONSE);
        if (this.portletRequest == null)
        {
            throw new IllegalStateException("A RenderResponse could not be found in the PageContext for the key='"
                + JAVAX_PORTLET_RESPONSE
                + "'");
        }
    }

    /**
     * @see org.displaytag.util.RequestHelper#getHref()
     */
    @Override
    public Href getHref()
    {
        final PortletHref href = new PortletHref(this.portletRequest, this.renderResponse);
        href.setParameterMap(this.portletRequest.getParameterMap());

        if (this.portletRequest.isSecure())
        {
            href.setRequestedSecure(true);
        }

        return href;
    }

    /**
     * @see org.displaytag.util.RequestHelper#getParameter(java.lang.String)
     */
    @Override
    public String getParameter(String key)
    {
        return this.portletRequest.getParameter(key);
    }

    /**
     * @see org.displaytag.util.RequestHelper#getIntParameter(java.lang.String)
     */
    @Override
    public Integer getIntParameter(String key)
    {
        try
        {
            return Integer.valueOf(this.getParameter(key));
        }
        catch (NumberFormatException nfe)
        {
            return null;
        }
    }

    /**
     * @see org.displaytag.util.RequestHelper#getParameterMap()
     */
    @Override
    public Map<String, String[]> getParameterMap()
    {
        return this.portletRequest.getParameterMap();
    }
}
