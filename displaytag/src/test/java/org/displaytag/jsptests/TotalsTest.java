package org.displaytag.jsptests;

import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownTypes;
import org.apache.commons.lang.StringUtils;

/* 
 * Created Date: Dec 31, 2004
 */

/**
 * @author rapruitt
 */
public class TotalsTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "totals.jsp";
//        return "DISPL-28.jsp";
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

//        if (true)
        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
//            System.out.println(response.getText());
        }

        WebTable[] tables = response.getTables();

        assertEquals("Expected one table in result.", 1, tables.length);

        assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());
        // The footer will PRECEDE the body.
        assertTrue("Totals should not be calculated / present if the column is not so marked.", StringUtils.isBlank(tables[0].getCellAsText(1, 0)));
        assertEquals("Bad value in footer cell total.", ""+(KnownTypes.LONG_VALUE.doubleValue()*2), tables[0].getCellAsText(1, 1));
        assertEquals("Bad value in footer cell total.", ""+(KnownTypes.LONG_VALUE.doubleValue()*2), tables[0].getCellAsText(1, 2));
    }
}

