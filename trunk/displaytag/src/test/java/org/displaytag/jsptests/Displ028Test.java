package org.displaytag.jsptests;

import java.text.SimpleDateFormat;

import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownTypes;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-252 - Multiple (chained) column decorators
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ028Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-028.jsp";
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

        assertEquals("Value not decorated as expected", "day is "
            + new SimpleDateFormat("dd").format(new KnownTypes().getTime()), tables[0].getCellAsText(1, 0));

    }

}