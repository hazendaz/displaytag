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

import java.util.*;
import java.text.MessageFormat;

import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.ObjectUtils;
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
 * String groupings.  Use it directly, subclass it, or use it as an example to better meet your local needs.
 * @author rapruitt
 * @author Fabrizio Giustina
 */
public class MultilevelTotalTableDecorator extends TableDecorator
{

    /**
     * If there are no columns that are totaled, we should not issue a totals row.
     */
    private boolean containsTotaledColumns = false;

    /**
     * No current reset group.
     */
    private static final int NO_RESET_GROUP = 4200;

    /**
     * Maps the groups to their current totals.
     */
    private Map groupNumberToGroupTotal = new HashMap();

    /**
     * The deepest reset group. Resets on an outer group will force any deeper groups to reset as well.
     */
    private int deepestResetGroup = NO_RESET_GROUP;

    /**
     * Controls when the subgroup is ended.
     */
    protected int innermostGroup;

    /**
     * Logger.
     */
    private Log logger = LogFactory.getLog(MultilevelTotalTableDecorator.class);

    /**
     * CSS class applied to grand total totals.
     */
    protected String grandTotalSum = "grandtotal-sum";

    /**
     * CSS class applied to grand total cells where the column is not totaled.
     */
    protected String grandTotalNoSum = "grandtotal-nosum";

    /**
     * CSS class applied to grand total lablels.
     */
    protected String grandTotalLabel = "grandtotal-label";

    /**
     * Grandtotal description.
     */
    protected String grandTotalDescription = "Grand Total";


    /**
     * CSS class appplied to subtotal headers.
     */
    private String subtotalHeaderClass = "subtotal-header";

    /**
     * CSS class applied to subtotal labels.
     */
    private String subtotalLabelClass = "subtotal-label";

    /**
     * Message format for subtotal descriptions.
     */
    private MessageFormat subtotalDesc = new MessageFormat("{0} Total");

    /**
     * CSS class applied to subtotal totals.
     */
    private String subtotalValueClass = "subtotal-sum";


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
            containsTotaledColumns = containsTotaledColumns || headerCell.isTotaled();
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

    public String getGrandTotalDescription()
    {
        return grandTotalDescription;
    }

    public void setGrandTotalDescription(String grandTotalDescription)
    {
        this.grandTotalDescription = grandTotalDescription;
    }

    /**
     * The pattern to use to generate the subtotal labels.  The grouping value of the cell will be the first arg.
     * The default value is "{0} Total".
     * @param pattern
     * @param locale
     */
    public void setSubtotalLabel(String pattern, Locale locale)
    {
        this.subtotalDesc = new MessageFormat(pattern, locale);
    }

    public String getGrandTotalLabel()
    {
        return grandTotalLabel;
    }

    public String getGrandTotalSum()
    {
        return grandTotalSum;
    }

    public String getGrandTotalNoSum()
    {
        return grandTotalNoSum;
    }

    public void setGrandTotalNoSum(String grandTotalNoSum) 
    {
        this.grandTotalNoSum = grandTotalNoSum;
    }

    public void setGrandTotalSum(String grandTotalSum)
    {
        this.grandTotalSum = grandTotalSum;
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
        if (containsTotaledColumns)
        {
            StringBuffer tr = new StringBuffer();
            tr.append("<tr>");
            GroupTotals groupTotals = (GroupTotals) groupNumberToGroupTotal.get(new Integer(group));
            int myColumnNumber = groupTotals.columnNumber;
            for (int i = 0; i < myColumnNumber; i++)
            {
                tr.append("<td></td>\n");
            }
            tr.append("<td class=\"").append(getSubtotalHeaderClass()).append(" group-").append(group).append("\" >");
            tr.append(value).append("</td>\n");
            List headerCells = tableModel.getHeaderCellList();
            for (int i = myColumnNumber; i < headerCells.size() - 1; i++)
            {
                tr.append("<td></td>\n");
            }
            tr.append("</tr>\n");
            headerRows.add(tr);
        }
    }

    public String displayGroupedValue(String value, short groupingStatus, int columnNumber)
    {
//        if (groupingStatus == TableWriterTemplate.GROUP_START_AND_END && columnNumber > 1)
//        {
//            return value;
//        }
//        else
//        {
            return "";
//        }
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
        String returnValue = "";
        if (containsTotaledColumns)
        {
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
        }
        return returnValue;
    }

