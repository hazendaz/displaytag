package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests with a null list.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class EmptyTableTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "empty.jsp";
    }

    /**
     * Verifies that the generated page doesn't contain any table (but doesn't crash!).
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

        // only the second table should be shown
        assertEquals("Wrong number of tables.", 1, tables.length);
        assertEquals("Empty table message: colspan should be 2", 2, tables[0].getTableCell(1, 0).getColSpan());
    }
}