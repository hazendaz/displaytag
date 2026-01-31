/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.displaytag.model.TableModel;

/**
 * A table decorator which adds checkboxes for selectable rows.
 */
public class CheckboxTableDecorator extends TableDecorator {

    /** The id. */
    private String id = "id";

    /** The checked ids. */
    private List<String> checkedIds;

    /** The field name. */
    private String fieldName = "_chk";

    /**
     * Setter for <code>id</code>.
     *
     * @param id
     *            The id to set.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Setter for <code>fieldName</code>.
     *
     * @param fieldName
     *            The fieldName to set.
     */
    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Inits the.
     *
     * @param pageContext
     *            the page context
     * @param decorated
     *            the decorated
     * @param tableModel
     *            the table model
     *
     * @see org.displaytag.decorator.Decorator#init(jakarta.servlet.jsp.PageContext, java.lang.Object,
     *      org.displaytag.model.TableModel)
     */
    @Override
    public void init(final PageContext pageContext, final Object decorated, final TableModel tableModel) {
        super.init(pageContext, decorated, tableModel);
        final String[] params = pageContext.getRequest().getParameterValues(this.fieldName);
        this.checkedIds = params != null ? new ArrayList<>(Arrays.asList(params)) : new ArrayList<>(0);
    }

    /**
     * Finish.
     *
     * @see org.displaytag.decorator.TableDecorator#finish()
     */
    @Override
    public void finish() {

        if (!this.checkedIds.isEmpty()) {
            final JspWriter writer = this.getPageContext().getOut();
            for (final String name : this.checkedIds) {
                final StringBuilder buffer = new StringBuilder();
                buffer.append("<input type=\"hidden\" name=\"");
                buffer.append(this.fieldName);
                buffer.append("\" value=\"");
                buffer.append(name);
                buffer.append("\">");
                try {
                    writer.write(buffer.toString());
                } catch (final IOException e) {
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
    public String getCheckbox() {

        final String evaluatedId = Objects.toString(this.evaluate(this.id));

        final boolean checked = this.checkedIds.contains(evaluatedId);

        final StringBuilder buffer = new StringBuilder();
        buffer.append("<input type=\"checkbox\" name=\"_chk\" value=\"");
        buffer.append(evaluatedId);
        buffer.append("\"");
        if (checked) {
            this.checkedIds.remove(evaluatedId);
            buffer.append(" checked=\"checked\"");
        }
        buffer.append("/>");

        return buffer.toString();
    }

}
