package org.displaytag.tags;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for "media" attribute support.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class MediaSupportTest extends DisplaytagCase
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(MediaSupportTest.class);

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public MediaSupportTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/tld11/media.jsp";
    }

    /**
     * Test as Html.
     * @throws Exception any axception thrown during test.
     */
    public void testAsHtml() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspName());

        WebResponse response = runner.getResponse(request);

        log.debug("RESPONSE: " + response.getText());

        WebTable[] tables = response.getTables();

        assertEquals("Expected one table in result.", 1, tables.length);

        assertEquals("Bad number of generated columns.", 2, tables[0].getColumnCount());

        assertEquals("Bad value in column header.", tables[0].getCellAsText(0, 0), StringUtils
            .capitalize(KnownValue.ANT));
        assertEquals("Bad value in column header.", tables[0].getCellAsText(0, 1), StringUtils
            .capitalize(KnownValue.CAMEL));
    }

    /**
     * Test as Xml.
     * @throws Exception any axception thrown during test.
     */
    public void testAsXml() throws Exception
    {

        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        WebRequest request = new GetMethodWebRequest(getJspName());
        request.setParameter(mediaParameter, "" + MediaTypeEnum.XML.getCode());

        WebResponse response = runner.getResponse(request);

        log.debug("RESPONSE: " + response.getText());

        // we are really testing an xml output?
        assertEquals("Expected a different content type.", "text/xml", response.getContentType());

        String output = response.getText();

        assertTrue("Expected value [" + KnownValue.BEE + "] missing", StringUtils.contains(output, KnownValue.BEE));
        assertTrue("Expected value [" + KnownValue.CAMEL + "] missing", StringUtils.contains(output, KnownValue.CAMEL));
        assertTrue("Unexpected value [" + KnownValue.ANT + "] found", !StringUtils.contains(output, KnownValue.ANT));

    }
}