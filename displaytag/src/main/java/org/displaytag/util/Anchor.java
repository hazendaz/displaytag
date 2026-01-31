/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

/**
 * Anchor object used to output an html link (an &lt;a&gt; tag).
 */
public class Anchor {

    /**
     * Href object to be written in the "href" html attribute.
     */
    private Href href;

    /**
     * link body text.
     */
    private String linkText;

    /**
     * HashMap containing all the html attributes.
     */
    private final HtmlAttributeMap attributeMap = new HtmlAttributeMap();

    /**
     * Creates a new Anchor whit the supplied Href and body text.
     *
     * @param linkHref
     *            baseHref
     * @param linkBody
     *            String link body
     */
    public Anchor(final Href linkHref, final String linkBody) {
        this.href = linkHref;
        this.linkText = linkBody;
    }

    /**
     * setter the anchor Href.
     *
     * @param linkHref
     *            Href
     */
    public void setHref(final Href linkHref) {
        this.href = linkHref;
    }

    /**
     * setter for the link body text.
     *
     * @param linkBody
     *            String
     */
    public void setText(final String linkBody) {
        this.linkText = linkBody;
    }

    /**
     * add a "class" attribute to the html link.
     *
     * @param cssClass
     *            String
     */
    public void setClass(final String cssClass) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, cssClass);
    }

    /**
     * add a "style" attribute to the html link.
     *
     * @param style
     *            String
     */
    public void setStyle(final String style) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_STYLE, style);
    }

    /**
     * add a "title" attribute to the html link.
     *
     * @param title
     *            String
     */
    public void setTitle(final String title) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_TITLE, title);
    }

    /**
     * returns the href attribute, surrounded by quotes and prefixed with " href=".
     *
     * @return String <code> href ="<em>href value</em>"</code> or an emty String if Href is null
     */
    private String getHrefString() {
        if (this.href == null) {
            return TagConstants.EMPTY_STRING;
        }
        return " href=\"" + this.href.toString() + "\""; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Returns the &lt;a&gt; tag, with rendered href and any html attribute.
     *
     * @return String
     */
    public String getOpenTag() {

        // shortcut for links with no attributes
        if (this.attributeMap.size() == 0) {
            return TagConstants.TAG_OPEN + TagConstants.TAGNAME_ANCHOR + this.getHrefString() + TagConstants.TAG_CLOSE;
        }

        // append all attributes
        final StringBuilder buffer = new StringBuilder();

        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_ANCHOR).append(this.getHrefString());

        buffer.append(this.attributeMap);

        buffer.append(TagConstants.TAG_CLOSE);

        return buffer.toString();
    }

    /**
     * returns the &lt;/a&gt; tag.
     *
     * @return String
     */
    public String getCloseTag() {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_ANCHOR + TagConstants.TAG_CLOSE;
    }

    /**
     * returns the full &lt;a href=""&gt;body&lt;/a&gt;.
     *
     * @return String html link
     */
    @Override
    public String toString() {
        return this.getOpenTag() + this.linkText + this.getCloseTag();
    }

}
