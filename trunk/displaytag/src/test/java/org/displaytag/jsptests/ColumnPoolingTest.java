package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for column pooling in tomcat.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ColumnPoolingTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "columnpooling.jsp";
    }

    /**
     * All the columns should be sortable.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);

        WebLink[] links = response.getLinks();
        assertEquals("links should be generated for all the columns", 3, links.length);

    }
}