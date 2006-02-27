package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * A table with a single row.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class OneRowOnlyTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "onerow.jsp";
    }

    /**
     * Checks for the expected values in columns.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        WebTable[] tables = response.getTables();

        assertEquals("Expected one table in result.", 1, tables.length);

        assertEquals("Bad number of generated columns.", 2, tables[0].getColumnCount());
        assertEquals("Bad number of generated rows.", 2, tables[0].getRowCount());

        assertEquals("Bad content in column 1.", "ant", tables[0].getCellAsText(1, 0));
        assertEquals("Bad content in column 2.", "bee", tables[0].getCellAsText(1, 1));
    }
}