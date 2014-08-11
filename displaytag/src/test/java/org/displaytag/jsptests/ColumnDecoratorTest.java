package org.displaytag.jsptests;

import org.displaytag.decorator.DateColumnDecorator;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.test.KnownTypes;
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
public class ColumnDecoratorTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "columndecorator.jsp";
    }

    /**
     * Checks that the generated page contains decorated values.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        WebTable[] tables = response.getTables();

        Assert.assertEquals("Wrong number of tables.", 1, tables.length);

        Assert.assertEquals(
            "Expected decorated value not found.",
            new DateColumnDecorator().decorate(KnownTypes.TIME_VALUE, null, null),
            tables[0].getCellAsText(1, 0));
    }
}