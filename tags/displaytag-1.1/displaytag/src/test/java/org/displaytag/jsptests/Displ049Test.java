package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-49 - style of column cannot be dynamically changed
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ049Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-049.jsp";
    }

    /**
     * Check variable style and class attributes.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables in result.", 1, tables.length);
        assertEquals("Wrong number of rows in result.", 3, tables[0].getRowCount());

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        TableCell row1Cell = tables[0].getTableCell(1, 0);
        assertEquals("Wrong style attribute.", "style-1", row1Cell.getAttribute("style"));
        assertEquals("Wrong class attribute.", "class-1", row1Cell.getClassName());

        TableCell row2Cell = tables[0].getTableCell(2, 0);
        assertEquals("Wrong style attribute.", "style-2", row2Cell.getAttribute("style"));
        assertEquals("Wrong class attribute.", "class-2", row2Cell.getClassName());
    }
}