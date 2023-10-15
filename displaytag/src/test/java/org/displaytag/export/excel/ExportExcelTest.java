/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.jasper.servlet.JasperInitializer;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;
import org.displaytag.export.ExportViewFactory;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class ExportExcelTest {

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
        return "http://localhost" + ExportExcelTest.CONTEXT + "/jsps/" + jsp;
    }

    /**
     * Sets the up.
     *
     * @throws Exception
     *             e
     *
     * @see junit.framework.TestCase#setUp()
     */
    @BeforeEach
    void setUp() throws Exception {
        // need to pass a web.xml file to setup servletunit working directory
        final ClassLoader classLoader = this.getClass().getClassLoader();
        final URL webXmlUrl = classLoader.getResource("WEB-INF/web.xml");
        final String path = URLDecoder.decode(webXmlUrl.getFile(), StandardCharsets.UTF_8);

        HttpUnitOptions.setDefaultCharacterSet(StandardCharsets.UTF_8.name());
        System.setProperty("file.encoding", StandardCharsets.UTF_8.name());

        // start servletRunner
        this.runner = new ServletRunner(new File(path), ExportExcelTest.CONTEXT);
        this.runner.getSession(true).getServletContext().setAttribute(InstanceManager.class.getName(),
                new SimpleInstanceManager());

        // Initializer Jasper
        final JasperInitializer jsp = new JasperInitializer();
        jsp.onStartup(null, this.runner.getSession(true).getServletContext());

        final Properties params = new Properties();
        params.put("javaEncoding", StandardCharsets.UTF_8.name());
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
    @AfterEach
    void tearDown() throws Exception {
        // shutdown servlet engine
        TableProperties.clearProperties();
        this.runner.shutDown();
    }

    /**
     * Test for content disposition and filename. jspName jsp name, with full path
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Test
    void doDefaultTest() throws Exception {
        final byte[] res = this.runPage("exportExcel.jsp");
        final File f = File.createTempFile("exporttest", ".xls");
        final FileOutputStream fw = new FileOutputStream(f);
        fw.write(res);
        fw.flush();
        fw.close();
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
        evf.registerExportView("excel", "org.displaytag.export.ExcelView");
        final MediaTypeEnum excelMedia = MediaTypeEnum.EXCEL;
        Assertions.assertNotNull(excelMedia, "Excel export view not correctly registered.");
        request.setParameter(mediaParameter, Integer.toString(excelMedia.getCode()));

        final WebResponse response = this.runner.getResponse(request);

        // we are really testing an xml output?
        Assertions.assertEquals("application/vnd.ms-excel",
                response.getContentType(), "Expected a different content type.");

        final InputStream stream = response.getInputStream();
        final byte[] result = new byte[9000];
        stream.read(result);
        return result;
    }

}
