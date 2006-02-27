package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-085 - Dynamic Column Creation
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ085Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-085.jsp";
    }

    /**
     * A simple way for creating columns on the fly using jstl.
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

        assertEquals("Wrong number of columns.", 2, tables[0].getColumnCount());

        assertEquals("Wrong title.", "ant title", tables[0].getCellAsText(0, 0));
        assertEquals("Wrong title.", "bee title", tables[0].getCellAsText(0, 1));

        assertEquals("Wrong content.", "ant", tables[0].getCellAsText(1, 0));
        assertEquals("Wrong content.", "bee", tables[0].getCellAsText(1, 1));

        // only one sortable column
        assertEquals("Wrong number of links.", 1, response.getLinks().length);

    }

}