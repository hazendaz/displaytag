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
 * RequestHelperFactory interface.
 * <p>
 * Users can specify a custom RequestHelperFactory implementation in <code>displaytag.properties</code>.
 * </p>
 * A custom RequestHelperFactory can return a different RequestHelper implementation (the
 * {@link DefaultRequestHelperFactory}returns instaces of {@link DefaultRequestHelper})
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public interface RequestHelperFactory
{

    /**
     * returns a RequestHelper instance for a given request.
     * @param pageContext PageContext passed by the tag
     * @return RequestHelper instance
     */
    RequestHelper getRequestHelperInstance(PageContext pageContext);

}
