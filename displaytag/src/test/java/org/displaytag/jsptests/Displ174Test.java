package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-174 - sometimes "title" - sometimes not
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ174Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-174.jsp";
    }

    /**
     * Decorated object based on a pageContext attribute
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

        assertEquals("Wrong value", "1234567890...", tables[0].getCellAsText(1, 0));
        assertEquals("Wrong title", "1234567890123", tables[0].getTableCell(1, 0).getAttribute("title"));

        assertEquals("Wrong value", "1234567890", tables[0].getCellAsText(1, 1));
        assertEquals("Wrong title", "", tables[0].getTableCell(1, 1).getAttribute("title"));

    }

}