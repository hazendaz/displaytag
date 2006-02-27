package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for basic displaytag functionalities.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ17Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-17.jsp";
    }

    /**
     * Verifies that the generated page contains a table with the expected number of columns.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);

        for (int j = 0; j < 4; j++)
        {
            WebLink[] links = response.getLinks();
            response = links[j].click();

            if (log.isDebugEnabled())
            {
                log.debug("After clicking on " + j + ":\n" + response.getText());
            }
            checkOnlyOneSorted(response, j);
        }

    }

    /**
     * Check that only the expected column is sorted.
     * @param response WebResponse
     * @param sortedColumn expected sorted column number
     * @throws SAXException in processing WebTables
     */
    private void checkOnlyOneSorted(WebResponse response, int sortedColumn) throws SAXException
    {
        WebTable[] tables = response.getTables();

        assertEquals("Expected one table in result.", 1, tables.length);
        assertEquals("Wrong number of columns in result.", 4, tables[0].getColumnCount());

        for (int j = 0; j < 4; j++)
        {
            TableCell cell = tables[0].getTableCell(0, j);
            boolean containsSorted = (cell.getAttribute("class").indexOf("sorted") > -1);
            if (j == sortedColumn)
            {
                assertTrue("Column " + j + " is not sorted as expected", containsSorted);
            }
            else
            {
                assertFalse("Column " + j + " is sorted, but only " + sortedColumn + " should be", containsSorted);
            }

        }

    }

}