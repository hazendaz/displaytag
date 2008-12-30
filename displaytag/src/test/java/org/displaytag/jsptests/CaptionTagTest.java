package org.displaytag.jsptests;

import org.apache.commons.lang.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

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

        String output = response.getText();

        Assert.assertTrue("Caption tag missing", StringUtils.contains(output, "<caption"));

        Assert.assertTrue(StringUtils.contains(output, "class=\"theclass\""));
        Assert.assertTrue(StringUtils.contains(output, "dir=\"thedir\""));
        Assert.assertTrue(StringUtils.contains(output, "id=\"theid\""));
        Assert.assertTrue(StringUtils.contains(output, "lang=\"thelang\""));
        Assert.assertTrue(StringUtils.contains(output, "style=\"thestyle\""));
        Assert.assertTrue(StringUtils.contains(output, "title=\"thetitle\""));

    }
}