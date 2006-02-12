package org.displaytag.properties;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for "titlekey" column attribute.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TitleKeyJstlFmtBundlePrefixTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "titlekeyfmtbundleprefix.jsp";
    }

    /**
     * Test that headers are correctly removed.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        // test keep
        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Expected one table", 1, tables.length);

        assertEquals("Header from resource is not valid.", "foo bundle prefixed", tables[0].getCellAsText(0, 0));
        assertEquals("Header from resource is not valid.", "baz bundle prefixed", tables[0].getCellAsText(0, 1));
        assertEquals("Header from resource is not valid.", "camel bundle prefixed", tables[0].getCellAsText(0, 2));
        assertEquals("Missing resource should generate the ???missing??? header.", "???missing???", tables[0]
            .getCellAsText(0, 3));

    }
}