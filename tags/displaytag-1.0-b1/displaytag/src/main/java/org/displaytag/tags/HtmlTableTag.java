package org.displaytag.tags;

import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.TagConstants;

/**
 * <p>Base tag wich provides setters for all the standard html attributes</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public abstract class HtmlTableTag extends TemplateTag
{

    /**
     * Map containing all the standard html attributes
     */
    private HtmlAttributeMap mAttributeMap = new HtmlAttributeMap();

    /**
     * Method setWidth
     * @param attributeValue String
     */
    public void setWidth(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_WIDTH, attributeValue);
    }

    /**
     * Method setBorder
     * @param attributeValue String
     */
    public void setBorder(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_BORDER, attributeValue);
    }

    /**
     * Method setCellspacing
     * @param attributeValue String
     */
    public void setCellspacing(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_CELLSPACING, attributeValue);
    }

    /**
     * Method setCellpadding
     * @param attributeValue String
     */
    public void setCellpadding(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_CELLPADDING, attributeValue);
    }

    /**
     * Method setAlign
     * @param attributeValue String
     */
    public void setAlign(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_ALIGN, attributeValue);
    }

    /**
     * Method setBackground
     * @param attributeValue String
     */
    public void setBackground(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_BACKGROUND, attributeValue);
    }

    /**
     * Method setBgcolor
     * @param attributeValue String
     */
    public void setBgcolor(String attributeValue)
    {

        mAttributeMap.put(TagConstants.ATTRIBUTE_BGCOLOR, attributeValue);
    }

    /**
     * Method setFrame
     * @param attributeValue String
     */
    public void setFrame(String attributeValue)
    {

        mAttributeMap.put(TagConstants.ATTRIBUTE_FRAME, attributeValue);
    }

    /**
     * Method setHeight
     * @param attributeValue String
     */
    public void setHeight(String attributeValue)
    {

        mAttributeMap.put(TagConstants.ATTRIBUTE_HEIGHT, attributeValue);
    }

    /**
     * Method setHspace
     * @param attributeValue String
     */
    public void setHspace(String attributeValue)
    {

        mAttributeMap.put(TagConstants.ATTRIBUTE_HSPACE, attributeValue);
    }

    /**
     * Method setRules
     * @param attributeValue String
     */
    public void setRules(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_RULES, attributeValue);
    }

    /**
     * Method setRules
     * @param attributeValue String
     */
    public void setStyle(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_STYLE, attributeValue);
    }

    /**
     * Method setSummary
     * @param attributeValue String
     */
    public void setSummary(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_SUMMARY, attributeValue);
    }

    /**
     * Method setVspace
     * @param attributeValue String
     */
    public void setVspace(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_VSPACE, attributeValue);
    }

    /**
     * set the html class attribute
     * @deprecated: use setClass()
     * @param attributeValue String CSS class
     */
    public void setStyleClass(String attributeValue)
    {
        setClass(attributeValue);
    }

    /**
     * Method setClass
     * @param attributeValue String attribute value
     */
    public void setClass(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_CLASS, attributeValue);
    }

    /**
     * Method setId
     * @param attributeValue String
     */
    public void setId(String attributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_ID, attributeValue);
        super.setId(attributeValue);
    }

    /**
     * Method getOpenTag
     * @return String
     */
    public String getOpenTag()
    {

        if (mAttributeMap.size() == 0)
        {
            return TagConstants.TAG_OPEN + TagConstants.TABLE_TAG_NAME + TagConstants.TAG_CLOSE;
        }

        StringBuffer lBuffer = new StringBuffer();

        lBuffer.append(TagConstants.TAG_OPEN).append(TagConstants.TABLE_TAG_NAME);

        lBuffer.append(mAttributeMap);

        lBuffer.append(TagConstants.TAG_CLOSE);

        return lBuffer.toString();
    }

    /**
     * Method getCloseTag
     * @return String
     */
    public String getCloseTag()
    {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TABLE_TAG_NAME + TagConstants.TAG_CLOSE;
    }

    /**
     * Method release
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        mAttributeMap.clear();
        super.release();
    }

}
