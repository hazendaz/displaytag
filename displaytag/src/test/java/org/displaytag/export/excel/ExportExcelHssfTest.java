/*
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tomcat.InstanceManager;
import org.displaytag.export.ExportViewFactory;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.displaytag.util.SimpleInstanceManager;
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
 * The Class ExportExcelTest.
 *
 * @author andy Date: Oct 30, 2010 Time: 12:04:04 PM
 */
public class ExportExcelHssfTest {

    /**
     * logger.
     */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * HttpUnit ServletRunner.
     */
    protected ServletRunner runner;

    /**
     * Context mapped to the test application.
     */
    public static final String CONTEXT = "/context";

    /**
     * Gets the jsp url.
     *
     * @param jsp
     *            the jsp
     *
     * @return the jsp url
     */
    protected String getJspUrl(final String jsp) {
        return "http://localhost" + ExportExcelHssfTest.CONTEXT + "/jsps/" + jsp;
    }

    /**
     * Sets the up.
     *
     * @throws Exception
     *             e
     *
     * @see junit.framework.TestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {
        // need to pass a web.xml file to setup servletunit working directory
        final ClassLoader classLoader = this.getClass().getClassLoader();
        final URL webXmlUrl = classLoader.getResource("WEB-INF/web.xml");
        final String path = URLDecoder.decode(webXmlUrl.getFile(), "UTF-8");

        HttpUnitOptions.setDefaultCharacterSet("utf-8");
        System.setProperty("file.encoding", "utf-8");

        // start servletRunner
        this.runner = new ServletRunner(new File(path), ExportExcelHssfTest.CONTEXT);
        this.runner.getSession(true).getServletContext().setAttribute(InstanceManager.class.getName(),
                new SimpleInstanceManager());

        final Hashtable<String, String> params = new Hashtable<>();
        params.put("javaEncoding", "utf-8");
        params.put("scratchdir", "target");
        this.runner.registerServlet("*.jsp", "org.apache.jasper.servlet.JspServlet", params);

        this.log.debug("ServletRunner setup OK");

    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             e
     *
     * @see junit.framework.TestCase#tearDown()
     */
    @After
    public void tearDown() throws Exception {
        // shutdown servlet engine
        TableProperties.clearProperties();
        this.runner.shutDown();
    }

    /**
     * Test for content disposition and filename. jspName jsp name, with full path
     *
     * @throws Exception
     *             any axception thrown during test.
     */
    @Test
    public void doDefaultTest() throws Exception {
        final byte[] res = this.runPage("exportExcel.jsp");
        final File f = File.createTempFile("exporttest", ".xls");
        final FileOutputStream fw = new FileOutputStream(f);
        fw.write(res);
        fw.flush();
        fw.close();

        final FileInputStream istr = new FileInputStream(f);
        final Workbook wb = new HSSFWorkbook(istr);

        final Sheet sh = wb.getSheetAt(0);
        Assert.assertNotNull("Not all rows have been exported", sh.getRow(4));
        Assert.assertEquals("bee", sh.getRow(4).getCell(0).getStringCellValue());
        istr.close();
        wb.close();
    }

    /**
     * Run page.
     *
     * @param jspPage
     *            the jsp page
     *
     * @return the byte[]
     *
     * @throws Exception
     *             the exception
     */
    public byte[] runPage(final String jspPage) throws Exception {

        final ParamEncoder encoder = new ParamEncoder("table");
        final String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(jspPage));

        // this will force media type initialization
        final ExportViewFactory evf = ExportViewFactory.getInstance();
        evf.registerExportView("excel", "org.displaytag.export.excel.ExcelHssfView");
        final MediaTypeEnum excelMedia = MediaTypeEnum.EXCEL;
        Assert.assertNotNull("Excel export view not correctly registered.", excelMedia);
        request.setParameter(mediaParameter, Integer.toString(excelMedia.getCode()));

        final WebResponse response = this.runner.getResponse(request);

        // we are really testing an xml output?
        Assert.assertEquals("Expected a different content type.", "application/vnd.ms-excel",
                response.getContentType());

        final InputStream stream = response.getInputStream();
        final byte[] result = new byte[9000];
        stream.read(result);
        return result;
    }

}