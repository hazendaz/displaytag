package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for DISPL-9 - Send user back to Page 1 on Desc/Asc.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ9Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-9.jsp";
    }

    /**
     * Verifies that the generated page contains a table with the expected number of columns.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response;

        // step 0
        response = runner.getResponse(request);

        // link[0] is a column header
        // link[1] is paging link

        // 1. User clicks column 3 header to sort ascending. User returned to page 1.
        response = response.getLinks()[0].click();
        assertNotNull("Not in page one as expected", response.getElementWithID("PAGEONE"));

        // 2. User clicks column 3 header to sort descending. User returned to page 1.
        response = response.getLinks()[0].click();
        assertNotNull("Not in page one as expected", response.getElementWithID("PAGEONE"));

        // 3. User navigates to page other than page 1.
        response = response.getLinks()[1].click();
        assertNotNull("Not in page two as expected", response.getElementWithID("OTHERPAGE"));

        // 4. User clicks column 3 header to sort ascending. User NOT returned to page 1, rather, user stays on current
        // page number.
        response = response.getLinks()[0].click();
        assertNotNull("Not in page one as expected", response.getElementWithID("PAGEONE"));
    }

}