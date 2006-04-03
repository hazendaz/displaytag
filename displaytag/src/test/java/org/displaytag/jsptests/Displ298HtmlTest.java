package org.displaytag.jsptests;

import org.displaytag.decorator.ModelDecorator;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


public class Displ298HtmlTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-298.jsp";
    }

    /**
     * Check that model modifications made by table decorator specified with in the decorator property the table tag
     * show up in the html output.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);
        WebResponse response = runner.getResponse(request);

        assertEquals("Expected a different content type.", "text/html", response.getContentType());
        String responseText = response.getText();
        boolean expectedTextPresent = responseText != null && responseText.indexOf(ModelDecorator.DECORATED_VALUE) > -1;
        assertTrue("Missing content.", expectedTextPresent);
    }

}