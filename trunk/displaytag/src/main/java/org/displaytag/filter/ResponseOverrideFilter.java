package org.displaytag.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
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
 * @since 1.0
 */
public class ResponseOverrideFilter implements Filter
{

    /**
     * Logger.
     */
    private Log log;

    /**
     * {@inheritDoc}
     */
    public void init(FilterConfig filterConfig)
    {
        log = LogFactory.getLog(ResponseOverrideFilter.class);
    }

    /**
     * {@inheritDoc}
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException
    {

        if (servletRequest.getParameter(TableTagParameters.PARAMETER_EXPORTING) == null)
        {
            if (log.isInfoEnabled())
            {
                log.info(Messages.getString("ResponseOverrideFilter.parameternotfound")); //$NON-NLS-1$
            }
            //don't filter!
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        BufferedResponseWrapper wrapper = new BufferedResponseWrapper((HttpServletResponse) servletResponse);

        // In a portlet environment, you do not have direct access to the actual request object, so any attribute that
        // is added will not be visible outside of your portlet. So instead, users MUST append to the StringBuffer, so
        // that the portlets do not have to bind a new attribute to the request.
        request.setAttribute(TableTag.FILTER_CONTENT_OVERRIDE_BODY, new StringBuffer(8096));
        request.setAttribute(TableTag.FILTER_CONTENT_OVERRIDE_TYPE, new StringBuffer(80));
        request.setAttribute(TableTag.FILTER_CONTENT_OVERRIDE_FILENAME, new StringBuffer(80));

        filterChain.doFilter(request, wrapper);

        String pageContent;
        String contentType;
        StringBuffer buf = (StringBuffer) request.getAttribute(TableTag.FILTER_CONTENT_OVERRIDE_BODY);
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String characterEncoding = resp.getCharacterEncoding();
        if (characterEncoding != null)
        {
            characterEncoding = "; charset=" + characterEncoding; //$NON-NLS-1$
        }
        if (buf != null && buf.length() > 0)
        {
            pageContent = buf.toString();
            contentType = ObjectUtils.toString(request.getAttribute(TableTag.FILTER_CONTENT_OVERRIDE_TYPE));
            if (log.isDebugEnabled())
            {
                log.debug(Messages.getString("ResponseOverrideFilter.overridingoutput", //$NON-NLS-1$
                    new Object[]{contentType}));
            }

            StringBuffer filename = (StringBuffer) request.getAttribute(TableTag.FILTER_CONTENT_OVERRIDE_FILENAME);

            if (filename != null && StringUtils.isNotEmpty(filename.toString()))
            {
                if (log.isDebugEnabled())
                {
                    log.debug(Messages.getString("ResponseOverrideFilter.filenameis", //$NON-NLS-1$
                        new Object[]{filename}));
                }
                resp.setHeader("Content-Disposition", //$NON-NLS-1$
                    "attachment; filename=\"" + filename + "\""); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        else
        {
            log.debug(Messages.getString("ResponseOverrideFilter.notoverriding")); //$NON-NLS-1$
            pageContent = wrapper.toString();
            contentType = wrapper.getContentType();
        }

        if (contentType != null)
        {
            if (contentType.indexOf("charset") > -1) //$NON-NLS-1$
            {
                // charset is already specified (see #921811)
                servletResponse.setContentType(contentType);
            }
            else
            {
                servletResponse.setContentType(contentType + StringUtils.defaultString(characterEncoding));
            }
        }
        servletResponse.setContentLength(pageContent.length());

        PrintWriter out = servletResponse.getWriter();
        out.write(pageContent);
        out.close();
    }

    /**
     * {@inheritDoc}
     */
    public void destroy()
    {
        // nothing to destroy
    }
}