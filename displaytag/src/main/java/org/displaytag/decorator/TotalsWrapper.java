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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.util.TagConstants;


/**
 * A TableDecorator that, in conjunction with totaled and grouped columns, produces multi level subtotals on arbitrary
 * String groupings.
 * @author rapruitt
 * @author Fabrizio Giustina
 */
public class TotalsWrapper extends TableDecorator
{

    /**
     * No current reset group.
     */
    private static final int NO_RESET_GROUP = 4200;

    /**
     * Controls when the subgroup is ended
     */
    protected int innermostGroup;

    /**
     * Logger.
     */
    private Log logger = LogFactory.getLog(TotalsWrapper.class);

    /**
     * CSS class applied to grand totals.
     */
    private String grandTotalLabel = "grandtotal-sum";

    /**
     * CSS class appplied to subtotal headers.
     */
    private String subtotalHeaderClass = "subtotal-header";

    /**
     * CSS class applied to subtotal labels.
     */
    private String subtotalLabelClass = "subtotal-label";

    /**
     * CSS class applied to subtotal totals.
     */
    private String subtotalValueClass = "subtotal-sum";

    /**
     * Maps the groups to their current totals.
     */
    private Map groupNumberToGroupTotal = new HashMap();

    /**
     * The deepest reset group.
     */
    private int deepestResetGroup = NO_RESET_GROUP;

    /**
     * Holds the header rows and their content for a particular group.
     */
    private List headerRows = new ArrayList(5);

    public void init(PageContext context, Object decorated, TableModel model)
    {
        super.init(context, decorated, model);
        List headerCells = model.getHeaderCellList();
        // go through each column, looking for grouped columns; add them to the group number map
        for (Iterator iterator = headerCells.iterator(); iterator.hasNext();)
        {
            HeaderCell headerCell = (HeaderCell) iterator.next();
            if (headerCell.getGroup() > 0)
            {
                groupNumberToGroupTotal.put(new Integer(headerCell.getGroup()), new GroupTotals(headerCell
                    .getColumnNumber()));
                if (headerCell.getGroup() > innermostGroup)
                {
                    innermostGroup = headerCell.getGroup();
                }
            }
        }
    }

    public String getGrandTotalLabel()
    {
        return grandTotalLabel;
    }

    public void setGrandTotalLabel(String grandTotalLabel)
    {
        this.grandTotalLabel = grandTotalLabel;
    }

    public String getSubtotalValueClass()
    {
        return subtotalValueClass;
    }

    public void setSubtotalValueClass(String subtotalValueClass)
    {
        this.subtotalValueClass = subtotalValueClass;
    }

    public String getSubtotalLabelClass()
    {
        return subtotalLabelClass;
    }

    public void setSubtotalLabelClass(String subtotalLabelClass)
    {
        this.subtotalLabelClass = subtotalLabelClass;
    }

    public String getSubtotalHeaderClass()
    {
        return subtotalHeaderClass;
    }

    public void setSubtotalHeaderClass(String subtotalHeaderClass)
    {
        this.subtotalHeaderClass = subtotalHeaderClass;
    }

    public void startOfGroup(String value, int group)
    {
        StringBuffer tr = new StringBuffer();
        tr.append("<tr>");
        for (int i = 1; i < group; i++)
        {
            tr.append("<td>&nbsp;</td>\n");
        }
        tr
            .append("<td colspan=\"100%\" class=\"")
            .append(getSubtotalHeaderClass())
            .append(" group-")
            .append(group)
            .append("\">");
        tr.append(value).append("</td>\n");
        tr.append("</tr>");
        headerRows.add(tr);
    }

    public String displayGroupedValue(String value, short groupingStatus)
    {
        return "&nbsp;";
    }

