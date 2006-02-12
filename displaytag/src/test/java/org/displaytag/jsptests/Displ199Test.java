package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-199 - Column Summation
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ199Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-199.jsp";
    }

    /**
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

        HTMLElement divtotal = response.getElementWithID("divtotal");
        assertEquals("7.0", divtotal.getText());

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables.", 1, tables.length);
        WebTable table = tables[0];
        assertEquals("Wrong number of rows.", 5, table.getRowCount());
        assertEquals("Total not found in las row.", "7.0", table.getCellAsText(4, 0));

    }

}