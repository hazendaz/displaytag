package org.displaytag.jsptests;

import org.apache.commons.lang.StringUtils;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Basic tests for pagination.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PaginationAllItemsTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "pagination-all-items.jsp";
    }

    /**
     * Just a quick test to assure that you can avoid page numbers when displaying one page.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        WebLink[] links = response.getLinks();

        assertEquals("Wrong number of links in result.", 0, links.length);
        assertFalse("Using setProperty you should not see any page number", StringUtils.contains(
            response.getText(),
            ">1<"));

    }
}