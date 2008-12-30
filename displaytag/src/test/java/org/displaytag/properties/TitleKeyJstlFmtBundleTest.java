package org.displaytag.properties;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for "titlekey" column attribute.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TitleKeyJstlFmtBundleTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "titlekeyfmtbundle.jsp";
    }

    /**
     * Test that headers are correctly removed.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Test
    public void doTest() throws Exception
    {
        // test keep
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Expected one table", 1, tables.length);

        Assert.assertEquals("Header from resource is not valid.", "foo bundle", tables[0].getCellAsText(0, 0));
        Assert.assertEquals("Header from resource is not valid.", "baz bundle", tables[0].getCellAsText(0, 1));
        Assert.assertEquals("Header from resource is not valid.", "camel bundle", tables[0].getCellAsText(0, 2));
        Assert.assertEquals("Missing resource should generate the ???missing??? header.", "???missing???", tables[0]
            .getCellAsText(0, 3));

    }
}