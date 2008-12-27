package org.displaytag.jsptests;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.URLAssert;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Testcase for #944056.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PaginationLinksTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "pagination-links.jsp";
    }

    /**
     * Checks generated pagination links.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName
            + "?initiator=AVINASH&wfid=&approvedTDate=&initiatedFDate=&status=default"
            + "&initiatedTDate=04/28/2004&approvedFDate=&method=search&approver=");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        WebLink[] links = response.getLinks();

        assertEquals("Wrong number of pagination links", 36, links.length);

        String lastLink = links[links.length - 1].getURLString();

        // remove prefix
        lastLink = lastLink.substring(lastLink.indexOf(getJspName()), lastLink.length());

        String encodedParam = new ParamEncoder("table2").encodeParameterName(TableTagParameters.PARAMETER_PAGE);

        String expected = "pagination-links.jsp?initiator=AVINASH&wfid=&approvedTDate=&initiatedFDate=&status=default"
            + "&initiatedTDate=04%2F28%2F2004&approvedFDate=&method=search&approver=&"
            + encodedParam
            + "=12";

        URLAssert.assertEquals(expected, lastLink);

    }
}