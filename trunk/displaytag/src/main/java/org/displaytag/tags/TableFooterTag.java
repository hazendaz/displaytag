package org.displaytag.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.TagStructureException;

/**
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class TableFooterTag extends BodyTagSupport
{

    /**
     * logger
     */
    private static Log log = LogFactory.getLog(TableFooterTag.class);

    /**
     * private boo
     */
    private boolean firstIteration = false;

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException
    {
        if (firstIteration)
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

            firstIteration = false;

        }

        return TagSupport.EVAL_PAGE;
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
            firstIteration = true;
            return BodyTagSupport.EVAL_BODY_TAG;
        }
        else
        {
            firstIteration = false;
            return TagSupport.SKIP_BODY;
        }

    }

}
