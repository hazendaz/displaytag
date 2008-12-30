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
 * Tests for column decorators.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ListIndexTableDecoratorTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "listindex.jsp";
    }

    /**
     * Checks that the generated page contains decorated values.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Test
    public void doTest() throws Exception
    {
        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_PAGE);

        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        request.setParameter(mediaParameter, "2");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        WebTable[] tables = response.getTables();

        Assert.assertEquals("Wrong number of tables.", 1, tables.length);
        Assert.assertEquals("ViewIndex is wrong", "0", tables[0].getCellAsText(1, 0));
        Assert.assertEquals("ViewIndex is wrong", "1", tables[0].getCellAsText(2, 0));

        Assert.assertEquals("ListIndex is wrong", "3", tables[0].getCellAsText(1, 1));
        Assert.assertEquals("ListIndex is wrong", "4", tables[0].getCellAsText(2, 1));

    }
}