package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-81 - using ColumnDecorator with tag body.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ081Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-081.jsp";
    }

    /**
     * Check that column body is decorated.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        WebResponse response = runner.getResponse(request);

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables.", 1, tables.length);

        Assert.assertEquals("Wrong number of columns.", 2, tables[0].getColumnCount());
        Assert.assertEquals("Wrong number of rows.", 2, tables[0].getRowCount());

        Assert.assertEquals("Wrong text in column 1", "decorated: ant", tables[0].getCellAsText(1, 0));
        Assert.assertEquals("Wrong text in column 2", "decorated: body", tables[0].getCellAsText(1, 1));
    }

}