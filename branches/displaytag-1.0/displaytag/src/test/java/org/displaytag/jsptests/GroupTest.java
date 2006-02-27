package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for basic displaytag functionalities.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class GroupTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "group.jsp";
    }

    /**
     * Tests row grouping. bug #923446
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
        assertEquals("Wrong number of rows in table.", 3, tables[0].getRowCount());

        assertEquals("Column not grouped.", "", tables[0].getCellAsText(2, 0));
        assertEquals("Column not grouped.", "", tables[0].getCellAsText(2, 1));
        assertEquals("Column should not be grouped.", KnownValue.CAMEL, tables[0].getCellAsText(2, 2));
    }
}