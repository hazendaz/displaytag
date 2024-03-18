/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.exception.DecoratorException;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A table decorator which adds rows with totals (for column with the "total" attribute set) and subtotals (grouping by
 * the column with a group="1" attribute).
 *
 * @author Fabrizio Giustina
 *
 * @version $Id$
 */
public class TotalTableDecorator extends TableDecorator {

    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(TotalTableDecorator.class);

    /**
     * total amount.
     */
    private final Map<String, Double> grandTotals = new HashMap<>();

    /**
     * total amount for current group.
     */
    private final Map<String, Double> subTotals = new HashMap<>();

    /**
     * Previous values needed for grouping.
     */
    private final Map<String, Object> previousValues = new HashMap<>();

    /**
     * Name of the property used for grouping.
     */
    private String groupPropertyName;

    /**
     * Label used for subtotals. Default: "{0} total".
     */
    private String subtotalLabel = "{0} subtotal";

    /**
     * Label used for totals. Default: "Total".
     */
    private String totalLabel = "Total";

    /**
     * Setter for <code>subtotalLabel</code>.
     *
     * @param subtotalLabel
     *            The subtotalLabel to set.
     */
    public void setSubtotalLabel(final String subtotalLabel) {
        this.subtotalLabel = subtotalLabel;
    }

    /**
     * Setter for <code>totalLabel</code>.
     *
     * @param totalLabel
     *            The totalLabel to set.
     */
    public void setTotalLabel(final String totalLabel) {
        this.totalLabel = totalLabel;
    }

    /**
     * Inits the.
     *
     * @param context
     *            the context
     * @param decorated
     *            the decorated
     * @param tableModel
     *            the table model
     *
     * @see org.displaytag.decorator.Decorator#init(PageContext, Object, TableModel)
     */
    @Override
    public void init(final PageContext context, final Object decorated, final TableModel tableModel) {
        super.init(context, decorated, tableModel);

        // reset
        this.groupPropertyName = null;
        this.grandTotals.clear();
        this.subTotals.clear();
        this.previousValues.clear();

        for (final HeaderCell cell : tableModel.getHeaderCellList()) {
            if (cell.getGroup() == 1) {
                this.groupPropertyName = cell.getBeanPropertyName();
            }
        }
    }

    /**
     * Start row.
     *
     * @return the string
     */
    @Override
    public String startRow() {
        String subtotalRow = null;

        if (this.groupPropertyName != null) {
            final Object groupedPropertyValue = this.evaluate(this.groupPropertyName);
            final Object previousGroupedPropertyValue = this.previousValues.get(this.groupPropertyName);
            // subtotals
            if (previousGroupedPropertyValue != null
                    && !Objects.equals(previousGroupedPropertyValue, groupedPropertyValue)) {
                subtotalRow = this.createTotalRow(false);
            }
            this.previousValues.put(this.groupPropertyName, groupedPropertyValue);
        }

        for (final HeaderCell cell : this.tableModel.getHeaderCellList()) {
            if (cell.isTotaled()) {
                final String totalPropertyName = cell.getBeanPropertyName();
                final Number amount = (Number) this.evaluate(totalPropertyName);

                final Number previousSubTotal = this.subTotals.get(totalPropertyName);
                final Number previousGrandTotals = this.grandTotals.get(totalPropertyName);

                this.subTotals.put(totalPropertyName,
                        Double.valueOf((previousSubTotal != null ? previousSubTotal.doubleValue() : 0)
                                + (amount != null ? amount.doubleValue() : 0)));

                this.grandTotals.put(totalPropertyName,
                        Double.valueOf((previousGrandTotals != null ? previousGrandTotals.doubleValue() : 0)
                                + (amount != null ? amount.doubleValue() : 0)));
            }
        }

        return subtotalRow;
    }

    /**
     * After every row completes we evaluate to see if we should be drawing a new total line and summing the results
     * from the previous group.
     *
     * @return String
     */
    @Override
    public final String finishRow() {
        final StringBuilder buffer = new StringBuilder(1000);

        // Grand totals...
        if (this.getViewIndex() == ((List<Object>) this.getDecoratedObject()).size() - 1) {
            if (this.groupPropertyName != null) {
                buffer.append(this.createTotalRow(false));
            }
            buffer.append(this.createTotalRow(true));
        }
        return buffer.toString();

    }

    /**
     * Creates the total row.
     *
     * @param grandTotal
     *            the grand total
     *
     * @return the string
     */
    protected String createTotalRow(final boolean grandTotal) {
        final StringBuilder buffer = new StringBuilder(1000);
        buffer.append("\n<tr class=\"total\">"); //$NON-NLS-1$

        final List<HeaderCell> headerCells = this.tableModel.getHeaderCellList();

        for (final HeaderCell cell : headerCells) {
            final Object cssClassObj = cell.getHtmlAttributes().get("class");
            final String cssClass = cssClassObj != null ? cssClassObj.toString() : StringUtils.EMPTY;

            buffer.append("<td"); //$NON-NLS-1$
            if (StringUtils.isNotEmpty(cssClass)) {
                buffer.append(" class=\""); //$NON-NLS-1$
                buffer.append(cssClass);
                buffer.append("\""); //$NON-NLS-1$
            }
            buffer.append(">"); //$NON-NLS-1$

            if (cell.isTotaled()) {
                final String totalPropertyName = cell.getBeanPropertyName();
                Object total = grandTotal ? this.grandTotals.get(totalPropertyName)
                        : this.subTotals.get(totalPropertyName);

                final DisplaytagColumnDecorator[] decorators = cell.getColumnDecorators();
                for (final DisplaytagColumnDecorator decorator : decorators) {
                    try {
                        total = decorator.decorate(total, this.getPageContext(), this.tableModel.getMedia());
                    } catch (final DecoratorException e) {
                        TotalTableDecorator.log.warn(e.getMessage(), e);
                        // ignore, use undecorated value for totals
                    }
                }
                buffer.append(total);
            } else if (this.groupPropertyName != null && this.groupPropertyName.equals(cell.getBeanPropertyName())) {
                buffer.append(grandTotal ? this.totalLabel
                        : new MessageFormat(this.subtotalLabel, this.tableModel.getProperties().getLocale())
                                .format(new Object[] { this.previousValues.get(this.groupPropertyName) }));
            }

            buffer.append("</td>"); //$NON-NLS-1$

        }

        buffer.append("</tr>"); //$NON-NLS-1$

        // reset subtotal
        this.subTotals.clear();

        return buffer.toString();
    }

}
