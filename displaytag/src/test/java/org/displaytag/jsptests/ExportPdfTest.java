/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.jsptests;

import com.lowagie.text.pdf.PdfReader;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Properties;

import org.displaytag.export.ExportViewFactory;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for pdf export.
 */
class ExportPdfTest extends DisplaytagCase {

    /**
     * Do test.
     *
     * @throws Exception
     *             the exception
     */
    @Override
    public void doTest() throws Exception {
        // Not used
    }

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     */
    @Override
    public String getJspName() {
        // Not used
        return null;
    }

    /**
     * Sets the up.
     *
     * @throws Exception
     *             the exception
     */
    @Override
    @BeforeEach
    public void setUp() throws Exception {
        final Properties p = new Properties();
        p.setProperty("export.pdf.class", "org.displaytag.export.FopExportView");
        TableProperties.setUserProperties(p);
        super.setUp();
    }

    /**
     * Tear down.
     *
     * @throws Exception
     *             the exception
     */
    @Override
    @AfterEach
    public void tearDown() throws Exception {
        TableProperties.clearProperties();
        super.tearDown();
    }

    /**
     * Gets the test file.
     *
     * @return the test file
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public File getTestFile() throws IOException {
        return File.createTempFile("inline", "pdf");
    }

    /**
     * Test for content disposition and filename. jspName jsp name, with full path
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Test
    void doDefaultTest() throws Exception {
        final byte[] res = this.runPage("exportfull.jsp");
        final File f = this.getTestFile();
        try (OutputStream fw = Files.newOutputStream(f.toPath())) {
            fw.write(res);
        }
    }

    /**
     * Do inline test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void doInlineTest() throws Exception {
        final byte[] res = this.runPage("exportFoInline.jsp");
        final File f = this.getTestFile();
        try (OutputStream fw = Files.newOutputStream(f.toPath())) {
            fw.write(res);
        }
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
        ExportViewFactory.getInstance();
        final MediaTypeEnum pdfMedia = MediaTypeEnum.PDF;
        Assertions.assertNotNull(pdfMedia, "Pdf export view not correctly registered.");
        request.setParameter(mediaParameter, Integer.toString(pdfMedia.getCode()));

        final WebResponse response = this.runner.getResponse(request);

        // we are really testing an xml output?
        Assertions.assertEquals("application/pdf", response.getContentType(), "Expected a different content type.");

        final InputStream stream = response.getInputStream();
        final byte[] result = new byte[9000];
        stream.read(result);

        final PdfReader reader = new PdfReader(result);
        // byte[] page = reader.getPageContent(1);
        Assertions.assertEquals(1, reader.getNumberOfPages(), "Expected a valid pdf file with a single page");

        return result;
    }

}
