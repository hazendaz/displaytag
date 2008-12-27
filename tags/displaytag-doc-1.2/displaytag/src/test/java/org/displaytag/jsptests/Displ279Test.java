package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-279 - new "value" column attribute.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ279Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-279.jsp";
    }

    /**
     * Check column values.
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
        assertEquals("Wrong number of tables in result.", 1, tables.length);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        assertEquals("Wrong number of rows.", 2, tables[0].getRowCount());
        assertEquals("Wrong number of columns.", 3, tables[0].getColumnCount());

        assertEquals("Wrong value in cell.", "ant", tables[0].getCellAsText(1, 0));
        assertEquals("Wrong value in cell.", "1", tables[0].getCellAsText(1, 1));
        assertEquals("Wrong value in cell.", "1 $", tables[0].getCellAsText(1, 2));

    }

}