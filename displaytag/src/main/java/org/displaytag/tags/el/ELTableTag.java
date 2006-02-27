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


/**
 * Adds EL support to displaytag's TableTag.
 * @author Tim McCune
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELTableTag extends org.displaytag.tags.TableTag
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
     * Expression for the "requestURIcontext" tag attribute.
     */
    private String requestURIcontextExpr;

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
     * Expression for the "excludedParams" tag attribute.
     */
    private String excludedParamsExpr;

    /**
     * Expression for the "id" tag attribute.
     */
    private String idExpr;

    /**
     * Expression for the "htmlId" tag attribute.
     */
    private String htmlIdExpr;

    /**
     * @see org.displaytag.tags.TableTag#setUid(java.lang.String)
     */
    public void setUid(String value)
    {
        idExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setCellpadding(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setCellpadding(String value)
    {
        cellpaddingExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setCellspacing(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setCellspacing(String value)
    {
        cellspacingExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setClass(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setClass(String value)
    {
        classExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setDecorator(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setDecorator(String value)
    {
        decoratorExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setDefaultsort(int)
     * @param value EL expression for attribute value
     */
    public void setDefaultsort(String value)
    {
        defaultsortExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setDefaultorder(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setDefaultorder(String value)
    {
        defaultorderExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setExport(boolean)
     * @param value EL expression for attribute value
     */
    public void setExport(String value)
    {
        exportExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setFrame(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setFrame(String value)
    {
        frameExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setLength(int)
     * @param value EL expression for attribute value
     */
    public void setLength(String value)
    {
        lengthExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setName(java.lang.Object)
     * @param value EL expression for attribute value
     */
    public void setName(String value)
    {
        nameExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setOffset(int)
     * @param value EL expression for attribute value
     */
    public void setOffset(String value)
    {
        offsetExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setPagesize(int)
     * @param value EL expression for attribute value
     */
    public void setPagesize(String value)
    {
        pagesizeExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setRequestURI(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setRequestURI(String value)
    {
        requestURIExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setRequestURIcontext(boolean)
     * @param value EL expression for attribute value
     */
    public void setRequestURIcontext(String value)
    {
        requestURIcontextExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setRules(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setRules(String value)
    {
        rulesExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setSort(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setSort(String value)
    {
        sortExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setExcludedParams(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setExcludedParams(String value)
    {
        excludedParamsExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#setHtmlId(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setHtmlId(String value)
    {
        htmlIdExpr = value;
    }

    /**
     * @see org.displaytag.tags.TableTag#getHtmlId()
     */
    public String getHtmlId()
    {
        return htmlIdExpr;
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

        if (idExpr != null)
        {
            super.setUid(eval.evalString("uid", idExpr)); //$NON-NLS-1$
        }
        if (htmlIdExpr != null)
        {
            super.setHtmlId(eval.evalString("htmlId", htmlIdExpr)); //$NON-NLS-1$
        }
        if (cellpaddingExpr != null)
        {
            super.setCellpadding(eval.evalString("cellpadding", cellpaddingExpr)); //$NON-NLS-1$
        }
        if (cellspacingExpr != null)
        {
            super.setCellspacing(eval.evalString("cellspacing", cellspacingExpr)); //$NON-NLS-1$
        }
        if (classExpr != null)
        {
            super.setClass(eval.evalString("class", classExpr)); //$NON-NLS-1$
        }
        if (decoratorExpr != null)
        {
            super.setDecorator(eval.evalString("decorator", decoratorExpr)); //$NON-NLS-1$
        }
        if (defaultorderExpr != null)
        {
            super.setDefaultorder(eval.evalString("defaultorder", defaultorderExpr)); //$NON-NLS-1$
        }
        if (excludedParamsExpr != null)
        {
            super.setExcludedParams(eval.evalString("excludedParams", excludedParamsExpr)); //$NON-NLS-1$
        }
        if (defaultsortExpr != null)
        {
            super.setDefaultsort(eval.evalInt("defaultsort", defaultsortExpr)); //$NON-NLS-1$
        }
        if (exportExpr != null)
        {
            super.setExport(eval.evalBoolean("export", exportExpr)); //$NON-NLS-1$
        }
        if (frameExpr != null)
        {
            super.setFrame(eval.evalString("frame", frameExpr)); //$NON-NLS-1$
        }
        if (lengthExpr != null)
        {
            super.setLength(eval.evalInt("length", lengthExpr)); //$NON-NLS-1$
        }
        if (nameExpr != null)
        {
            Object source = eval.eval("name", nameExpr, Object.class); //$NON-NLS-1$

            // be more user-friendly: accept both EL and legacy expressions
            if (source instanceof String)
            {
                super.setNameString((String) source);
            }
            else
            {
                // evaluate name only once, so assign it to "list"
                super.list = source;
            }
        }
        if (offsetExpr != null)
        {
            super.setOffset(eval.evalInt("offset", offsetExpr)); //$NON-NLS-1$
        }
        if (pagesizeExpr != null)
        {
            super.setPagesize(eval.evalInt("pagesize", pagesizeExpr)); //$NON-NLS-1$
        }
        if (requestURIExpr != null)
        {
            super.setRequestURI(eval.evalString("requestURI", requestURIExpr)); //$NON-NLS-1$
        }
        if (requestURIcontextExpr != null)
        {
            super.setRequestURIcontext(eval.evalBoolean("requestURIcontext", requestURIcontextExpr)); //$NON-NLS-1$
        }
        if (rulesExpr != null)
        {
            super.setRules(eval.evalString("rules", rulesExpr)); //$NON-NLS-1$
        }
        if (sortExpr != null)
        {
            super.setSort(eval.evalString("sort", sortExpr)); //$NON-NLS-1$
        }
        if (styleExpr != null)
        {
            super.setStyle(eval.evalString("style", styleExpr)); //$NON-NLS-1$
        }
        if (summaryExpr != null)
        {
            super.setSummary(eval.evalString("summary", summaryExpr)); //$NON-NLS-1$
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
        this.requestURIcontextExpr = null;
        this.rulesExpr = null;
        this.sortExpr = null;
        this.styleExpr = null;
        this.summaryExpr = null;
        this.excludedParamsExpr = null;
        this.idExpr = null;
        this.htmlIdExpr = null;
    }

}