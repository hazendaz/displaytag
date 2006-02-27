package org.displaytag.jsptests;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Basic tests for pagination.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PartialListTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "partialList.jsp";
    }

    /**
     * Verifies that the generated page contains the pagination links with the inupt parameter. Tests #917200 ("{}" in
     * parameters).
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);

        ParamEncoder p2 = new ParamEncoder("table2");
        ParamEncoder p3 = new ParamEncoder("table3");

        request.setParameter(p2.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "2"); // table 2 start page 2
        request.setParameter(p3.encodeParameterName(TableTagParameters.PARAMETER_SORT), "0"); // table 3 sort 0
        request.setParameter(p3.encodeParameterName(TableTagParameters.PARAMETER_ORDER), "1"); // table 3 order desc

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        WebLink[] links = response.getLinks();
        WebTable[] tables = response.getTables();
        // ensure all our search bar links contain page 2 as the param since we only have 2 pages
        assertEquals("2", links[0].getParameterValues(p2.encodeParameterName(TableTagParameters.PARAMETER_PAGE))[0]);
        assertEquals("2", links[1].getParameterValues(p2.encodeParameterName(TableTagParameters.PARAMETER_PAGE))[0]);
        assertEquals("2", links[2].getParameterValues(p2.encodeParameterName(TableTagParameters.PARAMETER_PAGE))[0]);
        assertEquals(3, tables[0].getRowCount()); // title row + 2 data's
        assertEquals("1", tables[0].getCellAsText(1, 0));
        assertEquals("4", tables[0].getCellAsText(2, 0));

        // second table assertions
        // links should point to first page
        assertEquals("1", links[4].getParameterValues(p2.encodeParameterName(TableTagParameters.PARAMETER_PAGE))[0]);
        assertEquals("1", links[5].getParameterValues(p2.encodeParameterName(TableTagParameters.PARAMETER_PAGE))[0]);
        assertEquals("1", links[6].getParameterValues(p2.encodeParameterName(TableTagParameters.PARAMETER_PAGE))[0]);
        assertEquals(3, tables[1].getRowCount()); // title row + 2 data's
        assertEquals("1", tables[1].getCellAsText(1, 0));
        assertEquals("4", tables[1].getCellAsText(2, 0));

        // third table assertions
        assertEquals(3, tables[2].getRowCount()); // title row + 2 data's
        assertEquals("4", tables[2].getCellAsText(1, 0));
        assertEquals("1", tables[2].getCellAsText(2, 0));
    }
}