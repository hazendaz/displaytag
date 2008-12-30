package org.displaytag.jsptests;

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests removal of no-cache headers.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExportHeadersTest extends DisplaytagCase
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

        // test remove
        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        request = new GetMethodWebRequest(getJspUrl(getJspName()));
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.XML.getCode()));

        response = runner.getResponse(request);

        Assert.assertNull("Header Cache-Control not overwritten", response.getHeaderField("Cache-Control"));
        Assert.assertNull("Header Expires not overwritten", response.getHeaderField("Expires"));
        Assert.assertNull("Header Pragma not overwritten", response.getHeaderField("Pragma"));
    }

}