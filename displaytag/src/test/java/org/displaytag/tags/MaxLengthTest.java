package org.displaytag.tags;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for maxlength attribute.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class MaxLengthTest extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public MaxLengthTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/tld11/maxlength.jsp";
    }

    /**
     * Test that title is escaped correctly.
     * @throws Exception any axception thrown during test.
     */
    public void testJsp() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspName());

        WebResponse response = runner.getResponse(request);

        WebTable[] tables = response.getTables();
        assertEquals("Expected one table in result.", 1, tables.length);

        assertEquals("Broken title.", "123\"567890\"123", tables[0].getTableCell(1, 0).getTitle());
    }
}