package org.displaytag.jsptests;

import java.io.InputStream;

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for DISPL-107: Excel and Text exports use Windows Latin-1 encoding.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ107Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-107.jsp";
    }

    /**
     * Encoding should be utf8.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);
        assertEquals("Wrong encoding", "UTF8", response.getCharacterSet());

        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);
        request = new GetMethodWebRequest(jspName);
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.CSV.getCode()));

        response = runner.getResponse(request);
        checkContent(response);

        // enabled filter
        request.setParameter(TableTagParameters.PARAMETER_EXPORTING, "1");
        response = runner.getResponse(request);
        checkContent(response);

    }

    /**
     * Actually check exported bytes
     * @param response WebResponse
     * @throws Exception any axception thrown during test.
     */
    private void checkContent(WebResponse response) throws Exception
    {
        // we are really testing an xml output?
        assertEquals("Expected a different content type.", "text/csv", response.getContentType());
        assertEquals("Wrong encoding", "UTF8", response.getCharacterSet());

        InputStream stream = response.getInputStream();
        byte[] result = new byte[11];
        stream.read(result);

        byte[] expected = "ant,аим\n".getBytes("utf-8");
        if (log.isDebugEnabled())
        {
            log.debug("expected: [" + new String(expected, "utf-8") + "]");
            log.debug("result:   [" + new String(result, "utf-8") + "]");
        }
        assertEquals("Wrong length", expected.length, result.length);

        for (int j = 0; j < result.length; j++)
        {
            assertEquals(
                "Wrong byte at position " + j + ", output=" + new String(result, "utf-8"),
                expected[j],
                result[j]);

        }
    }

}