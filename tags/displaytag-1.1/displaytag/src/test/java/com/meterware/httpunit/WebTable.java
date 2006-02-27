/*
 * fgiust
 * PATCHED to add support for table rows
 */
package com.meterware.httpunit;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.meterware.httpunit.scripting.ScriptableDelegate;


/**
 * This class represents a table in an HTML page.
 * @author <a href="mailto:russgold@httpunit.org">Russell Gold</a>
 * @author <a href="mailto:bx@bigfoot.com">Benoit Xhenseval</a>
 */
public class WebTable extends HTMLElementBase
{

    /** Predicate to match the complete text of a table's first non-blank cell. * */
    public final static HTMLElementPredicate MATCH_FIRST_NONBLANK_CELL;

    /** Predicate to match a prefix of a table's first non-blank cell. * */
    public final static HTMLElementPredicate MATCH_FIRST_NONBLANK_CELL_PREFIX;

    /** Predicate to match a table's summary attribute. * */
    public final static HTMLElementPredicate MATCH_SUMMARY;

    /** Predicate to match a table's ID. * */
    public final static HTMLElementPredicate MATCH_ID;

    /**
     * Returns the number of rows in the table.
     */
    public int getRowCount()
    {
        return getCells().length;
    }

    private TableCell[][] getCells()
    {
        if (_cells == null)
            readTable();
        return _cells;

    }

    /**
     * Returns the number of columns in the table.
     */
    public int getColumnCount()
    {
        if (getCells().length == 0)
            return 0;
        return getCells()[0].length;
    }

    /**
     * Returns the contents of the specified table cell as text. The row and column numbers are zero-based.
     * @throws IndexOutOfBoundsException if the specified cell numbers are not valid
     */
    public String getCellAsText(int row, int column)
    {
        TableCell cell = getTableCell(row, column);
        return (cell == null) ? "" : cell.getText();
    }

    /**
     * Returns the contents of the specified table cell as text. The row and column numbers are zero-based.
     * @throws IndexOutOfBoundsException if the specified cell numbers are not valid
     */
    public TableCell getTableCell(int row, int column)
    {
        return getCells()[row][column];
    }

    /**
     * Returns the contents of the specified table cell with a given ID
     * @return TableCell with given ID or null if ID is not found.
     */
    public TableCell getTableCellWithID(String id)
    {
        for (int i = 0; i < getRowCount(); i++)
        {
            for (int j = 0; j < getColumnCount(); j++)
            {
                final TableCell tableCell = getCells()[i][j];
                if (tableCell != null && tableCell.getID().equals(id))
                    return tableCell;
            }
        }
        return null;
    }

    /**
     * Removes all rows and all columns from this table which have no visible text in them.
     */
    public void purgeEmptyCells()
    {
        int numRowsWithText = 0;
        int numColumnsWithText = 0;
        boolean rowHasText[] = new boolean[getRowCount()];
        boolean columnHasText[] = new boolean[getColumnCount()];
        Hashtable spanningCells = new Hashtable();

        // look for rows and columns with any text in a non-spanning cell
        for (int i = 0; i < rowHasText.length; i++)
        {
            for (int j = 0; j < columnHasText.length; j++)
            {
                if (getCellAsText(i, j).trim().length() == 0)
                    continue;
                if (getTableCell(i, j).getColSpan() == 1 && getTableCell(i, j).getRowSpan() == 1)
                {
                    if (!rowHasText[i])
                        numRowsWithText++;
                    if (!columnHasText[j])
                        numColumnsWithText++;
                    rowHasText[i] = columnHasText[j] = true;
                }
                else if (!spanningCells.containsKey(getTableCell(i, j)))
                {
                    spanningCells.put(getTableCell(i, j), new int[]{i, j});
                }
            }
        }

        // look for requirements to keep spanning cells: special processing is needed if either:
        // none of its rows already have text, or none of its columns already have text.
        for (Enumeration e = spanningCells.keys(); e.hasMoreElements();)
        {
            TableCell cell = (TableCell) e.nextElement();
            int coords[] = (int[]) spanningCells.get(cell);
            boolean neededInRow = true;
            boolean neededInCol = true;
            for (int i = coords[0]; neededInRow && (i < rowHasText.length) && (i < coords[0] + cell.getRowSpan()); i++)
            {
                neededInRow = !rowHasText[i];
            }
            for (int j = coords[1]; neededInCol && (j < columnHasText.length) && (j < coords[1] + cell.getColSpan()); j++)
            {
                neededInCol = !columnHasText[j];
            }
            if (neededInRow)
            {
                rowHasText[coords[0]] = true;
                numRowsWithText++;
            }
            if (neededInCol)
            {
                columnHasText[coords[1]] = true;
                numColumnsWithText++;
            }
        }

        TableCell[][] remainingCells = new TableCell[numRowsWithText][numColumnsWithText];

        int targetRow = 0;
        for (int i = 0; i < rowHasText.length; i++)
        {
            if (!rowHasText[i])
                continue;
            int targetColumn = 0;
            for (int j = 0; j < columnHasText.length; j++)
            {
                if (!columnHasText[j])
                    continue;
                remainingCells[targetRow][targetColumn++] = _cells[i][j];
            }
            targetRow++;
        }

        _cells = remainingCells;

    }

