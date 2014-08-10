package org.displaytag.jsptests;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.URLAssert;
import org.displaytag.util.ParamEncoder;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-112 - Allow requestURI with only parameters.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ112Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-112.jsp";
    }

    /**
     * Test link generated using href="".
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        ParamEncoder encoder = new ParamEncoder("table");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER), "2");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT), "0");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of table in result.", 2, tables.length);

        WebLink[] links = response.getLinks();
        Assert.assertEquals("Wrong number of links in result.", 2, links.length);

        URLAssert.assertEquals("?d-148916-s=0&d-148916-o=1", links[0].getURLString());
        URLAssert.assertEquals("?more=true&d-148916-s=0&d-148916-o=1", links[1].getURLString());
    }

}