package org.displaytag.jsptests;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Test for DISPL-305 - External paging paging.banner.some_items_found displays 1-10 in the second page (other pages as
 * well).
 * @author Fabrizio Giustina
 * @version $Id: Displ305Test.java 1081 2006-04-03 20:26:34Z fgiust $
 */
public class Displ304Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-304.jsp";
    }

    /**
     * Check sorted column.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);
        ParamEncoder pe = new ParamEncoder("table");

        request.setParameter(pe.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "2");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }
        // <span class="pagebanner">3 items found, displaying 1 to 1.</span>

        HTMLElement pagebanner = response.getElementWithID("pagebanner");
        assertEquals("Wrong page banner", "4|3|4", pagebanner.getText());
    }

}