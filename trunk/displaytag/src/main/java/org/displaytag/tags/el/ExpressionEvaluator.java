package org.displaytag.tags.el;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * Utility class to help with the evaluation of JSTL Expression Language. It mainly encapsulates the calls to
 * ExpressionEvaluationManager to ease the use of this class.
 * @version $Revision$ ($Author$)
 */
public class ExpressionEvaluator
{

    private PageContext _context;
    private Tag _tag;

    public ExpressionEvaluator(Tag tag, PageContext context)
    {
        _tag = tag;
        _context = context;
    }

    /**
     * Evaluate expression in attrValue.
     * @return evaluate expression of attrValue, null if attrValue is null.
     */
    public Object eval(String attrName, String attrValue, Class returnClass) throws JspException
    {
        Object result = null;
        if (attrValue != null)
        {
            result = ExpressionEvaluatorManager.evaluate(attrName, attrValue, returnClass, _tag, _context);
        }
        return result;
    }

    public String evalString(String attrName, String attrValue) throws JspException
    {
        return (String) eval(attrName, attrValue, String.class);
    }

    public boolean evalBoolean(String attrName, String attrValue) throws JspException
    {
        Boolean rtn = (Boolean) eval(attrName, attrValue, Boolean.class);
        if (rtn != null)
        {
            return rtn.booleanValue();
        }
        else
        {
            return false;
        }
    }

    public long evalLong(String attrName, String attrValue) throws JspException
    {
        Long rtn = (Long) eval(attrName, attrValue, Long.class);
        if (rtn != null)
        {
            return rtn.longValue();
        }
        else
        {
            return -1L;
        }
    }

    public int evalInt(String attrName, String attrValue) throws JspException
    {
        Integer rtn = (Integer) eval(attrName, attrValue, Integer.class);
        if (rtn != null)
        {
            return rtn.intValue();
        }
        else
        {
            return -1;
        }
    }
}
