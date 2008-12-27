package org.displaytag.jsptests;

import org.apache.commons.lang.StringUtils;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-264 - Export to Excel not appending form parameters.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ264Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-264.jsp";
    }

    /**
     * Check generated links for form parameters.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        request.setParameter("test", "value");
        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables in result.", 1, tables.length);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebLink[] links = response.getLinks();
        assertTrue("No links found.", links.length > 0);
        String url = links[0].getURLString();
        assertTrue("Expected parameter not found in url " + url, StringUtils.contains(url, "test=value"));
    }

}