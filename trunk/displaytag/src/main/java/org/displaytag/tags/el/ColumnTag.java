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
 * fmt:message's key property. This tag must be the descendant of a fmt:bundle tag in order to use the titleKey. This
 * is just a shortcut, which makes
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
 * If you don't define a title or a titleKey property on your column, first the tag will attempt to look up the
 * property property in your ResourceBundle. Failing that, it will fall back to the parent class's behavior of just
 * using the property name.
 * @author Tim McCune
 * @version $Revision$ ($Author$)
 */
public class ColumnTag extends org.displaytag.tags.ColumnTag
{

    private static final String UNDEFINED_KEY = "???";

    private String _autoLink;
    public void setAutolink(String s)
    {
        _autoLink = s;
    }

    private String _class;
    public void setClass(String s)
    {
        _class = s;
    }

    private String _decorator;

    public void setDecorator(String s)
    {
        _decorator = s;
    }

    private String _group;
    public void setGroup(String s)
    {
        _decorator = s;
    }

    private String _headerClass;
    public void setHeaderClass(String s)
    {
        _headerClass = s;
    }

    private String _href;
    public void setHref(String s)
    {
        _href = s;
    }

    private String _maxLength;
    public void setMaxLength(String s)
    {
        _maxLength = s;
    }

    private String _maxWords;
    public void setMaxWords(String s)
    {
        _maxWords = s;
    }

    private String _media;
    public void setMedia(String s)
    {
        _media = s;
    }

    private String _nulls;
    public void setNulls(String s)
    {
        _nulls = s;
    }

    private String _paramId;
    public void setParamId(String s)
    {
        _paramId = s;
    }

    private String _paramName;
    public void setParamName(String s)
    {
        _paramName = s;
    }

    private String _paramProperty;
    public void setParamProperty(String s)
    {
        _paramProperty = s;
    }

    private String _paramScope;
    public void setParamScope(String s)
    {
        _paramScope = s;
    }

    private String _property;
    public void setProperty(String s)
    {
        _property = s;
    }
    private String _parentProperty; //Parent class has no getter

    private String _sortable;
    public void setSortable(String s)
    {
        _sortable = s;
    }

    private String _title;
    public void setTitle(String s)
    {
        _title = s;
    }
    private String _parentTitle; //Parent class has no getter

    private String _titleKey;
    public void setTitleKey(String s)
    {
        _titleKey = s;
    }

    private String _url;
    public void setUrl(String s)
    {
        _url = s;
    }

    public ColumnTag()
    {
        super();
        init();
    }

    public void release()
    {
        super.release();
        init();
    }

    public int doStartTag() throws JspException
    {
        evaluateExpressions();

        if (_titleKey != null || _parentTitle == null)
        {
            String key = (_titleKey != null) ? _titleKey : _parentProperty;
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
                    if (_titleKey != null)
                    {
                        super.setTitle(UNDEFINED_KEY + _titleKey + UNDEFINED_KEY);
                    }
                }
            }

        }
        return super.doStartTag();
    }

    private void init()
    {
        _autoLink = null;
        _class = null;
        _decorator = null;
        _group = null;
        _headerClass = null;
        _href = null;
        _maxLength = null;
        _maxWords = null;
        _media = null;
        _nulls = null;
        _paramId = null;
        _paramName = null;
        _paramProperty = null;
        _paramScope = null;
        _property = null;
        _parentProperty = null;
        _sortable = null;
        _title = null;
        _parentTitle = null;
        _titleKey = null;
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);
        String s;
        if ((s = eval.evalString("autolink", _autoLink)) != null)
        {
            super.setAutolink(s);
        }
        if ((s = eval.evalString("class", _class)) != null)
        {
            super.setClass(s);
        }
        if ((s = eval.evalString("decorator", _decorator)) != null)
        {
            super.setDecorator(s);
        }
        if ((s = eval.evalString("group", _group)) != null)
        {
            super.setGroup(s);
        }
        if ((s = eval.evalString("headerClass", _headerClass)) != null)
        {
            super.setHeaderClass(s);
        }
        if ((s = eval.evalString("href", _href)) != null)
        {
            super.setHref(s);
        }
        if ((s = eval.evalString("url", _url)) != null)
        {
            HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
            super.setHref(req.getContextPath() + s);
        }
        super.setMaxLength(eval.evalInt("maxLength", _maxLength));
        super.setMaxWords(eval.evalInt("maxWords", _maxWords));
        if ((s = eval.evalString("media", _media)) != null)
        {
            super.setMedia(s);
        }
        if ((s = eval.evalString("nulls", _nulls)) != null)
        {
            super.setNulls(s);
        }
        if ((s = eval.evalString("paramId", _paramId)) != null)
        {
            super.setParamId(s);
        }
        if ((s = eval.evalString("paramName", _paramName)) != null)
        {
            super.setParamName(s);
        }
        if ((s = eval.evalString("paramProperty", _paramProperty)) != null)
        {
            super.setParamProperty(s);
        }
        if ((s = eval.evalString("paramScope", _paramScope)) != null)
        {
            super.setParamScope(s);
        }
        s = eval.evalString("property", _property);
        super.setProperty(s);
        _parentProperty = s;
        if ((s = eval.evalString("sortable", _sortable)) != null)
        {
            super.setSortable(s);
        }
        s = eval.evalString("title", _title);
        super.setTitle(s);
        _parentTitle = s;
    }

}