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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.TagStructureException;


/**
 * Display a table footer. Html only, not included in export.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TableFooterTag extends BodyTagSupport
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(TableFooterTag.class);

    /**
     * is this the first iteration?
     */
    private boolean firstIteration;

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
                throw new TagStructureException(getClass(), "footer", "table");
            }

            // add column header only once

            log.debug("first call to doEndTag, setting footer");

            if (getBodyContent() != null)
            {
                tableTag.setFooter(getBodyContent().getString());

            }

            this.firstIteration = false;

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

        // add column header only once
        if (tableTag.isFirstIteration())
        {
            this.firstIteration = true;
            // using int to avoid deprecation error in compilation using j2ee 1.3 (EVAL_BODY_TAG)
            return 2;
        }

        this.firstIteration = false;
        return SKIP_BODY;
    }

}