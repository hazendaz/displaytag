package org.displaytag.util;

/**
 * <p>Class with static utility methods to add html links in a String</p>
 * @author mraible
 * @version $Revision$ ($Author$)
 */
public final class LinkUtil
{

    /**
     * don't instantiate a LinkUtil
     */
    private LinkUtil()
    {
    }

    /**
     * http urls
     */
    private static final String URL_HTTP = "http://";

    /**
     * <p>This takes the string that is passed in, and "auto-links" it, it turns
     * email addresses into hyperlinks, and also turns things that looks like
     * URLs into hyperlinks as well.  The rules are currently very basic, In
     * Perl regex lingo...</p>
     * <ul>
     * <li>Email:  \b\S+\@[^\@\s]+\b</li>
     * <li>URL:    (http|https|ftp)://\S+\b</li>
     * </ul>
     * <p>I'm doing this via brute-force since I don't want to be dependent on a
     * third party regex package.</p>
     * @param pData String
     * @return String
     */
    public static String autoLink(String pData)
    {
        String lWorkString = new String(pData);
        int lIndex = -1;
        StringBuffer lBuffer = new StringBuffer();

        if (pData == null || pData.length() == 0)
        {
            return pData;
        }

        // First check for email addresses.

        while ((lIndex = lWorkString.indexOf("@")) != -1)
        {
            int lStart = 0;
            int lEnd = lWorkString.length() - 1;

            // scan backwards...
            for (int lCount = lIndex; lCount >= 0; lCount--)
            {
                if (Character.isWhitespace(lWorkString.charAt(lCount)))
                {
                    lStart = lCount + 1;
                    break;
                }
            }

            // scan forwards...
            for (int lCount = lIndex; lCount <= lEnd; lCount++)
            {
                if (Character.isWhitespace(lWorkString.charAt(lCount)))
                {
                    lEnd = lCount - 1;
                    break;
                }
            }

            String lEmail = lWorkString.substring(lStart, (lEnd - lStart + 1));

            lBuffer
                .append(lWorkString.substring(0, lStart))
                .append("<a href=\"mailto:")
                .append(lEmail + "\">")
                .append(lEmail)
                .append("</a>");

            if (lEnd == lWorkString.length())
            {
                lWorkString = "";
            }
            else
            {
                lWorkString = lWorkString.substring(lEnd + 1);
            }
        }

        lWorkString = lBuffer.toString() + lWorkString;
        lBuffer = new StringBuffer();

        // Now check for urls...

        while ((lIndex = lWorkString.indexOf(URL_HTTP)) != -1)
        {
            int lEnd = lWorkString.length() - 1;

            // scan forwards...
            for (int lCount = lIndex; lCount <= lEnd; lCount++)
            {
                if (Character.isWhitespace(lWorkString.charAt(lCount)))
                {
                    lEnd = lCount - 1;
                    break;
                }
            }

            String lUrl = lWorkString.substring(lIndex, (lEnd - lIndex + 1));

            lBuffer.append(lWorkString.substring(0, lIndex)).append("<a href=\"").append(lUrl).append("\">").append(
                lUrl).append(
                "</a>");

            if (lEnd == lWorkString.length())
            {
                lWorkString = "";
            }
            else
            {
                lWorkString = lWorkString.substring(lEnd + 1);
            }
        }

        lBuffer.append(lWorkString);
        return lBuffer.toString();
    }

}