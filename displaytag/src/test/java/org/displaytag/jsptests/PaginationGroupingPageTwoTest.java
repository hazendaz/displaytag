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
public class PaginationGroupingPageTwoTest extends DisplaytagCase {

    @Override
    public void doTest() throws Exception {
        // This is not a test.
    }

    @Test
    public void useOffsetToGetPageTwo() throws Exception {
        WebRequest request = new GetMethodWebRequest(getJspUrl("pagination-grouping-page2.jsp"));
        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();

        assertEquals("Wrong number of tables.", 1, tables.length);
        assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());
        assertEquals("Bad sub-total for group 1", "8.0", tables[0].getCellAsText(6, 1));
        assertEquals("Bad grand total", "10.0", tables[0].getCellAsText(9, 1));
    }

    @Test
    public void navigateToPageTwo() throws Exception {
        WebRequest request = new GetMethodWebRequest(getJspUrl("pagination-grouping.jsp"));
        request.setParameter("d-148916-p", "2");
        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();

        assertEquals("Wrong number of tables.", 1, tables.length);
        assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());
        assertEquals("Bad sub-total for group 1", "8.0", tables[0].getCellAsText(6, 1));
        assertEquals("Bad grand total", "10.0", tables[0].getCellAsText(9, 1));
    }
}
