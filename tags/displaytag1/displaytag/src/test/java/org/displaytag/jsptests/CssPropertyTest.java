package org.displaytag.jsptests;

import org.apache.commons.lang.StringUtils;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for SetProperty tag.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class CssPropertyTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "cssproperty.jsp";
    }

    /**
     * Check that css classes are set correctly according to the ones in setProperty tags.
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

        assertEquals("Expected 1 table in result.", 1, tables.length);
        TableCell cell = tables[0].getTableCell(0, 0);
        assertTrue("Expected css class \"green\" not found", StringUtils.contains(cell.getClassName(), "green"));
        assertTrue("Expected css class \"purple\" not found", StringUtils.contains(cell.getClassName(), "purple"));
    }
}