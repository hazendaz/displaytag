/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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

import java.util.List;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import jakarta.servlet.jsp.tagext.Tag;
import jakarta.servlet.jsp.tagext.TagSupport;

import org.displaytag.exception.TagStructureException;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MediaUtil;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;

/**
 * Simple caption tag which mimics a standard html caption.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class CaptionTag extends BodyTagSupport implements MediaUtil.SupportsMedia {

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Map containing all the standard html attributes.
     */
    private final HtmlAttributeMap attributeMap = new HtmlAttributeMap();

    /** is this the first iteration?. */
    private boolean firstIteration = true;

    /**
     * The media supported attribute.
     */
    private transient List<MediaTypeEnum> supportedMedia;

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
     * setter for the "class" html attribute.
     *
     * @param value
     *            attribute value
     */
    public void setClass(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute(value));
    }

    /**
     * setter for the "id" html attribute.
     *
     * @param value
     *            attribute value
     */
    @Override
    public void setId(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_ID, value);
    }

    /**
     * setter for the "title" html attribute.
     *
     * @param value
     *            attribute value
     */
    public void setTitle(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_TITLE, value);
    }

    /**
     * setter for the "lang" html attribute.
     *
     * @param value
     *            attribute value
     */
    public void setLang(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_LANG, value);
    }

    /**
     * setter for the "dir" html attribute.
     *
     * @param value
     *            attribute value
     */
    public void setDir(final String value) {
        this.attributeMap.put(TagConstants.ATTRIBUTE_DIR, value);
    }

    /**
     * create the open tag containing all the attributes.
     *
     * @return open tag string
     */
    public String getOpenTag() {

        if (this.attributeMap.size() == 0) {
            return TagConstants.TAG_OPEN + TagConstants.TAGNAME_CAPTION + TagConstants.TAG_CLOSE;
        }

        final StringBuilder buffer = new StringBuilder();

        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_CAPTION);

        buffer.append(this.attributeMap);

        buffer.append(TagConstants.TAG_CLOSE);

        return buffer.toString();
    }

    /**
     * create the closing tag.
     *
     * @return <code>&lt;/caption&gt;</code>
     */
    public String getCloseTag() {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_CAPTION + TagConstants.TAG_CLOSE;
    }

    /**
     * Do start tag.
     *
     * @return the int
     *
     * @throws JspException
     *             the jsp exception
     *
     * @see jakarta.servlet.jsp.tagext.Tag#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        final TableTag tableTag = (TableTag) TagSupport.findAncestorWithClass(this, TableTag.class);

        if (tableTag == null) {
            throw new TagStructureException(this.getClass(), "caption", "table"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        final MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext
                .findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
        if (!MediaUtil.availableForMedia(this, currentMediaType)) {
            return Tag.SKIP_BODY;
        }

        // add caption only once
        if (tableTag.isFirstIteration()) {
            this.firstIteration = true;
            // using int to avoid deprecation error in compilation using j2ee 1.3 (EVAL_BODY_TAG)
            return 2;
        }

        this.firstIteration = false;
        return Tag.SKIP_BODY;
    }

    /**
     * Sets the supported media.
     *
     * @param media
     *            the new supported media
     *
     * @see org.displaytag.util.MediaUtil.SupportsMedia#setSupportedMedia(java.util.List)
     */
    @Override
    public void setSupportedMedia(final List<MediaTypeEnum> media) {
        this.supportedMedia = media;
    }

    /**
     * Gets the supported media.
     *
     * @return the supported media
     *
     * @see org.displaytag.util.MediaUtil.SupportsMedia#getSupportedMedia()
     */
    @Override
    public List<MediaTypeEnum> getSupportedMedia() {
        return this.supportedMedia;
    }

    /**
     * Tag setter.
     *
     * @param media
     *            the space delimited list of supported types
     */
    public void setMedia(final String media) {
        MediaUtil.setMedia(this, media);
    }

    /**
     * Do end tag.
     *
     * @return the int
     *
     * @throws JspException
     *             the jsp exception
     *
     * @see jakarta.servlet.jsp.tagext.Tag#doEndTag()
     */
    @Override
    public int doEndTag() throws JspException {
        if (this.firstIteration) {
            final TableTag tableTag = (TableTag) TagSupport.findAncestorWithClass(this, TableTag.class);

            if (tableTag == null) {
                throw new TagStructureException(this.getClass(), "caption", "table"); //$NON-NLS-1$ //$NON-NLS-2$
            }

            final MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext
                    .findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
            if (currentMediaType != null && !MediaUtil.availableForMedia(this, currentMediaType)) {
                return Tag.SKIP_BODY;
            }

            if (this.getBodyContent() != null) {
                // set the caption format-agnostic content so it can be written in various formats.
                tableTag.setCaption(this.getBodyContent().getString());
                // set the nested caption tag to write the caption in html format. See HtmlTableWriter.writeCaption
                tableTag.setCaptionTag(this);
            }

            this.firstIteration = false;

        }

        return Tag.EVAL_PAGE;
    }

    /**
     * Release.
     *
     * @see jakarta.servlet.jsp.tagext.Tag#release()
     */
    @Override
    public void release() {
        super.release();
        this.attributeMap.clear();
        this.supportedMedia = null;
    }

}
