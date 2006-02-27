package org.displaytag.jsptests;

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for DISPL-142: Export of nested tables does not work.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ142Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-142.jsp";
    }

    /**
     * Nested tables should be ignored.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        WebRequest request = new GetMethodWebRequest(jspName);
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.CSV.getCode()));

        WebResponse response = runner.getResponse(request);

        assertEquals("Expected a different content type.", "text/csv", response.getContentType());

        // second column should be empty
        assertEquals("Wrong content.", "ant,,camel\nant,,camel\n", response.getText());

        // now export a nested table
        encoder = new ParamEncoder("nested");
        mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);
        request = new GetMethodWebRequest(jspName);
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.CSV.getCode()));
        // this test needs the export filter
        request.setParameter(TableTagParameters.PARAMETER_EXPORTING, "1");
        response = runner.getResponse(request);

        assertEquals("Expected a different content type.", "text/csv", response.getContentType());

        // second column should be empty
        assertEquals("Wrong content.", "bee\nbee\n", response.getText());
    }

}