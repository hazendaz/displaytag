package org.displaytag.util;

import javax.servlet.jsp.PageContext;


/**
 * RequestHelperFactory implementation that will work in Websphere Portal Server. (Tested on version 4.2).
 * <p>
 * In WPS links with "standard" parameters are handled, but if you turn on Portlet caching links which don't contain a
 * PortletAction don't cause the cache to be refreshed (= the click is ignored).
 * </p>
 * <p>
 * This factory implementation returns a standard {@link WpsRequestHelper}instance.
 * </p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class WpsRequestHelperFactory implements RequestHelperFactory
{

    /**
     * @see org.displaytag.util.RequestHelperFactory#getRequestHelperInstance(javax.servlet.jsp.PageContext)
     */
    public RequestHelper getRequestHelperInstance(PageContext pageContext)
    {
        return new WpsRequestHelper(pageContext);
    }

}
