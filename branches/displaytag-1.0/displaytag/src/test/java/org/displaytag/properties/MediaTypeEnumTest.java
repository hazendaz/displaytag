package org.displaytag.properties;

import junit.framework.TestCase;


/**
 * Test case for org.displaytag.properties.MediaTypeEnum.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class MediaTypeEnumTest extends TestCase
{

    /**
     * Instantiate a new test.
     * @param name test name
     */
    public MediaTypeEnumTest(String name)
    {
        super(name);
    }

    /**
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return getClass().getName() + "." + super.getName();
    }

    /**
     * Test for the "fixed" enum instances.
     */
    public void testCsv()
    {
        assertEquals(MediaTypeEnum.fromCode(1), MediaTypeEnum.CSV);
        assertEquals(MediaTypeEnum.fromName("csv"), MediaTypeEnum.CSV);
        assertEquals(MediaTypeEnum.fromCode(1), MediaTypeEnum.fromName("csv"));
    }

    /**
     * Test for the "fixed" enum instances.
     */
    public void testExcel()
    {
        assertEquals(MediaTypeEnum.fromCode(2), MediaTypeEnum.EXCEL);
        assertEquals(MediaTypeEnum.fromName("excel"), MediaTypeEnum.EXCEL);
        assertEquals(MediaTypeEnum.fromCode(2), MediaTypeEnum.fromName("excel"));
    }

    /**
     * Test for the "fixed" enum instances.
     */
    public void testXml()
    {
        assertEquals(MediaTypeEnum.fromCode(3), MediaTypeEnum.XML);
        assertEquals(MediaTypeEnum.fromName("xml"), MediaTypeEnum.XML);
        assertEquals(MediaTypeEnum.fromCode(3), MediaTypeEnum.fromName("xml"));
    }

    /**
     * Test for the "fixed" enum instances.
     */
    public void testHtml()
    {
        assertEquals(MediaTypeEnum.fromCode(0), MediaTypeEnum.HTML);
        assertEquals(MediaTypeEnum.fromName("html"), MediaTypeEnum.HTML);
        assertEquals(MediaTypeEnum.fromCode(0), MediaTypeEnum.fromName("html"));
    }

}