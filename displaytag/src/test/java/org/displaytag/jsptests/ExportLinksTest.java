package org.displaytag.jsptests;

import java.util.HashSet;
import java.util.Set;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for "media" attribute support.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExportLinksTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "media.jsp";
    }

    /**
     * Test that headers are correctly removed.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Test
    public void doTest() throws Exception
    {
        // test keep
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        WebResponse response = runner.getResponse(request);

        WebLink[] links = response.getLinks();

        Assert.assertEquals("Wrong number of export links. ", 4, links.length);

        Set<String> linkTexts = new HashSet<String>();
        for (int j = 0; j < links.length; j++)
        {
            String url = links[j].getURLString();
            log.debug(url);
            if (linkTexts.contains(url))
            {
                Assert.fail("Found duplicated link in export banner: " + url);
            }
            linkTexts.add(url);
        }

    }

}