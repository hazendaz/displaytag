package org.displaytag.tags.el;

import javax.servlet.jsp.JspException;


/**
 * Adds EL support to displaytag's TableTag.
 * @author Tim McCune
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELTableTag extends org.displaytag.tags.TableTag
{

    /**
     * Expression for the "decorator" tag attribute.
     */
    private String decoratorExpr;

    /**
     * Expression for the "defaultsort" tag attribute.
     */
    private Object defaultsortExpr;

    /**
     * Expression for the "export" tag attribute.
     */
    private Object exportExpr;

    /**
     * Expression for the "length" tag attribute.
     */
    private Object lengthExpr;

    /**
     * Expression for the "name" tag attribute.
     */
    private String nameExpr;

    /**
     * Expression for the "offset" tag attribute.
     */
    private Object offsetExpr;

    /**
     * Expression for the "pagesize" tag attribute.
     */
    private Object pagesizeExpr;

    /**
     * Expression for the "requestURI" tag attribute.
     */
    private String requestURIExpr;

    /**
     * Expression for the "sort" tag attribute.
     */
    private String sortExpr;

    /**
     * @see org.displaytag.tags.TableTag#setDecorator(java.lang.String)
     */
    public void setDecorator(String value)
    {
        decoratorExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setDefaultsort(java.lang.Object)
     */
    public void setDefaultsort(Object value)
    {
        defaultsortExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setExport(java.lang.Object)
     */
    public void setExport(Object value)
    {
        exportExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setLength(java.lang.Object)
     */
    public void setLength(Object value)
    {
        lengthExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setName(java.lang.String)
     */
    public void setName(String value)
    {
        nameExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setOffset(java.lang.Object)
     */
    public void setOffset(Object value)
    {
        offsetExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setPagesize(java.lang.Object)
     */
    public void setPagesize(Object value)
    {
        pagesizeExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setRequestURI(java.lang.String)
     */
    public void setRequestURI(String value)
    {
        requestURIExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setSort(java.lang.String)
     */
    public void setSort(String value)
    {
        sortExpr = value;
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
     * Evaluates EL expressions and sets values in the parent Table Tag.
     * @throws JspException for errors during evaluation
     */
    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);
        String s;
        if ((s = eval.evalString("decorator", decoratorExpr)) != null)
        {
            super.setDecorator(s);
        }
        if ((s = eval.evalString("requestURI", requestURIExpr)) != null)
        {
            super.setRequestURI(s);
        }
        if ((s = eval.evalString("sort", sortExpr)) != null)
        {
            super.setSort(s);
        }

        super.setDefaultsort(eval.evalInt("defaultsort", (String) defaultsortExpr));

        super.setExport(eval.evalBoolean("export", (String) exportExpr));

        super.setLength(eval.evalInt("length", (String) lengthExpr));

        super.setOffset(eval.evalInt("offset", (String) offsetExpr));

        super.setPagesize(eval.evalInt("pagesize", (String) pagesizeExpr));

        // evaluate name only once
        this.list = eval.eval("name", nameExpr, Object.class);
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        super.release();
        decoratorExpr = null;
        defaultsortExpr = null;
        exportExpr = null;
        lengthExpr = null;
        nameExpr = null;
        offsetExpr = null;
        pagesizeExpr = null;
        requestURIExpr = null;
        sortExpr = null;
    }


}