package org.displaytag.tags.el;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;


/**
 * Adds EL support to displaytag's ColumnTag. Also supports a new "titleKey" property that works the same as
 * fmt:message's key property. This tag must be the descendant of a fmt:bundle tag in order to use the titleKey. This is
 * just a shortcut, which makes
 * 
 * <pre>
 * &lt;display:column titleKey="bar"/&gt;
 * </pre>
 * 
 * behave the same as
 * 
 * <pre>
 * &lt;c:set var="foo"&gt;&lt;fmt:message key="bar"/&gt;&lt;/c:set&gt;
 * &lt;display:column title="${foo}"/&gt;
 * </pre>
 * 
 * If you don't define a title or a titleKey property on your column, first the tag will attempt to look up the property
 * property in your ResourceBundle. Failing that, it will fall back to the parent class's behavior of just using the
 * property name.
 * @author Tim McCune
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELColumnTag extends org.displaytag.tags.ColumnTag
{

    private static final String UNDEFINED_KEY = "???";

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
     * Expression for the "parentProperty" tag attribute.
     */
    private String parentPropertyExpr; //Parent class has no getter

    /**
     * Expression for the "title" tag attribute.
     */
    private String titleExpr;

    /**
     * Expression for the "parentTitle" tag attribute.
     */
    private String parentTitleExpr; //Parent class has no getter

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

    public void setAutolink(String value)
    {
        autoLinkExpr = value;
    }

    public void setClass(String value)
    {
        classExpr = value;
    }

    public void setDecorator(String value)
    {
        decoratorExpr = value;
    }

    public void setGroup(String value)
    {
        groupExpr = value;
    }

    public void setHeaderClass(String value)
    {
        headerClassExpr = value;
    }
    public void setHref(String value)
    {
        hrefExpr = value;
    }

    public void setMaxLength(String value)
    {
        maxLengthExpr = value;
    }

    public void setMaxWords(String value)
    {
        maxWordsExpr = value;
    }

    public void setMedia(String value)
    {
        mediaExpr = value;
    }

    public void setNulls(String value)
    {
        nullsExpr = value;
    }

    public void setParamId(String value)
    {
        paramIdExpr = value;
    }

    public void setParamName(String value)
    {
        paramNameExpr = value;
    }

    public void setParamProperty(String value)
    {
        paramPropertyExpr = value;
    }

    public void setParamScope(String value)
    {
        paramScopeExpr = value;
    }

    public void setProperty(String value)
    {
        propertyExpr = value;
    }
    public void setSortable(String value)
    {
        sortableExpr = value;
    }

    public void setTitle(String value)
    {
        titleExpr = value;
    }
    public void setTitleKey(String value)
    {
        titleKeyExpr = value;
    }

    public void setUrl(String value)
    {
        urlExpr = value;
    }

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
                    if (titleKeyExpr != null)
                    {
                        super.setTitle(UNDEFINED_KEY + titleKeyExpr + UNDEFINED_KEY);
                    }
                }
            }

        }
        return super.doStartTag();
    }


    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);
        String s;
        if ((s = eval.evalString("autolink", autoLinkExpr)) != null)
        {
            super.setAutolink(s);
        }
        if ((s = eval.evalString("class", classExpr)) != null)
        {
            super.setClass(s);
        }
        if ((s = eval.evalString("decorator", decoratorExpr)) != null)
        {
            super.setDecorator(s);
        }
        if ((s = eval.evalString("group", groupExpr)) != null)
        {
            super.setGroup(s);
        }
        if ((s = eval.evalString("headerClass", headerClassExpr)) != null)
        {
            super.setHeaderClass(s);
        }
        if ((s = eval.evalString("href", hrefExpr)) != null)
        {
            super.setHref(s);
        }
        if ((s = eval.evalString("url", urlExpr)) != null)
        {
            HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
            super.setHref(req.getContextPath() + s);
        }
        super.setMaxLength(eval.evalInt("maxLength", maxLengthExpr));
        super.setMaxWords(eval.evalInt("maxWords", maxWordsExpr));
        if ((s = eval.evalString("media", mediaExpr)) != null)
        {
            super.setMedia(s);
        }
        if ((s = eval.evalString("nulls", nullsExpr)) != null)
        {
            super.setNulls(s);
        }
        if ((s = eval.evalString("paramId", paramIdExpr)) != null)
        {
            super.setParamId(s);
        }
        if ((s = eval.evalString("paramName", paramNameExpr)) != null)
        {
            super.setParamName(s);
        }
        if ((s = eval.evalString("paramProperty", paramPropertyExpr)) != null)
        {
            super.setParamProperty(s);
        }
        if ((s = eval.evalString("paramScope", paramScopeExpr)) != null)
        {
            super.setParamScope(s);
        }
        s = eval.evalString("property", propertyExpr);
        super.setProperty(s);
        parentPropertyExpr = s;
        if ((s = eval.evalString("sortable", sortableExpr)) != null)
        {
            super.setSortable(s);
        }
        s = eval.evalString("title", titleExpr);
        super.setTitle(s);
        parentTitleExpr = s;
    }

    public void release()
    {
        super.release();
        autoLinkExpr = null;
        classExpr = null;
        decoratorExpr = null;
        groupExpr = null;
        headerClassExpr = null;
        hrefExpr = null;
        maxLengthExpr = null;
        maxWordsExpr = null;
        mediaExpr = null;
        nullsExpr = null;
        paramIdExpr = null;
        paramNameExpr = null;
        paramPropertyExpr = null;
        paramScopeExpr = null;
        propertyExpr = null;
        parentPropertyExpr = null;
        sortableExpr = null;
        titleExpr = null;
        parentTitleExpr = null;
        titleKeyExpr = null;
    }

}