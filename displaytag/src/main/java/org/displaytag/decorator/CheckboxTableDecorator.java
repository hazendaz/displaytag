/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.decorator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.displaytag.model.TableModel;


/**
 * A table decorator which adds checkboxes for selectable rows.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class CheckboxTableDecorator extends TableDecorator
{

    /** The id. */
    private String id = "id";

    /** The checked ids. */
    private List<String> checkedIds;

    /** The field name. */
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
    @Override
    public void init(PageContext pageContext, Object decorated, TableModel tableModel)
    {
        super.init(pageContext, decorated, tableModel);
        String[] params = pageContext.getRequest().getParameterValues(this.fieldName);
        this.checkedIds = params != null ? new ArrayList<>(Arrays.asList(params)) : new ArrayList<String>(0);
    }

    /**
     * @see org.displaytag.decorator.TableDecorator#finish()
     */
    @Override
    public void finish()
    {

        if (!this.checkedIds.isEmpty())
        {
            JspWriter writer = getPageContext().getOut();
            for (String name : this.checkedIds) {
                StringBuilder buffer = new StringBuilder();
                buffer.append("<input type=\"hidden\" name=\"");
                buffer.append(this.fieldName);
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

    /**
     * Gets the checkbox.
     *
     * @return the checkbox
     */
    public String getCheckbox()
    {

        String evaluatedId = Objects.toString(evaluate(this.id));

        boolean checked = this.checkedIds.contains(evaluatedId);

        StringBuilder buffer = new StringBuilder();
        buffer.append("<input type=\"checkbox\" name=\"_chk\" value=\"");
        buffer.append(evaluatedId);
        buffer.append("\"");
        if (checked)
        {
            this.checkedIds.remove(evaluatedId);
            buffer.append(" checked=\"checked\"");
        }
        buffer.append("/>");

        return buffer.toString();
    }

}
