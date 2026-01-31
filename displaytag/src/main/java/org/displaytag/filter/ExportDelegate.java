/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
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

        if (characterEncoding != null && !Strings.CS.contains(contentType, "charset") && pageContent instanceof String) //$NON-NLS-1$
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
