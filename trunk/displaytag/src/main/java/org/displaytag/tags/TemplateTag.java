package org.displaytag.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.displaytag.util.LookupUtil;

/**
 * Base template class
 * @author bgsmith
 * @version $Revision$ ($Author$)
 */
public abstract class TemplateTag extends BodyTagSupport
{

    /**
     * Utility method. Write a string to the default out
     * @param string String
     * @throws JspTagException if an IOException occurs
     */
    public void write(String string) throws JspTagException
    {
        try
        {
            JspWriter out = pageContext.getOut();
            out.write(string);
        }
        catch (IOException e)
        {
            throw new JspTagException("Writer Exception: " + e.getMessage());
        }
    }

    /**
     * Utility method. Write a string to the default out
     * @param buffer StringBuffer
     * @throws JspTagException if an IOException occurs
     */
    public void write(StringBuffer buffer) throws JspTagException
    {
        this.write(buffer.toString());
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
     * @param expression expression to evaluate
     * @return Object result
     * @throws JspException generic exception
     */
    protected Object evaluateExpression(String expression) throws JspException
    {

        String expressionWithoutScope = expression;

        // default scope = request
        // this is for compatibility with the previous version, probably default should be PAGE
        int scope = PageContext.REQUEST_SCOPE;

        if (expression.startsWith("pageScope."))
        {
            scope = PageContext.PAGE_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);
        }
        else if (expression.startsWith("requestScope."))
        {
            scope = PageContext.REQUEST_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);

        }
        else if (expression.startsWith("sessionScope."))
        {
            scope = PageContext.SESSION_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);

        }
        else if (expression.startsWith("applicationScope."))
        {
            scope = PageContext.APPLICATION_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);

        }

        return LookupUtil.getBeanValue(pageContext, expressionWithoutScope, scope);

    }

}
