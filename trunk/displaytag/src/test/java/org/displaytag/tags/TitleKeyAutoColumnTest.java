package org.displaytag.tags;

import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for "titlekey" column attribute.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TitleKeyAutoColumnTest extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public TitleKeyAutoColumnTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "titlekeyautocolumn.jsp";
    }

    /**
     * Test that headers are correctly removed.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        // test keep
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Expected one table", 1, tables.length);

        // find the "camel" column
        int j;
        for (j = 0; j < tables[0].getColumnCount(); j++)
        {
            if (KnownValue.CAMEL.equals(tables[0].getCellAsText(1, j)))
            {
                break;
            }
        }

        // resource should be used also without the property attribute for the "camel" header
        assertEquals("Header from resource is not valid.", "camel title", tables[0].getCellAsText(0, j));

    }
}