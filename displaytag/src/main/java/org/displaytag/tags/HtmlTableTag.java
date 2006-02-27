/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.tags;

import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;


/**
 * Base tag which provides setters for all the standard html attributes.
 * @author Fabrizio Giustina
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
        this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     * setter for the "id" html attribute. Don't use setId() to avoid overriding original TagSupport method.
     * @param value attribute value
     */
    public void setHtmlId(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_ID, value);
    }

    /**
     * getter for the "id" html attribute.
     * @return attribute value
     */
    protected String getHtmlId()
    {
        return (String) this.attributeMap.get(TagConstants.ATTRIBUTE_ID);
    }

    /**
     * Adds a css class to the class attribute (html class suports multiple values).
     * @param value attribute value
     */
    public void addClass(String value)
    {
        Object classAttributes = this.attributeMap.get(TagConstants.ATTRIBUTE_CLASS);

        if (classAttributes == null)
        {
            this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
        }
        else
        {
            ((MultipleHtmlAttribute) classAttributes).addAttributeValue(value);
        }
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