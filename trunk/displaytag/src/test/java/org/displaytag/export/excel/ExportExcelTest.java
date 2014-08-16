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
package org.displaytag.export.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Hashtable;
import java.util.Properties;

import org.displaytag.export.ExportViewFactory;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;


/**
 * @author andy Date: Oct 30, 2010 Time: 12:04:04 PM
 */
public class ExportExcelTest
{

    /**
     * logger.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * HttpUnit ServletRunner.
     */
    protected ServletRunner runner;

    /**
     * Context mapped to the test application.
     */
    public static final String CONTEXT = "/context";

    protected String getJspUrl(String jsp)
    {
        return "http://localhost" + CONTEXT + "/jsps/" + jsp;
    }

    /**
     * @see junit.framework.TestCase#setUp()
     * @throws Exception e
     */
    @Before
    public void setUp() throws Exception
    {
        // need to pass a web.xml file to setup servletunit working directory
        Properties p = new Properties();
        p.setProperty("export.pdf.class", "org.displaytag.export.FopExportView");
        TableProperties.setUserProperties(p);

        ClassLoader classLoader = getClass().getClassLoader();
        URL webXmlUrl = classLoader.getResource("WEB-INF/web.xml");
        String path = URLDecoder.decode(webXmlUrl.getFile(), "UTF-8");

        HttpUnitOptions.setDefaultCharacterSet("utf-8");
        System.setProperty("file.encoding", "utf-8");

        // start servletRunner
        runner = new ServletRunner(new File(path), CONTEXT);

        Hashtable<String, String> params = new Hashtable<String, String>();
        params.put("javaEncoding", "utf-8");
        runner.registerServlet("*.jsp", "org.apache.jasper.servlet.JspServlet", params);

        log.debug("ServletRunner setup OK");

    }

    /**
     * @see junit.framework.TestCase#tearDown()
     * @throws Exception e
     */
    @After
    public void tearDown() throws Exception
    {
        // shutdown servlet engine
        TableProperties.clearProperties();
        runner.shutDown();
    }

    /**
     * Test for content disposition and filename. jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Test
    public void doDefaultTest() throws Exception
    {
        byte[] res = runPage("exportExcel.jsp");
        File f = File.createTempFile("exporttest", "xls");
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
        MediaTypeEnum excelMedia = MediaTypeEnum.EXCEL;
        Assert.assertNotNull("Excel export view not correctly registered.", excelMedia);
        request.setParameter(mediaParameter, Integer.toString(excelMedia.getCode()));

        WebResponse response = runner.getResponse(request);

        // we are really testing an xml output?
        Assert
            .assertEquals("Expected a different content type.", "application/vnd.ms-excel", response.getContentType());

        InputStream stream = response.getInputStream();
        byte[] result = new byte[9000];
        stream.read(result);
        return result;
    }

}