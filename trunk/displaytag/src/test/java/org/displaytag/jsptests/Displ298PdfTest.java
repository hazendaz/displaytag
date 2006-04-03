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


public class Displ298PdfTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-298.jsp";
    }

    /**
     * Check that model modifications made by table decorator specified with in the decorator property the table tag
     * show up in the pdf export.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);
        WebRequest request = new GetMethodWebRequest(jspName);

        // this will force media type initialization
        MediaTypeEnum.registerMediaType("wpdf");
        ExportViewFactory factory = ExportViewFactory.getInstance();
        factory.registerExportView("wpdf", "org.displaytag.export.DefaultPdfExportView");
        MediaTypeEnum pdfMedia = MediaTypeEnum.fromName("wpdf");
        assertNotNull("Pdf export view not correctly registered.", pdfMedia);
        request.setParameter(mediaParameter, Integer.toString(pdfMedia.getCode()));

        WebResponse response = runner.getResponse(request);

        assertEquals("Expected a different content type.", "application/pdf", response.getContentType());

        InputStream stream = response.getInputStream();
        byte[] result = new byte[3000];
        stream.read(result);

        PdfReader reader = new PdfReader(result);
        assertEquals("Expected a valid pdf file with a single page", 1, reader.getNumberOfPages());

        // @todo assert expected content.

    }

}