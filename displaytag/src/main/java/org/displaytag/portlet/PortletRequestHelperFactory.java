/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.portlet;

import jakarta.servlet.jsp.PageContext;

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
     * @see org.displaytag.util.RequestHelperFactory#getRequestHelperInstance(jakarta.servlet.jsp.PageContext)
     */
    @Override
    public RequestHelper getRequestHelperInstance(final PageContext context) {
        return new PortletRequestHelper(context);
    }
}
