package org.displaytag.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.jetspeed.portlet.PortletResponse;
import org.apache.jetspeed.portlet.PortletURI;


/**
 * RequestHelper which will work in Websphere Portal Server 4.2 (tested on 4.2.1, will probably not work in versions
 * 4.1.x). Simply overrides the standard getHref() method to return an URL generated from calling
 * portletRequest.getPortletURI().
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class WpsRequestHelper extends DefaultRequestHelper
{

    /**
     * Action name added to the portlet URI.
     */
    private static final String REFRESH_ACTION = "refresh";

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
        super((HttpServletRequest) context.getRequest());
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
        portletURI.addParameter("init", "init");

        return portletURI.toString();
    }

    /**
     * Returns the PortletURI.
     * @return PortletURI
     */
    public PortletURI getPortletURI()
    {
        PortletURI portletURI;

        // HttpResponse can be casted directly to PortletResponse in WPS 4.2.1
        PortletResponse portletResponse = (PortletResponse) this.pageContext.getResponse();

        // initialize a new PortletURI
        portletURI = portletResponse.createURI();

        return portletURI;
    }

}
