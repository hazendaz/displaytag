package org.displaytag.util;

/**
 * Class with static utility methods to add html links in a String.
 * @author mraible
 * @version $Revision$ ($Author$)
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

        String workString = new String(string);
        int index = -1;
        StringBuffer buffer = new StringBuffer();

        // First check for email addresses.

        while ((index = workString.indexOf("@")) != -1)
        {
            int start = 0;
            int end = workString.length() - 1;

            // scan backwards...
            for (int j = index; j >= 0; j--)
            {
                if (Character.isWhitespace(workString.charAt(j)))
                {
                    start = j + 1;
                    break;
                }
            }

            // scan forwards...
            for (int j = index; j <= end; j++)
            {
                if (Character.isWhitespace(workString.charAt(j)))
                {
                    end = j - 1;
                    break;
                }
            }

            String email = workString.substring(start, (end - start + 1));

            buffer.append(workString.substring(0, start)).append("<a href=\"mailto:").append(email + "\">").append(
                email).append("</a>");

            if (end == workString.length())
            {
                workString = "";
            }
            else
            {
                workString = workString.substring(end + 1);
            }
        }

        workString = buffer.toString() + workString;
        buffer = new StringBuffer();

        // Now check for urls...

        while ((index = workString.indexOf(URL_HTTP)) != -1)
        {
            int end = workString.length() - 1;

            // scan forwards...
            for (int j = index; j <= end; j++)
            {
                if (Character.isWhitespace(workString.charAt(j)))
                {
                    end = j - 1;
                    break;
                }
            }

            String url = workString.substring(index, (end - index + 1));

            buffer
                .append(workString.substring(0, index))
                .append("<a href=\"")
                .append(url)
                .append("\">")
                .append(url)
                .append("</a>");

            if (end == workString.length())
            {
                workString = "";
            }
            else
            {
                workString = workString.substring(end + 1);
            }
        }

        buffer.append(workString);
        return buffer.toString();
    }

}