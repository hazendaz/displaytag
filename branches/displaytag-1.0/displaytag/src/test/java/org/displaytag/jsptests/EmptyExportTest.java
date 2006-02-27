package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests export with a null list.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class EmptyExportTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "emptyexport.jsp";
    }

    /**
     * Test that export links are not shown on generated page.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);

        assertEquals("Export links should not be shown for an empty table", 0, response.getLinks().length);

    }
}