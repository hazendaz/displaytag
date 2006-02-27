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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.Messages;
import org.displaytag.tags.TableTag;
import org.displaytag.tags.TableTagParameters;


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
 *  &lt;filter>
 *      &lt;filter-name>ResponseOverrideFilter&lt;/filter-name>
 *      &lt;filter-class>org.displaytag.filter.ResponseOverrideFilter&lt;/filter-class>
 *  &lt;/filter>
 *  &lt;filter-mapping>
 *      &lt;filter-name>ResponseOverrideFilter&lt;/filter-name>
 *      &lt;url-pattern>*.do&lt;/url-pattern>
 *  &lt;/filter-mapping>
 *  &lt;filter-mapping>
 *      &lt;filter-name>ResponseOverrideFilter&lt;/filter-name>
 *      &lt;url-pattern>*.jsp&lt;/url-pattern>
 *  &lt;/filter-mapping>
 * </pre>
 * 
 * <p>
 * By default the filter buffers all the export content before writing it out. You can set an optional parameter
 * <code>buffer</code> to <code>false</code> to make the filter write directly to the output stream. This could be
 * faster and uses less memory, but the content length will not be set.
 * </p>
 * 
 * <pre>
 *  &lt;filter>
 *      &lt;filter-name>ResponseOverrideFilter&lt;/filter-name>
 *      &lt;filter-class>org.displaytag.filter.ResponseOverrideFilter&lt;/filter-class>
 *      &lt;init-param>
 *          &lt;param-name>buffer&lt;/param-name>
 *          &lt;param-value>false&lt;/param-value>
 *      &lt;/init-param>
 *  &lt;/filter>
 *  </pre>
 * 
 * @author rapruitt
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ResponseOverrideFilter implements Filter
{

    /**
     * Logger.
     */
    private Log log;

    /**
     * Force response buffering. Enabled by default.
     */
    private boolean buffer = true;

    /**
     * {@inheritDoc}
     */
    public void init(FilterConfig filterConfig)
    {
        log = LogFactory.getLog(ResponseOverrideFilter.class);
        String bufferParam = filterConfig.getInitParameter("buffer");
        if (log.isDebugEnabled())
        {
            log.debug("bufferParam=" + bufferParam);
        }
        buffer = bufferParam == null || StringUtils.equalsIgnoreCase("true", bufferParam);

        log.info("Filter initialized. Response buffering is " + (buffer ? "enabled" : "disabled"));
    }

    /**
     * {@inheritDoc}
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException
    {

        if (servletRequest.getParameter(TableTagParameters.PARAMETER_EXPORTING) == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug(Messages.getString("ResponseOverrideFilter.parameternotfound")); //$NON-NLS-1$
            }
            // don't filter!
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        BufferedResponseWrapper wrapper = new BufferedResponseWrapper13Impl((HttpServletResponse) servletResponse);

        Map contentBean = new HashMap(4);
        if (buffer)
        {
            contentBean.put(TableTagParameters.BEAN_BUFFER, Boolean.TRUE);
        }
        request.setAttribute(TableTag.FILTER_CONTENT_OVERRIDE_BODY, contentBean);

        filterChain.doFilter(request, wrapper);

        ExportDelegate.writeExport((HttpServletResponse) servletResponse, servletRequest, wrapper);
    }

    /**
     * {@inheritDoc}
     */
    public void destroy()
    {
        // nothing to destroy
    }
}