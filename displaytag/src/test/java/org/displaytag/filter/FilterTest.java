package org.displaytag.filter;

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpInternalErrorException;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests the ResponseOverrideFilter.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class FilterTest extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public FilterTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "filter.jsp";
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#doTest(java.lang.String)
     */
    public void doTest(String jspName) throws Exception
    {
        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        WebRequest request = new GetMethodWebRequest(jspName);
        request.setParameter(mediaParameter, "" + MediaTypeEnum.XML.getCode());

        try
        {
            // check if page need a filter (unfiltered request)
            runner.getResponse(request);
            fail("Request works also without a filter. Can't test it properly.");
        }
        catch (HttpInternalErrorException e)
        {
            // it's ok
        }

        request = new GetMethodWebRequest(jspName + MockFilterSupport.FILTERED_EXTENSION);
        request.setParameter(mediaParameter, "" + MediaTypeEnum.XML.getCode());

        WebResponse response = runner.getResponse(request);

        assertEquals("Expected a different content type.", "text/xml", response.getContentType());
    }

}