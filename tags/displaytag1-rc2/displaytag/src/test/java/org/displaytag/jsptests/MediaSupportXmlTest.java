package org.displaytag.jsptests;

import org.apache.commons.lang.StringUtils;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for "media" attribute support.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class MediaSupportXmlTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "media.jsp";
    }

    /**
     * Test as Xml.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        WebRequest request = new GetMethodWebRequest(jspName);
        request.setParameter(mediaParameter, "" + MediaTypeEnum.XML.getCode());

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        // we are really testing an xml output?
        assertEquals("Expected a different content type.", "text/xml", response.getContentType());

        String output = response.getText();

        assertTrue("Expected value [" + KnownValue.BEE + "] missing", StringUtils.contains(output, KnownValue.BEE));
        assertTrue("Expected value [" + KnownValue.CAMEL + "] missing", StringUtils.contains(output, KnownValue.CAMEL));
        assertTrue("Unexpected value [" + KnownValue.ANT + "] found", !StringUtils.contains(output, KnownValue.ANT));

    }

}