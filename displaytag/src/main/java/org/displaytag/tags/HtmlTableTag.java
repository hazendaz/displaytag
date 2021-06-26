/*
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
     * Stable serialVersionUID.
     */
    private static final long serialVersionUID = 42L;

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
    @Override
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