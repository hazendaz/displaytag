package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for standard html attributes of table and column tags.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class HtmlAttributesTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "htmlattributes.jsp";
    }

    /**
     * Check content and ids in generated tables.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response;

        response = runner.getResponse(request);

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables.", 1, tables.length);
        WebTable table = tables[0];

        assertEquals("invalid id", "idX", table.getID());

        assertEquals("invalid attribute value", "cellspacingX", table.getAttribute("cellspacing"));
        assertEquals("invalid attribute value", "cellpaddingX", table.getAttribute("cellpadding"));
        assertEquals("invalid attribute value", "frameX", table.getAttribute("frame"));
        assertEquals("invalid attribute value", "rulesX", table.getAttribute("rules"));
        assertEquals("invalid attribute value", "styleX", table.getAttribute("style"));
        assertEquals("invalid attribute value", "summaryX", table.getAttribute("summary"));
        assertEquals("invalid attribute value", "classX", table.getAttribute("class"));

        TableCell cell = table.getTableCell(1, 0);
        assertEquals("invalid attribute value", "styleX", cell.getAttribute("style"));
        assertEquals("invalid attribute value", "classX", cell.getAttribute("class"));

    }
}