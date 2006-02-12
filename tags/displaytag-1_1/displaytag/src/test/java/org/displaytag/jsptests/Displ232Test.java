package org.displaytag.jsptests;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Test for DISPL-232 - paging.banner.full: {6} is not the total number of pages.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ232Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-232.jsp";
    }

    /**
     * Paging banner should contain [10]
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        ParamEncoder encoder = new ParamEncoder("table");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "3");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        HTMLElement pagination = response.getElementWithID("pagination");
        assertNotNull("Paging banner not found.", pagination);
        assertEquals("Total number of pages is not displayted properly.", "[10]", pagination.getText());

        request = new GetMethodWebRequest(jspName);
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "10");
        response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        pagination = response.getElementWithID("pagination");
        assertNotNull("Paging banner not found.", pagination);
        assertEquals("Total number of pages is not displayted properly.", "[10]", pagination.getText());

    }

}