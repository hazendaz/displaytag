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
 * @author Fabrizio Giustina
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
