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
package org.displaytag.decorator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.ObjectUtils;
import org.displaytag.model.TableModel;


/**
 * A table decorator which adds checkboxes for selectable rows.
 * @author Fabrizio Giustina
 * @version $Id: $
 */
public class CheckboxTableDecorator extends TableDecorator
{

    private String id = "id";

    private Map params;

    private List checkedIds;

    private String fieldName = "_chk";

    /**
     * Setter for <code>id</code>.
     * @param id The id to set.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Setter for <code>fieldName</code>.
     * @param fieldName The fieldName to set.
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    /**
     * @see org.displaytag.decorator.Decorator#init(javax.servlet.jsp.PageContext, java.lang.Object,
     * org.displaytag.model.TableModel)
     */
    public void init(PageContext pageContext, Object decorated, TableModel tableModel)
    {
        super.init(pageContext, decorated, tableModel);
        String[] params = pageContext.getRequest().getParameterValues(fieldName);
        checkedIds = params != null ? new ArrayList(Arrays.asList(params)) : new ArrayList(0);
    }

    /**
     * @see org.displaytag.decorator.TableDecorator#finish()
     */
    public void finish()
    {

        if (!checkedIds.isEmpty())
        {
            JspWriter writer = getPageContext().getOut();
            for (Iterator it = checkedIds.iterator(); it.hasNext();)
            {
                String name = (String) it.next();
                StringBuffer buffer = new StringBuffer();
                buffer.append("<input type=\"hidden\" name=\"");
                buffer.append(fieldName);
                buffer.append("\" value=\"");
                buffer.append(name);
                buffer.append("\">");
                try
                {
                    writer.write(buffer.toString());
                }
                catch (IOException e)
                {
                    // should never happen
                }
            }
        }

        super.finish();

    }

    public String getCheckbox()
    {

        String evaluatedId = ObjectUtils.toString(evaluate(id));

        boolean checked = checkedIds.contains(evaluatedId);

        StringBuffer buffer = new StringBuffer();
        buffer.append("<input type=\"checkbox\" name=\"_chk\" value=\"");
        buffer.append(evaluatedId);
        buffer.append("\"");
        if (checked)
        {
            checkedIds.remove(evaluatedId);
            buffer.append(" checked=\"checked\"");
        }
        buffer.append("/>");

        return buffer.toString();
    }

}
