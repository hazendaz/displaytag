package org.displaytag.tags;

import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for basic displaytag functionalities.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class GroupTest extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public GroupTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/tld11/group.jsp";
    }

    /**
     * Tests row grouping. bug #923446
     * @throws Exception any axception thrown during test.
     */
    public void testGroup() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspName());

        WebResponse response = runner.getResponse(request);

        log.debug("RESPONSE: " + response.getText());

        WebTable[] tables = response.getTables();

        assertEquals("Expected one table in result.", 1, tables.length);

        assertEquals("Column not grouped.", "", tables[0].getCellAsText(2, 0));
        assertEquals("Column not grouped.", "", tables[0].getCellAsText(2, 1));
        assertEquals("Column should not be grouped.", KnownValue.CAMEL, tables[0].getCellAsText(2, 2));
    }
}