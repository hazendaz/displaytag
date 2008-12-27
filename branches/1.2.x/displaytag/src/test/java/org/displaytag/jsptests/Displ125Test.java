package org.displaytag.jsptests;

import java.io.IOException;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-125 - Preserve The Current Page And Sort Across Session.
 * @author Fabrizio Giustina
 * @version $Id: $
 */
public class Displ125Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-125.jsp";
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
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "3");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT), "0");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER), "1");

        WebResponse response = runner.getResponse(request);

        checkResponse(response);

        // repeating the same request without parameters must return the same result (using session)
        request = new GetMethodWebRequest(jspName);
        response = runner.getResponse(request);
        checkResponse(response);
    }

    /**
     * @param response
     * @throws SAXException
     */
    private void checkResponse(WebResponse response) throws SAXException, IOException
    {

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables.", 1, tables.length);
        assertEquals("Wrong number of rows.", 2, tables[0].getRowCount());
        assertEquals("Column content missing?", "ant", tables[0].getCellAsText(1, 0));

        HTMLElement pagination = response.getElementWithID("pagination");
        assertNotNull("Paging banner not found.", pagination);
        assertEquals("Pagination links are not as expected.", "1, 2, [3]", pagination.getText());

        assertEquals("Column 1 should be marked as sorted.", "sortable sorted order2", tables[0]
            .getTableCell(0, 0)
            .getClassName());
    }

}