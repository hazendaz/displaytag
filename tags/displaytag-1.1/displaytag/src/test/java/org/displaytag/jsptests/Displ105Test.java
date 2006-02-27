package org.displaytag.jsptests;

import org.apache.commons.lang.StringUtils;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for DISPL-105: https hrefs in Table get generated as http.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ105Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-105.jsp";
    }

    /**
     * Generated link should be https.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        String httpsUrl = StringUtils.replace(jspName, "http://", "https://");
        WebRequest request = new GetMethodWebRequest(httpsUrl);

        WebResponse response = runner.getResponse(request);

        WebLink[] links = response.getLinks();
        assertEquals("Wrong number of generated links.", 1, links.length);

        assertTrue("Generated link doesn't start with https: " + links[0].getURLString(), links[0]
            .getURLString()
            .startsWith("https://"));

    }

}