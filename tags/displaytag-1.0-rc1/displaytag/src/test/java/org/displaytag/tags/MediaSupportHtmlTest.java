package org.displaytag.tags;

import org.apache.commons.lang.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownValue;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * Tests for "media" attribute support.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class MediaSupportHtmlTest extends DisplaytagCase
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public MediaSupportHtmlTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "media.jsp";
    }

    /**
     * Test as Html.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        WebTable[] tables = response.getTables();

        assertEquals("Expected one table in result.", 1, tables.length);

        assertEquals("Bad number of generated columns.", 2, tables[0].getColumnCount());

        assertEquals("Bad value in column header.", tables[0].getCellAsText(0, 0), StringUtils
            .capitalize(KnownValue.ANT));
        assertEquals("Bad value in column header.", tables[0].getCellAsText(0, 1), StringUtils
            .capitalize(KnownValue.CAMEL));
    }

}