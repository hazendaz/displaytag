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
     * setter for the "frame" html attribute.
     * @param value attribute value
     */
    public void setFrame(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_FRAME, value);
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
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        this.attributeMap.clear();
        super.release();
    }

    /**
     * Return a map of html attributes. Should be used for extensions only, html attributes are normally printed out in
     * the <code>getOpenTag()</code> method.
     * @return map of html attributes
     */
    public HtmlAttributeMap getAttributeMap()
    {
        return this.attributeMap;
    }

}