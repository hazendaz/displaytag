package org.displaytag.tags;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for different kind of "data sources".
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class DataSourceTableTagTest extends DisplaytagCase
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(DataSourceTableTagTest.class);


    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public DataSourceTableTagTest(String name)
    {
        super(name);
    }

    /**
     * Test with a Map[].
     * @throws Exception any axception thrown during test.
     */
    public void testMapArray() throws Exception
    {

        WebRequest request = new GetMethodWebRequest("http://localhost/map.jsp");

        WebResponse response = runner.getResponse(request);

        log.debug("RESPONSE: " + response.getText());

        WebTable[] tables = response.getTables();

        assertEquals("Expected one table in result.", 1, tables.length);

        assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());

        assertEquals("Bad value in column header.", tables[0].getCellAsText(0, 0), StringUtils
            .capitalize(KnownValue.ANT));
        assertEquals("Bad value in column header.", tables[0].getCellAsText(0, 1), StringUtils
            .capitalize(KnownValue.BEE));
        assertEquals("Bad value in column header.", tables[0].getCellAsText(0, 2), StringUtils
            .capitalize(KnownValue.CAMEL));


        assertEquals("Bad value in column content.", tables[0].getCellAsText(1, 0), KnownValue.ANT);
        assertEquals("Bad value in column content.", tables[0].getCellAsText(1, 1), KnownValue.BEE);
        assertEquals("Bad value in column content.", tables[0].getCellAsText(1, 2), KnownValue.CAMEL);
    }

    /**
     * Test with a Map[] with automatically discoverd column.
     * @throws Exception any axception thrown during test.
     */
    public void testMapArrayAutoColumns() throws Exception
    {

        WebRequest request = new GetMethodWebRequest("http://localhost/map_autocolumn.jsp");

        WebResponse response = runner.getResponse(request);

        log.debug("RESPONSE: " + response.getText());

        WebTable[] tables = response.getTables();

        assertEquals("Expected one table in result.", 1, tables.length);

        assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());

        assertEquals("Bad value in column header.", tables[0].getCellAsText(0, 0), StringUtils
            .capitalize(KnownValue.ANT));
        assertEquals("Bad value in column header.", tables[0].getCellAsText(0, 1), StringUtils
            .capitalize(KnownValue.BEE));
        assertEquals("Bad value in column header.", tables[0].getCellAsText(0, 2), StringUtils
            .capitalize(KnownValue.CAMEL));


        assertEquals("Bad value in column content.", tables[0].getCellAsText(1, 0), KnownValue.ANT);
        assertEquals("Bad value in column content.", tables[0].getCellAsText(1, 1), KnownValue.BEE);
        assertEquals("Bad value in column content.", tables[0].getCellAsText(1, 2), KnownValue.CAMEL);
    }
}
