package org.displaytag.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.displaytag.util.LookupUtil;

/**
 * <p>Base template class</p>
 * @author bgsmith
 * @version $Revision$ ($Author$)
 */
public abstract class TemplateTag extends BodyTagSupport
{

    /**
     * Utility method. Write a string to the default out
     * @param pString String
     * @throws JspTagException if an IOException occurs
     */
    public void write(String pString) throws JspTagException
    {
        try
        {
            JspWriter lOut = pageContext.getOut();
            lOut.write(pString);
        }
        catch (IOException e)
        {
            throw new JspTagException("Writer Exception: " + e.getMessage());
        }
    }

    /**
     * Utility method. Write a string to the default out
     * @param pString StringBuffer
     * @throws JspTagException if an IOException occurs
     */
    public void write(StringBuffer pString) throws JspTagException
    {
        this.write(pString.toString());
    }

    /**
     * <p>evaluate an expression in a way similar to LE in jstl.</p>
     * <p>the first token is supposed to be an object in the page scope (default scope) or one of the following:</p>
     * <ul>
     *     <li>pageScope</li>
     *     <li>requestScope</li>
     *     <li>sessionScope</li>
     *     <li>applicationScope</li>
     * </ul>
     * <p>Tokens after the object name are interpreted as javabean properties (accessed through getters), mapped or
     * indexed properties, using the jakarta common-beans library</p>
     * @param pExpression expression to evaluate
     * @return Object result
     * @throws JspException generic exception
     */
    protected Object evaluateExpression(String pExpression) throws JspException
    {

        String lExpression = pExpression;

        // default scope = request
        // this is for compatibility with the previous version, probably default should be PAGE
        int lScope = PageContext.REQUEST_SCOPE;

        if (pExpression.startsWith("pageScope."))
        {
            lScope = PageContext.PAGE_SCOPE;
            lExpression = lExpression.substring(lExpression.indexOf('.') + 1);
        }
        else if (pExpression.startsWith("requestScope."))
        {
            lScope = PageContext.REQUEST_SCOPE;
            lExpression = lExpression.substring(lExpression.indexOf('.') + 1);

        }
        else if (pExpression.startsWith("sessionScope."))
        {
            lScope = PageContext.SESSION_SCOPE;
            lExpression = lExpression.substring(lExpression.indexOf('.') + 1);

        }
        else if (pExpression.startsWith("applicationScope."))
        {
            lScope = PageContext.APPLICATION_SCOPE;
            lExpression = lExpression.substring(lExpression.indexOf('.') + 1);

        }

        return LookupUtil.getBeanValue(pageContext, lExpression, lScope);

    }

}
