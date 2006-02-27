package org.displaytag.jsptests;

import java.io.InputStream;

import org.displaytag.export.ExportViewFactory;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.lowagie.text.pdf.PdfReader;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for pdf export.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExportPdfFilterTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "exportfull.jsp";
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

        // this will force media type initialization
        ExportViewFactory.getInstance();
        MediaTypeEnum pdfMedia = MediaTypeEnum.fromName("pdf");
        assertNotNull("Pdf export view not correctly registered.", pdfMedia);
        request.setParameter(mediaParameter, Integer.toString(pdfMedia.getCode()));

        // this will enable the filter!
        request.setParameter(TableTagParameters.PARAMETER_EXPORTING, "1");

        WebResponse response = runner.getResponse(request);

        // we are really testing an xml output?
        assertEquals("Expected a different content type.", "application/pdf", response.getContentType());

        assertTrue("Content length should be set.", response.getContentLength() > -1);
        InputStream stream = response.getInputStream();
        byte[] result = new byte[response.getContentLength()];
        stream.read(result);

        PdfReader reader = new PdfReader(result);
        assertEquals("Expected a valid pdf file with a single page", 1, reader.getNumberOfPages());

    }

}