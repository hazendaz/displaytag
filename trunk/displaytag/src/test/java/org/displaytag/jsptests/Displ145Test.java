package org.displaytag.jsptests;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.test.DisplaytagCase;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Tests for DISPL-145: check that pooled tags are not affected by the fix.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class Displ145Test extends DisplaytagCase
{

    /**
     * 4 generated tables.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        String httpsUrl = StringUtils.replace(getJspUrl("DISPL-145.jsp"), "http://", "https://");
        WebRequest request = new GetMethodWebRequest(httpsUrl);

        WebResponse response = runner.getResponse(request);

        WebTable[] tables = response.getTables();
        Assert.assertEquals("Wrong number of tables.", 6, tables.length);

    }

}