package org.displaytag.tags;

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
     * Instantiates a new test case.
     * @param name test name
     */
    public EmptyExportTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/tld11/emptyexport.jsp";
    }

    /**
     * Test that export links are not shown on generated page.
     * @throws Exception any axception thrown during test.
     */
    public void testJsp() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspName());

        WebResponse response = runner.getResponse(request);

        assertEquals("Export links should not be shown for an empty table", 0, response.getLinks().length);

    }
}