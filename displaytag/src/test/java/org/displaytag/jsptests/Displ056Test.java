package org.displaytag.jsptests;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-56 - unable to dinamically generate multiple tables on the same page with indipendent sorting
 * (different id).
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ056Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-056.jsp";
    }

    /**
     * Try to sort generated tables.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        WebResponse response;

        response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables in result.", 3, tables.length);

        for (int j = 0; j < tables.length; j++)
        {
            Assert.assertEquals("invalid id", "row" + j, tables[j].getID());
        }

        WebLink[] links = response.getLinks();
        Assert.assertEquals("Wrong number of links in result.", 3, links.length);

        // click to sort the first table
        response = links[0].click();

        // get the links
        links = response.getLinks();
        Assert.assertEquals("Wrong number of links in result.", 3, links.length);

        // and click again to sort in reversed order
        response = links[0].click();

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        tables = response.getTables();
        Assert.assertEquals("Wrong number of tables in result.", 3, tables.length);

        // first is sorted, other aren't
        Assert.assertTrue("First table should be sorted. Wrong class attribute.", //
            StringUtils.contains(tables[0].getTableCell(0, 0).getClassName(), "sorted"));
        Assert.assertEquals("Second table should not be sorted. Wrong class attribute.", //
            "sortable",
            tables[1].getTableCell(0, 0).getClassName());
        Assert.assertEquals("Third table should not be sorted. Wrong class attribute.", //
            "sortable",
            tables[2].getTableCell(0, 0).getClassName());

        // and just to be sure also check values: sorted table
        for (int j = 1; j < tables[0].getRowCount(); j++)
        {
            Assert.assertEquals(
                "Unexpected value in table cell",
                Integer.toString(4 - j),
                tables[0].getCellAsText(j, 0));
        }

        // unsorted tables:
        for (int j = 1; j < tables[1].getRowCount(); j++)
        {
            Assert.assertEquals("Unexpected value in table cell", Integer.toString(j), tables[1].getCellAsText(j, 0));
            Assert.assertEquals("Unexpected value in table cell", Integer.toString(j), tables[2].getCellAsText(j, 0));
        }
    }
}