package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-68 - Allow row object to be of type Collection.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ068Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-068.jsp";
    }

    /**
     * Test with a list of lists.
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

        assertEquals("Wrong column content.", "one", tables[0].getCellAsText(1, 0));
        assertEquals("Wrong column content.", "two", tables[0].getCellAsText(1, 1));
        assertEquals("Wrong column content.", "one", tables[0].getCellAsText(2, 0));
        assertEquals("Wrong column content.", "two", tables[0].getCellAsText(2, 1));
    }

}