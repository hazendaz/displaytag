package org.displaytag.util;

/**
 * <p>Anchor object used to output an html link (an &lt;a&gt; tag)</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class Anchor
{

    /**
     * Href object to be written in the "href" html attribute
     */
    private Href mHref;

    /**
     * link body text
     */
    private String mLinkText;

    /**
     * HashMap containing all the html attributes
     */
    private HtmlAttributeMap mAttributeMap = new HtmlAttributeMap();

    /**
     * Creates a new anchor with the supplied body text
     * @param pLinkText String body text
     */
    public Anchor(String pLinkText)
    {
        mLinkText = pLinkText;
    }

    /**
     * Creates a new Anchor whit the supplied Href
     * @param pHref Href
     */
    public Anchor(Href pHref)
    {
        mHref = pHref;
    }

    /**
     * Creates a new Anchor whit the supplied Href and body text
     * @param pHref Href
     * @param pLinkText String link body
     */
    public Anchor(Href pHref, String pLinkText)
    {
        mHref = pHref;
        mLinkText = pLinkText;
    }

    /**
     * setter the anchor Href
     * @param pHref Href
     */
    public void setHref(Href pHref)
    {
        mHref = pHref;
    }

    /**
     * setter for the link body text
     * @param pLinkText String
     */
    public void setText(String pLinkText)
    {
        mLinkText = pLinkText;
    }

    /**
     * add a "class" attribute to the html link
     * @param pClass String
     */
    public void setClass(String pClass)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_CLASS, pClass);
    }

    /**
     * add a "style" attribute to the html link
     * @param pStyle String
     */
    public void setStyle(String pStyle)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_STYLE, pStyle);
    }

    /**
     * add a "title" attribute to the html link
     * @param pTitle String
     */
    public void setTitle(String pTitle)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_TITLE, pTitle);
    }

    /**
     * returns the href attribute, surrounded by quotes and prefixed with " href="
     * @return String <code> href ="<em>href value</em>"</code> or an emty String if Href is null
     */
    private String getHrefString()
    {
        if (mHref == null)
        {
            return "";
        }
        return " href=\"" + mHref.toString() + "\"";
    }

    /**
     * returns the &lt;a&gt; tag, with rendered href and any ther setted html attribute
     * @return String
     */
    public String getOpenTag()
    {

        // shortcut for links with no attributes
        if (mAttributeMap.size() == 0)
        {
            return TagConstants.TAG_OPEN + TagConstants.TAGNAME_ANCHOR + getHrefString() + TagConstants.TAG_CLOSE;
        }

        // append all attributes
        StringBuffer lBuffer = new StringBuffer();

        lBuffer.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_ANCHOR).append(getHrefString());

        lBuffer.append(mAttributeMap);

        lBuffer.append(TagConstants.TAG_CLOSE);

        return lBuffer.toString();
    }

    /**
     * returns the &lt;/a&gt; tag
     * @return String
     */
    public String getCloseTag()
    {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_ANCHOR + TagConstants.TAG_CLOSE;
    }

    /**
     * returns the full &lt;a href=""&gt;body&lt;/a&gt;
     * @return String html link
     */
    public String toString()
    {
        return getOpenTag() + mLinkText + getCloseTag();
    }

}