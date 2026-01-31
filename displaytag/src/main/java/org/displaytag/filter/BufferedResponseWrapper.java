/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.filter;

import jakarta.servlet.http.HttpServletResponse;

/**
 * Buffers the response; will not send anything directly through to the actual response. Note that this blocks the
 * content-type from being set, you must set it manually in the response.
 */
public interface BufferedResponseWrapper extends HttpServletResponse {

    /**
     * Headers which cause problems during file download.
     */
    String[] FILTERED_HEADERS = { "cache-control", "expires", "pragma" };

    /**
     * Return <code>true</code> if ServletOutputStream has been requested from Table tag.
     *
     * @return <code>true</code> if ServletOutputStream has been requested
     */
    boolean isOutRequested();

    /**
     * If the app server sets the content-type of the response, it is sticky and you will not be able to change it.
     * Therefore it is intercepted here.
     *
     * @return the ContentType that was most recently set
     */
    @Override
    String getContentType();

    /**
     * Returns the String representation of the content written to the response.
     *
     * @return the content of the response
     */
    String getContentAsString();

}
