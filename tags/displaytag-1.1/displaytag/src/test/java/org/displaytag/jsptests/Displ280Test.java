package org.displaytag.jsptests;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-280 - Sortable header links fail when using external sorting and an integer as the sortName.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ280Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-280.jsp";
    }

    /**
     * Check sorted column.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);

        ParamEncoder encoder = new ParamEncoder("table");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT), "1");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORTUSINGNAME), "1");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables in result.", 1, tables.length);
        assertEquals("Wrong number of rows in result.", 3, tables[0].getRowCount());

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        assertEquals("Wrong value in first row. Table incorrectly sorted?", "2", tables[0].getCellAsText(1, 1));
        assertEquals("Column 1 should not be marked as sorted.", "sortable", tables[0]
            .getTableCell(0, 1)
            .getClassName());
        assertEquals("Column 2 should be marked as sorted.", "sortable sorted order1", tables[0]
            .getTableCell(0, 2)
            .getClassName());

    }

}