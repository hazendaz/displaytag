package org.displaytag.tags.el;

import javax.servlet.jsp.JspException;

/**
 * Adds EL support to displaytag's TableTag.
 * @author Tim McCune
 * @version $Revision$ ($Author$)
 */
public class TableTag extends org.displaytag.tags.TableTag
{

    private String _decorator;
    public void setDecorator(String s)
    {
        _decorator = s;
    }

    private String _defaultsort;
    public void setDefaultsort(String s)
    {
        _defaultsort = s;
    }

    private String _export;
    public void setExport(String s)
    {
        _export = s;
    }

    private String _footer;
    public void setFooter(String s)
    {
        _footer = s;
    }

    private String _length;
    public void setLength(String s)
    {
        _length = s;
    }

    private String _name;
    public void setName(String s)
    {
        _name = s;
    }

    private String _offset;
    public void setOffset(String s)
    {
        _offset = s;
    }

    private String _pagesize;
    public void setPagesize(String s)
    {
        _pagesize = s;
    }

    private String _requestURI;
    public void setRequestURI(String s)
    {
        _requestURI = s;
    }

    private String _sort;
    public void setSort(String s)
    {
        _sort = s;
    }

    public TableTag()
    {
        super();
        init();
    }

    private void init()
    {
        _decorator = null;
        _defaultsort = null;
        _export = null;
        _footer = null;
        _length = null;
        _name = null;
        _offset = null;
        _pagesize = null;
        _requestURI = null;
        _sort = null;
    }

    public void release()
    {
        super.release();
        init();
    }

    public int doStartTag() throws JspException
    {
        evaluateExpressions();
        return super.doStartTag();
    }

    private void evaluateExpressions() throws JspException
    {
        ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);
        String s;
        if ((s = eval.evalString("decorator", _decorator)) != null)
        {
            super.setDecorator(s);
        }
        if ((s = eval.evalString("defaultsort", _defaultsort)) != null)
        {
            super.setDefaultsort(s);
        }
        if ((s = eval.evalString("export", _export)) != null)
        {
            super.setExport(s);
        }
        if ((s = eval.evalString("footer", _footer)) != null)
        {
            super.setFooter(s);
        }
        if ((s = eval.evalString("length", _length)) != null)
        {
            super.setLength(s);
        }
        if ((s = eval.evalString("name", _name)) != null)
        {
            super.setName(s);
        }
        if ((s = eval.evalString("offset", _offset)) != null)
        {
            super.setOffset(s);
        }
        if ((s = eval.evalString("pagesize", _pagesize)) != null)
        {
            super.setPagesize(s);
        }
        if ((s = eval.evalString("requestURI", _requestURI)) != null)
        {
            super.setRequestURI(s);
        }
        if ((s = eval.evalString("sort", _sort)) != null)
        {
            super.setSort(s);
        }
    }

}
