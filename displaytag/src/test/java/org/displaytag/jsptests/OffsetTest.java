package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for offset/length attributes.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class OffsetTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "offset.jsp";
    }

    /**
     * Only items from 2 to 4 should show up in the response.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {

        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        WebTable[] tables = response.getTables();

        Assert.assertEquals("Wrong number of tables.", 1, tables.length);

        Assert.assertEquals("Bad number of generated rows.", 3, tables[0].getRowCount());
        Assert.assertEquals("Wrong cell content.", "2", tables[0].getCellAsText(1, 0));
        Assert.assertEquals("Wrong cell content.", "3", tables[0].getCellAsText(2, 0));
    }
}