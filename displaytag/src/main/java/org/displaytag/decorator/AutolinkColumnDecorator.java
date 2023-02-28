/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.decorator;

import jakarta.servlet.jsp.PageContext;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.util.TagConstants;

/**
 * This takes the string that is passed in, and "auto-links" it, it turns email addresses into hyperlinks, and also
 * turns things that looks like URLs into hyperlinks as well.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class AutolinkColumnDecorator implements DisplaytagColumnDecorator {

    /**
     * Instance used for the "autolink" tag attribute.
     */
    public static final DisplaytagColumnDecorator INSTANCE = new AutolinkColumnDecorator();

    /**
     * "://".
     */
    private static final String URL_DELIM = "://"; //$NON-NLS-1$

    /**
     * Urls.
     */
    private static final String[] URLS_PREFIXES = //
            { "http", "https", "ftp" }; //$NON-NLS-1$

    /**
     * Decorate.
     *
     * @param columnValue
     *            the column value
     * @param pageContext
     *            the page context
     * @param media
     *            the media
     *
     * @return the object
     *
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(Object, PageContext, MediaTypeEnum)
     */
    @Override
    public Object decorate(final Object columnValue, final PageContext pageContext, final MediaTypeEnum media) {

        if (columnValue == null) {
            return null;
        }
        String work = columnValue.toString();

        int urlBegin;
        StringBuilder buffer = new StringBuilder();

        // First check for email addresses.
        while ((urlBegin = work.indexOf('@')) != -1) {
            int start = 0;
            int end = work.length() - 1;

            // scan backwards...
            for (int j = urlBegin; j >= 0; j--) {
                if (Character.isWhitespace(work.charAt(j))) {
                    start = j + 1;
                    break;
                }
            }

            // scan forwards...
            for (int j = urlBegin; j <= end; j++) {
                if (Character.isWhitespace(work.charAt(j))) {
                    end = j - 1;
                    break;
                }
            }

            final String email = work.substring(start, end + 1);

            buffer.append(work.substring(0, start)).append("<a href=\"mailto:") //$NON-NLS-1$
                    .append(email + "\">") //$NON-NLS-1$
                    .append(email).append("</a>"); //$NON-NLS-1$

            if (end == work.length()) {
                work = TagConstants.EMPTY_STRING;
            } else {
                work = work.substring(end + 1);
            }
        }

        work = buffer.toString() + work;
        buffer = new StringBuilder();

        // Now check for urls...
        while ((urlBegin = work.indexOf(AutolinkColumnDecorator.URL_DELIM)) != -1) {

            // scan backwards...
            int fullUrlBegin = urlBegin;
            final StringBuilder prefixBuffer = new StringBuilder(10);
            for (int j = fullUrlBegin - 1; j >= 0; j--) {
                if (Character.isWhitespace(work.charAt(j))) {
                    fullUrlBegin = j + 1;
                    break;
                }
                fullUrlBegin = j;
                prefixBuffer.append(work.charAt(j));
            }

            if (!ArrayUtils.contains(AutolinkColumnDecorator.URLS_PREFIXES,
                    StringUtils.reverse(prefixBuffer.toString()))) {

                buffer.append(work.substring(0, urlBegin + 3));
                work = work.substring(urlBegin + 3);
                continue;
            }

            int urlEnd = work.length();

            // scan forwards...
            for (int j = urlBegin; j < urlEnd; j++) {
                if (Character.isWhitespace(work.charAt(j))) {
                    urlEnd = j;
                    break;
                }
            }

            final String url = work.substring(fullUrlBegin, urlEnd);

            buffer.append(work.substring(0, fullUrlBegin)).append("<a href=\"")//$NON-NLS-1$
                    .append(url).append("\">")//$NON-NLS-1$
                    .append(url).append("</a>"); //$NON-NLS-1$

            if (urlEnd >= work.length()) {
                work = TagConstants.EMPTY_STRING;
            } else {
                work = work.substring(urlEnd);
            }
        }

        buffer.append(work);
        return buffer.toString();
    }

}
