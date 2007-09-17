package org.displaytag.jsptests;

import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElement;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-129 - Partial list support with valuelist pattern.
 * @author Fabrizio Giustina
 * @version $Id: $
 */
public class Displ129Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-129.jsp";
    }

    /**
     * No exception when an invalid page is requested.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);

        ParamEncoder encoder = new ParamEncoder("table");
        String pageParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE);
        request.setParameter(pageParameter, "2");

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

        assertEquals("Wrong column header.", "Number", tables[0].getCellAsText(0, 0));
        assertEquals("Wrong column content.", "3", tables[0].getCellAsText(1, 0));
        assertEquals("Wrong column content.", "4", tables[0].getCellAsText(2, 0));

        TableCell headerCell = tables[0].getTableCell(0, 0);

        String cssClass = headerCell.getClassName();
        assertEqualsIgnoreOrder("Wrong css attributes.", new String[]{"sortable", "sorted", "order2"}, StringUtils
            .split(cssClass));

        WebLink[] headerLinks = headerCell.getLinks();
        assertEquals("Sorting link not found.", 1, headerLinks.length);
        WebLink sortingLink = headerLinks[0];
        assertEqualsIgnoreOrder(
            "Wrong parameters.",
            new String[]{"sort", "searchid", "dir", pageParameter},
            sortingLink.getParameterNames());

        HTMLElement pagebanner = response.getElementWithID("pagebanner");
        assertEquals("Wrong page banner", "10|3|4", pagebanner.getText());
        HTMLElement pagelinks = response.getElementWithID("pagelinks");
        assertEquals("Wrong page links", "1|[2]|3|4|5", pagelinks.getText());

    }

}