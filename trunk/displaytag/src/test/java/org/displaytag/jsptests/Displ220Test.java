package org.displaytag.jsptests;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test for DISPL-220 - Case-insensitive sorting.
 * @author Fabrizio Giustina
 * @version $Id$
 */
public class Displ220Test extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "DISPL-220.jsp";
    }

    /**
     * No exception when an invalid page is requested.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        WebResponse response = runner.getResponse(request);

        ParamEncoder encoder = new ParamEncoder("table");
        request.setParameter(encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE), "2");

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables in result.", 1, tables.length);
        Assert.assertEquals("Wrong number of rows in result.", 6, tables[0].getRowCount());

        if (log.isDebugEnabled())
        {
            log.debug(response.getText());
        }

        Assert.assertEquals("Wrong column content.", "aaa", tables[0].getCellAsText(1, 0));
        Assert.assertEquals("Wrong column content.", "AAb", tables[0].getCellAsText(2, 0));
        Assert.assertEquals("Wrong column content.", "àAc", tables[0].getCellAsText(3, 0));
        Assert.assertEquals("Wrong column content.", "Aàd", tables[0].getCellAsText(4, 0));
        Assert.assertEquals("Wrong column content.", "bbb", tables[0].getCellAsText(5, 0));
    }

}