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
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;


/**
 * Utility class to help with the evaluation of JSTL Expression Language. It mainly encapsulates the calls to
 * ExpressionEvaluationManager to ease the use of this class.
 * @author Tim McCune
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExpressionEvaluator
{

    /**
     * page context.
     */
    private PageContext pageContext;

    /**
     * tag.
     */
    private Tag tag;

    /**
     * Creates a new ExpressionEvaluator for the given tag and pagecontext.
     * @param sourceTag tag where the expression has to be evaluated
     * @param context the page context
     */
    public ExpressionEvaluator(Tag sourceTag, PageContext context)
    {
        this.tag = sourceTag;
        this.pageContext = context;
    }

    /**
     * Evaluate expression in attrValue.
     * @param attrName attribute name
     * @param attrValue attribute value
     * @return evaluate expression of attrValue, null if attrValue is null.
     * @param returnClass class the returned object is instance of
     * @throws JspException exception thrown by ExpressionEvaluatorManager
     */
    public Object eval(String attrName, String attrValue, Class returnClass) throws JspException
    {
        Object result = null;
        if (attrValue != null)
        {
            result = ExpressionEvaluatorManager.evaluate(attrName, attrValue, returnClass, this.tag, this.pageContext);
        }
        return result;
    }

    /**
     * Evaluate expression in attrValueas as a String.
     * @param attrName attribute name
     * @param attrValue attribute value
     * @return evaluate expression of attrValue, null if attrValue is null.
     * @throws JspException exception thrown by ExpressionEvaluatorManager
     */
    public String evalString(String attrName, String attrValue) throws JspException
    {
        return (String) eval(attrName, attrValue, String.class);
    }

    /**
     * Evaluate expression in attrValueas as a boolean.
     * @param attrName attribute name
     * @param attrValue attribute value
     * @return evaluate expression of attrValue, false if attrValue is null.
     * @throws JspException exception thrown by ExpressionEvaluatorManager
     */
    public boolean evalBoolean(String attrName, String attrValue) throws JspException
    {
        Boolean rtn = (Boolean) eval(attrName, attrValue, Boolean.class);
        if (rtn != null)
        {
            return rtn.booleanValue();
        }

        return false;
    }

    /**
     * Evaluate expression in attrValueas as a long.
     * @param attrName attribute name
     * @param attrValue attribute value
     * @return evaluate expression of attrValue, -1 if attrValue is null.
     * @throws JspException exception thrown by ExpressionEvaluatorManager
     */
    public long evalLong(String attrName, String attrValue) throws JspException
    {
        Long rtn = (Long) eval(attrName, attrValue, Long.class);
        if (rtn != null)
        {
            return rtn.longValue();
        }

        return -1L;
    }

    /**
     * Evaluate expression in attrValueas as a int.
     * @param attrName attribute name
     * @param attrValue attribute value
     * @return evaluate expression of attrValue, -1 if attrValue is null.
     * @throws JspException exception thrown by ExpressionEvaluatorManager
     */
    public int evalInt(String attrName, String attrValue) throws JspException
    {
        Integer rtn = (Integer) eval(attrName, attrValue, Integer.class);
        if (rtn != null)
        {
            return rtn.intValue();
        }

        return -1;
    }
}