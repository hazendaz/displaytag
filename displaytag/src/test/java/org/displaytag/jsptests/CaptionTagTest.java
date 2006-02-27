package org.displaytag.jsptests;

import org.apache.commons.lang.StringUtils;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for caption tag.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class CaptionTagTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "caption.jsp";
    }

    /**
     * Verifies that the generated page contains a table with a caption and checks all the caption html attributes.
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

        String output = response.getText();

        assertTrue("Caption tag missing", StringUtils.contains(output, "<caption"));

        assertTrue(StringUtils.contains(output, "class=\"theclass\""));
        assertTrue(StringUtils.contains(output, "dir=\"thedir\""));
        assertTrue(StringUtils.contains(output, "id=\"theid\""));
        assertTrue(StringUtils.contains(output, "lang=\"thelang\""));
        assertTrue(StringUtils.contains(output, "style=\"thestyle\""));
        assertTrue(StringUtils.contains(output, "title=\"thetitle\""));

    }
}