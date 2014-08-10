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
package org.displaytag.portlet;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;
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

    public static final String JAVAX_PORTLET_RESPONSE = "javax.portlet.response";

    public static final String JAVAX_PORTLET_REQUEST = "javax.portlet.request";

    private final PortletRequest portletRequest;

    private final RenderResponse renderResponse;

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

        this.renderResponse = (RenderResponse) pageContext.findAttribute(JAVAX_PORTLET_RESPONSE);
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
    public String getParameter(String key)
    {
        return this.portletRequest.getParameter(key);
    }

    /**
     * @see org.displaytag.util.RequestHelper#getIntParameter(java.lang.String)
     */
    public Integer getIntParameter(String key)
    {
        try
        {
            return new Integer(this.getParameter(key));
        }
        catch (NumberFormatException nfe)
        {
            return null;
        }
    }

    /**
     * @see org.displaytag.util.RequestHelper#getParameterMap()
     */
    public Map getParameterMap()
    {
        return this.portletRequest.getParameterMap();
    }
}
