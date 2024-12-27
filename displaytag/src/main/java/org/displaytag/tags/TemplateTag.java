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

import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.BodyTagSupport;

import org.displaytag.exception.ObjectLookupException;
import org.displaytag.util.LookupUtil;

/**
 * Base template class.
 */
public abstract class TemplateTag extends BodyTagSupport {

    /**
     * Stable serialVersionUID.
     */
    private static final long serialVersionUID = 42L;

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
     *
     * @param expression
     *            expression to evaluate
     *
     * @return Object result
     *
     * @throws ObjectLookupException
     *             if unable to get a bean using the given expression
     */
    protected Object evaluateExpression(final String expression) throws ObjectLookupException {

        String expressionWithoutScope = expression;

        // default scope = request
        // this is for compatibility with the previous version, probably default should be PAGE
        int scope = PageContext.REQUEST_SCOPE;

        if (expression.startsWith("pageScope.")) //$NON-NLS-1$
        {
            scope = PageContext.PAGE_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);
        } else if (expression.startsWith("requestScope.")) //$NON-NLS-1$
        {
            scope = PageContext.REQUEST_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);

        } else if (expression.startsWith("sessionScope.")) //$NON-NLS-1$
        {
            scope = PageContext.SESSION_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);

        } else if (expression.startsWith("applicationScope.")) //$NON-NLS-1$
        {
            scope = PageContext.APPLICATION_SCOPE;
            expressionWithoutScope = expressionWithoutScope.substring(expressionWithoutScope.indexOf('.') + 1);

        }

        return LookupUtil.getBeanValue(this.pageContext, expressionWithoutScope, scope);

    }

}
