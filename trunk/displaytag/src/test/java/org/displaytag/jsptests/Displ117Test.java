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
 * Test for DISPL-117 - Allow ColumnTag to generate links to current page.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ117Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-117.jsp";
    }

    /**
     * Test link generated using href="".
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
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
        Assert.assertEquals("Wrong number of tables.", 1, tables.length);

        WebLink[] links = response.getLinks();
        Assert.assertEquals("Wrong number of links in result.", 1, links.length);

        URLAssert.assertEquals("/context/goforit?param=ant&amp;d-148916-s=0&amp;d-148916-o=2", links[0].getURLString());
    }

}