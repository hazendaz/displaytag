package org.displaytag.tags;

import org.apache.commons.lang.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for different kind of "data sources".
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DataSourceMapTest extends DisplaytagCase
{


    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public DataSourceMapTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "map.jsp";
    }

    /**
     * Test with a Map[].
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);

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