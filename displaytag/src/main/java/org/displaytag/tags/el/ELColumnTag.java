package org.displaytag.tags.el;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;


/**
 * Adds EL support to displaytag's ColumnTag. Also supports a new "titleKey" property that works the same as
 * fmt:message's key property. This tag must be the descendant of a fmt:bundle tag in order to use the titleKey. This is
 * just a shortcut, which makes <code>
 * &lt;display:column titleKey="bar"/&gt;
 * </code> behave the same as <code>
 * &lt;c:set var="foo"&gt;&lt;fmt:message key="bar"/&gt;&lt;/c:set&gt;<br/>
 * &lt;display:column title="${foo}"/&gt;
 * </code>.
 * If you don't define a title or a titleKey property on your column, first the tag will attempt to look up the property
 * property in your ResourceBundle. Failing that, it will fall back to the parent class's behavior of just using the
 * property name.
 * @author Tim McCune
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELColumnTag extends org.displaytag.tags.ColumnTag
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(ELColumnTag.class);

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
     * Expression for the "parentProperty" tag attribute.
     */
    private String parentPropertyExpr; //Parent class has no getter

    /**
     * Expression for the "parentTitle" tag attribute.
     */
    private String parentTitleExpr; //Parent class has no getter

    /**
     * @see org.displaytag.tags.ColumnTag#setAutolink(boolean)
     */
    public void setAutolink(String value)
    {
        autoLinkExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setClass(java.lang.String)
     */
    public void setClass(String value)
    {
        classExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setDecorator(java.lang.String)
     */
    public void setDecorator(String value)
    {
        decoratorExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setGroup(int)
     */
    public void setGroup(String value)
    {
        groupExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setHeaderClass(java.lang.String)
     */
    public void setHeaderClass(String value)
    {
        headerClassExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setHref(java.lang.String)
     */
    public void setHref(String value)
    {
        hrefExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setMaxLength(int)
     */
    public void setMaxLength(String value)
    {
        maxLengthExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setMaxWords(int)
     */
    public void setMaxWords(String value)
    {
        maxWordsExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setMedia(java.lang.String)
     */
    public void setMedia(String value)
    {
        mediaExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setNulls(boolean)
     */
    public void setNulls(String value)
    {
        nullsExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setParamId(java.lang.String)
     */
    public void setParamId(String value)
    {
        paramIdExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setParamName(java.lang.String)
     */
    public void setParamName(String value)
    {
        paramNameExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setParamProperty(java.lang.String)
     */
    public void setParamProperty(String value)
    {
        paramPropertyExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setParamScope(java.lang.String)
     */
    public void setParamScope(String value)
    {
        paramScopeExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setProperty(java.lang.String)
     */
    public void setProperty(String value)
    {
        propertyExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setSortable(boolean)
     */
    public void setSortable(String value)
    {
        sortableExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setTitle(java.lang.String)
     */
    public void setTitle(String value)
    {
        titleExpr = value;
    }

    /**
     * @see org.displaytag.tags.ColumnTag#setStyle(java.lang.String)
     */
    public void setStyle(String value)
    {
        styleExpr = value;
    }

    /**
     * Sets the name of a property in a resource bundle to be used as the title for the column.
     * @param value property name
     * @todo shoud not be here. No difference between the el and standard version, please
     */
    public void setTitleKey(String value)
    {
        titleKeyExpr = value;
    }

    /**
     * Setter for the "href" attribute which prepends the context path.
     * @param value href to be used for links
     * @todo shoud not be here. No difference between the el and standard version, please
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

        if (titleKeyExpr != null || parentTitleExpr == null)
        {
            String key = (titleKeyExpr != null) ? titleKeyExpr : parentPropertyExpr;
            Tag tag = findAncestorWithClass(this, BundleSupport.class);
            ResourceBundle bundle = null;
            if (tag != null)
            {
                BundleSupport parent = (BundleSupport) tag;
                if (key != null)
                {
                    String prefix = parent.getPrefix();
                    if (prefix != null)
                    {
                        key = prefix + key;
                    }
                }
                bundle = parent.getLocalizationContext().getResourceBundle();
            }
            else
            {
                // check for the localizationContext in applicationScope, set in web.xml
                LocalizationContext localization = BundleSupport.getLocalizationContext(pageContext);

                if (localization != null)
                {
                    bundle = localization.getResourceBundle();
                }
            }

            if (bundle != null)
            {
                try
                {
                    if (key != null)
                    {
                        super.setTitle(bundle.getString(key));
                    }
                }
                catch (MissingResourceException e)
                {
                    log.info("Missing resource for title key [" + titleKeyExpr + "]");
                    if (titleKeyExpr != null)
                    {
                        super.setTitle(titleKeyExpr);
                    }
                }
            }

        }
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
            super.setAutolink(eval.evalBoolean("autolink", autoLinkExpr));
        }
        if (classExpr != null)
        {
            super.setClass(eval.evalString("class", classExpr));
        }
        if (decoratorExpr != null)
        {
            super.setDecorator(eval.evalString("decorator", decoratorExpr));
        }
        if (groupExpr != null)
        {
            super.setGroup(eval.evalInt("group", groupExpr));
        }
        if (headerClassExpr != null)
        {
            super.setHeaderClass(eval.evalString("headerClass", headerClassExpr));
        }
        if (hrefExpr != null)
        {
            super.setHref(eval.evalString("href", hrefExpr));
        }
        if (maxLengthExpr != null)
        {
            super.setMaxLength(eval.evalInt("maxLength", maxLengthExpr));
        }
        if (maxWordsExpr != null)
        {
            super.setMaxWords(eval.evalInt("maxWords", maxWordsExpr));
        }
        if (mediaExpr != null)
        {
            super.setMedia(eval.evalString("media", mediaExpr));
        }
        if (nullsExpr != null)
        {
            super.setNulls(eval.evalBoolean("nulls", nullsExpr));
        }
        if (paramIdExpr != null)
        {
            super.setParamId(eval.evalString("paramId", paramIdExpr));
        }
        if (paramNameExpr != null)
        {
            super.setParamName(eval.evalString("paramName", paramNameExpr));
        }
        if (paramPropertyExpr != null)
        {
            super.setParamProperty(eval.evalString("paramProperty", paramPropertyExpr));
        }
        if (paramScopeExpr != null)
        {
            super.setParamScope(eval.evalString("paramScope", paramScopeExpr));
        }
        if (sortableExpr != null)
        {
            super.setSortable(eval.evalBoolean("sortable", sortableExpr));
        }
        if (styleExpr != null)
        {
            super.setStyle(eval.evalString("style", styleExpr));
        }
        if (urlExpr != null)
        {
            HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
            super.setHref(req.getContextPath() + eval.evalString("url", urlExpr));
        }
        if (propertyExpr != null)
        {
            String property = eval.evalString("property", propertyExpr);
            super.setProperty(property);
            this.parentPropertyExpr = property;
        }
        if (titleExpr != null)
        {
            String title = eval.evalString("title", titleExpr);
            super.setTitle(title);
            this.parentTitleExpr = title;
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
        this.parentPropertyExpr = null;
        this.parentTitleExpr = null;
        this.propertyExpr = null;
        this.sortableExpr = null;
        this.styleExpr = null;
        this.titleExpr = null;
        this.titleKeyExpr = null;
        this.urlExpr = null;
    }

}