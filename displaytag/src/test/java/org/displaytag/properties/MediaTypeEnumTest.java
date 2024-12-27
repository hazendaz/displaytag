/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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
