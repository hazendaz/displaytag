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
 * Adds EL support to ColumnTag.
 * @author Tim McCune
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELColumnTag extends org.displaytag.tags.ColumnTag
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Expression for the "autoLink" tag attribute.
     */
    private String autoLinkExpr;

    /**
     * Expression for the "class" tag attribute.
     */
    private String classExpr;

    /**
     * Expression for the "decorator" tag attribute.
     */
    private String decoratorExpr;

    /**
     * Expression for the "group" tag attribute.
     */
    private String groupExpr;

    /**
     * Expression for the "headerClass" tag attribute.
     */
    private String headerClassExpr;

    /**
     * Expression for the "href" tag attribute.
     */
    private String hrefExpr;

    /**
     * Expression for the "maxLength" tag attribute.
     */
    private String maxLengthExpr;

    /**
     * Expression for the "maxWords" tag attribute.
     */
    private String maxWordsExpr;

    /**
     * Expression for the "media" tag attribute.
     */
    private String mediaExpr;

    /**
     * Expression for the "nulls" tag attribute.
     */
    private String nullsExpr;

    /**
     * Expression for the "paramId" tag attribute.
     */
    private String paramIdExpr;

    /**
     * Expression for the "paramName" tag attribute.
     */
    private String paramNameExpr;

    /**
     * Expression for the "paramProperty" tag attribute.
     */
    private String paramPropertyExpr;

    /**
     * Expression for the "paramScope" tag attribute.
     */
    private String paramScopeExpr;

    /**
     * Expression for the "property" tag attribute.
     */
    private String propertyExpr;

    /**
     * Expression for the "title" tag attribute.
     */
    private String titleExpr;

    /**
     * Expression for the "style" tag attribute.
     */
    private String styleExpr;

    /**
     * Expression for the "url" tag attribute.
     */
    private String urlExpr;

    /**
     * Expression for the "titleKey" tag attribute.
     */
    private String titleKeyExpr;

    /**
     * Expression for the "sortable" tag attribute.
     */
    private String sortableExpr;

    /**
     * Expression for the "sortProperty" tag attribute.
     */
    private String sortPropertyExpr;

    /**
     * @see org.displaytag.tags.ColumnTag#setAutolink(boolean)
     * @param value EL expression for attribute value
     */
    public void setAutolink(String value)
    {
        autoLinkExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setClass(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setClass(String value)
    {
        classExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setDecorator(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setDecorator(String value)
    {
        decoratorExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setGroup(int)
     * @param value EL expression for attribute value
     */
    public void setGroup(String value)
    {
        groupExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setHeaderClass(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setHeaderClass(String value)
    {
        headerClassExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setHref(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setHref(String value)
    {
        hrefExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setMaxLength(int)
     * @param value EL expression for attribute value
     */
    public void setMaxLength(String value)
    {
        maxLengthExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setMaxWords(int)
     * @param value EL expression for attribute value
     */
    public void setMaxWords(String value)
    {
        maxWordsExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setMedia(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setMedia(String value)
    {
        mediaExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setNulls(boolean)
     * @param value EL expression for attribute value
     */
    public void setNulls(String value)
    {
        nullsExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setParamId(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setParamId(String value)
    {
        paramIdExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setParamName(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setParamName(String value)
    {
        paramNameExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setParamProperty(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setParamProperty(String value)
    {
        paramPropertyExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setParamScope(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setParamScope(String value)
    {
        paramScopeExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setProperty(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setProperty(String value)
    {
        propertyExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setSortable(boolean)
     * @param value EL expression for attribute value
     */
    public void setSortable(String value)
    {
        sortableExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setTitle(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setTitle(String value)
    {
        titleExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setStyle(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setStyle(String value)
    {
        styleExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setTitleKey(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setTitleKey(String value)
    {
        titleKeyExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setUrl(java.lang.String)
     * @param value EL expression for attribute value
     */
    public void setUrl(String value)
    {
        urlExpr = value;
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

        if (autoLinkExpr != null)
        {
            super.setAutolink(eval.evalBoolean("autolink", autoLinkExpr)); //$NON-NLS-1$
        }
        if (classExpr != null)
        {
            super.setClass(eval.evalString("class", classExpr)); //$NON-NLS-1$
        }
        if (decoratorExpr != null)
        {
            super.setDecorator(eval.evalString("decorator", decoratorExpr)); //$NON-NLS-1$
        }
        if (groupExpr != null)
        {
            super.setGroup(eval.evalInt("group", groupExpr)); //$NON-NLS-1$
        }
        if (headerClassExpr != null)
        {
            super.setHeaderClass(eval.evalString("headerClass", headerClassExpr)); //$NON-NLS-1$
        }
        if (hrefExpr != null)
        {
            super.setHref(eval.evalString("href", hrefExpr)); //$NON-NLS-1$
        }
        if (maxLengthExpr != null)
        {
            super.setMaxLength(eval.evalInt("maxLength", maxLengthExpr)); //$NON-NLS-1$
        }
        if (maxWordsExpr != null)
        {
            super.setMaxWords(eval.evalInt("maxWords", maxWordsExpr)); //$NON-NLS-1$
        }
        if (mediaExpr != null)
        {
            super.setMedia(eval.evalString("media", mediaExpr)); //$NON-NLS-1$
        }
        if (nullsExpr != null)
        {
            super.setNulls(eval.evalBoolean("nulls", nullsExpr)); //$NON-NLS-1$
        }
        if (paramIdExpr != null)
        {
            super.setParamId(eval.evalString("paramId", paramIdExpr)); //$NON-NLS-1$
        }
        if (paramNameExpr != null)
        {
            // todo should be handled the same way the "name" table attribute is handled, no double evaluation
            super.setParamName(eval.evalString("paramName", paramNameExpr)); //$NON-NLS-1$
        }
        if (paramPropertyExpr != null)
        {
            super.setParamProperty(eval.evalString("paramProperty", paramPropertyExpr)); //$NON-NLS-1$
        }
        if (paramScopeExpr != null)
        {
            super.setParamScope(eval.evalString("paramScope", paramScopeExpr)); //$NON-NLS-1$
        }
        if (sortableExpr != null)
        {
            super.setSortable(eval.evalBoolean("sortable", sortableExpr)); //$NON-NLS-1$
        }
        if (styleExpr != null)
        {
            super.setStyle(eval.evalString("style", styleExpr)); //$NON-NLS-1$
        }
        if (urlExpr != null)
        {
            super.setUrl(eval.evalString("url", urlExpr)); //$NON-NLS-1$
        }
        if (propertyExpr != null)
        {
            String property = eval.evalString("property", propertyExpr); //$NON-NLS-1$
            super.setProperty(property);
        }
        if (titleExpr != null)
        {
            String title = eval.evalString("title", titleExpr); //$NON-NLS-1$
            super.setTitle(title);
        }
        if (titleKeyExpr != null)
        {
            super.setTitleKey(eval.evalString("titleKey", titleKeyExpr)); //$NON-NLS-1$
        }
        if (sortPropertyExpr != null)
        {
            super.setSortProperty(eval.evalString("sortProperty", sortPropertyExpr)); //$NON-NLS-1$
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        super.release();
        this.autoLinkExpr = null;
        this.classExpr = null;
        this.decoratorExpr = null;
        this.groupExpr = null;
        this.headerClassExpr = null;
        this.hrefExpr = null;
        this.maxLengthExpr = null;
        this.maxWordsExpr = null;
        this.mediaExpr = null;
        this.nullsExpr = null;
        this.paramIdExpr = null;
        this.paramNameExpr = null;
        this.paramPropertyExpr = null;
        this.paramScopeExpr = null;
        this.propertyExpr = null;
        this.sortableExpr = null;
        this.styleExpr = null;
        this.titleExpr = null;
        this.titleKeyExpr = null;
        this.urlExpr = null;
        this.sortPropertyExpr = null;
    }

}