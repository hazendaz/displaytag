/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Strings;
import org.displaytag.Messages;
import org.displaytag.tags.TableTag;
import org.displaytag.tags.TableTagParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Allow the author of an included JSP page to reset the content type to something else (like a binary stream), and then
 * write the new info back as the exclusive response, clearing the buffers of all previously added content.
 * </p>
 * <p>
 * This filter allows TableTag users to perform exports from pages that are run as includes, such as from Struts or a
 * jsp:include. If that is your intention, just add this Filter to your web.xml and map it to the appropriate requests,
 * using something like:
 * </p>
 *
 * <pre>
 *  &lt;filter&gt;
 *      &lt;filter-name&gt;ResponseOverrideFilter&lt;/filter-name&gt;
 *      &lt;filter-class&gt;org.displaytag.filter.ResponseOverrideFilter&lt;/filter-class&gt;
 *  &lt;/filter&gt;
 *  &lt;filter-mapping&gt;
 *      &lt;filter-name&gt;ResponseOverrideFilter&lt;/filter-name&gt;
 *      &lt;url-pattern&gt;*.do&lt;/url-pattern&gt;
 *  &lt;/filter-mapping&gt;
 *  &lt;filter-mapping&gt;
 *      &lt;filter-name&gt;ResponseOverrideFilter&lt;/filter-name&gt;
 *      &lt;url-pattern&gt;*.jsp&lt;/url-pattern&gt;
 *  &lt;/filter-mapping&gt;
 * </pre>
 * <p>
 * By default the filter buffers all the export content before writing it out. You can set an optional parameter
 * <code>buffer</code> to <code>false</code> to make the filter write directly to the output stream. This could be
 * faster and uses less memory, but the content length will not be set.
 * </p>
 *
 * <pre>
 *  &lt;filter&gt;
 *      &lt;filter-name&gt;ResponseOverrideFilter&lt;/filter-name&gt;
 *      &lt;filter-class&gt;org.displaytag.filter.ResponseOverrideFilter&lt;/filter-class&gt;
 *      &lt;init-param&gt;
 *          &lt;param-name&gt;buffer&lt;/param-name&gt;
 *          &lt;param-value&gt;false&lt;/param-value&gt;
 *      &lt;/init-param&gt;
 *  &lt;/filter&gt;
 * </pre>
 */
public class ResponseOverrideFilter implements Filter {

    /**
     * Logger.
     */
    private Logger log;

    /**
     * Force response buffering. Enabled by default.
     */
    private boolean buffer = true;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final FilterConfig filterConfig) {
        this.log = LoggerFactory.getLogger(ResponseOverrideFilter.class);
        final String bufferParam = filterConfig.getInitParameter("buffer");
        if (this.log.isDebugEnabled()) {
            this.log.debug("bufferParam={}", bufferParam);
        }
        this.buffer = bufferParam == null || Strings.CI.equals("true", bufferParam);

        this.log.info("Filter initialized. Response buffering is {}", this.buffer ? "enabled" : "disabled");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
            final FilterChain filterChain) throws IOException, ServletException {

        if (servletRequest.getParameter(TableTagParameters.PARAMETER_EXPORTING) == null) {
            if (this.log.isDebugEnabled()) {
                this.log.debug(Messages.getString("ResponseOverrideFilter.parameternotfound")); //$NON-NLS-1$
            }
            // don't filter!
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        final BufferedResponseWrapper wrapper = new BufferedResponseWrapper13Impl(
                (HttpServletResponse) servletResponse);

        final Map<String, Boolean> contentBean = new HashMap<>(4);
        if (this.buffer) {
            contentBean.put(TableTagParameters.BEAN_BUFFER, Boolean.TRUE);
        }
        request.setAttribute(TableTag.FILTER_CONTENT_OVERRIDE_BODY, contentBean);

        filterChain.doFilter(request, wrapper);

        ExportDelegate.writeExport((HttpServletResponse) servletResponse, servletRequest, wrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        // nothing to destroy
    }
}
