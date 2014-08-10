package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-052 - Support for checkboxes.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ052Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-052.jsp";
    }

    /**
     * Preserve The Current Page And Sort Across Session.
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
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables.", 1, tables.length);
        Assert.assertEquals("Wrong number of rows.", 3, tables[0].getRowCount());
        Assert.assertEquals("Column content missing?", "ant", tables[0].getCellAsText(1, 2));
        Assert.assertEquals("Checkbox missing?", "input", tables[0].getTableCell(1, 0).getElementsWithName("_chk")[0]
            .getTagName());
        Assert.assertEquals(
            "Checkbox value missing?",
            "10",
            tables[0].getTableCell(1, 0).getElementsWithName("_chk")[0].getAttribute("value"));

        WebLink[] links = response.getLinks();
        Assert.assertEquals(
            "Wrong link generated",
            "javascript:displaytagform(\'displ\',[{f:\'d-148916-p\',v:\'2\'}])",
            links[0].getAttribute("href"));

    }

}