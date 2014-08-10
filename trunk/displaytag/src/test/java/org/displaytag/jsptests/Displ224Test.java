package org.displaytag.jsptests;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-224 - Adding the "scope" attribute to table header cells for web accessibility.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ224Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-224.jsp";
    }

    /**
     * Check the content of the title attribute.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        WebResponse response = runner.getResponse(request);

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables in result.", 1, tables.length);

        Assert.assertEquals("Wrong number of rows in result.", 2, tables[0].getRowCount());
        Assert.assertEquals("col", tables[0].getTableCell(0, 0).getAttribute("scope"));
        Assert.assertEquals(StringUtils.EMPTY, tables[0].getTableCell(0, 1).getAttribute("scope"));
        Assert.assertEquals(StringUtils.EMPTY, tables[0].getTableCell(1, 0).getAttribute("scope"));
        Assert.assertEquals("row", tables[0].getTableCell(1, 1).getAttribute("scope"));

    }

}