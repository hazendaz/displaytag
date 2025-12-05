/*
 * Copyright (C) 2002-2025 Fabrizio Giustina, the Displaytag team
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
