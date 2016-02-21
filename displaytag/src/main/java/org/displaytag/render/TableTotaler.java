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
package org.displaytag.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;

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
        this.firstRowForEachGroup = new HashMap<Integer,Integer>();
        for (HeaderCell c : model.getHeaderCellList())
        {
            if (c.getGroup() > 0)
            {
                this.firstRowForEachGroup.put(c.getGroup(), 0);
                this.howManyGroups++;
            }
        }

    }

    public void initRow(int currentViewIndex, int currentListIndex)
    {
        this.openedColumns.clear();
        this.closedColumns.clear();
        this.currentRowNumber = currentListIndex;
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
        this.openedColumns.add(asColumn(groupNumber));
        this.groupingValuesByColumn.put(asColumn(groupNumber), groupingValue);
        this.firstRowForEachGroup.put(groupNumber, this.currentRowNumber);
    }

    public List<Integer> getOpenedColumns()
    {
        return new ArrayList<Integer>(this.openedColumns);
    }

    public List<Integer> getClosedColumns()
    {
        return this.closedColumns;
    }

    public void stopGroup(String value, int groupNumber)
    {
        this.closedColumns.add(asColumn(groupNumber));
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
            ColumnIterator columnIterator = row.getColumnIterator(this.tableModel.getHeaderCellList());
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
                        this.logger.error(e);
                    }
                    catch (DecoratorException e)
                    {
                        this.logger.error(e);
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
        List<Row> fullList = this.tableModel.getRowListFull();
        Integer startRow = this.firstRowForEachGroup.get(groupNumber);
        Integer stopRow = this.currentRowNumber + 1;
        if (groupNumber == WHOLE_TABLE)
        {   // asking for a total for the entire table
            startRow = 0;
        }
        List<Row> window = fullList.subList(startRow, stopRow);
        return getTotalForList(window, columnNumber);
    }

    public String getGroupingValue(Integer columnNumber)
    {
        return this.groupingValuesByColumn.get(columnNumber);
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
