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
     * Expression for the "class" tag attribute.
     */
    private String classExpr;

    /**
     * Expression for the "cellpadding" tag attribute.
     */
    private String cellpaddingExpr;

    /**
     * Expression for the "cellspacing" tag attribute.
     */
    private String cellspacingExpr;

    /**
     * Expression for the "decorator" tag attribute.
     */
    private String decoratorExpr;

    /**
     * Expression for the "defaultsort" tag attribute.
     */
    private String defaultsortExpr;

    /**
     * Expression for the "defaultorder" tag attribute.
     */
    private String defaultorderExpr;

    /**
     * Expression for the "export" tag attribute.
     */
    private String exportExpr;

    /**
     * Expression for the "frame" tag attribute.
     */
    private String frameExpr;

    /**
     * Expression for the "length" tag attribute.
     */
    private String lengthExpr;

    /**
     * Expression for the "name" tag attribute.
     */
    private String nameExpr;

    /**
     * Expression for the "offset" tag attribute.
     */
    private String offsetExpr;

    /**
     * Expression for the "pagesize" tag attribute.
     */
    private String pagesizeExpr;

    /**
     * Expression for the "requestURI" tag attribute.
     */
    private String requestURIExpr;

    /**
     * Expression for the "rules" tag attribute.
     */
    private String rulesExpr;

    /**
     * Expression for the "sort" tag attribute.
     */
    private String sortExpr;

    /**
     * Expression for the "style" tag attribute.
     */
    private String styleExpr;

    /**
     * Expression for the "summary" tag attribute.
     */
    private String summaryExpr;

    /**
     * @see org.displaytag.tags.TableTag#setCellpadding(java.lang.String)
     */
    public void setCellpadding(String value)
    {
        cellpaddingExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setCellspacing(java.lang.String)
     */
    public void setCellspacing(String value)
    {
        cellspacingExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setClass(java.lang.String)
     */
    public void setClass(String value)
    {
        classExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setDecorator(java.lang.String)
     */
    public void setDecorator(String value)
    {
        decoratorExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setDefaultsort(java.lang.String)
     */
    public void setDefaultsort(String value)
    {
        defaultsortExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setDefaultorder(java.lang.String)
     */
    public void setDefaultorder(String value)
    {
        defaultorderExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setExport(boolean)
     */
    public void setExport(String value)
    {
        exportExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setFrame(java.lang.String)
     */
    public void setFrame(String value)
    {
        frameExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setLength(int)
     */
    public void setLength(String value)
    {
        lengthExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setName(java.lang.Object)
     */
    public void setName(String value)
    {
        nameExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setOffset(int)
     */
    public void setOffset(String value)
    {
        offsetExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setPagesize(int)
     */
    public void setPagesize(String value)
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
     * @see org.displaytag.tags.TableTag#setRules(java.lang.String)
     */
    public void setRules(String value)
    {
        rulesExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setSort(boolean)
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

        if (cellpaddingExpr != null)
        {
            super.setCellpadding(eval.evalString("cellpadding", cellpaddingExpr));
        }
        if (cellspacingExpr != null)
        {
            super.setCellspacing(eval.evalString("cellspacing", cellspacingExpr));
        }
        if (classExpr != null)
        {
            super.setClass(eval.evalString("class", classExpr));
        }
        if (decoratorExpr != null)
        {
            super.setDecorator(eval.evalString("decorator", decoratorExpr));
        }
        if (defaultorderExpr != null)
        {
            super.setDefaultorder(eval.evalString("defaultorder", defaultorderExpr));
        }
        if (defaultsortExpr != null)
        {
            super.setDefaultsort(eval.evalInt("defaultsort", defaultsortExpr));
        }
        if (exportExpr != null)
        {
            super.setExport(eval.evalBoolean("export", exportExpr));
        }
        if (frameExpr != null)
        {
            super.setFrame(eval.evalString("frame", frameExpr));
        }
        if (lengthExpr != null)
        {
            super.setLength(eval.evalInt("length", lengthExpr));
        }
        if (nameExpr != null)
        {
            // evaluate name only once, so assign it to "list"
            super.list = eval.eval("name", nameExpr, Object.class);
        }
        if (offsetExpr != null)
        {
            super.setOffset(eval.evalInt("offset", offsetExpr));
        }
        if (pagesizeExpr != null)
        {
            super.setPagesize(eval.evalInt("pagesize", pagesizeExpr));
        }
        if (requestURIExpr != null)
        {
            super.setRequestURI(eval.evalString("requestURI", requestURIExpr));
        }
        if (rulesExpr != null)
        {
            super.setRules(eval.evalString("rules", rulesExpr));
        }
        if (sortExpr != null)
        {
            super.setSort(eval.evalString("sort", sortExpr));
        }
        if (styleExpr != null)
        {
            super.setStyle(eval.evalString("style", styleExpr));
        }
        if (summaryExpr != null)
        {
            super.setSummary(eval.evalString("summary", summaryExpr));
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        super.release();
        this.cellpaddingExpr = null;
        this.cellspacingExpr = null;
        this.classExpr = null;
        this.decoratorExpr = null;
        this.defaultorderExpr = null;
        this.defaultsortExpr = null;
        this.exportExpr = null;
        this.frameExpr = null;
        this.lengthExpr = null;
        this.nameExpr = null;
        this.offsetExpr = null;
        this.pagesizeExpr = null;
        this.requestURIExpr = null;
        this.rulesExpr = null;
        this.sortExpr = null;
        this.styleExpr = null;
        this.summaryExpr = null;
    }


}