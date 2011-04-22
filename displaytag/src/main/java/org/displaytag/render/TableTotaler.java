package org.displaytag.render;

import org.displaytag.model.*;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.exception.DecoratorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * This class just keeps a running grouped total. It does not output anything; it is the responsibility of the exporter
 * or of the decorator to actually output the results.
 * @author rapruitt
 * Date: May 21, 2010
 * Time: 9:17:43 PM
 */
public class  TableTotaler
{
    protected Log logger = LogFactory.getLog(this.getClass());

    public static final TableTotaler NULL  = new TableTotaler();

    protected Map<Integer,Integer> firstRowForEachGroup = new HashMap<Integer,Integer>();
    protected int howManyGroups = 0;
    protected Integer currentRowNumber = 0;
    protected TableModel tableModel;
    List<Integer> openedColumns = new ArrayList<Integer>();    // in excel, i need to know which ones are currently open; in xml, just what has just opened
    TreeMap<Integer,String> groupingValuesByColumn = new TreeMap<Integer,String>();    // in excel, i need to know which ones are currently open; in xml, just what has just opened
    List<Integer> closedColumns = new ArrayList<Integer>();
    /**
     * Magic constant to indicate that we want the whole list, not just a subgroup.
     */
    public static final Integer WHOLE_TABLE = 0;

    public void init(TableModel model)
    {
        this.tableModel = model;
        firstRowForEachGroup = new HashMap<Integer,Integer>();
        for (HeaderCell c : model.getHeaderCellList())
        {
            if (c.getGroup() > 0)
            {
                firstRowForEachGroup.put(c.getGroup(), 0);
                howManyGroups++;
            }
        }

    }

    public void initRow(int currentViewIndex, int currentListIndex)
    {
        openedColumns.clear();
        closedColumns.clear();
        currentRowNumber = currentListIndex;
    }

    /**
     *
     * @param groupNumber
     * @return
     */
    public int asColumn(int groupNumber)
    {
        return groupNumber;
    }

    public int asGroup(int columnNumber)
    {
        return columnNumber;
    }

    public void startGroup(String groupingValue,  int groupNumber)
    {
        openedColumns.add(asColumn(groupNumber));
        groupingValuesByColumn.put(asColumn(groupNumber), groupingValue);
        firstRowForEachGroup.put(groupNumber, currentRowNumber);
    }

    public List<Integer> getOpenedColumns()
    {
        return new ArrayList<Integer>(openedColumns);
    }

    public List<Integer> getClosedColumns()
    {
        return closedColumns;
    }

    public void stopGroup(String value, int groupNumber)
    {
        closedColumns.add(asColumn(groupNumber));
    }


    /**
     * Override locally to perform your own math.
     * @param column
     * @param total
     * @param value
     * @return
     */
    public Object add(Column column, Object total, Object value)
    {
        if (value == null)
        {
            return total;
        }
        else if (value instanceof Number)
        {
            Number oldTotal = (double) 0;
            if (total != null)
            {
                oldTotal = (Number) total;
            }
            return oldTotal.doubleValue() + ((Number) value).doubleValue();
        }
        else
        {
            throw new UnsupportedOperationException("Cannot add a value of "
                + value
                + " in column "
                + column.getHeaderCell().getTitle());
        }
    }

    /**
     * Override locally to format it yourself.
     * @param cell the current cell
     * @param total the current value
     * @return
     */
    public String formatTotal(HeaderCell cell, Object total)
    {
        if (total == null)
        {
            total = "";
        }
        return total instanceof String ? (String) total : total.toString();
    }


    protected Object getTotalForList(List<Row> window, int columnNumber)
    {
        Object total = null;
        for (Row row : window)
        {
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
                    if (value != null && !"".equals(value))
                    {
                        total = add(column, total, value);
                    }
                }
            }
        }
        return total;
    }

    public Object getTotalForColumn(int columnNumber, int groupNumber)
    {
        List<Row> fullList = tableModel.getRowListFull();
        Integer startRow = this.firstRowForEachGroup.get(groupNumber);
        Integer stopRow = currentRowNumber + 1;
        if (groupNumber == WHOLE_TABLE)
        {   // asking for a total for the entire table
            startRow = 0;
        }
        List<Row> window = fullList.subList(startRow, stopRow);
        return getTotalForList(window, columnNumber);
    }

    public String getGroupingValue(Integer columnNumber)
    {
        return groupingValuesByColumn.get(columnNumber);
    }



    public void reset()
    {
        this.closedColumns.clear();
        this.openedColumns.clear();
        this.groupingValuesByColumn.clear();
        this.currentRowNumber = 0;
        this.howManyGroups = 0;
        this.firstRowForEachGroup.clear();
    }
}
