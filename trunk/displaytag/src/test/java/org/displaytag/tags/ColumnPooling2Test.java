package org.displaytag.tags;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * Tests for column pooling in tomcat.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ColumnPooling2Test extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ColumnPooling2Test(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "columnpooling2.jsp";
    }

    /**
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Expected one table", 1, tables.length);
        WebTable table = tables[0];
        for (int j = 1; j < 4; j++)
        {
            TableCell cell = table.getTableCell(j, 0);
            String cssClass = cell.getClassName();
            String expected = cell.asText().substring("should be ".length());
            assertEquals("Wrong css class", expected, cssClass);
        }

    }
}