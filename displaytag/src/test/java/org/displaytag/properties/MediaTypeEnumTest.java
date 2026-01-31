/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for org.displaytag.properties.MediaTypeEnum.
 */
class MediaTypeEnumTest {

    /**
     * Test for the "fixed" enum instances.
     */
    @Test
    void testCsv() {
        Assertions.assertEquals(MediaTypeEnum.CSV, MediaTypeEnum.fromCode(1));
        Assertions.assertEquals(MediaTypeEnum.CSV, MediaTypeEnum.fromName("csv"));
        Assertions.assertEquals(MediaTypeEnum.fromCode(1), MediaTypeEnum.fromName("csv"));
    }

    /**
     * Test for the "fixed" enum instances.
     */
    @Test
    void testExcel() {
        Assertions.assertEquals(MediaTypeEnum.EXCEL, MediaTypeEnum.fromCode(2));
        Assertions.assertEquals(MediaTypeEnum.EXCEL, MediaTypeEnum.fromName("excel"));
        Assertions.assertEquals(MediaTypeEnum.fromCode(2), MediaTypeEnum.fromName("excel"));
    }

    /**
     * Test for the "fixed" enum instances.
     */
    @Test
    void testXml() {
        Assertions.assertEquals(MediaTypeEnum.XML, MediaTypeEnum.fromCode(3));
        Assertions.assertEquals(MediaTypeEnum.XML, MediaTypeEnum.fromName("xml"));
        Assertions.assertEquals(MediaTypeEnum.fromCode(3), MediaTypeEnum.fromName("xml"));
    }

    /**
     * Test for the "fixed" enum instances.
     */
    @Test
    void testHtml() {
        Assertions.assertEquals(MediaTypeEnum.HTML, MediaTypeEnum.fromCode(0));
        Assertions.assertEquals(MediaTypeEnum.HTML, MediaTypeEnum.fromName("html"));
        Assertions.assertEquals(MediaTypeEnum.fromCode(0), MediaTypeEnum.fromName("html"));
    }

    /**
     * Test pdf.
     */
    @Test
    void testPdf() {
        Assertions.assertEquals(MediaTypeEnum.PDF, MediaTypeEnum.fromCode(4));
        Assertions.assertEquals(MediaTypeEnum.PDF, MediaTypeEnum.fromName("pdf"));
        Assertions.assertEquals(MediaTypeEnum.fromCode(4), MediaTypeEnum.fromName("pdf"));
    }

}
