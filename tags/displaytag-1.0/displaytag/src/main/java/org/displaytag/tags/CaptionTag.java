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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.displaytag.exception.TagStructureException;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;


/**
 * Simple caption tag which mimics a standard html caption.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class CaptionTag extends BodyTagSupport
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Map containing all the standard html attributes.
     */
    private HtmlAttributeMap attributeMap = new HtmlAttributeMap();

    /**
     * is this the first iteration?
     */
    private boolean firstIteration = true;

    /**
     * setter for the "style" html attribute.
     * @param value attribute value
     */
    public void setStyle(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_STYLE, value);
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
     * setter for the "id" html attribute.
     * @param value attribute value
     */
    public void setId(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_ID, value);
    }

    /**
     * setter for the "title" html attribute.
     * @param value attribute value
     */
    public void setTitle(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_TITLE, value);
    }

    /**
     * setter for the "lang" html attribute.
     * @param value attribute value
     */
    public void setLang(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_LANG, value);
    }

    /**
     * setter for the "dir" html attribute.
     * @param value attribute value
     */
    public void setDir(String value)
    {
        this.attributeMap.put(TagConstants.ATTRIBUTE_DIR, value);
    }

    /**
     * create the open tag containing all the attributes.
     * @return open tag string
     */
    public String getOpenTag()
    {

        if (this.attributeMap.size() == 0)
        {
            return TagConstants.TAG_OPEN + TagConstants.TAGNAME_CAPTION + TagConstants.TAG_CLOSE;
        }

        StringBuffer buffer = new StringBuffer();

        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TAGNAME_CAPTION);

        buffer.append(this.attributeMap);

        buffer.append(TagConstants.TAG_CLOSE);

        return buffer.toString();
    }

    /**
     * create the closing tag.
     * @return <code>&lt;/caption&gt;</code>
     */
    public String getCloseTag()
    {
        return TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_CAPTION + TagConstants.TAG_CLOSE;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

        if (tableTag == null)
        {
            throw new TagStructureException(getClass(), "caption", "table"); //$NON-NLS-1$ //$NON-NLS-2$
        }

        // add caption only once
        if (tableTag.isFirstIteration())
        {
            this.firstIteration = true;
            // using int to avoid deprecation error in compilation using j2ee 1.3 (EVAL_BODY_TAG)
            return 2;
        }

        this.firstIteration = false;
        return SKIP_BODY;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException
    {
        if (this.firstIteration)
        {
            TableTag tableTag = (TableTag) findAncestorWithClass(this, TableTag.class);

            if (tableTag == null)
            {
                throw new TagStructureException(getClass(), "caption", "table"); //$NON-NLS-1$ //$NON-NLS-2$
            }

            StringBuffer buffer = new StringBuffer();
            buffer.append(getOpenTag());

            if (getBodyContent() != null)
            {
                buffer.append(getBodyContent().getString());
            }

            buffer.append(getCloseTag());

            tableTag.setCaption(buffer.toString());

            this.firstIteration = false;

        }

        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        super.release();
        this.attributeMap.clear();
    }

}