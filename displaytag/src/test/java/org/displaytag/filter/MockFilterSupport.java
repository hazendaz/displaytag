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
package org.displaytag.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simulates the behaviour of a filter using a simple servlet. The servlet must be mapped to the "*.filtered" extension;
 * request include this extension after the name of the tested jsp. Since servletunit doesn't support filter testing, we
 * are passing the request to this servlet which calls the filter and then forward the request to the given path without
 * ".filtered". <strong>Replaced by native filter support in ServletUnit 1.6. </strong>
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class MockFilterSupport extends HttpServlet {

    /**
     * extension mapped to this servlet.
     */
    public static final String FILTERED_EXTENSION = ".filtered";

    /**
     * logger.
     */
    protected static Logger log = LoggerFactory.getLogger(MockFilterSupport.class);

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Do get.
     *
     * @param request
     *            the request
     * @param response
     *            the response
     *
     * @throws ServletException
     *             the servlet exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     *
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        MockFilterSupport.log.debug("Mock servlet called, simulating filter");
        final Filter filter = new ResponseOverrideFilter();
        filter.init(null);
        filter.doFilter(request, response, new MockFilterChain());
    }

    /**
     * Simple FilterChain used to test Filters.
     */
    public static class MockFilterChain implements FilterChain {

        /**
         * Do filter.
         *
         * @param request
         *            the request
         * @param response
         *            the response
         *
         * @throws IOException
         *             Signals that an I/O exception has occurred.
         * @throws ServletException
         *             the servlet exception
         *
         * @see javax.servlet.FilterChain#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
         */
        @Override
        public void doFilter(final ServletRequest request, final ServletResponse response)
                throws IOException, ServletException {
            String uri = ((HttpServletRequest) request).getRequestURI();
            final String requestContext = ((HttpServletRequest) request).getContextPath();

            if (StringUtils.isNotEmpty(requestContext) && uri.startsWith(requestContext)) {
                uri = uri.substring(requestContext.length());
            }

            uri = StringUtils.replace(uri, MockFilterSupport.FILTERED_EXTENSION, "");

            if (MockFilterSupport.log.isDebugEnabled()) {
                MockFilterSupport.log.debug("Redirecting to [" + uri + "]");
            }
            final RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
            dispatcher.forward(request, response);
        }

    }

}
