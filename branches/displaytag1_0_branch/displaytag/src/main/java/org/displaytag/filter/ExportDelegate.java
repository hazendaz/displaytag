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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.tags.TableTag;
import org.displaytag.tags.TableTagParameters;


/**
 * Actually writes out the content of the wrapped response. Used by the j2ee filter and the Spring interceptor
 * implementations.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public final class ExportDelegate
{

    /**
     * logger
     */
    private static Log log = LogFactory.getLog(ExportDelegate.class);

    /**
     * Donìt instantiate.
     */
    private ExportDelegate()
    {
        // unused
    }

    /**
     * Actually writes exported data. Extracts content from the Map stored in request with the
     * <code>TableTag.FILTER_CONTENT_OVERRIDE_BODY</code> key.
     * @param wrapper BufferedResponseWrapper implementation
     * @param response HttpServletResponse
     * @param request ServletRequest
     * @throws IOException exception thrown by response writer/outputStream
     */
    protected static void writeExport(HttpServletResponse response, ServletRequest request,
        BufferedResponseWrapper wrapper) throws IOException
    {

        if (wrapper.isOutRequested())
        {
            // data already written
            log.debug("Filter operating in unbuffered mode. Everything done, exiting");
            return;
        }

        // if you reach this point the PARAMETER_EXPORTING has been found, but the special header has never been set in
        // response (this is the signal from table tag that it is going to write exported data)
        log.debug("Filter operating in buffered mode. ");

        Map bean = (Map) request.getAttribute(TableTag.FILTER_CONTENT_OVERRIDE_BODY);

        if (log.isDebugEnabled())
        {
            log.debug(bean);
        }

        Object pageContent = bean.get(TableTagParameters.BEAN_BODY);

        if (pageContent == null)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Filter is enabled but exported content has not been found. Maybe an error occurred?");
            }

            PrintWriter out = response.getWriter();
            out.write(wrapper.getContentAsString());
            out.flush();
            return;
        }

        // clear headers
        if (!response.isCommitted())
        {
            response.reset();
        }

        String filename = (String) bean.get(TableTagParameters.BEAN_FILENAME);
        String contentType = (String) bean.get(TableTagParameters.BEAN_CONTENTTYPE);

        if (StringUtils.isNotBlank(filename))
        {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        }

        String characterEncoding = wrapper.getCharacterEncoding();
        String wrappedContentType = wrapper.getContentType();

        if (wrappedContentType != null && wrappedContentType.indexOf("charset") > -1)
        {
            // charset is already specified (see #921811)
            characterEncoding = StringUtils.substringAfter(wrappedContentType, "charset=");
        }

        if (characterEncoding != null && contentType.indexOf("charset") == -1) //$NON-NLS-1$
        {
            contentType += "; charset=" + characterEncoding; //$NON-NLS-1$
        }

        response.setContentType(contentType);

        if (pageContent instanceof String)
        {
            // text content
            if (characterEncoding != null)
            {
                response.setContentLength(((String) pageContent).getBytes(characterEncoding).length);
            }
            else
            {
                response.setContentLength(((String) pageContent).getBytes().length);
            }

            PrintWriter out = response.getWriter();
            out.write((String) pageContent);
            out.flush();
        }
        else
        {
            // dealing with binary content
            byte[] content = (byte[]) pageContent;
            response.setContentLength(content.length);
            OutputStream out = response.getOutputStream();
            out.write(content);
            out.flush();
        }
    }
}
