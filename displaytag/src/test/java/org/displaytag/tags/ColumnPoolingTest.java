package org.displaytag.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for column pooling in tomcat.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class ColumnPoolingTest extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ColumnPoolingTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/tld11/columnpooling.jsp";
    }

    /**
     * All the columns should be sortable.
     * @throws Exception any axception thrown during test.
     */
    public void testJsp() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspName());

        WebResponse response = runner.getResponse(request);

        WebLink[] links = response.getLinks();
        assertEquals("links should be generated for all the columns", 3, links.length);

    }
}