    /**
     * Returns a rendering of this table with all cells converted to text.
     */
    public String[][] asText()
    {
        String[][] result = new String[getRowCount()][getColumnCount()];

        for (int i = 0; i < result.length; i++)
        {
            for (int j = 0; j < result[0].length; j++)
            {
                result[i][j] = getCellAsText(i, j);
            }
        }
        return result;
    }

    /**
     * Returns the summary attribute associated with this table.
     */
    public String getSummary()
    {
        return NodeUtils.getNodeAttribute(_dom, "summary");
    }

    public String toString()
    {
        String eol = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer(HttpUnitUtils.DEFAULT_TEXT_BUFFER_SIZE).append("WebTable:").append(eol);
        for (int i = 0; i < getCells().length; i++)
        {
            sb.append("[").append(i).append("]: ");
            for (int j = 0; j < getCells()[i].length; j++)
            {
                sb.append("  [").append(j).append("]=");
                if (getCells()[i][j] == null)
                {
                    sb.append("null");
                }
                else
                {
                    sb.append(getCells()[i][j].getText());
                }
            }
            sb.append(eol);
        }
        return sb.toString();
    }

    protected ScriptableDelegate newScriptable()
    {
        return new HTMLElementScriptable(this);
    }

    protected ScriptableDelegate getParentDelegate()
    {
        return _response.getScriptableObject().getDocument();
    }

    // ----------------------------------- private members -----------------------------------

    private Element _dom;

    URL _url;

    FrameSelector _frameName;

    String _baseTarget;

    String _characterSet;

    WebResponse _response;

    private TableCell[][] _cells;

    WebTable(
        WebResponse response,
        FrameSelector frame,
        Node domTreeRoot,
        URL sourceURL,
        String baseTarget,
        String characterSet)
    {
        super(domTreeRoot);
        _response = response;
        _frameName = frame;
        _dom = (Element) domTreeRoot;
        _url = sourceURL;
        _baseTarget = baseTarget;
        _characterSet = characterSet;
    }

    private void readTable()
    {
        TableRow[] rows = getRows();
        int[] columnsRequired = new int[rows.length];

        for (int i = 0; i < rows.length; i++)
        {
            TableCell[] cells = rows[i].getCells();
            for (int j = 0; j < cells.length; j++)
            {
                int spannedRows = Math.min(columnsRequired.length - i, cells[j].getRowSpan());
                for (int k = 0; k < spannedRows; k++)
                {
                    columnsRequired[i + k] += cells[j].getColSpan();
                }
            }
        }
        int numColumns = 0;
        for (int i = 0; i < columnsRequired.length; i++)
        {
            numColumns = Math.max(numColumns, columnsRequired[i]);
        }

        _cells = new TableCell[columnsRequired.length][numColumns];

        for (int i = 0; i < rows.length; i++)
        {
            TableCell[] cells = rows[i].getCells();
            for (int j = 0; j < cells.length; j++)
            {
                int spannedRows = Math.min(columnsRequired.length - i, cells[j].getRowSpan());
                for (int k = 0; k < spannedRows; k++)
                {
                    for (int l = 0; l < cells[j].getColSpan(); l++)
                    {
                        placeCell(i + k, j + l, cells[j]);
                    }
                }
            }
        }
    }

    private void placeCell(int row, int column, TableCell cell)
    {
        while (_cells[row][column] != null)
            column++;
        _cells[row][column] = cell;
    }

    private ArrayList _rows = new ArrayList();

    void addRow(TableRow tableRow)
    {
        _cells = null;
        _rows.add(tableRow);
    }

    TableRow newTableRow(Element element)
    {
        return new TableRow(_response, _frameName, element, _url, _baseTarget, _characterSet);
    }

    public TableRow[] getRows()
    {
        return (TableRow[]) _rows.toArray(new TableRow[_rows.size()]);
    }

    static
    {
        MATCH_FIRST_NONBLANK_CELL = new HTMLElementPredicate()
        { // XXX find a way to do this w/o purging the table cells

            public boolean matchesCriteria(Object htmlElement, Object criteria)
            {
                WebTable table = ((WebTable) htmlElement);
                table.purgeEmptyCells();
                return table.getRowCount() > 0
                    && HttpUnitUtils.matches(table.getCellAsText(0, 0).trim(), (String) criteria);
            };
        };

        MATCH_FIRST_NONBLANK_CELL_PREFIX = new HTMLElementPredicate()
        { // XXX find a way to do this w/o purging the table cells

            public boolean matchesCriteria(Object htmlElement, Object criteria)
            {
                WebTable table = ((WebTable) htmlElement);
                table.purgeEmptyCells();
                return table.getRowCount() > 0
                    && HttpUnitUtils.hasPrefix(table.getCellAsText(0, 0).toUpperCase().trim(), (String) criteria);
            };
        };

        MATCH_ID = new HTMLElementPredicate()
        {

            public boolean matchesCriteria(Object htmlElement, Object criteria)
            {
                return HttpUnitUtils.matches(((WebTable) htmlElement).getID(), (String) criteria);
            };
        };

        MATCH_SUMMARY = new HTMLElementPredicate()
        {

            public boolean matchesCriteria(Object htmlElement, Object criteria)
            {
                return HttpUnitUtils.matches(((WebTable) htmlElement).getSummary(), (String) criteria);
            };
        };

    }

}
