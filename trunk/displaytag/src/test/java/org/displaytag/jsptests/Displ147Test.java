package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Test for DISPL-147.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ147Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-147.jsp";
    }

    /**
     * Test link generated using column attributes.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);
        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebLink[] links = response.getLinks();
        assertEquals("Wrong number of links in result.", 1, links.length);

        assertEquals("Link text is wrong.", "/context/dynlink?param=Raja%26Siva", links[0].getURLString());
    }

}