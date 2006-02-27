package org.displaytag.jsptests;

import org.apache.commons.lang.SystemUtils;
import org.displaytag.properties.MediaTypeEnum;
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
        WebRequest request = new GetMethodWebRequest(jspName);
        request.setParameter("city", "München");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Expected one table in result.", 1, tables.length);

        WebLink[] links = response.getLinks();
        assertEquals("Wrong number of links in result.", MediaTypeEnum.getSize() - 1, links.length);

        if (SystemUtils.isJavaVersionAtLeast(1.4f))
        {
            assertTrue("Encoded parameter in link is wrong: " + links[0].getURLString(), links[0]
                .getURLString()
                .indexOf("M%C3%BCnchen") > -1);
        }
        else
        {

            log.info("Warning, jse 1.4 not available. Encoding used in link will be wrong");
            assertTrue("Encoded parameter in link is wrong: " + links[0].getURLString(), links[0]
                .getURLString()
                .indexOf("M%FCnchen") > -1);
        }

    }
}