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

        StringBuffer lBuffer = new StringBuffer();

        lBuffer.append(TagConstants.TAG_OPEN).append(pTagName).append(pAttributes.toString()).append(
            TagConstants.TAG_CLOSE);

        return lBuffer.toString();

    }

}