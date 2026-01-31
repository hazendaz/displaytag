/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import jakarta.servlet.jsp.PageContext;

/**
 * RequestHelperFactory interface.
 * <p>
 * Users can specify a custom RequestHelperFactory implementation in <code>displaytag.properties</code>.
 * <p>
 * A custom RequestHelperFactory can return a different RequestHelper implementation (the
 * {@link DefaultRequestHelperFactory}returns instances of {@link DefaultRequestHelper})
 */
public interface RequestHelperFactory {

    /**
     * returns a RequestHelper instance for a given request.
     *
     * @param pageContext
     *            PageContext passed by the tag
     *
     * @return RequestHelper instance
     */
    RequestHelper getRequestHelperInstance(PageContext pageContext);

}
