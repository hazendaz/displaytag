package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for dynamic links.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ColumnLinksTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "columnlinks.jsp";
    }

    /**
     * Test link generated using column attributes.
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
        assertEquals("Expected one table in result.", 1, tables.length);

        WebLink[] links = response.getLinks();
        assertEquals("Wrong number of links in result.", 6, links.length);

        assertEquals("Text in first link is wrong.", "/context/dynlink?param=ant", links[0].getURLString());
        assertEquals("Text in second link is wrong.", "/context/dynlink?param=ant", links[1].getURLString());
        assertEquals("Text in third link is wrong.", "dynlink?param=ant", links[2].getURLString());
        assertEquals("Text in fourth link is wrong.", "http://something/dynlink?param=ant", links[3].getURLString());
        assertEquals("Text in fifth link is wrong.", "http://something/dynlink", links[4].getURLString());
        assertEquals("Text in sixth link is wrong.", "/context/dynlink", links[5].getURLString());
    }

}