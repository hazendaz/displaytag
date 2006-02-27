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

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.displaytag.exception.TagStructureException;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.util.MediaUtil;


/**
 * Display a table footer. Html only, not included in export.
 * @author Fabrizio Giustina
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class TableFooterTag extends BodyTagSupport implements MediaUtil.SupportsMedia
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * The media supported attribute.
     */
    private List supportedMedia;

    /**
     * Show the footer as a last table row.
     */
    private boolean showAsLastRow;

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException
    {
        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

        if (tableTag == null)
        {
            throw new TagStructureException(getClass(), "footer", "table");
        }

        MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext.findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
        if (currentMediaType != null && !MediaUtil.availableForMedia(this, currentMediaType))
        {
            return SKIP_BODY;
        }

        if (tableTag.isLastIteration())
        {
            if (getBodyContent() != null)
            {
                tableTag.setFooter(getBodyContent().getString());
            }
        }

        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

        if (tableTag == null)
        {
            throw new TagStructureException(getClass(), "footer", "table");
        }

        MediaTypeEnum currentMediaType = (MediaTypeEnum) this.pageContext.findAttribute(TableTag.PAGE_ATTRIBUTE_MEDIA);
        if (!MediaUtil.availableForMedia(this, currentMediaType))
        {
            return SKIP_BODY;
        }

        // Run the footer only when all of the cells have been populated
        if (tableTag.isLastIteration())
        {
            if (tableTag.getVarTotals() != null)
            {
                Map totals = tableTag.getTotals();
                this.pageContext.setAttribute(tableTag.getVarTotals(), totals);
            }
            // using int to avoid deprecation error in compilation using j2ee 1.3 (EVAL_BODY_TAG)
            return 2;
        }

        return SKIP_BODY;
    }

    /**
     * @see org.displaytag.util.MediaUtil.SupportsMedia#setSupportedMedia(java.util.List)
     */
    public void setSupportedMedia(List media)
    {
        this.supportedMedia = media;
    }

    /**
     * @see org.displaytag.util.MediaUtil.SupportsMedia#getSupportedMedia()
     */
    public List getSupportedMedia()
    {
        return this.supportedMedia;
    }

    /**
     * Tag setter.
     * @param media the space delimited list of supported types
     */
    public void setMedia(String media)
    {
        MediaUtil.setMedia(this, media);
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        this.supportedMedia = null;
        this.showAsLastRow = false;
        super.release();
    }

    /**
     * Tag setter.
     * @param showAsLastRow the space delimited list of supported types
     */
    public void setShowAsLastRow(boolean showAsLastRow)
    {
        this.showAsLastRow = showAsLastRow;
    }
}