package org.displaytag.jsptests;

import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebTable;
import com.meterware.httpunit.TableCell;
import org.xml.sax.SAXException;
import org.displaytag.test.DisplaytagCase;

/* 
 * Created Date: Jan 5, 2005
 */

/**
 * @author rapruitt
 */
public class Displ18Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-18.jsp";
    }

    /**
     * Check sorted columns.
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
        assertEquals("Wrong number of tables.", 4, tables.length);

        for (int j = 0; j < tables.length; j++)
        {
            WebTable table = tables[j];
            assertEquals("Wrong number of columns in table." + j, 2, table.getColumnCount());
            assertEquals("Wrong number of rows in table." + j, 5, table.getRowCount());

            for (int u = 1; u < 5; u++)
            {
                assertEquals("Wrong value in table cell.", Integer.toString(u), table.getCellAsText(u, 1));
            }
        }

    }

}