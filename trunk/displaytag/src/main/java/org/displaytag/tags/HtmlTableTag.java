package org.displaytag.tags;

import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.TagConstants;

/**
 * Base tag which provides setters for all the standard html attributes.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public abstract class HtmlTableTag extends TemplateTag
{

    /**
     * Map containing all the standard html attributes.
     */
    private HtmlAttributeMap attributeMap = new HtmlAttributeMap();

    /**
     * setter for the "width" html attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setWidth(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_WIDTH, value);
    }

    /**
     * setter for the "border" html attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setBorder(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_BORDER, value);
    }

    /**
     * setter for the "cellspacing" html attribute.
     * @param value attribute value
     */
    public void setCellspacing(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CELLSPACING, value);
    }

    /**
     * setter for the "cellpadding" html attribute.
     * @param value attribute value
     */
    public void setCellpadding(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CELLPADDING, value);
    }

    /**
     * setter for the "align" html attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setAlign(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_ALIGN, value);
    }

    /**
     * setter for the "background" html attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setBackground(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_BACKGROUND, value);
    }

    /**
     * setter for the "bgcolor" html attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setBgcolor(String value)
    {

        this.attributeMap.put(TagConstants.ATTRIBUTE_BGCOLOR, value);
    }

    /**
     * setter for the "frame" html attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setFrame(String value)
    {

        this.attributeMap.put(TagConstants.ATTRIBUTE_FRAME, value);
    }

    /**
     * setter for the "height" html attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setHeight(String value)
    {

        this.attributeMap.put(TagConstants.ATTRIBUTE_HEIGHT, value);
    }

    /**
     * setter for the "hspace" html attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setHspace(String value)
    {

        this.attributeMap.put(TagConstants.ATTRIBUTE_HSPACE, value);
    }

    /**
     * setter for the "rules" html attribute.
     * @param value attribute value
     */
    public void setRules(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_RULES, value);
    }

    /**
     * setter for the "style" html attribute.
     * @param value attribute value
     */
    public void setStyle(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_STYLE, value);
    }

    /**
     * setter for the "summary" html attribute.
     * @param value attribute value
     */
    public void setSummary(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_SUMMARY, value);
    }

    /**
     * setter for the "vspace" html attribute.
     * @param value attribute value
     * @deprecated use css in "class" or "style"
     */
    public void setVspace(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_VSPACE, value);
    }

    /**
     * setter for the "class" html attribute.
     * @param value attribute value
     * @deprecated use setClass()
     */
    public void setStyleClass(String value)
    {
        setClass(value);
    }

    /**
     * setter for the "class" html attribute.
     * @param value attribute value
     */
    public void setClass(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, value);
    }

    /**
     * setter for the "id" html attribute.
     * @param value attribute value
     */
    public void setId(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_ID, value);
        super.setId(value);
    }

    /**
     * create the open tag containing all the attributes.
     * @return open tag string: <code>%lt;table attribute="value" ... ></code>
     */
    public String getOpenTag()
    {

        if (this.attributeMap.size() == 0)
        {
            return TagConstants.TAG_OPEN + TagConstants.TABLE_TAG_NAME + TagConstants.TAG_CLOSE;
        }

        StringBuffer buffer = new StringBuffer();

        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TABLE_TAG_NAME);

        buffer.append(this.attributeMap);

        buffer.append(TagConstants.TAG_CLOSE);

        return buffer.toString();
    }

    /**
     * create the closing tag.
     * @return <code>%lt;/table></code>
     */
    public String getCloseTag()
    {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TABLE_TAG_NAME + TagConstants.TAG_CLOSE;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        this.attributeMap.clear();
        super.release();
    }

}
