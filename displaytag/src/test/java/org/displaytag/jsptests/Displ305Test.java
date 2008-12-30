package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TableRow;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-305 - NPE when row css class is not set.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ305Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-305.jsp";
    }

    /**
     * Check sorted column.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables in result.", 1, tables.length);
        Assert.assertEquals("Wrong number of rows in result.", 3, tables[0].getRowCount());

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        TableRow[] tr = tables[0].getRows();
        Assert.assertEquals("No css class expected on even tr", "", tr[1].getClassName());
        Assert.assertEquals("No css class expected on odd tr", "", tr[2].getClassName());

    }

}