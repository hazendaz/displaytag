package org.displaytag.jsptests;

import org.apache.commons.lang.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for DISPL-105: https hrefs in Table get generated as http.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ105Test extends DisplaytagCase
{

    /**
     * Generated link should be https.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Test
    public void doTest() throws Exception
    {
        String httpsUrl = StringUtils.replace("DISPL-105.jsp", "http://", "https://");
        WebRequest request = new GetMethodWebRequest(httpsUrl);

        WebResponse response = runner.getResponse(request);

        WebLink[] links = response.getLinks();
        Assert.assertEquals("Wrong number of generated links.", 1, links.length);

        Assert.assertTrue("Generated link doesn't start with https: " + links[0].getURLString(), links[0]
            .getURLString()
            .startsWith("https://"));

    }

}