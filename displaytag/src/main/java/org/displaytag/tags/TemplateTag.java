/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
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
