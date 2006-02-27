package org.displaytag.jsptests;

import org.apache.commons.lang.StringUtils;
import org.displaytag.decorator.DateColumnDecorator;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownTypes;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for column decorators.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExportDecoratedTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "columndecorator.jsp";
    }

    /**
     * Export should not be decorated.
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
            log.debug(response.getText());
        }

        assertEquals("Expected a different content type.", "text/xml", response.getContentType());
        assertFalse("Export should not be decorated", StringUtils.contains(
            response.getText(),
            new DateColumnDecorator().decorate(KnownTypes.TIME_VALUE)));
        assertTrue("Export should not be decorated", StringUtils.contains(response.getText(), KnownTypes.TIME_VALUE
            .toString()));
    }
}