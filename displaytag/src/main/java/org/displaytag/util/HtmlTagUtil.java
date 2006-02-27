package org.displaytag.util;

/**
 * Utility methods for writing html tags.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public final class HtmlTagUtil
{

    /**
     * don't instantiate a new HtmlTagUtil.
     */
    private HtmlTagUtil()
    {
    }

    /**
     * costruct a tag from a name and a collection of attributes.
     * @param tagName String tag name
     * @param attributes HtmlAttributeMap containing all the tag attributes
     * @return String open tag with attributes
     */
    public static String createOpenTagString(String tagName, HtmlAttributeMap attributes)
    {

        StringBuffer buffer = new StringBuffer();

        buffer.append(TagConstants.TAG_OPEN).append(tagName);

        if (attributes != null)
        {
            buffer.append(attributes.toString());
        }
        buffer.append(TagConstants.TAG_CLOSE);

        return buffer.toString();

    }

}