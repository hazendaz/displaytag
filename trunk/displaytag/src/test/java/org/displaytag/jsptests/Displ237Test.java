package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-237 - Problems using sorting, defaultsort
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ237Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-237.jsp";
    }

    /**
     * Trying to reproduce an IndexOutOfBoundsException...
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
        assertEquals("Wrong number of tables", 1, tables.length);

        WebLink[] links = response.getLinks();
        assertEquals("Wrong number of links", 6, links.length); // sorting + paging

        response = links[3].click(); // sort again on default sorted column
        response = links[4].click(); // sort on column 2

        response.getTables();
        assertEquals("Wrong number of tables", 1, tables.length);

        links = response.getLinks();
        assertEquals("Wrong number of links", 6, links.length); // sorting + paging

        response = links[0].click(); // go to page 2

        response.getTables();
        assertEquals("Wrong number of tables", 1, tables.length);

        links = response.getLinks();
        assertEquals("Wrong number of links", 6, links.length); // sorting + paging

        response = links[3].click(); // sort again on default sorted column
        response = links[4].click(); // sort on column 2

    }

}