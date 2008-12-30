package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-4 - Html tags in "title" attribute when using maxLength.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ004Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-004.jsp";
    }

    /**
     * Check the content of the title attribute.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        WebResponse response = runner.getResponse(request);

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Expected 1 table in result.", 1, tables.length);
        Assert.assertEquals("Wrong title in column", "\"the link\" is here", tables[0].getTableCell(1, 0).getTitle());
    }

}