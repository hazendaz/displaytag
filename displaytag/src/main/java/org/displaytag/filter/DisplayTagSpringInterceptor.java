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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.Messages;
import org.displaytag.tags.TableTag;
import org.displaytag.tags.TableTagParameters;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;


/**
 * <p>
 * Allow the author of an included JSP page to reset the content type to something else (like a binary stream), and then
 * write the new info back as the exclusive response, clearing the buffers of all previously added content.
 * </p>
 * <p>
 * This interceptor allows TableTag users to perform exports from pages that are run as includes, such as from Struts or
 * a jsp:include. If that is your intention, just add this interceptor to your spring dispatcher context xml and map it
 * to the appropriate requests, using something like:
 * </p>
 * 
 * <pre>
 * &lt;bean id="urlMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
 *   &lt;property name="interceptors">
 *     &lt;list>
 *       &lt;bean class="org.displaytag.filter.DisplayTagSpringInterceptor"/>
 *     &lt;/list>
 *   &lt;/property
 * &lt;/bean>
 * </pre>
 * 
 * <p>
 * By default the interceptor buffers all the export content before writing it out. You can set an optional parameter
 * <code>buffer</code> to <code>false</code> to make the interceptor write directly to the output stream. This could
 * be faster and uses less memory, but the content length will not be set.
 * </p>
 * 
 * <pre>
 *  &lt;bean class="org.displaytag.filter.DisplayTagSpringInterceptor">
 *      &lt;property name="buffer">&lt;value>false&lt;/value>&lt;/property>
 *  &lt;/bean>
 *  </pre>
 * 
 * @author Keith Garry Boyce
 * @author rapruitt
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DisplayTagSpringInterceptor implements HandlerInterceptor
{

    /**
     * Logger.
     */
    static Log log = LogFactory.getLog(DisplayTagSpringInterceptor.class);

    /**
     * Force response buffering. Enabled by default.
     */
    private boolean buffer = true;

    /**
     * Sets the buffer state.
     * @param bufferingEnabled it <code>true</code> buffering will be used
     */
    public void setBuffer(boolean bufferingEnabled)
    {
        this.buffer = bufferingEnabled;
    }

    /**
     * @see HandlerInterceptor#preHandle(HttpServletRequest,HttpServletResponse, Object)
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {

        if (request.getParameter(TableTagParameters.PARAMETER_EXPORTING) == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug(Messages.getString("ResponseOverrideFilter.parameternotfound")); //$NON-NLS-1$
            }
            // don't intercept!
            return true;
        }

        BufferedResponseWrapper wrapper = new BufferedResponseWrapper12Impl(response);

        Map contentBean = new HashMap(4);
        if (buffer)
        {
            contentBean.put(TableTagParameters.BEAN_BUFFER, Boolean.TRUE);
        }
        request.setAttribute(TableTag.FILTER_CONTENT_OVERRIDE_BODY, contentBean);

        if (log.isDebugEnabled())
        {
            log.debug("handler is " + handler);
        }

        HandlerAdapter handlerAdaptor = new SimpleControllerHandlerAdapter();
        handlerAdaptor.handle(request, wrapper, handler);

        ExportDelegate.writeExport(response, request, wrapper);

        return false;
    }

    /**
     * @see HandlerInterceptor#postHandle(HttpServletRequest,HttpServletResponse, Object, ModelAndView)
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj,
        ModelAndView modelAndView) throws Exception
    {
        // Nothing to do
    }

    /**
     * @see HandlerInterceptor#afterCompletion(HttpServletRequest,HttpServletResponse, Object, Exception)
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj,
        Exception exception) throws Exception
    {
        // Nothing to do
    }

}
