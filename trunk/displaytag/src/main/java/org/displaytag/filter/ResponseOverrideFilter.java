package org.displaytag.filter;

import java.io.IOException;

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
 * Allow the author of an included JSP page to reset the content type to something else (like a binary stream), and then
 * write the new info back as the exclusive response, clearing the buffers of all previously added content.
 * <p>
 * The page author should write to, but not replace, the StringBuffer objects placed into request scope at
 * CONTENT_OVERRIDE_BODY and CONTENT_OVERRIDE_TYPE.
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
     * Force response buffering.
     */
    private boolean buffer;

    /**
     * {@inheritDoc}
     */
    public void init(FilterConfig filterConfig)
    {
        log = LogFactory.getLog(ResponseOverrideFilter.class);
        String bufferParam = filterConfig.getInitParameter("buffer");
        buffer = StringUtils.isNotEmpty(bufferParam) && !"false".equals(bufferParam);

        log.info("Filter initialized. Forced buffering is " + (buffer ? "enabled" : "disabled"));
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
            //don't filter!
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        BufferedResponseWrapper wrapper = new BufferedResponseWrapper13Impl((HttpServletResponse) servletResponse);

        request.setAttribute(TableTag.FILTER_CONTENT_OVERRIDE_BODY, Boolean.TRUE);

        filterChain.doFilter(request, wrapper);

        ExportDelegate.writeExport(wrapper, servletResponse);
    }

    /**
     * {@inheritDoc}
     */
    public void destroy()
    {
        // nothing to destroy
    }
}