package org.displaytag.tags;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for table footer.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class FooterTagTest extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public FooterTagTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/tld11/footer.jsp";
    }

    /**
     * Checks that the generated page contains a table with the expected footer.
     * @throws Exception any axception thrown during test.
     */
    public void testFooter() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspName());

        WebResponse response = runner.getResponse(request);

        log.debug("RESPONSE: " + response.getText());

        WebTable[] tables = response.getTables();

        assertEquals("Expected one table in result.", 1, tables.length);

        assertEquals("Bad footer content.", "1footer", tables[0].getCellAsText(tables[0].getRowCount() - 1, 0));
    }
}