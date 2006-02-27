package org.displaytag.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for SetProperty tag.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class SetPropertyTagTest extends DisplaytagCase
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(SetPropertyTagTest.class);

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public SetPropertyTagTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/tld11/setproperty.jsp";
    }

    /**
     * Check that the "show header" property only affects the correct tables in the page.
     * @throws Exception any axception thrown during test.
     */
    public void testSetProperty() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspName());

        WebResponse response = runner.getResponse(request);

        log.debug("RESPONSE: " + response.getText());

        WebTable[] tables = response.getTables();

        assertEquals("Expected 3 table in result.", 3, tables.length);

        assertEquals("First table should contain one row only", 1, tables[0].getRowCount());
        assertEquals("Second table should contain header plus one row", 2, tables[1].getRowCount());
        assertEquals("Third table should contain one row only", 1, tables[2].getRowCount());
    }
}