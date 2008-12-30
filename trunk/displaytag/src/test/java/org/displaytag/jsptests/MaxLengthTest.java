package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for maxlength attribute.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class MaxLengthTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "maxlength.jsp";
    }

    /**
     * Test that title is escaped correctly.
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
        Assert.assertEquals("Wrong number of columns.", 4, tables[0].getColumnCount());

        Assert.assertEquals("Broken title.", "123\"567890\"123", tables[0].getTableCell(1, 0).getTitle());

        Assert.assertEquals("Wrong content in column 1", "123\"567890...", tables[0].getCellAsText(1, 0));
        Assert.assertEquals("Wrong content in column 2", "Lorem ipsum dolor...", tables[0].getCellAsText(1, 1));
        Assert.assertEquals("Wrong content in column 3", "", tables[0].getCellAsText(1, 2));
        Assert.assertEquals("Wrong content in column 4", "", tables[0].getCellAsText(1, 3));

    }
}