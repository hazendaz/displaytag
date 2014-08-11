package org.displaytag.jsptests;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownTypes;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class TotalsTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "totals.jsp";
    }

    /**
     * Check sorted columns.
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

        Assert.assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());
        // The footer will PRECEDE the body.
        Assert.assertTrue(
            "Totals should not be calculated / present if the column is not so marked.",
            StringUtils.isBlank(tables[0].getCellAsText(1, 0)));
        Assert.assertEquals(
            "Bad value in footer cell total.",
            "" + (KnownTypes.LONG_VALUE.doubleValue() * 2),
            tables[0].getCellAsText(1, 1));
        Assert.assertEquals(
            "Bad value in footer cell total.",
            "" + (KnownTypes.LONG_VALUE.doubleValue() * 2),
            tables[0].getCellAsText(1, 2));
    }
}
