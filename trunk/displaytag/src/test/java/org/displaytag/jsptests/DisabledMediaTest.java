package org.displaytag.jsptests;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.displaytag.tags.ColumnTag;
import org.displaytag.test.DisplaytagCase;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpInternalErrorException;
import com.meterware.httpunit.WebRequest;


/**
 * Test for #968559.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DisabledMediaTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "disabledmedia.jsp";
    }

    /**
     * Should not break on media="foo", since foo could be a valid media not enabled at runtime.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {
        // test keep
        WebRequest request = new GetMethodWebRequest(jspName);

        // save previous level, since we are expeting an excetion I don't want to fill logs
        Level previousLevel = Logger.getLogger(ColumnTag.class).getLevel();
        Logger.getLogger(ColumnTag.class).setLevel(Level.OFF);

        try
        {
            runner.getResponse(request);
        }
        catch (HttpInternalErrorException e)
        {
            fail("Should not get any error also if \"foo\" media type is not defined. " + e.getMessage());
        }
        finally
        {
            Logger.getLogger(ColumnTag.class).setLevel(previousLevel);
        }

        // ok

    }

}