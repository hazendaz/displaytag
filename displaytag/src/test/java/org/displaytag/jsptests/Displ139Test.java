package org.displaytag.jsptests;

import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-139 - Will ColumnDecorator regain access to the PageContext?
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ139Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-139.jsp";
    }

    /**
     * Decorated object based on a pageContext attribute
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        WebRequest request = new GetMethodWebRequest(jspName);

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        assertEquals("Wrong number of tables.", 1, tables.length);

        assertEquals("Wrong (undecorated?) value", "pageContext: html ant", tables[0].getCellAsText(1, 0));

    }

}