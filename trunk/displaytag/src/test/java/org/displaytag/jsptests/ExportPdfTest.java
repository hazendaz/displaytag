package org.displaytag.jsptests;

import java.io.*;
import java.util.Properties;

import org.displaytag.export.ExportViewFactory;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import com.lowagie.text.pdf.PdfReader;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for pdf export.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExportPdfTest extends DisplaytagCase
{

    public void doTest() throws Exception
    {
    }

    @Override
    @Before
    public void setUp() throws Exception
    {
        Properties p = new Properties();
        p.setProperty("export.pdf.class","org.displaytag.export.FopExportView");
        TableProperties.setUserProperties(p);
        super.setUp();
    }

    @Override
    @After
    public void tearDown() throws Exception
    {
        TableProperties.clearProperties();
        super.tearDown();
    }

    public File getTestFile() throws IOException
    {
        return File.createTempFile("inline","pdf");
    }
    /**
     * Test for content disposition and filename.
     *  jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Test
    public void doDefaultTest() throws Exception
    {
        byte[] res = runPage("exportfull.jsp");
        File f = getTestFile();
        FileOutputStream fw = new FileOutputStream(f);
        fw.write(res);
        fw.flush();
        fw.close();
    }

    @Test
    public void doInlineTest() throws Exception
    {
        byte[] res = runPage("exportFoInline.jsp");
        File f =  getTestFile();
        FileOutputStream fw = new FileOutputStream(f);
        fw.write(res);
        fw.flush();
        fw.close();
    }

    public byte[] runPage(String jspPage) throws Exception
    {

        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);
        WebRequest request = new GetMethodWebRequest(getJspUrl(jspPage));

        // this will force media type initialization
        ExportViewFactory.getInstance();
        MediaTypeEnum pdfMedia = MediaTypeEnum.PDF;
        Assert.assertNotNull("Pdf export view not correctly registered.", pdfMedia);
        request.setParameter(mediaParameter, Integer.toString(pdfMedia.getCode()));

        WebResponse response = runner.getResponse(request);

        // we are really testing an xml output?
        Assert.assertEquals("Expected a different content type.", "application/pdf", response.getContentType());

        InputStream stream = response.getInputStream();
        byte[] result = new byte[9000];
        stream.read(result);

        PdfReader reader = new PdfReader(result);
//        byte[] page = reader.getPageContent(1);
        Assert.assertEquals("Expected a valid pdf file with a single page", 1, reader.getNumberOfPages());

        return result;
    }

}