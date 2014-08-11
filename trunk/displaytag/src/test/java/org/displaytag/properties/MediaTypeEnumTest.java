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