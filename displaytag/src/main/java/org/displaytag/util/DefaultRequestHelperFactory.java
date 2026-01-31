/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * Default RequestHelperFactory implementation. Returns instaces of {@link DefaultRequestHelper}.
 */
public class DefaultRequestHelperFactory implements RequestHelperFactory {

    /**
     * Gets the request helper instance.
     *
     * @param pageContext
     *            the page context
     *
     * @return the request helper instance
     *
     * @see org.displaytag.util.RequestHelperFactory#getRequestHelperInstance(javax.servlet.jsp.PageContext)
     */
    @Override
    public RequestHelper getRequestHelperInstance(final PageContext pageContext) {
        return new DefaultRequestHelper((HttpServletRequest) pageContext.getRequest(),
                (HttpServletResponse) pageContext.getResponse());
    }

}
