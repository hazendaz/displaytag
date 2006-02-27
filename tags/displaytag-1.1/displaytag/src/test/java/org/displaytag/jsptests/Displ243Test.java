package org.displaytag.jsptests;

import org.displaytag.properties.SortOrderEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-243 - Default column sort breaks sorting after a few sorts of the column
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ243Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-243.jsp";
    }

    /**
     * CHeck sort order after some clicks
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        ParamEncoder encoder = new ParamEncoder("table");
        String orderParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER);

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables.", 1, tables.length);

        WebLink[] links = response.getLinks();
        assertEquals("Wrong number of links.", 1, links.length);

        assertEquals("wrong sorting order", Integer.toString(SortOrderEnum.DESCENDING.getCode()), links[0]
            .getParameterValues(orderParameter)[0]);

        // a few clicks...
        for (int j = 0; j < 10; j++)
        {
            String expectedSortOrder = (j % 2 == 0) ? SortOrderEnum.ASCENDING.getName() : SortOrderEnum.DESCENDING
                .getName();

            response = links[0].click();

            if (log.isDebugEnabled())
            {
                log.debug(response.getText());
            }

            tables = response.getTables();
            assertEquals("Wrong number of tables.", 1, tables.length);

            links = response.getLinks();
            assertEquals("Wrong number of links.", 1, links.length);

            assertEquals("Wrong sorting order for iteration " + j, expectedSortOrder, SortOrderEnum.fromCode(
                Integer.parseInt(links[0].getParameterValues(orderParameter)[0])).getName());
        }

    }

}