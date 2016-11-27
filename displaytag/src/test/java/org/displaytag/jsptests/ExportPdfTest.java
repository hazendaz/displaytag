/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.jsptests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.displaytag.export.ExportViewFactory;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.itextpdf.text.pdf.PdfReader;
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

    @Override
    public void doTest() throws Exception
    {
        // Not used
    }

    @Override
    public String getJspName() {
        // Not used
        return null;
    }

    @Override
    @Before
    public void setUp() throws Exception
    {
        Properties p = new Properties();
        p.setProperty("export.pdf.class", "org.displaytag.export.FopExportView");
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

    /**
     * Gets the test file.
     *
     * @return the test file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public File getTestFile() throws IOException
    {
        return File.createTempFile("inline", "pdf");
    }

    /**
     * Test for content disposition and filename. jspName jsp name, with full path
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

    /**
     * Do inline test.
     *
     * @throws Exception the exception
     */
    @Test
    public void doInlineTest() throws Exception
    {
        byte[] res = runPage("exportFoInline.jsp");
        File f = getTestFile();
        FileOutputStream fw = new FileOutputStream(f);
        fw.write(res);
        fw.flush();
        fw.close();
    }

    /**
     * Run page.
     *
     * @param jspPage the jsp page
     * @return the byte[]
     * @throws Exception the exception
     */
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

        WebResponse response = this.runner.getResponse(request);

        // we are really testing an xml output?
        Assert.assertEquals("Expected a different content type.", "application/pdf", response.getContentType());

        InputStream stream = response.getInputStream();
        byte[] result = new byte[9000];
        stream.read(result);

        PdfReader reader = new PdfReader(result);
        // byte[] page = reader.getPageContent(1);
        Assert.assertEquals("Expected a valid pdf file with a single page", 1, reader.getNumberOfPages());

        return result;
    }

}