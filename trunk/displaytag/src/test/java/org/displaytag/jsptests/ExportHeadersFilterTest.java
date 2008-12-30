package org.displaytag.jsptests;

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
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
public class ExportHeadersFilterTest extends ExportHeadersTest
{

    /**
     * Test that headers are correctly removed.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Test
    public void doTest() throws Exception
    {

        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        // test keep
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.XML.getCode()));

        // this will enable the filter!
        request.setParameter(TableTagParameters.PARAMETER_EXPORTING, "1");
        WebResponse response = runner.getResponse(request);

        Assert.assertNull("Header Cache-Control not overwritten", response.getHeaderField("Cache-Control"));
        Assert.assertNull("Header Expires not overwritten", response.getHeaderField("Expires"));
        Assert.assertNull("Header Pragma not overwritten", response.getHeaderField("Pragma"));
    }

}