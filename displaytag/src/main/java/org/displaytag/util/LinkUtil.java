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
    private static final String URL_HTTP = "http://";

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

        int index = -1;
        StringBuffer buffer = new StringBuffer();

        // First check for email addresses.

        while ((index = string.indexOf("@")) != -1)
        {
            int start = 0;
            int end = string.length() - 1;

            // scan backwards...
            for (int j = index; j >= 0; j--)
            {
                if (Character.isWhitespace(string.charAt(j)))
                {
                    start = j + 1;
                    break;
                }
            }

            // scan forwards...
            for (int j = index; j <= end; j++)
            {
                if (Character.isWhitespace(string.charAt(j)))
                {
                    end = j - 1;
                    break;
                }
            }

            String email = string.substring(start, end + 1);

            buffer
                .append(string.substring(0, start))
                .append("<a href=\"mailto:")
                .append(email + "\">")
                .append(email)
                .append("</a>");

            if (end == string.length())
            {
                string = "";
            }
            else
            {
                string = string.substring(end + 1);
            }
        }

        string = buffer.toString() + string;
        buffer = new StringBuffer();

        // Now check for urls...
        while ((index = string.indexOf(URL_HTTP)) != -1)
        {
            int end = string.length() - 1;

            // scan forwards...
            for (int j = index; j <= end; j++)
            {
                if (Character.isWhitespace(string.charAt(j)))
                {
                    end = j - 1;
                    break;
                }
            }

            String url = string.substring(index, end + 1);

            buffer
                .append(string.substring(0, index))
                .append("<a href=\"")
                .append(url)
                .append("\">")
                .append(url)
                .append("</a>");

            if (end == string.length())
            {
                string = "";
            }
            else
            {
                string = string.substring(end + 1);
            }
        }

        buffer.append(string);
        return buffer.toString();
    }

}