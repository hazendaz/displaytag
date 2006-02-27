package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for DISPL-26 - More params for paging.banner.*_items_found.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ26Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-26.jsp";
    }

    /**
     * Check addictional parameters in paging.banner.*.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response;

        response = runner.getResponse(request);
        assertEquals("Parameters {5} and {6} are not correctly evaluated in paging.banner.first.", "1|3", response
            .getElementWithID("numbers")
            .getText());
        assertEquals(
            "Parameters {4} and {5} are not correctly evaluated in paging.banner.some_items_found.",
            "1|3",
            response.getElementWithID("label").getText());
    }

}