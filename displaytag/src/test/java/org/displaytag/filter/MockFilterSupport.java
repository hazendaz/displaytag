/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simulates the behaviour of a filter using a simple servlet. The servlet must be mapped to the "*.filtered" extension;
 * request include this extension after the name of the tested jsp. Since servletunit doesn't support filter testing, we
 * are passing the request to this servlet which calls the filter and then forward the request to the given path without
 * ".filtered". <strong>Replaced by native filter support in ServletUnit 1.6. </strong>
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
     * Serial ID.
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
     * @see jakarta.servlet.http.HttpServlet#doGet(HttpServletRequest, HttpServletResponse)
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
         * @see jakarta.servlet.FilterChain#doFilter(jakarta.servlet.ServletRequest, jakarta.servlet.ServletResponse)
         */
        @Override
        public void doFilter(final ServletRequest request, final ServletResponse response)
                throws IOException, ServletException {
            String uri = ((HttpServletRequest) request).getRequestURI();
            final String requestContext = ((HttpServletRequest) request).getContextPath();

            if (StringUtils.isNotEmpty(requestContext) && uri.startsWith(requestContext)) {
                uri = uri.substring(requestContext.length());
            }

            uri = Strings.CS.replace(uri, MockFilterSupport.FILTERED_EXTENSION, "");

            if (MockFilterSupport.log.isDebugEnabled()) {
                MockFilterSupport.log.debug("Redirecting to [" + uri + "]");
            }
            final RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
            dispatcher.forward(request, response);
        }

    }

}
