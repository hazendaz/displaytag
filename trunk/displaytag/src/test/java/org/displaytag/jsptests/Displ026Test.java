package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests for DISPL-26 - More params for paging.banner.*_items_found.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ026Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-026.jsp";
    }

    /**
     * Check addictional parameters in paging.banner.*.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        WebResponse response;

        response = runner.getResponse(request);
        Assert.assertEquals(
            "Parameters {5} and {6} are not correctly evaluated in paging.banner.first.",
            "1|3",
            response.getElementWithID("numbers").getText());
        Assert.assertEquals(
            "Parameters {4} and {5} are not correctly evaluated in paging.banner.some_items_found.",
            "1|3",
            response.getElementWithID("label").getText());
    }

}