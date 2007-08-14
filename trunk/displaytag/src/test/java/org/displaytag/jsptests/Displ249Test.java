package org.displaytag.jsptests;

import java.net.URLDecoder;

import org.apache.commons.lang.StringUtils;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-249 - Link generated for results navigation is breaking national character taken from FormBean.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ249Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-249.jsp";
    }

    /**
     * Link generated for results navigation is breaking national character taken from FormBean.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName); // use post
        String paramValue = "aàeèiìoòuù";

        request.setParameter("testparam", paramValue);

        request.setHeaderField("Content-Type", "text/html; charset=utf-8");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables", 1, tables.length);

        WebLink[] links = response.getLinks();
        assertEquals("Wrong number of links", 3, links.length); // sorting + paging

        String url = URLDecoder.decode(links[0].getURLString(), "UTF-8");

        String actual = StringUtils.substringAfter(url, "testparam=");
        if (StringUtils.contains(actual, "&"))
        {
            actual = StringUtils.substringBefore(actual, "&");
        }

        assertEquals(paramValue, actual);
    }

}