    /**
     * Issue a grand total row at the bottom.
     * @return the suitable string
     */
    protected String totalAllRows()
    {
        if (containsTotaledColumns)
        {
            List headerCells = tableModel.getHeaderCellList();
            StringBuffer output = new StringBuffer();
            int currentRow = getListIndex();
            output.append(TagConstants.TAG_OPEN + TagConstants.TAGNAME_ROW
                    + " class=\"grandtotal-row\"" + TagConstants.TAG_CLOSE);
            boolean first = true;
            for (Iterator iterator = headerCells.iterator(); iterator.hasNext();)
            {
                HeaderCell headerCell = (HeaderCell) iterator.next();
                if (first)
                {
                    output.append(getTotalsTdOpen(headerCell, getGrandTotalLabel()));
                    output.append(getGrandTotalDescription());
                    first = false;
                }
                else if (headerCell.isTotaled())
                {
                    // a total if the column should be totaled
                    Object total = getTotalForColumn(headerCell.getColumnNumber(), 0, currentRow);
                    output.append(getTotalsTdOpen(headerCell, getGrandTotalSum()));
                    output.append(formatTotal(headerCell, total));
                }
                else
                {
                    // blank, if it is not a totals column
                    output.append(getTotalsTdOpen(headerCell, getGrandTotalNoSum()));
                }
                output.append(TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN + TagConstants.TAG_CLOSE);
            }
            output.append("\n</tr>\n");

            return output.toString();
        }
        else
        {
            return "";
        }
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

    protected Object getTotalForColumn(int columnNumber, int startRow, int stopRow)
    {
        List fullList = tableModel.getRowListFull();
        List window = fullList.subList(startRow, stopRow + 1);
        Object total = null;
        for (Iterator iterator = window.iterator(); iterator.hasNext();)
        {
            Row row = (Row) iterator.next();
            ColumnIterator columnIterator = row.getColumnIterator(tableModel.getHeaderCellList());
            while (columnIterator.hasNext())
            {
                Column column = columnIterator.nextColumn();
                if (column.getHeaderCell().getColumnNumber() == columnNumber)
                {
                    Object value = null;
                    try
                    {
                        value = column.getValue(false);
                    }
                    catch (ObjectLookupException e)
                    {
                        logger.error(e);
                    }
                    catch (DecoratorException e)
                    {
                        logger.error(e);
                    }
                    if (value != null && ! TagConstants.EMPTY_STRING.equals(value))
                    {
                        total = add(column, total, value);
                    }
                }
            }
        }
        return total;
    }

    protected Object add(Column column, Object total, Object value) {
        if (value == null)
        {
            return total;
        }
        else if (value instanceof Number)
        {
            Number oldTotal = new Double(0);
            if (total != null)
            {
                oldTotal = (Number)total;
            }
            return new Double(oldTotal.doubleValue() + ((Number) value).doubleValue());
        }
        else
        {
            throw new UnsupportedOperationException("Cannot add a value of " + value + " in column " + column.getHeaderCell().getTitle());
        }
    }

    public String getTotalsTdOpen(HeaderCell header, String totalClass)
    {

        String cssClass = ObjectUtils.toString(header.getHtmlAttributes().get("class"));

        StringBuffer buffer = new StringBuffer();
        buffer.append(TagConstants.TAG_OPEN);
        buffer.append(TagConstants.TAGNAME_COLUMN);
        if (cssClass != null || totalClass != null)
        {
            buffer.append(" class=\"");

            if (cssClass != null)
            {
                buffer.append(cssClass);
                if (totalClass != null)
                {
                    buffer.append(" ");
                }
            }
            if (totalClass != null)
            {
                buffer.append(totalClass);
            }
            buffer.append("\"");
        }
        buffer.append(TagConstants.TAG_CLOSE);
        return buffer.toString();
    }

    public String getTotalsRowOpen()
    {
        return TagConstants.TAG_OPEN + TagConstants.TAGNAME_ROW + " class=\"subtotal\"" + TagConstants.TAG_CLOSE;
    }

    public String getTotalRowLabel(String groupingValue)
    {
        return subtotalDesc.format(new Object[]{groupingValue});
    }

    public String formatTotal(HeaderCell header, Object total)
    {
        Object displayValue = total;
        if (header.getColumnDecorators().length > 0)
        {
            for (int i = 0; i < header.getColumnDecorators().length; i++)
            {
                DisplaytagColumnDecorator decorator = header.getColumnDecorators()[i];
                try
                {
                    displayValue = decorator.decorate(total, this.getPageContext(), tableModel.getMedia());
                }
                catch (DecoratorException e)
                {
                    logger.warn(e.getMessage(), e);
                    // ignore, use undecorated value for totals
                }
            }
        }
        return displayValue != null ? displayValue.toString() : "";
    }

    class GroupTotals
    {

        /**
         * The label class.
         */
        protected String totalLabelClass = getSubtotalLabelClass();
        /**
         * The row opener
         */
        protected String totalsRowOpen = getTotalsRowOpen();

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
                out.append(totalsRowOpen);
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
                        Object total = getTotalForColumn(headerCell.getColumnNumber(),
                                firstRowOfCurrentSet, currentRow);
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
                    }
                    out.append(TagConstants.TAG_OPENCLOSING + TagConstants.TAGNAME_COLUMN + TagConstants.TAG_CLOSE);
                }
                out.append("\n</tr>\n");
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

        public void setTotalsRowOpen(String totalsRowOpen)
        {
            this.totalsRowOpen = totalsRowOpen;
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
