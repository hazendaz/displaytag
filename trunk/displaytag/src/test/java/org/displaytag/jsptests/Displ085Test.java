package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-085 - Dynamic Column Creation
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ085Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-085.jsp";
    }

    /**
     * A simple way for creating columns on the fly using jstl.
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
        Assert.assertEquals("Wrong number of tables.", 1, tables.length);

        Assert.assertEquals("Wrong number of columns.", 2, tables[0].getColumnCount());

        Assert.assertEquals("Wrong title.", "ant title", tables[0].getCellAsText(0, 0));
        Assert.assertEquals("Wrong title.", "bee title", tables[0].getCellAsText(0, 1));

        Assert.assertEquals("Wrong content.", "ant", tables[0].getCellAsText(1, 0));
        Assert.assertEquals("Wrong content.", "bee", tables[0].getCellAsText(1, 1));

        // only one sortable column
        Assert.assertEquals("Wrong number of links.", 1, response.getLinks().length);

    }

}