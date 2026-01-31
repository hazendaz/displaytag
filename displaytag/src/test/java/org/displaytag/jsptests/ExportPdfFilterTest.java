/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.jsptests;

import com.itextpdf.text.pdf.PdfReader;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

import java.io.InputStream;

import org.displaytag.export.ExportViewFactory;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for pdf export.
 */
class ExportPdfFilterTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "exportfull.jsp";
    }

    /**
     * Test for content disposition and filename.
     *
     * @throws Exception
     *             any exception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception {

        final ParamEncoder encoder = new ParamEncoder("table");
        final String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);
        final WebRequest request = new GetMethodWebRequest(this.getJspUrl(this.getJspName()));

        // this will force media type initialization
        ExportViewFactory.getInstance();
        final MediaTypeEnum pdfMedia = MediaTypeEnum.fromName("pdf");
        Assertions.assertNotNull(pdfMedia, "Pdf export view not correctly registered.");
        request.setParameter(mediaParameter, Integer.toString(pdfMedia.getCode()));

        // this will enable the filter!
        request.setParameter(TableTagParameters.PARAMETER_EXPORTING, "1");

        final WebResponse response = this.runner.getResponse(request);

        // we are really testing an xml output?
        Assertions.assertEquals("application/pdf", response.getContentType(), "Expected a different content type.");

        Assertions.assertTrue(response.getContentLength() > -1, "Content length should be set.");
        final InputStream stream = response.getInputStream();
        final byte[] result = new byte[response.getContentLength()];
        stream.read(result);

        final PdfReader reader = new PdfReader(result);
        Assertions.assertEquals(1, reader.getNumberOfPages(), "Expected a valid pdf file with a single page");

    }

}
