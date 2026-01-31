/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.portlet;

import javax.servlet.jsp.PageContext;

import org.displaytag.util.RequestHelper;
import org.displaytag.util.RequestHelperFactory;

/**
 * Generates {@link PortletRequestHelper} objects.
 *
 * @author Eric Dalquist <a href="mailto:dalquist@gmail.com">dalquist@gmail.com</a>
 *
 * @version $Id$
 */
public class PortletRequestHelperFactory implements RequestHelperFactory {

    /**
     * Gets the request helper instance.
     *
     * @param context
     *            the context
     *
     * @return the request helper instance
     *
     * @see org.displaytag.util.RequestHelperFactory#getRequestHelperInstance(javax.servlet.jsp.PageContext)
     */
    @Override
    public RequestHelper getRequestHelperInstance(final PageContext context) {
        return new PortletRequestHelper(context);
    }
}
