package org.displaytag.util;

import org.apache.commons.lang.StringUtils;


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

    /**
     * Strips html tags from a String.
     * @param text input string containing html tags (<code>null</code> is <strong>not </strong> handled)
     * @return input message without tags
     */
    public static String stripHTMLTags(String text)
    {
        // encode quotes, this will be used as a tag attribute value
        text = StringUtils.replace(text, "\"", "&quot;");

        int startPosition = text.indexOf('<'); // encountered the first opening brace
        if (startPosition == -1)
        {
            // quick exit to avoid useless creation of a Stringbuffer
            return text;
        }

        StringBuffer returnMessage = new StringBuffer(text);

        int endPosition = text.indexOf('>'); // encountered the first closing braces
        while (startPosition != -1 && endPosition != -1)
        {
            returnMessage.delete(startPosition, endPosition + 1); // remove the tag
            startPosition = returnMessage.indexOf("<"); // look for the next opening brace
            endPosition = returnMessage.indexOf(">"); // look for the next closing brace
        }
        return returnMessage.toString();
    }

    /**
     * Abbreviates a String which can contain html tags. Html tags are not counted in String length.
     * @param str full String. <code>null</code> is handled by returning <code>null</code>
     * @param maxLength maximum number of characters (excluding tags)
     * @param byNumberOfWords if <code>true</code> maxLength will be the number of words returned, elsewhere will
     * represent the number of characters.
     * @return abbreviated String
     * @todo try to close open tag remembering open ones
     */
    public static String abbreviateHtmlString(String str, int maxLength, boolean byNumberOfWords)
    {
        if (str == null || str.length() <= maxLength)
        {
            // quick exit to avoid useless creation of a Stringbuffer
            return str;
        }

        int sz = str.length();
        StringBuffer buffer = new StringBuffer(sz);

        boolean inTag = false;
        int count = 0;
        boolean chopped = false;

        for (int i = 0; i < sz; i++)
        {
            if (count >= maxLength)
            {
                chopped = true;
                break;
            }

            char c = str.charAt(i);

            if (c == '<')
            {
                inTag = true;
            }
            else if (c == '>')
            {
                inTag = false;
            }
            else if (!inTag)
            {
                if (!byNumberOfWords || Character.isWhitespace(c))
                {
                    count++;
                }
            }
            if (inTag || (!byNumberOfWords || count < maxLength))
            {
                buffer.append(c);
            }
        }

        if (chopped)
        {
            buffer.append("...");
        }

        return buffer.toString();
    }

}