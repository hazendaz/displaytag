package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for the htmlId attribute.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class HtmlIdTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "htmlid.jsp";
    }

    /**
     * Check content and ids in generated tables.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response;

        response = runner.getResponse(request);

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables in result.", 3, tables.length);

        for (int j = 0; j < tables.length; j++)
        {
            assertEquals("invalid id", "html" + (j + 1), tables[j].getID());
            assertEquals("Unexpected value in table cell", "bee", tables[j].getCellAsText(1, 0));
        }

    }
}