    public String startRow()
    {
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = headerRows.iterator(); iterator.hasNext();)
        {
            StringBuffer stringBuffer = (StringBuffer) iterator.next();
            sb.append(stringBuffer);
        }
        return sb.toString();
    }

    public void endOfGroup(String value, int groupNumber)
    {
        if (deepestResetGroup > groupNumber)
        {
            deepestResetGroup = groupNumber;
        }
    }

    public String finishRow()
    {
        String returnValue;
        if (innermostGroup > 0 && deepestResetGroup != NO_RESET_GROUP)
        {
            StringBuffer out = new StringBuffer();
            // Starting with the deepest group, print the current total and reset. Do not reset unaffected groups.
            for (int i = innermostGroup; i >= deepestResetGroup; i--)
            {
                Integer groupNumber = new Integer(i);

                GroupTotals totals = (GroupTotals) groupNumberToGroupTotal.get(groupNumber);
                if (totals == null)
                {
                    logger.warn("There is a gap in the defined groups - no group defined for " + groupNumber);
                    continue;
                }
                totals.printTotals(getListIndex(), out);
                totals.setStartRow(getListIndex() + 1);
            }
            returnValue = out.toString();
        }
        else
        {
            returnValue = null;
        }
        deepestResetGroup = NO_RESET_GROUP;
        headerRows.clear();
        if (isLastRow())
        {
            returnValue = StringUtils.defaultString(returnValue);
            returnValue += totalAllRows();
        }
        return returnValue;
    }

    /**
     * Issue a grand total row at the bottom.
     * @return the suitable string
     */
    protected String totalAllRows()
    {
        GroupTotals grandTotal = new GroupTotals(-1);
        StringBuffer out = new StringBuffer();
        grandTotal.setStartRow(0);
        grandTotal.setTotalValueClass(getGrandTotalLabel());
        grandTotal.printTotals(getListIndex(), out);
        return out.toString();
    }

    protected String getCellValue(int columnNumber, int rowNumber)
    {
        List fullList = tableModel.getRowListFull();
        Row row = (Row) fullList.get(rowNumber);
        ColumnIterator columnIterator = row.getColumnIterator(tableModel.getHeaderCellList());
        while (columnIterator.hasNext())
        {
            Column column = columnIterator.nextColumn();
            if (column.getHeaderCell().getColumnNumber() == columnNumber)
            {
                try
                {
                    column.initialize();
                    return column.getChoppedAndLinkedValue();
                }
                catch (ObjectLookupException e)
                {
                    logger.error("Error: " + e.getMessage(), e);
                    throw new RuntimeException("Error: " + e.getMessage(), e);
                }
                catch (DecoratorException e)
                {
                    logger.error("Error: " + e.getMessage(), e);
                    throw new RuntimeException("Error: " + e.getMessage(), e);
                }
            }
        }
        throw new RuntimeException("Unable to find column " + columnNumber + " in the list of columns");
    }

    protected double getTotalForColumn(int columnNumber, int startRow, int stopRow)
    {
        List fullList = tableModel.getRowListFull();
        List window = fullList.subList(startRow, stopRow + 1);
        double total = 0;
        for (Iterator iterator = window.iterator(); iterator.hasNext();)
        {
            Row row = (Row) iterator.next();
            ColumnIterator columnIterator = row.getColumnIterator(tableModel.getHeaderCellList());
            while (columnIterator.hasNext())
            {
                Column column = columnIterator.nextColumn();
                if (column.getHeaderCell().getColumnNumber() == columnNumber)
                {
                    Number value = null;
                    try
                    {
                        value = (Number) column.getValue(false);
                    }
                    catch (ObjectLookupException e)
                    {
                        logger.error(e);
                    }
                    catch (DecoratorException e)
                    {
                        logger.error(e);
                    }
                    if (value != null)
                    {
                        total += value.doubleValue();
                    }
                }
            }
        }
        return total;
    }

    public String getTotalsTdOpen(HeaderCell header, String styleClass)
    {
        return TagConstants.TAG_OPEN
            + TagConstants.TAGNAME_COLUMN
            + " class=\""
            + StringUtils.defaultString(styleClass)
            + "\""
            + TagConstants.TAG_CLOSE;
    }

    public String getTotalsRowOpen()
    {
        return TagConstants.TAG_OPEN + TagConstants.TAGNAME_ROW + " class=\"subtotal\"" + TagConstants.TAG_CLOSE;
    }

    public String getTotalRowLabel(String groupingValue)
    {
        return groupingValue + " Total";
    }

    public String formatTotal(HeaderCell header, double total)
    {
        Object displayValue = new Double(total);
        if (header.getColumnDecorators().length > 0)
        {
            for (int i = 0; i < header.getColumnDecorators().length; i++)
            {
                DisplaytagColumnDecorator decorator = header.getColumnDecorators()[i];
                try
                {
                    displayValue = decorator.decorate(displayValue, this.getPageContext(), tableModel.getMedia());
                }
                catch (DecoratorException e)
                {
                    logger.warn(e.getMessage(), e);
                    // ignore, use undecorated value for totals
                }
            }
        }
        return displayValue.toString();
    }

    class GroupTotals
    {

        /**
         * The label class.
         */
        protected String totalLabelClass = getSubtotalLabelClass();

        /**
         * The value class.
         */
        protected String totalValueClass = getSubtotalValueClass();

        private int columnNumber;

        private int firstRowOfCurrentSet;

        public GroupTotals(int headerCellColumn)
        {
            this.columnNumber = headerCellColumn;
            this.firstRowOfCurrentSet = 0;
        }

        public void printTotals(int currentRow, StringBuffer out)
        {

            // For each column, output:
            List headerCells = tableModel.getHeaderCellList();
            if (firstRowOfCurrentSet < currentRow) // If there is more than one row, show a total
            {
                out.append(getTotalsRowOpen());
                for (Iterator iterator = headerCells.iterator(); iterator.hasNext();)
                {
                    HeaderCell headerCell = (HeaderCell) iterator.next();

                    if (columnNumber == headerCell.getColumnNumber())
                    {
                        // a totals label if it is the column for the current group
                        String currentLabel = getCellValue(columnNumber, firstRowOfCurrentSet);
                        out.append(getTotalsTdOpen(headerCell, getTotalLabelClass() + " group-" + (columnNumber + 1)));
                        out.append(getTotalRowLabel(currentLabel));
                    }
                    else if (headerCell.isTotaled())
                    {
                        // a total if the column should be totaled
                        double total = getTotalForColumn(headerCell.getColumnNumber(), firstRowOfCurrentSet, currentRow);
                        out.append(getTotalsTdOpen(headerCell, getTotalValueClass() + " group-" + (columnNumber + 1)));
                        out.append(formatTotal(headerCell, total));
                    }
                    else
                    {
                        // blank, if it is not a totals column
                        String style = "group-" + (columnNumber + 1);
                        if (headerCell.getColumnNumber() < innermostGroup)
                        {
                            style += " " + getTotalLabelClass() + " ";
                        }
                        out.append(getTotalsTdOpen(headerCell, style));
                        out.append("&nbsp;");
                    }
                    out.append(TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN + TagConstants.TAG_CLOSE);
                }
                out.append("</tr>\n");
            }
        }

        public void setStartRow(int i)
        {
            firstRowOfCurrentSet = i;
        }

        public String getTotalLabelClass()
        {
            return totalLabelClass;
        }

        public void setTotalLabelClass(String totalLabelClass)
        {
            this.totalLabelClass = totalLabelClass;
        }

        public String getTotalValueClass()
        {
            return totalValueClass;
        }

        public void setTotalValueClass(String totalValueClass)
        {
            this.totalValueClass = totalValueClass;
        }
    }
}
