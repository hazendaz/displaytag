package org.displaytag.tags.el;

import javax.servlet.jsp.JspException;

import org.displaytag.tags.CaptionTag;


/**
 * Adds EL support to CaptionTag.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELCaptionTag extends CaptionTag
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Expression for the "class" tag attribute.
     */
    private String classExpr;

    /**
     * Expression for the "dir" tag attribute.
     */
    private String dirExpr;

    /**
     * Expression for the "id" tag attribute.
     */
    private String idExpr;

    /**
     * Expression for the "lang" tag attribute.
     */
    private String langExpr;

    /**
     * Expression for the "style" tag attribute.
     */
    private String styleExpr;

    /**
     * Expression for the "title" tag attribute.
     */
    private String titleExpr;

    /**
     * @see org.displaytag.tags.CaptionTag#setClass(java.lang.String)
     */
    public void setClass(String value)
    {
        classExpr = value;
    }

    /**
     * @see org.displaytag.tags.CaptionTag#setDir(java.lang.String)
     */
    public void setDir(String value)
    {
        dirExpr = value;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#setId(java.lang.String)
     */
    public void setId(String value)
    {
        idExpr = value;
    }

    /**
     * @see org.displaytag.tags.CaptionTag#setLang(java.lang.String)
     */
    public void setLang(String value)
    {
        langExpr = value;
    }

    /**
     * @see org.displaytag.tags.CaptionTag#setStyle(java.lang.String)
     */
    public void setStyle(String value)
    {
        styleExpr = value;
    }

    /**
     * @see org.displaytag.tags.CaptionTag#setTitle(java.lang.String)
     */
    public void setTitle(String value)
    {
        titleExpr = value;
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

        if (classExpr != null)
        {
            super.setClass(eval.evalString("class", classExpr));
        }
        if (dirExpr != null)
        {
            super.setDir(eval.evalString("dir", dirExpr));
        }
        if (idExpr != null)
        {
            super.setId(eval.evalString("id", idExpr));
        }
        if (langExpr != null)
        {
            super.setLang(eval.evalString("lang", langExpr));
        }
        if (styleExpr != null)
        {
            super.setStyle(eval.evalString("style", styleExpr));
        }
        if (titleExpr != null)
        {
            super.setTitle(eval.evalString("title", titleExpr));
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        super.release();
        this.classExpr = null;
        this.dirExpr = null;
        this.idExpr = null;
        this.langExpr = null;
        this.styleExpr = null;
        this.titleExpr = null;
    }

}