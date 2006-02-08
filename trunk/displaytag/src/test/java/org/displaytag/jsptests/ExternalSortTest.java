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
public class ExternalSortTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "externalSort.jsp";
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
        ParamEncoder p1 = new ParamEncoder("table");
        ParamEncoder p2 = new ParamEncoder("table2");

        request.setParameter(p2.encodeParameterName(TableTagParameters.PARAMETER_SORT), "number");
        request.setParameter(p2.encodeParameterName(TableTagParameters.PARAMETER_SORTUSINGNAME), "1");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        WebLink[] links = response.getLinks();

        assertEquals("0", links[0].getParameterValues(p1.encodeParameterName(TableTagParameters.PARAMETER_SORT))[0]);
        assertEquals("2", links[0].getParameterValues(p1.encodeParameterName(TableTagParameters.PARAMETER_ORDER))[0]);

        assertEquals("buzz", links[1].getParameterValues(p1.encodeParameterName(TableTagParameters.PARAMETER_SORT))[0]);
        assertEquals("2", links[1].getParameterValues(p1.encodeParameterName(TableTagParameters.PARAMETER_ORDER))[0]);

        // test that the column with sortName buzz was set as sorted and now has a link to sort desc
        assertEquals(
            "number",
            links[2].getParameterValues(p2.encodeParameterName(TableTagParameters.PARAMETER_SORT))[0]);
        assertEquals("1", links[2].getParameterValues(p2.encodeParameterName(TableTagParameters.PARAMETER_ORDER))[0]);

        // now ensure that our data has not been sorted at all since we are doing it 'externally'
        WebTable[] tables = response.getTables();
        assertEquals("1", tables[1].getCellAsText(1, 0));
        assertEquals("4", tables[1].getCellAsText(2, 0));
        assertEquals("2", tables[1].getCellAsText(3, 0));
        assertEquals("3", tables[1].getCellAsText(4, 0));
    }
}