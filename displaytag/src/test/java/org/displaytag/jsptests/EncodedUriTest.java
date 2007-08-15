package org.displaytag.jsptests;

import org.apache.commons.lang.StringUtils;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for encoded uri.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class EncodedUriTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "requesturi.jsp";
    }

    /**
     * Test link generated using requestUri.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        assertEquals("utf-8", System.getProperty("file.encoding"));

        WebRequest request = new GetMethodWebRequest(jspName);
        request.setParameter("city", "MünchenXX");
        request.setHeaderField("Content-Type", "text/html; charset=utf-8");

        // just check that everything is ok before reaching displaytag
        assertEquals("MünchenXX", request.getParameter("city"));
        WebResponse response = runner.getResponse(request);

        assertEquals("utf-8", response.getCharacterSet());

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables.", 1, tables.length);

        WebLink[] links = response.getLinks();
        assertEquals("Wrong number of links in result.", 4, links.length);

        String expected = "M%C3%BCnchen";

        String actual = StringUtils.substringBetween(links[0].getURLString(), "city=", "XX");

        assertEquals(expected, actual);
    }
}