package org.displaytag.jsptests;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for different kind of "data sources".
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DataSourceMapTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "map.jsp";
    }

    /**
     * Test with a Map[].
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

        Assert.assertEquals("Bad number of generated columns.", 3, tables[0].getColumnCount());

        Assert.assertEquals("Bad value in column header.", //
            StringUtils.capitalize(KnownValue.ANT),
            tables[0].getCellAsText(0, 0));
        Assert.assertEquals("Bad value in column header.", //
            StringUtils.capitalize(KnownValue.BEE),
            tables[0].getCellAsText(0, 1));
        Assert.assertEquals("Bad value in column header.", //
            StringUtils.capitalize(KnownValue.CAMEL),
            tables[0].getCellAsText(0, 2));

        Assert.assertEquals("Bad value in column content.", KnownValue.ANT, tables[0].getCellAsText(1, 0));
        Assert.assertEquals("Bad value in column content.", KnownValue.BEE, tables[0].getCellAsText(1, 1));
        Assert.assertEquals("Bad value in column content.", KnownValue.CAMEL, tables[0].getCellAsText(1, 2));
    }

}