package org.displaytag.jsptests;

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.springframework.web.servlet.DispatcherServlet;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for SpringInterceptor.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class SpringInterceptorTest extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public SpringInterceptorTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "filter.jsp.spring";
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#doTest(java.lang.String)
     */
    public void doTest(String jspName) throws Exception
    {
        this.runner.registerServlet("*.spring", DispatcherServlet.class.getName());

        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        WebRequest request = new GetMethodWebRequest(jspName);
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.XML.getCode()));

        // this enable the filter!
        request.setParameter(TableTagParameters.PARAMETER_EXPORTING, "1");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        assertEquals("Expected a different content type.", "text/xml", response.getContentType());
    }

}