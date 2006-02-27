package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-161 - "null" still displayed even when nulls="false".
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ161Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-161.jsp";
    }

    /**
     * No exception when an invalid page is requested.
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
        assertEquals("Wrong number of tables.", 1, tables.length);

        assertEquals("Wrong content for first column", "", tables[0].getCellAsText(1, 0));
        assertEquals("Wrong content for second column", "", tables[0].getCellAsText(1, 1));
        assertEquals("Wrong content for third column", "null", tables[0].getCellAsText(1, 2));
    }

}