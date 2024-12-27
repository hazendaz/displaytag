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
package org.displaytag.filter;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.tags.TableTag;
import org.displaytag.tags.TableTagParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Actually writes out the content of the wrapped response. Used by the j2ee filter and the Spring interceptor
 * implementations.
 */
public final class ExportDelegate {

    /**
     * logger.
     */
    private static Logger log = LoggerFactory.getLogger(ExportDelegate.class);

    /**
     * Don't instantiate.
     */
    private ExportDelegate() {
        // unused
    }

    /**
     * Actually writes exported data. Extracts content from the Map stored in request with the
     * <code>TableTag.FILTER_CONTENT_OVERRIDE_BODY</code> key.
     *
     * @param response
     *            HttpServletResponse
     * @param request
     *            ServletRequest
     * @param wrapper
     *            BufferedResponseWrapper implementation
     *
     * @throws IOException
     *             exception thrown by response writer/outputStream
     */
    protected static void writeExport(final HttpServletResponse response, final ServletRequest request,
            final BufferedResponseWrapper wrapper) throws IOException {

        if (wrapper.isOutRequested()) {
            // data already written
            ExportDelegate.log.debug("Filter operating in unbuffered mode. Everything done, exiting");
            return;
        }

        // if you reach this point the PARAMETER_EXPORTING has been found, but the special header has never been set in
        // response (this is the signal from table tag that it is going to write exported data)
        ExportDelegate.log.debug("Filter operating in buffered mode. ");

        final Map<String, Object> bean = (Map<String, Object>) request
                .getAttribute(TableTag.FILTER_CONTENT_OVERRIDE_BODY);

        if (ExportDelegate.log.isDebugEnabled()) {
            ExportDelegate.log.debug("{}", bean);
        }

        final Object pageContent = bean.get(TableTagParameters.BEAN_BODY);

        if (pageContent == null) {
            if (ExportDelegate.log.isDebugEnabled()) {
                ExportDelegate.log
                        .debug("Filter is enabled but exported content has not been found. Maybe an error occurred?");
            }

            response.setContentType(wrapper.getContentType());
            final PrintWriter out = response.getWriter();

            out.write(wrapper.getContentAsString());
            out.flush();
            return;
        }

        String characterEncoding = wrapper.getCharacterEncoding();
        final String wrappedContentType = wrapper.getContentType();

        // clear headers
        if (!response.isCommitted()) {
            response.reset();
        }

        final String filename = (String) bean.get(TableTagParameters.BEAN_FILENAME);
        String contentType = (String) bean.get(TableTagParameters.BEAN_CONTENTTYPE);

        if (StringUtils.isNotBlank(filename)) {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        }

        if (wrappedContentType != null && wrappedContentType.indexOf("charset") > -1) {
            // charset is already specified (see #921811)
            characterEncoding = StringUtils.substringAfter(wrappedContentType, "charset=");
        }

        if (characterEncoding != null && !StringUtils.contains(contentType, "charset") && pageContent instanceof String) //$NON-NLS-1$
        {
            contentType += "; charset=" + characterEncoding; //$NON-NLS-1$
        }

        response.setContentType(contentType);

        if (pageContent instanceof String) {
            // text content
            if (characterEncoding != null) {
                response.setContentLength(((String) pageContent).getBytes(Charset.forName(characterEncoding)).length);
            } else {
                response.setContentLength(((String) pageContent).getBytes(StandardCharsets.UTF_8).length);
            }

            final PrintWriter out = response.getWriter();
            out.write((String) pageContent);
            out.flush();
        } else {
            // dealing with binary content
            final byte[] content = (byte[]) pageContent;
            response.setContentLength(content.length);
            final OutputStream out = response.getOutputStream();
            out.write(content);
            out.flush();
        }
    }
}
