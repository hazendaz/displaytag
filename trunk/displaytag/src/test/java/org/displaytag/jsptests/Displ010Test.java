package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-10: using displaytag.properties and override with attributes.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ010Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-010.jsp";
    }

    /**
     * Check the value of class attribute in table.
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
        Assert.assertEquals("Expected 1 table in result.", 1, tables.length);

        Assert.assertEquals(
            "Class attribute not overridden by setProperties as expected.",
            "new",
            tables[0].getClassName());

    }

}