package org.displaytag.jsptests;

import org.displaytag.decorator.ModelDecorator;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


public class Displ298CsvTest extends DisplaytagCase
{

    protected String getMimeType()
    {
        return "text/csv";
    }

    protected int getCode()
    {
        return MediaTypeEnum.CSV.getCode();
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-298.jsp";
    }

    /**
     * Check that model modifications made by table decorator specified with in the decorator property the table tag
     * show up in the csv export.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        WebRequest request = new GetMethodWebRequest(jspName);
        request.setParameter(mediaParameter, Integer.toString(this.getCode()));

        WebResponse response = runner.getResponse(request);

        assertEquals("Expected a different content type.", this.getMimeType(), response.getContentType());
        String responseText = response.getText();
        boolean expectedTextPresent = responseText != null && responseText.indexOf(ModelDecorator.DECORATED_VALUE) > -1;
        assertTrue("Missing content.", expectedTextPresent);
    }

}