package org.displaytag.jsptests;

import static org.junit.Assert.*;

import org.displaytag.test.DisplaytagCase;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * @author rwest
 * @version $Revision: 1159 $ ($Author: rwest $)
 */
public class PaginationGroupingTest extends DisplaytagCase
{

    protected String getJspName()
    {
        return "pagination-grouping.jsp";
    }

    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();

        assertEquals("Wrong number of tables.", 1, tables.length);
        assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());
        assertEquals("Bad sub-total for group 1", "4.0", tables[0].getCellAsText(4, 1));
        assertEquals("Bad sub-total for group 2", "6.0", tables[0].getCellAsText(9, 1));
        assertEquals("Bad grand total", "10.0", tables[0].getCellAsText(10, 1));
    }
}
