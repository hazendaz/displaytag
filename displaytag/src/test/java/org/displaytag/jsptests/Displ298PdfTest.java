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
 * The Class Displ298PdfTest.
 */
class Displ298PdfTest extends DisplaytagCase {

    /**
     * Gets the jsp name.
     *
     * @return the jsp name
     *
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    @Override
    public String getJspName() {
        return "DISPL-298.jsp";
    }

    /**
     * Check that model modifications made by table decorator specified with in the decorator property the table tag
     * show up in the pdf export.
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
        MediaTypeEnum.registerMediaType("wpdf");
        final ExportViewFactory factory = ExportViewFactory.getInstance();
        factory.registerExportView("wpdf", "org.displaytag.export.DefaultPdfExportView");
        final MediaTypeEnum pdfMedia = MediaTypeEnum.fromName("wpdf");
        Assertions.assertNotNull(pdfMedia, "Pdf export view not correctly registered.");
        request.setParameter(mediaParameter, Integer.toString(pdfMedia.getCode()));

        final WebResponse response = this.runner.getResponse(request);

        Assertions.assertEquals("application/pdf", response.getContentType(), "Expected a different content type.");

        final InputStream stream = response.getInputStream();
        final byte[] result = new byte[3000];
        stream.read(result);

        final PdfReader reader = new PdfReader(result);
        Assertions.assertEquals(1, reader.getNumberOfPages(), "Expected a valid pdf file with a single page");
    }

}
