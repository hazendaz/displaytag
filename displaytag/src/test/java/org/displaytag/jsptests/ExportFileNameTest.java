package org.displaytag.jsptests;

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for export file name property.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExportFileNameTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "exportfilename.jsp";
    }

    /**
     * Test for content disposition and filename.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        WebRequest request = new GetMethodWebRequest(jspName);
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.XML.getCode()));

        WebResponse response = runner.getResponse(request);

        // we are really testing an xml output?
        assertEquals("Expected a different content type.", "text/xml", response.getContentType());
        assertEquals("Wrong or missing disposition/filename.", "attachment; filename=\"file.txt\"", response
            .getHeaderField("CONTENT-DISPOSITION"));

    }

}