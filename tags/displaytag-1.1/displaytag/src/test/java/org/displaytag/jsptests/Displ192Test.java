package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TableRow;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-192 - add row style to current row from TableDecorator.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ192Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-192.jsp";
    }

    /**
     * No exception when an invalid page is requested.
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
        assertEquals("Wrong number of rows in result.", 4, tables[0].getRowCount());

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        TableRow[] rows = tables[0].getRows();
        assertEquals("Wrong number of rows in result.", 4, rows.length);

        assertEquals("", rows[0].getClassName());
        assertEquals("odd", rows[1].getClassName());
        assertEquals("even", rows[2].getClassName());
        assertEquals("odd highlighted", rows[3].getClassName());

        assertEquals("", rows[0].getID());
        assertEquals("rowid0", rows[1].getID());
        assertEquals("rowid1", rows[2].getID());
        assertEquals("rowid2", rows[3].getID());

    }

}