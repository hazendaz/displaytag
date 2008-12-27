package org.displaytag.jsptests;

import java.util.HashSet;
import java.util.Set;

import org.displaytag.test.DisplaytagCase;

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
    public void doTest(String jspName) throws Exception
    {
        // test keep
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response = runner.getResponse(request);

        WebLink[] links = response.getLinks();

        assertEquals("Wrong number of export links. ", 4, links.length);

        Set linkTexts = new HashSet();
        for (int j = 0; j < links.length; j++)
        {
            String url = links[j].getURLString();
            log.debug(url);
            if (linkTexts.contains(url))
            {
                fail("Found duplicated link in export banner: " + url);
            }
            linkTexts.add(url);
        }

    }

}