package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-49 - style of column cannot be dynamically changed
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ049Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-049.jsp";
    }

    /**
     * Check variable style and class attributes.
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
        Assert.assertEquals("Wrong number of tables in result.", 1, tables.length);
        Assert.assertEquals("Wrong number of rows in result.", 3, tables[0].getRowCount());

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        TableCell row1Cell = tables[0].getTableCell(1, 0);
        Assert.assertEquals("Wrong style attribute.", "style-1", row1Cell.getAttribute("style"));
        Assert.assertEquals("Wrong class attribute.", "class-1", row1Cell.getClassName());

        TableCell row2Cell = tables[0].getTableCell(2, 0);
        Assert.assertEquals("Wrong style attribute.", "style-2", row2Cell.getAttribute("style"));
        Assert.assertEquals("Wrong class attribute.", "class-2", row2Cell.getClassName());
    }
}