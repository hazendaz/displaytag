/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.tags;

import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;

/**
 * Base tag which provides setters for all the standard html attributes.
 */
public abstract class HtmlTableTag extends TemplateTag {

    /**
     * Stable serialVersionUID.
     */
    private static final long serialVersionUID = 42L;

    /**
     * Map containing all the standard html attributes.
     */
    private final HtmlAttributeMap attributeMap = new HtmlAttributeMap();

    /**
     * setter for the "cellspacing" html attribute.
     *
     * @param value
     *            attribute value
     */
    public void setCellspacing(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CELLSPACING, value);
    }

    /**
     * setter for the "cellpadding" html attribute.
     *
     * @param value
     *            attribute value
     */
    public void setCellpadding(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CELLPADDING, value);
    }

    /**
     * setter for the "frame" html attribute.
     *
     * @param value
     *            attribute value
     */
    public void setFrame(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_FRAME, value);
    }

    /**
     * setter for the "rules" html attribute.
     *
     * @param value
     *            attribute value
     */
    public void setRules(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_RULES, value);
    }

    /**
     * setter for the "style" html attribute.
     *
     * @param value
     *            attribute value
     */
    public void setStyle(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_STYLE, value);
    }

    /**
     * setter for the "summary" html attribute.
     *
     * @param value
     *            attribute value
     */
    public void setSummary(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_SUMMARY, value);
    }

    /**
     * setter for the "class" html attribute.
     *
     * @param value
     *            attribute value
     */
    public void setClass(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     * setter for the "id" html attribute. Don't use setId() to avoid overriding original TagSupport method.
     *
     * @param value
     *            attribute value
     */
    public void setHtmlId(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_ID, value);
    }

    /**
     * Adds a css class to the class attribute (html class suports multiple values).
     *
     * @param value
     *            attribute value
     */
    public void addClass(final String value) {
        final Object classAttributes = this.attributeMap.get(TagConstants.ATTRIBUTE_CLASS);

        if (classAttributes == null) {
            this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
        } else {
            ((MultipleHtmlAttribute) classAttributes).addAttributeValue(value);
        }
    }

    /**
     * Release.
     *
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    @Override
    public void release() {
        this.attributeMap.clear();
        super.release();
    }

    /**
     * Return a map of html attributes. Should be used for extensions only, html attributes are normally printed out in
     * the <code>getOpenTag()</code> method.
     *
     * @return map of html attributes
     */
    public HtmlAttributeMap getAttributeMap() {
        return this.attributeMap;
    }

}
