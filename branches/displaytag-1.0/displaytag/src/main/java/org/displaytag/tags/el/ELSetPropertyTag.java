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
package org.displaytag.tags.el;

import javax.servlet.jsp.JspException;

import org.displaytag.tags.SetPropertyTag;


/**
 * Adds EL support to SetPropertyTag.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELSetPropertyTag extends SetPropertyTag
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Expression for the "name" tag attribute.
     */
    private String nameExpr;

    /**
     * Expression for the "value" tag attribute.
     */
    private String valueExpr;

    /**
     * @see org.displaytag.tags.SetPropertyTag#setName(java.lang.String)
     */
    public void setName(String value)
    {
        nameExpr = value;
    }

    /**
     * @see org.displaytag.tags.SetPropertyTag#setValue(java.lang.String)
     */
    public void setValue(String value)
    {
        valueExpr = value;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException
    {
        evaluateExpressions();
        return super.doStartTag();
    }

    /**
     * Evaluates the expressions for all the given attributes and pass results up to the parent tag.
     * @throws JspException for exceptions occurred during evaluation.
     */
    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        super.setName(eval.evalString("name", nameExpr)); //$NON-NLS-1$

        if (valueExpr != null)
        {
            super.setValue(eval.evalString("value", valueExpr)); //$NON-NLS-1$
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        super.release();
        this.nameExpr = null;
        this.valueExpr = null;
    }

}