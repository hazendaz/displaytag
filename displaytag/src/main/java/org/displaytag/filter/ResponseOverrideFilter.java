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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Allow the author of an included JSP page to reset the content type to something else (like a binary stream), and then
 * write the new info back as the exclusive response, clearing the buffers of all previously added content.
 * <p>The page
 * author should write to, but not replace, the StringBuffer objects placed into request scope at CONTENT_OVERRIDE_BODY
 * and CONTENT_OVERRIDE_TYPE.</p>
 * <p/>
 * <p>This filter allows TableTag users to perform exports from pages that are run as includes, such as from Struts or
 * a jsp:include.  If that is your intention, just add this Filter to your web.xml and map it to the appropriate
 * requests, using something like: </p>
 * <p/>
 * <pre>
 * &lt;filter&gt;
 *     &lt;filter-name&gt;ResponseOverrideFilter&lt;/filter-name&gt;
 *     &lt;filter-class&gt;org.displaytag.filter.ResponseOverrideFilter&lt;/filter-class&gt;
 * &lt;/filter&gt;
 * 
 * &lt;filter-mapping&gt;
 *     &lt;filter-name&gt;ResponseOverrideFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;*.do&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * 
 * &lt;filter-mapping&gt;
 *     &lt;filter-name&gt;ResponseOverrideFilter&lt;/filter-name&gt;
 *     &lt;url-pattern&gt;*.jsp&lt;/url-pattern&gt;
 * &lt;/filter-mapping&gt;
 * </pre>
 * @author rapruitt
 * @version $Revision$ ($Author$)
 * @since 1.0
 */
public class ResponseOverrideFilter implements Filter
{
    /**
     * Logger.
     */
    private Log logger;
    /**
     * If this buffer has been appended to at all, the contents of the buffer will be served as the sole output of the
     * request. Request variable.
     */
    public static final String CONTENT_OVERRIDE_BODY =
        ResponseOverrideFilter.class.getName() + "CONTENT_OVERWRIDE_BODY";
    /** If the request content is overriden, you must also set the content type appropriately. Request variable. */
    public static final String CONTENT_OVERRIDE_TYPE =
        ResponseOverrideFilter.class.getName() + "CONTENT_OVERWRIDE_TYPE";
    /**
    * {@inheritDoc}
    */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        logger = LogFactory.getLog(ResponseOverrideFilter.class);
    }

    /**
    * {@inheritDoc}
    */
    public void doFilter(ServletRequest srequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) srequest;

        BufferedResponseWrapper wrapper = new BufferedResponseWrapper((HttpServletResponse) servletResponse);
        // In a portlet environment, you do not have direct access to the actual request object, so any attributes that
        // are added will not be visible outside of your portlet.  So instead, users MUST append to the StringBuffer, so
        // that the portlets do not have to bind a new attribute to the request.
        request.setAttribute(CONTENT_OVERRIDE_BODY, new StringBuffer(8096));
        request.setAttribute(CONTENT_OVERRIDE_TYPE, new StringBuffer(80));

        filterChain.doFilter(request, wrapper);

        String pageContent;
        String contentType;
        StringBuffer buf = (StringBuffer) request.getAttribute(CONTENT_OVERRIDE_BODY);
        if (buf != null && buf.length() > 0)
        {
            pageContent = buf.toString();
            contentType = "" + request.getAttribute(CONTENT_OVERRIDE_TYPE);
            logger.debug("Overriding output, writing new output with content type " + contentType);
        }
        else
        {
            logger.debug("NOT overriding input. ");
            pageContent = wrapper.toString();
            contentType = wrapper.getContentType();
        }
        servletResponse.setContentType(contentType);
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
    }
}
