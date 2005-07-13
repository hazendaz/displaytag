package org.displaytag.jsptests;

import org.apache.commons.lang.NotImplementedException;
import org.displaytag.test.DisplaytagCase;


/**
 * Tests for DISPL-224 - Adding the "scope" attribute to table header cells for web accessibility.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ224Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-224.jsp";
    }

    /**
     * Check the content of the title attribute.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        // a reminder, test is missing
        throw new NotImplementedException("test not implemented");

        // WebRequest request = new GetMethodWebRequest(jspName);
        // WebResponse response = runner.getResponse(request);
        //
        // WebTable[] tables = response.getTables();
        // assertEquals("Expected 1 table in result.", 1, tables.length);

    }

}