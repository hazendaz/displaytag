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
 * Tests for 1016089 - Param values not URLEncoded.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class EncodedParametersTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "encodedparameter.jsp";
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

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables.", 1, tables.length);

        WebLink[] links = response.getLinks();
        Assert.assertEquals("Wrong number of links in result.", 1, links.length);

        Assert.assertEquals("Parameter in link should be encoded.", "/context/dynlink?param=1%2B1", links[0]
            .getURLString());
    }

}