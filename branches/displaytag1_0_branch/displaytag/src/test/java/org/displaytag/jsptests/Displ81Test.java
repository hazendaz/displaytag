package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-81 - using ColumnDecorator with tag body.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ81Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-81.jsp";
    }

    /**
     * Check that column body is decorated.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response = runner.getResponse(request);

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables.", 1, tables.length);

        assertEquals("Wrong number of columns.", 2, tables[0].getColumnCount());
        assertEquals("Wrong number of rows.", 2, tables[0].getRowCount());

        assertEquals("Wrong text in column 1", "decorated: ant", tables[0].getCellAsText(1, 0));
        assertEquals("Wrong text in column 2", "decorated: body", tables[0].getCellAsText(1, 1));
    }

}