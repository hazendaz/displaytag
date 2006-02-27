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

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.displaytag.exception.ObjectLookupException;
import org.displaytag.exception.WrappedRuntimeException;
import org.displaytag.util.LookupUtil;


/**
 * Base template class.
 * @author bgsmith
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public abstract class TemplateTag extends BodyTagSupport
{

    /**
     * Utility method. Write a string to the default out
     * @param string String
     */
    public void write(String string)
    {
        write(string, this.pageContext.getOut());
    }

    /**
     * Utility method. Write a string to the given JspWriter
     * @param string String
     * @param out JspWriter
     */
    public void write(String string, JspWriter out)
    {
        if (string == null)
        {
            return;
        }

        try
        {
            out.write(string);
        }
        catch (IOException e)
        {
            throw new WrappedRuntimeException(getClass(), e);
        }
    }

    /**
     * Utility method. Write a string to the default out
     * @param buffer StringBuffer
     */
    public void write(StringBuffer buffer)
    {
        this.write(buffer.toString());
    }

    /**
     * <p>
     * evaluate an expression in a way similar to LE in jstl.
     * </p>
     * <p>
     * the first token is supposed to be an object in the page scope (default scope) or one of the following:
     * </p>
     * <ul>
     * <li>pageScope</li>
     * <li>requestScope</li>
     * <li>sessionScope</li>
     * <li>applicationScope</li>
     * </ul>
     * <p>
     * Tokens after the object name are interpreted as javabean properties (accessed through getters), mapped or indexed
     * properties, using the jakarta common-beans library
     * </p>
     * @param expression expression to evaluate
     * @return Object result
     * @throws ObjectLookupException if unable to get a bean using the given expression
     */
    protected Object evaluateExpression(String expression) throws ObjectLookupException
    {

        String expressionWithoutScope = expression;

        // default scope = request
        // this is for compatibility with the previous version, probably default should be PAGE
        int scope = PageContext.REQUEST_SCOPE;

        if (expression.startsWith("pageScope.")) //$NON-NLS-1$
        {
            scope = PageContext.PAGE_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);
        }
        else if (expression.startsWith("requestScope.")) //$NON-NLS-1$
        {
            scope = PageContext.REQUEST_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);

        }
        else if (expression.startsWith("sessionScope.")) //$NON-NLS-1$
        {
            scope = PageContext.SESSION_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);

        }
        else if (expression.startsWith("applicationScope.")) //$NON-NLS-1$
        {
            scope = PageContext.APPLICATION_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);

        }

        return LookupUtil.getBeanValue(this.pageContext, expressionWithoutScope, scope);

    }

}