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
     * http urls.
     */
    private static final String URL_HTTP = "http://"; //$NON-NLS-1$

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

        int index;
        StringBuffer buffer = new StringBuffer();

        // First check for email addresses.
        while ((index = work.indexOf('@')) != -1)
        {
            int start = 0;
            int end = work.length() - 1;

            // scan backwards...
            for (int j = index; j >= 0; j--)
            {
                if (Character.isWhitespace(work.charAt(j)))
                {
                    start = j + 1;
                    break;
                }
            }

            // scan forwards...
            for (int j = index; j <= end; j++)
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
        while ((index = work.indexOf(URL_HTTP)) != -1)
        {
            int end = work.length() - 1;

            // scan forwards...
            for (int j = index; j <= end; j++)
            {
                if (Character.isWhitespace(work.charAt(j)))
                {
                    end = j - 1;
                    break;
                }
            }

            String url = work.substring(index, end + 1);

            buffer.append(work.substring(0, index)).append("<a href=\"")//$NON-NLS-1$
                .append(url).append("\">")//$NON-NLS-1$
                .append(url).append("</a>"); //$NON-NLS-1$

            if (end == work.length())
            {
                work = TagConstants.EMPTY_STRING;
            }
            else
            {
                work = work.substring(end + 1);
            }
        }

        buffer.append(work);
        return buffer.toString();
    }

}
