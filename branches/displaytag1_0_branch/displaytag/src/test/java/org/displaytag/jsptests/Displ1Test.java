package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-1 - Autolink and maxlength problem.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ1Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-1.jsp";
    }

    /**
     * Check the content of the title attribute.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response = runner.getResponse(request);

        WebTable[] tables = response.getTables();
        assertEquals("Expected 1 table in result.", 1, tables.length);
        assertEquals("Wrong title in column", "averylongemail@mail.com", tables[0].getTableCell(1, 0).getTitle());

        WebLink[] links = tables[0].getTableCell(1, 0).getLinks();
        assertEquals("Expected link not found", 1, links.length);
        assertEquals("Wrong text in link", "averylongemail@...", links[0].getText());
        assertEquals("Wrong url in link", "mailto:averylongemail@mail.com", links[0].getURLString());
    }

}