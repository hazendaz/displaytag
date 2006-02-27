package org.displaytag.jsptests;

import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for optimized iterations (don't evaluate unneeded body of columns).
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class OptimizedIteration2Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "optimizediteration2.jsp";
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
        ParamEncoder encoder = new ParamEncoder("table");

        // page 1, not sorted
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "1");
        checkNumberOfIterations(runner.getResponse(request), 1);

        // page 2, not sorted
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "2");
        checkNumberOfIterations(runner.getResponse(request), 1);

        // page 1, sorted (only current page)
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT), "1");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "1");
        checkNumberOfIterations(runner.getResponse(request), 1);

        // page 2, sorted (only current page)
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_SORT), "1");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "2");
        checkNumberOfIterations(runner.getResponse(request), 1);

        // page 1, export single page
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "2");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE), Integer
            .toString(MediaTypeEnum.CSV.getCode()));
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "1");

        WebResponse response = runner.getResponse(request);
        String csvExport = response.getText();
        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        assertEquals("Wrong csv export", "ant,1\n", csvExport);

    }

    /**
     * @param response WebResponse
     * @param iterations expected number of iterations
     * @throws Exception any axception thrown during test.
     */
    private void checkNumberOfIterations(WebResponse response, int iterations) throws Exception
    {
        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Expected 1 table in result.", 1, tables.length);
        assertEquals("Expected 2 rows in table.", 2, tables[0].getRowCount());

        assertEquals("Wrong number of iterations. Evaluated column bodies number is different from expected", Integer
            .toString(iterations), response.getElementWithID("iterations").getText());
    }
}