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

        super.setName(eval.evalString("name", nameExpr));

        if (valueExpr != null)
        {
            super.setValue(eval.evalString("value", valueExpr));
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