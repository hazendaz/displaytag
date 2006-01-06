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

import javax.servlet.jsp.PageContext;

import org.displaytag.util.RequestHelper;
import org.displaytag.util.RequestHelperFactory;


/**
 * Generates {@link PortletRequestHelper} objects.
 * @author Eric Dalquist <a href="mailto:dalquist@gmail.com">dalquist@gmail.com</a>
 * @version $Id$
 */
public class PortletRequestHelperFactory implements RequestHelperFactory
{

    /**
     * @see org.displaytag.util.RequestHelperFactory#getRequestHelperInstance(javax.servlet.jsp.PageContext)
     */
    public RequestHelper getRequestHelperInstance(PageContext context)
    {
        return new PortletRequestHelper(context);
    }
}
