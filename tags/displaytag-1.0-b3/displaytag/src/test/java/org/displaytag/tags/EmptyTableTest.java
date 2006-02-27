package org.displaytag.tags;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests with a null list.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class EmptyTableTest extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public EmptyTableTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/tld11/empty.jsp";
    }

    /**
     * Verifies that the generated page doesn't contain any table (but doesn't crash!).
     * @throws Exception any axception thrown during test.
     */
    public void testEmpty() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspName());

        WebResponse response = runner.getResponse(request);

        WebTable[] tables = response.getTables();

        assertEquals(0, tables.length);
    }
}