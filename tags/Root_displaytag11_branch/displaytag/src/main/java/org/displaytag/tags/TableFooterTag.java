package org.displaytag.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.TagStructureException;


/**
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TableFooterTag extends BodyTagSupport
{

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
        else
        {
            this.firstIteration = false;
            return SKIP_BODY;
        }

    }

}