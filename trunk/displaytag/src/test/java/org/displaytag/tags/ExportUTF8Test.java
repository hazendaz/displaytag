package org.displaytag.tags;

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Test for #968559.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExportUTF8Test extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ExportUTF8Test(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "utf8.jsp";
    }

    /**
     * Test that headers are correctly removed.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        // test keep
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response = runner.getResponse(request);

        // test remove
        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        request = new GetMethodWebRequest(jspName);
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.XML.getCode()));

        response = runner.getResponse(request);

        String encoding = response.getCharacterSet();

        assertEquals("Encoding is not utf-8 as expected", "utf-8", encoding);

    }

}