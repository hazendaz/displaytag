package org.displaytag.util;

/**
 * <p>Utility methods for writing html tags</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public final class HtmlTagUtil
{

    /**
     * don't instantiate a new HtmlTagUtil
     */
    private HtmlTagUtil()
    {
    }

    /**
     * costruct a tag from a name and a collection of attributes
     * @param pTagName String tag name
     * @param pAttributes HtmlAttributeMap containing all the tag attributes
     * @return String open tag with attributes
     */
    public static String createOpenTagString(String pTagName, HtmlAttributeMap pAttributes)
    {

        StringBuffer buffer = new StringBuffer();

        buffer.append(TagConstants.TAG_OPEN).append(pTagName);

        if (pAttributes != null)
        {
            buffer.append(pAttributes.toString());
        }
        buffer.append(TagConstants.TAG_CLOSE);

        return buffer.toString();

    }

}