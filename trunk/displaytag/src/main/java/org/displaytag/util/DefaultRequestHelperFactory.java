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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;


/**
 * Default RequestHelperFactory implementation. Returns instaces of {@link DefaultRequestHelper}.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DefaultRequestHelperFactory implements RequestHelperFactory
{

    /**
     * @see org.displaytag.util.RequestHelperFactory#getRequestHelperInstance(javax.servlet.jsp.PageContext)
     */
    public RequestHelper getRequestHelperInstance(PageContext pageContext)
    {
        return new DefaultRequestHelper(
            (HttpServletRequest) pageContext.getRequest(),
            (HttpServletResponse) pageContext.getResponse());
    }

}