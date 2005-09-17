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
 * Test for DISPL-208 - Column level default sort order.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ208Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-208.jsp";
    }

    /**
     * No exception when an invalid page is requested.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables.", 1, tables.length);

        WebLink[] links = response.getLinks();
        assertEquals("Wrong number of links.", 3, links.length);

        ParamEncoder encoder = new ParamEncoder("table");
        String orderParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_ORDER);

        assertEquals("wrong sorting order", Integer.toString(SortOrderEnum.DESCENDING.getCode()), links[0]
            .getParameterValues(orderParameter)[0]);
        assertEquals("wrong sorting order", Integer.toString(SortOrderEnum.ASCENDING.getCode()), links[1]
            .getParameterValues(orderParameter)[0]);
        assertEquals("wrong sorting order", Integer.toString(SortOrderEnum.ASCENDING.getCode()), links[2]
            .getParameterValues(orderParameter)[0]);

    }

}