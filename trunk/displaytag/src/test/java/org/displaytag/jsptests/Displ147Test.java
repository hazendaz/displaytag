package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

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
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));

        WebResponse response = runner.getResponse(request);
        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebLink[] links = response.getLinks();
        Assert.assertEquals("Wrong number of links in result.", 1, links.length);

        Assert.assertEquals("Link text is wrong.", "/context/dynlink?param=Raja%26Siva", links[0].getURLString());
    }

}