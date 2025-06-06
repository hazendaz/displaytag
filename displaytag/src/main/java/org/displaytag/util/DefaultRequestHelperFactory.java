/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.PageContext;

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
     * @see org.displaytag.util.RequestHelperFactory#getRequestHelperInstance(jakarta.servlet.jsp.PageContext)
     */
    @Override
    public RequestHelper getRequestHelperInstance(final PageContext pageContext) {
        return new DefaultRequestHelper((HttpServletRequest) pageContext.getRequest(),
                (HttpServletResponse) pageContext.getResponse());
    }

}
