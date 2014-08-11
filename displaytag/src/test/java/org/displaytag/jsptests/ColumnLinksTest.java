package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for dynamic links.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ColumnLinksTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "columnlinks.jsp";
    }

    /**
     * Test link generated using column attributes.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));

        WebResponse response = runner.getResponse(request);
        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables.", 1, tables.length);

        WebLink[] links = response.getLinks();
        Assert.assertEquals("Wrong number of links in result.", 6, links.length);

        Assert.assertEquals("Text in first link is wrong.", "/context/dynlink?param=ant", links[0].getURLString());
        Assert.assertEquals("Text in second link is wrong.", "/context/dynlink?param=ant", links[1].getURLString());
        Assert.assertEquals("Text in third link is wrong.", "dynlink?param=ant", links[2].getURLString());
        Assert.assertEquals(
            "Text in fourth link is wrong.",
            "http://something/dynlink?param=ant",
            links[3].getURLString());
        Assert.assertEquals("Text in fifth link is wrong.", "http://something/dynlink", links[4].getURLString());
        Assert.assertEquals("Text in sixth link is wrong.", "/context/dynlink", links[5].getURLString());
    }

}