/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.displaytag.exception.TagStructureException;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.util.MediaUtil;

/**
 * Display a table footer. Html only, not included in export.
 *
 * @author Fabrizio Giustina
 * @author rapruitt
 *
 * @version $Revision$ ($Author$)
 */
public class TableFooterTag extends BodyTagSupport implements MediaUtil.SupportsMedia {

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * The media supported attribute.
     */
    private transient List<MediaTypeEnum> supportedMedia;

    /**
     * Do end tag.
     *
     * @return the int
     *
     * @throws JspException
     *             the jsp exception
     *
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    @Override
    public int doEndTag() throws JspException {
        final TableTag tableTag = (TableTag) TagSupport.findAncestorWithClass(this, TableTag.class);

        if (tableTag == null) {
            throw new TagStructureException(this.getClass(), "footer", "table");
        }

        final MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext
                .findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
        if (currentMediaType != null && !MediaUtil.availableForMedia(this, currentMediaType)) {
            return Tag.SKIP_BODY;
        }

        if (tableTag.isLastIteration() && this.getBodyContent() != null) {
            tableTag.setFooter(this.getBodyContent().getString());
        }

        return Tag.EVAL_PAGE;
    }

    /**
     * Do start tag.
     *
     * @return the int
     *
     * @throws JspException
     *             the jsp exception
     *
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        final TableTag tableTag = (TableTag) TagSupport.findAncestorWithClass(this, TableTag.class);

        if (tableTag == null) {
            throw new TagStructureException(this.getClass(), "footer", "table");
        }

        final MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext
                .findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
        if (!MediaUtil.availableForMedia(this, currentMediaType)) {
            return Tag.SKIP_BODY;
        }

        // Run the footer only when all of the cells have been populated
        if (tableTag.isLastIteration()) {
            if (tableTag.getVarTotals() != null) {
                final Map<String, Double> totals = tableTag.getTotals();
                this.pageContext.setAttribute(tableTag.getVarTotals(), totals);
            }
            // using int to avoid deprecation error in compilation using j2ee 1.3 (EVAL_BODY_TAG)
            return 2;
        }

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
     * Release.
     *
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    @Override
    public void release() {
        this.supportedMedia = null;
        super.release();
    }

    /**
     * Tag setter.
     *
     * @param showAsLastRow
     *            the space delimited list of supported types
     */
    public void setShowAsLastRow(final boolean showAsLastRow) {
    }
}
