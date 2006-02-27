package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-10: using displaytag.properties and override with attributes.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ10Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-10.jsp";
    }

    /**
     * Check the value of class attribute in table.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);

        WebTable[] tables = response.getTables();
        assertEquals("Expected 1 table in result.", 1, tables.length);

        assertEquals("Class attribute not overridden by setProperties as expected.", "new", tables[0].getClassName());

    }

}