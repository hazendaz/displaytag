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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.jetspeed.portlet.PortletResponse;
import org.apache.jetspeed.portlet.PortletURI;


/**
 * RequestHelper which will work in Websphere Portal Server 4.2 (tested on 4.2.1, should work also in versions 4.x).
 * Simply overrides the standard getHref() method to return an URL generated from calling
 * portletRequest.getPortletURI(). Note you need to add the portletAPI:init tag before any displaytag tag to make this
 * working.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class WpsRequestHelper extends DefaultRequestHelper
{

    /**
     * Action name added to the portlet URI.
     */
    private static final String REFRESH_ACTION = "refresh"; //$NON-NLS-1$

    /**
     * Page context attribute containing the portlet response object.
     */
    private static final String PORTLET_RESPONSE = "portletResponse"; //$NON-NLS-1$

    /**
     * jsp page context.
     */
    private PageContext pageContext;

    /**
     * Initialize a new WpsRequestHelper.
     * @param context jsp page context
     */
    public WpsRequestHelper(PageContext context)
    {
        super((HttpServletRequest) context.getRequest(), (HttpServletResponse) context.getResponse());
        this.pageContext = context;
    }

    /**
     * @see org.displaytag.util.RequestHelper#getHref()
     */
    public Href getHref()
    {
        Href standardHref = super.getHref();

        Map parameterMap = standardHref.getParameterMap();

        Href portalHref = new Href(getRefreshUri());
        portalHref.setParameterMap(parameterMap);

        return portalHref;
    }

    /**
     * Returns the url of a refresh uri (a simple action which forces the portlet to reload).
     * @return String portletURI.toString()
     */
    private String getRefreshUri()
    {
        // get a base portlet URI from the portal without parameters
        PortletURI portletURI = getPortletURI();

        // must be unique to force refresh
        portletURI.addAction(REFRESH_ACTION + Math.random());

        // it seems you need to add at least one parameter to the uri, if you want standard parameters to be catched...
        portletURI.addParameter("init", "init"); //$NON-NLS-1$ //$NON-NLS-2$

        return portletURI.toString();
    }

    /**
     * Returns the PortletURI.
     * @return PortletURI
     */
    public PortletURI getPortletURI()
    {
        PortletResponse portletResponse = getPortletResponse();

        // initialize a new PortletURI
        PortletURI portletURI = portletResponse.createURI();

        return portletURI;
    }

    /**
     * Find the portlet response.
     * @return a PortletResponse instance
     */
    protected PortletResponse getPortletResponse()
    {

        // esiste già nel pageContext la PortletResponse?
        PortletResponse portletResponse = (PortletResponse) pageContext.getAttribute(PORTLET_RESPONSE);

        // this will allow to obtain directly the portlet request using jetspeed internal APIs
        // it's more "fail safe" than expecting the portlet response directly in the page context, but it's not
        // officially supported. To compile these lines you also need jetspeed PORTAL classes in the classpath

        // if (portletResponse == null)
        // {
        // portletResponse = (PortletResponse) ThreadAttributesManager
        // .getAttribute("org.apache.jetspeed.portletcontainer.portlet.response");
        // // sped up next calls
        // pageContext.setAttribute(PORTLET_RESPONSE, portletResponse);
        // }

        if (portletResponse == null)
        {
            throw new RuntimeWpsSupportException();
        }

        return portletResponse;
    }

    /**
     * Simple runtime exception to inform the user about the missing &lt;portletAPI:init> tag.
     * @author Fabrizio Giustina
     * @version $Revision$ ($Author$)
     */
    public static class RuntimeWpsSupportException extends RuntimeException
    {

        /**
         * D1597A17A6.
         */
        private static final long serialVersionUID = 899149338534L;

        /**
         * Portlet response couldn't be found.
         */
        public RuntimeWpsSupportException()
        {
            super("Portlet response couldn't be found. "
                + "Be sure to have the portletAPI:init tag at the beginning of the page");
        }

    }
}