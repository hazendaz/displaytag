package org.displaytag.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;


/**
 * Default RequestHelperFactory implementation. Returns instaces of {@link DefaultRequestHelper}.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class DefaultRequestHelperFactory implements RequestHelperFactory
{

    /**
     * @see org.displaytag.util.RequestHelperFactory#getRequestHelperInstance(javax.servlet.jsp.PageContext)
     */
    public RequestHelper getRequestHelperInstance(PageContext pageContext)
    {
        return new DefaultRequestHelper((HttpServletRequest) pageContext.getRequest());
    }

}
