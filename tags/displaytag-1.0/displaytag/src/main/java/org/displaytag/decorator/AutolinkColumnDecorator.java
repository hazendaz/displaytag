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
package org.displaytag.decorator;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.util.TagConstants;


/**
 * This takes the string that is passed in, and "auto-links" it, it turns email addresses into hyperlinks, and also
 * turns things that looks like URLs into hyperlinks as well.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class AutolinkColumnDecorator implements ColumnDecorator
{

    /**
     * Instance used for the "autolink" tag attribute. Will be removed in future along with the attribute.
     */
    public static final ColumnDecorator INSTANCE = new AutolinkColumnDecorator();

    /**
     * "://".
     */
    private static final String URL_DELIM = "://"; //$NON-NLS-1$

    /**
     * Urls.
     */
    private static final String[] URLS_PREFIXES = //
    new String[]{"http", "https", "ftp"}; //$NON-NLS-1$ //$NON-NLS-1$ //$NON-NLS-1$

    /**
     * @see org.displaytag.decorator.ColumnDecorator#decorate(java.lang.Object)
     */
    public String decorate(Object columnValue)
    {
        if (columnValue == null)
        {
            return null;
        }
        String work = columnValue.toString();

        int urlBegin;
        StringBuffer buffer = new StringBuffer();

        // First check for email addresses.
        while ((urlBegin = work.indexOf('@')) != -1)
        {
            int start = 0;
            int end = work.length() - 1;

            // scan backwards...
            for (int j = urlBegin; j >= 0; j--)
            {
                if (Character.isWhitespace(work.charAt(j)))
                {
                    start = j + 1;
                    break;
                }
            }

            // scan forwards...
            for (int j = urlBegin; j <= end; j++)
            {
                if (Character.isWhitespace(work.charAt(j)))
                {
                    end = j - 1;
                    break;
                }
            }

            String email = work.substring(start, end + 1);

            buffer.append(work.substring(0, start)).append("<a href=\"mailto:") //$NON-NLS-1$
                .append(email + "\">") //$NON-NLS-1$
                .append(email).append("</a>"); //$NON-NLS-1$

            if (end == work.length())
            {
                work = TagConstants.EMPTY_STRING;
            }
            else
            {
                work = work.substring(end + 1);
            }
        }

        work = buffer.toString() + work;
        buffer = new StringBuffer();

        // Now check for urls...
        while ((urlBegin = work.indexOf(URL_DELIM)) != -1)
        {

            // scan backwards...
            int fullUrlBegin = urlBegin;
            StringBuffer prefixBuffer = new StringBuffer(10);
            for (int j = fullUrlBegin - 1; j >= 0; j--)
            {
                if (Character.isWhitespace(work.charAt(j)))
                {
                    fullUrlBegin = j + 1;
                    break;
                }
                fullUrlBegin = j;
                prefixBuffer.append(work.charAt(j));
            }

            if (!ArrayUtils.contains(URLS_PREFIXES, StringUtils.reverse(prefixBuffer.toString())))
            {

                buffer.append(work.substring(0, urlBegin + 3));
                work = work.substring(urlBegin + 3);
                continue;
            }

            int urlEnd = work.length();

            // scan forwards...
            for (int j = urlBegin; j < urlEnd; j++)
            {
                if (Character.isWhitespace(work.charAt(j)))
                {
                    urlEnd = j;
                    break;
                }
            }

            String url = work.substring(fullUrlBegin, urlEnd);

            buffer.append(work.substring(0, fullUrlBegin)).append("<a href=\"")//$NON-NLS-1$
                .append(url).append("\">")//$NON-NLS-1$
                .append(url).append("</a>"); //$NON-NLS-1$

            if (urlEnd >= work.length())
            {
                work = TagConstants.EMPTY_STRING;
            }
            else
            {
                work = work.substring(urlEnd);
            }
        }

        buffer.append(work);
        return buffer.toString();
    }

}
