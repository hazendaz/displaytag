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
package org.displaytag.properties;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test case for org.displaytag.properties.MediaTypeEnum.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class MediaTypeEnumTest
{

    /**
     * Test for the "fixed" enum instances.
     */
    @Test
    public void testCsv()
    {
        Assert.assertEquals(MediaTypeEnum.fromCode(1), MediaTypeEnum.CSV);
        Assert.assertEquals(MediaTypeEnum.fromName("csv"), MediaTypeEnum.CSV);
        Assert.assertEquals(MediaTypeEnum.fromCode(1), MediaTypeEnum.fromName("csv"));
    }

    /**
     * Test for the "fixed" enum instances.
     */
    @Test
    public void testExcel()
    {
        Assert.assertEquals(MediaTypeEnum.fromCode(2), MediaTypeEnum.EXCEL);
        Assert.assertEquals(MediaTypeEnum.fromName("excel"), MediaTypeEnum.EXCEL);
        Assert.assertEquals(MediaTypeEnum.fromCode(2), MediaTypeEnum.fromName("excel"));
    }

    /**
     * Test for the "fixed" enum instances.
     */
    @Test
    public void testXml()
    {
        Assert.assertEquals(MediaTypeEnum.fromCode(3), MediaTypeEnum.XML);
        Assert.assertEquals(MediaTypeEnum.fromName("xml"), MediaTypeEnum.XML);
        Assert.assertEquals(MediaTypeEnum.fromCode(3), MediaTypeEnum.fromName("xml"));
    }

    /**
     * Test for the "fixed" enum instances.
     */
    @Test
    public void testHtml()
    {
        Assert.assertEquals(MediaTypeEnum.fromCode(0), MediaTypeEnum.HTML);
        Assert.assertEquals(MediaTypeEnum.fromName("html"), MediaTypeEnum.HTML);
        Assert.assertEquals(MediaTypeEnum.fromCode(0), MediaTypeEnum.fromName("html"));
    }

    @Test
    public void testPdf()
    {
        Assert.assertEquals(MediaTypeEnum.fromCode(4), MediaTypeEnum.PDF);
        Assert.assertEquals(MediaTypeEnum.fromName("pdf"), MediaTypeEnum.PDF);
        Assert.assertEquals(MediaTypeEnum.fromCode(4), MediaTypeEnum.fromName("pdf"));
    }

}