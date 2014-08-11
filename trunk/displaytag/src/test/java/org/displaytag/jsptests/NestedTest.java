package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HTMLElementPredicate;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Simple nested tables.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class NestedTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "nested.jsp";
    }

    /**
     * Test for content disposition and filename.
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

        // nested tables don't show up in the count
        Assert.assertEquals("Wrong number of first-level tables", 1, tables.length);
        Assert.assertEquals("Wrong number of columns in main table", 4, tables[0].getColumnCount());
        Assert.assertEquals("Wrong number of rows in main table", 4, tables[0].getRowCount());

        for (int j = 1; j < tables[0].getRowCount(); j++)
        {
            Assert.assertEquals(
                "Content in cell [" + j + ",0] in main table is wrong",
                Integer.toString(j),
                tables[0].getCellAsText(j, 0));
            Assert.assertEquals(
                "Content in cell [" + j + ",1] in main table is wrong",
                KnownValue.ANT,
                tables[0].getCellAsText(j, 1));
            Assert.assertEquals(
                "Content in cell [" + j + ",2] in main table is wrong",
                KnownValue.BEE,
                tables[0].getCellAsText(j, 2));

            WebTable nested = tables[0].getTableCell(j, 3).getFirstMatchingTable(new HTMLElementPredicate()
            {

                @Override
                public boolean matchesCriteria(Object htmlElement, Object criteria)
                {
                    return true;
                }
            }, null);

            Assert.assertNotNull("Nested table not found in cell [" + j + ",3]", nested);
            Assert.assertEquals("Wrong number of columns in nested table", 3, nested.getColumnCount());
            Assert.assertEquals("Wrong number of rows in nested table", 4, nested.getRowCount());

            for (int x = 1; x < nested.getRowCount(); x++)
            {
                Assert.assertEquals(
                    "Content in cell [" + x + ",0] in nested table is wrong",
                    Integer.toString(x),
                    nested.getCellAsText(x, 0));
                Assert.assertEquals(
                    "Content in cell [" + x + ",1] in nested table is wrong",
                    KnownValue.ANT,
                    nested.getCellAsText(x, 1));
                Assert.assertEquals(
                    "Content in cell [" + x + ",2] in nested table is wrong",
                    KnownValue.CAMEL,
                    nested.getCellAsText(x, 2));
            }

        }

    }

}