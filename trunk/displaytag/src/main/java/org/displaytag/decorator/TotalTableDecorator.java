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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.DecoratorException;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.TableModel;


/**
 * A table decorator which adds rows with totals (for column with the "total" attribute set) and subtotals (grouping by
 * the column with a group="1" attribute).
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class TotalTableDecorator extends TableDecorator
{

    /**
     * Logger.
     */
    private static Log log = LogFactory.getLog(TotalTableDecorator.class);

    /**
     * total amount.
     */
    private Map<String, Double> grandTotals = new HashMap<String, Double>();

    /**
     * total amount for current group.
     */
    private Map<String, Double> subTotals = new HashMap<String, Double>();

    /**
     * Previous values needed for grouping.
     */
    private Map<String, Object> previousValues = new HashMap<String, Object>();

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
     * @param subtotalLabel The subtotalLabel to set.
     */
    public void setSubtotalLabel(String subtotalLabel)
    {
        this.subtotalLabel = subtotalLabel;
    }

    /**
     * Setter for <code>totalLabel</code>.
     * @param totalLabel The totalLabel to set.
     */
    public void setTotalLabel(String totalLabel)
    {
        this.totalLabel = totalLabel;
    }

    /**
     * @see org.displaytag.decorator.Decorator#init(PageContext, Object, TableModel)
     */
    @Override
    public void init(PageContext context, Object decorated, TableModel tableModel)
    {
        super.init(context, decorated, tableModel);

        // reset
        groupPropertyName = null;
        grandTotals.clear();
        subTotals.clear();
        previousValues.clear();

        for (Iterator<HeaderCell> it = tableModel.getHeaderCellList().iterator(); it.hasNext();)
        {
            HeaderCell cell = it.next();
            if (cell.getGroup() == 1)
            {
                groupPropertyName = cell.getBeanPropertyName();
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public String startRow()
    {
        String subtotalRow = null;

        if (groupPropertyName != null)
        {
            Object groupedPropertyValue = evaluate(groupPropertyName);
            Object previousGroupedPropertyValue = previousValues.get(groupPropertyName);
            // subtotals
            if (previousGroupedPropertyValue != null
                && !ObjectUtils.equals(previousGroupedPropertyValue, groupedPropertyValue))
            {
                subtotalRow = createTotalRow(false);
            }
            previousValues.put(groupPropertyName, groupedPropertyValue);
        }

        for (Iterator<HeaderCell> it = tableModel.getHeaderCellList().iterator(); it.hasNext();)
        {
            HeaderCell cell = it.next();
            if (cell.isTotaled())
            {
                String totalPropertyName = cell.getBeanPropertyName();
                Number amount = (Number) evaluate(totalPropertyName);

                Number previousSubTotal = subTotals.get(totalPropertyName);
                Number previousGrandTotals = grandTotals.get(totalPropertyName);

                subTotals.put(totalPropertyName, new Double((previousSubTotal != null
                    ? previousSubTotal.doubleValue()
                    : 0) + (amount != null ? amount.doubleValue() : 0)));

                grandTotals.put(
                    totalPropertyName,
                    new Double((previousGrandTotals != null ? previousGrandTotals.doubleValue() : 0)
                        + (amount != null ? amount.doubleValue() : 0)));
            }
        }

        return subtotalRow;
    }

    /**
     * After every row completes we evaluate to see if we should be drawing a new total line and summing the results
     * from the previous group.
     * @return String
     */
    @Override
    public final String finishRow()
    {
        StringBuffer buffer = new StringBuffer(1000);

        // Grand totals...
        if (getViewIndex() == ((List<Object>) getDecoratedObject()).size() - 1)
        {
            if (groupPropertyName != null)
            {
                buffer.append(createTotalRow(false));
            }
            buffer.append(createTotalRow(true));
        }
        return buffer.toString();

    }

    protected String createTotalRow(boolean grandTotal)
    {
        StringBuffer buffer = new StringBuffer(1000);
        buffer.append("\n<tr class=\"total\">"); //$NON-NLS-1$

        List<HeaderCell> headerCells = tableModel.getHeaderCellList();

        for (Iterator<HeaderCell> it = headerCells.iterator(); it.hasNext();)
        {
            HeaderCell cell = it.next();
            Object cssClassObj = cell.getHtmlAttributes().get("class");
            String cssClass = cssClassObj != null ? cssClassObj.toString() : StringUtils.EMPTY;

            buffer.append("<td"); //$NON-NLS-1$
            if (StringUtils.isNotEmpty(cssClass))
            {
                buffer.append(" class=\""); //$NON-NLS-1$
                buffer.append(cssClass);
                buffer.append("\""); //$NON-NLS-1$
            }
            buffer.append(">"); //$NON-NLS-1$

            if (cell.isTotaled())
            {
                String totalPropertyName = cell.getBeanPropertyName();
                Object total = grandTotal ? grandTotals.get(totalPropertyName) : subTotals.get(totalPropertyName);

                DisplaytagColumnDecorator[] decorators = cell.getColumnDecorators();
                for (int j = 0; j < decorators.length; j++)
                {
                    try
                    {
                        total = decorators[j].decorate(total, this.getPageContext(), tableModel.getMedia());
                    }
                    catch (DecoratorException e)
                    {
                        log.warn(e.getMessage(), e);
                        // ignore, use undecorated value for totals
                    }
                }
                buffer.append(total);
            }
            else if (groupPropertyName != null && groupPropertyName.equals(cell.getBeanPropertyName()))
            {
                buffer.append(grandTotal ? totalLabel : MessageFormat.format(
                    subtotalLabel,
                    new Object[]{previousValues.get(groupPropertyName)}));
            }

            buffer.append("</td>"); //$NON-NLS-1$

        }

        buffer.append("</tr>"); //$NON-NLS-1$

        // reset subtotal
        this.subTotals.clear();

        return buffer.toString();
    }

}
