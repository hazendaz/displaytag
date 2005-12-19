package org.displaytag.decorator;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.conversion.PropertyConvertorFactory;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.TagConstants;

import javax.servlet.jsp.PageContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A TableDecorator that, in conjunction with totaled and grouped columns, produces multi level subtotals on arbitrary
 * String groupings.
 *
 * @author rapruitt
 */
public class TotalsWrapper extends TableDecorator
{
    /**
     * Logger.
     */
    private Log logger = LogFactory.getLog(TotalsWrapper.class);

    /**
     * CSS class appplied to subtotal headers.
     */
    public static final String SUBTOTAL_HEADER_CLASS = "subtotal-header"; 

    /**
     * CSS class applied to subtotal labels.
     */
    public static final String SUBTOTAL_LABEL_CLASS = "subtotal-label";

    /**
     * CSS class applied to subtotal totals.
     */
    public static final String SUBTOTAL_VALUE_CLASS = "subtotal-sum";

    /**
     * Converter for producing numeric cell values.
     */
    private Converter propertyConvertor;

    /**
     * Maps the groups to their current totals.
     */
    private Map groupNumberToGroupTotal = new HashMap();
    /**
     * No current reset group.
     */
    private final static int NO_RESET_GROUP = 4200;
    /**
     * The deepest reset group.
     */
    private int deepestResetGroup = NO_RESET_GROUP;

    /**
     * Controls when the subgroup is ended
     */
    private int innermostGroup;

    /**
     * Holds the header rows and their content for a particular group.
     */
    private List headerRows = new ArrayList(5);

    public TotalsWrapper()
    {
        super();
        TableProperties properties = TableProperties.getInstance(null);
        propertyConvertor = PropertyConvertorFactory.createNumberConverter(properties);
    }

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
                groupNumberToGroupTotal.put(new Integer(headerCell.getGroup()), new GroupTotals(headerCell));
                if (headerCell.getGroup() > innermostGroup)
                {
                    innermostGroup = headerCell.getGroup();
                }
            }
        }
    }

    public void startOfGroup(String value, int group)
    {
        StringBuffer tr = new StringBuffer();
        tr.append("<tr>");
        for (int i = 1; i < group; i++)
        {
            tr.append("<td>&nbsp;</td>\n");
        }
        tr.append("<td class=\"").append(SUBTOTAL_HEADER_CLASS).append(" group-").append(group).append("\">");
        tr.append(value).append("</td>\n");
        tr.append("</tr>");
        headerRows.add(tr);
    }

    public String displayValue(String value, short groupingStatus)
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
                totals.setStartRow(getListIndex()+1);
            }
            returnValue = out.toString();
        }
        else
        {
            returnValue = null;
        }
        deepestResetGroup = NO_RESET_GROUP;
        headerRows.clear();
        return returnValue;
    }

    String getCellValue(int columnNumber, int rowNumber)
    {
        TableModel model = getTableModel();
        List fullList = model.getRowListFull();
        Row row = (Row) fullList.get(rowNumber);
        ColumnIterator columnIterator = row.getColumnIterator(model.getHeaderCellList());
        while (columnIterator.hasNext())
        {
            Column column = columnIterator.nextColumn();
            if (column.getHeaderCell().getColumnNumber() == columnNumber)
            {
                try
                {
                    return column.createChoppedAndLinkedValue();
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

    double getTotalForColumn(int columnNumber, int startRow, int stopRow)
    {
        TableModel model = getTableModel();
        List fullList = model.getRowListFull();
        List window = fullList.subList(startRow, stopRow+1);
        double total = 0;
        for (Iterator iterator = window.iterator(); iterator.hasNext();)
        {
            Row row = (Row) iterator.next();
            ColumnIterator columnIterator = row.getColumnIterator(model.getHeaderCellList());
            while (columnIterator.hasNext())
            {
                Column column = columnIterator.nextColumn();
                if (column.getHeaderCell().getColumnNumber() == columnNumber)
                {
                    Number value;
                    try
                    {
                        value = (Number) propertyConvertor.convert(Number.class, column.createChoppedAndLinkedValue());
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
                    total += value.doubleValue();
                }
            }
        }
        return total;
    }

    public String getTotalsTdOpen(HeaderCell header, String styleClass)
    {
        return TagConstants.TAG_OPEN + TagConstants.TAGNAME_COLUMN + " class=\""
                + StringUtils.defaultString(styleClass) + "\"" + TagConstants.TAG_CLOSE;
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
        return "" + total;
    }


    class GroupTotals
    {
        private int columnNumber;
        private int firstRowOfCurrentSet;

        public GroupTotals(HeaderCell headerCell)
        {
            this.columnNumber = headerCell.getColumnNumber();
            this.firstRowOfCurrentSet = 0;
        }


        public void printTotals(int currentRow, StringBuffer out)
        {

            // For each column, output:
            List headerCells = getTableModel().getHeaderCellList();
            if (firstRowOfCurrentSet < currentRow)  // If there is more than one row, show a total
            {
                out.append(getTotalsRowOpen());
                for (Iterator iterator = headerCells.iterator(); iterator.hasNext();)
                {
                    HeaderCell headerCell = (HeaderCell) iterator.next();


                    if (columnNumber == headerCell.getColumnNumber())
                    {
                        // a totals label if it is the column for the current group
                        String currentLabel = getCellValue(columnNumber, firstRowOfCurrentSet);
                        out.append(getTotalsTdOpen(headerCell, SUBTOTAL_LABEL_CLASS + " group-" + (columnNumber + 1)));
                        out.append(getTotalRowLabel(currentLabel));
                    }
                    else if (headerCell.isTotaled())
                    {
                        // a total if the column should be totaled
                        double total = getTotalForColumn(headerCell.getColumnNumber(), firstRowOfCurrentSet, currentRow);
                        out.append(getTotalsTdOpen(headerCell, SUBTOTAL_VALUE_CLASS + " group-" + (columnNumber + 1)));
                        out.append(formatTotal(headerCell, total));
                    }
                    else
                    {
                        // blank, if it is not a totals column
                        String style = "group-" + (columnNumber + 1);
                        if (headerCell.getColumnNumber() < innermostGroup)
                        {
                            style += " " + SUBTOTAL_LABEL_CLASS + " ";
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
    }
}
