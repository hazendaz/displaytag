package org.displaytag.util;

/**
 * Class with static utility methods to add html links in a String.
 * @author mraible
 * @version $Revision $ ($Author $)
 */
public final class LinkUtil
{

    /**
     * http urls.
     */
    private static final String URL_HTTP = "http://"; //$NON-NLS-1$

    /**
     * don't instantiate a LinkUtil.
     */
    private LinkUtil()
    {
        // unused
    }

    /**
     * <p>
     * This takes the string that is passed in, and "auto-links" it, it turns email addresses into hyperlinks, and also
     * turns things that looks like URLs into hyperlinks as well. The rules are currently very basic, In Perl regex
     * lingo...
     * </p>
     * <ul>
     * <li>Email: \b\S+\@[^\@\s]+\b</li>
     * <li>URL: (http|https|ftp)://\S+\b</li>
     * </ul>
     * <p>
     * I'm doing this via brute-force since I don't want to be dependent on a third party regex package.
     * </p>
     * @param string String
     * @return String
     */
    public static String autoLink(String string)
    {
        if (string == null || string.length() == 0)
        {
            return string;
        }
        String work = string;

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