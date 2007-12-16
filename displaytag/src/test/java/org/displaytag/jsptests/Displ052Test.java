package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Test for DISPL-052 - Support for checkboxes.
 * @author Fabrizio Giustina
 * @version $Id: $
 */
public class Displ052Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-052.jsp";
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#testEL()
     */
    public void testEL() throws Exception
    {
        // disabled
    }

    /**
     * Preserve The Current Page And Sort Across Session.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        ParamEncoder encoder = new ParamEncoder("table");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        // WebTable[] tables = response.getTables();
        // assertEquals("Wrong number of tables.", 1, tables.length);
        // assertEquals("Wrong number of rows.", 2, tables[0].getRowCount());
        // assertEquals("Column content missing?", "ant", tables[0].getCellAsText(1, 0));
        //
        // HTMLElement pagination = response.getElementWithID("pagination");
        // assertNotNull("Paging banner not found.", pagination);
        // assertEquals("Pagination links are not as expected.", "1, 2, [3]", pagination.getText());
        //
        // assertEquals("Column 1 should be marked as sorted.", "sortable sorted order2", tables[0]
        // .getTableCell(0, 0)
        // .getClassName());

        fail("to be implemented");

    }

}