/*
 * fgiust
 * PATCHED to add support for table rows
 */
package com.meterware.httpunit;

import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.Element;


/**
 * A single row in an HTML table.
 */
public class TableRow extends BlockElement
{

    TableRow(
        WebResponse response,
        FrameSelector frame,
        Element tablerowNode,
        URL url,
        String parentTarget,
        String characterSet)
    {
        super(response, frame, url, parentTarget, tablerowNode, characterSet);
    }

    public TableCell[] getCells()
    {
        return (TableCell[]) _cells.toArray(new TableCell[_cells.size()]);
    }

    void addTableCell(TableCell cell)
    {
        _cells.add(cell);
    }

    TableCell newTableCell(Element element)
    {
        return new TableCell(this.getResponse(), _frame, element, _baseURL, _baseTarget, _characterSet);
    }

    private ArrayList _cells = new ArrayList();

}