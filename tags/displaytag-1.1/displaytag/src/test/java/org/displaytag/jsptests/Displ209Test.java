package org.displaytag.jsptests;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-209 - getListIndex() does not return the real list index. Note: the result is different from the one
 * expected from the decription in the Jira report, but after the test it was clear that there is no usable way to get
 * the desired result.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ209Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-209.jsp";
    }

    /**
     * Check list index/view index values.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response = runner.getResponse(request);

        ParamEncoder encoder = new ParamEncoder("table");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "2");

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

        assertEquals("Wrong value", "a", tables[0].getCellAsText(1, 0));
        assertEquals("Wrong viewIndex", "0", tables[0].getCellAsText(1, 1));
        assertEquals("Wrong listIndex", "0", tables[0].getCellAsText(1, 2));

        assertEquals("Wrong value", "b", tables[0].getCellAsText(2, 0));
        assertEquals("Wrong viewIndex", "1", tables[0].getCellAsText(2, 1));
        assertEquals("Wrong listIndex", "1", tables[0].getCellAsText(2, 2));

        assertEquals("Wrong value", "c", tables[0].getCellAsText(3, 0));
        assertEquals("Wrong viewIndex", "2", tables[0].getCellAsText(3, 1));
        assertEquals("Wrong listIndex", "2", tables[0].getCellAsText(3, 2));
    }

}