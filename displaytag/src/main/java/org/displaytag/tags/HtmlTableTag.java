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
     * @param pAttributeValue String
     */
    public void setWidth(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_WIDTH, pAttributeValue);
    }

    /**
     * Method setBorder
     * @param pAttributeValue String
     */
    public void setBorder(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_BORDER, pAttributeValue);
    }

    /**
     * Method setCellspacing
     * @param pAttributeValue String
     */
    public void setCellspacing(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_CELLSPACING, pAttributeValue);
    }

    /**
     * Method setCellpadding
     * @param pAttributeValue String
     */
    public void setCellpadding(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_CELLPADDING, pAttributeValue);
    }

    /**
     * Method setAlign
     * @param pAttributeValue String
     */
    public void setAlign(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_ALIGN, pAttributeValue);
    }

    /**
     * Method setBackground
     * @param pAttributeValue String
     */
    public void setBackground(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_BACKGROUND, pAttributeValue);
    }

    /**
     * Method setBgcolor
     * @param pAttributeValue String
     */
    public void setBgcolor(String pAttributeValue)
    {

        mAttributeMap.put(TagConstants.ATTRIBUTE_BGCOLOR, pAttributeValue);
    }

    /**
     * Method setFrame
     * @param pAttributeValue String
     */
    public void setFrame(String pAttributeValue)
    {

        mAttributeMap.put(TagConstants.ATTRIBUTE_FRAME, pAttributeValue);
    }

    /**
     * Method setHeight
     * @param pAttributeValue String
     */
    public void setHeight(String pAttributeValue)
    {

        mAttributeMap.put(TagConstants.ATTRIBUTE_HEIGHT, pAttributeValue);
    }

    /**
     * Method setHspace
     * @param pAttributeValue String
     */
    public void setHspace(String pAttributeValue)
    {

        mAttributeMap.put(TagConstants.ATTRIBUTE_HSPACE, pAttributeValue);
    }

    /**
     * Method setRules
     * @param pAttributeValue String
     */
    public void setRules(String pAttributeValue)
    {

        mAttributeMap.put(TagConstants.ATTRIBUTE_RULES, pAttributeValue);
    }

    /**
     * Method setSummary
     * @param pAttributeValue String
     */
    public void setSummary(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_SUMMARY, pAttributeValue);
    }

    /**
     * Method setVspace
     * @param pAttributeValue String
     */
    public void setVspace(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_VSPACE, pAttributeValue);
    }

    /**
     * set the html class attribute
     * @deprecated: use setClass()
     * @param pAttributeValue String CSS class
     */
    public void setStyleClass(String pAttributeValue)
    {
        setClass(pAttributeValue);
    }

    /**
     * Method setClass
     * @param pAttributeValue String attribute value
     */
    public void setClass(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_CLASS, pAttributeValue);
    }

    /**
     * Method setId
     * @param pAttributeValue String
     */
    public void setId(String pAttributeValue)
    {
        mAttributeMap.put(TagConstants.ATTRIBUTE_ID, pAttributeValue);
        super.setId(pAttributeValue);
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
