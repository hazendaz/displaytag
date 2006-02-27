package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-58 - Additional attribute: specify property used for sorting.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ58Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-58.jsp";
    }

    /**
     * Check sorted columns.
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
        assertEquals("Wrong number of tables.", 4, tables.length);

        for (int j = 0; j < tables.length; j++)
        {
            WebTable table = tables[j];
            assertEquals("Wrong number of columns in table." + j, 2, table.getColumnCount());
            assertEquals("Wrong number of rows in table." + j, 5, table.getRowCount());

            for (int u = 1; u < 5; u++)
            {
                assertEquals("Wrong value in table cell.", Integer.toString(u), table.getCellAsText(u, 1));
            }
        }

    }
}