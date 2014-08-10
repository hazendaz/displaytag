package org.displaytag.jsptests;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.tags.TableTag;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.test.DisplaytagCase;
import org.displaytag.util.ParamEncoder;
import org.junit.Assert;
import org.junit.Test;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpInternalErrorException;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;


/**
 * Tests the ResponseOverrideFilter.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class FilterTest extends DisplaytagCase
{

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "filter.jsp";
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#doTest(java.lang.String)
     */
    @Override
    @Test
    public void doTest() throws Exception
    {
        ParamEncoder encoder = new ParamEncoder("table");
        String mediaParameter = encoder.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);

        WebRequest request = new GetMethodWebRequest(getJspUrl(getJspName()));
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.XML.getCode()));

        // save previous level, since we are expeting an excetion I don't want to fill logs
        Level previousLevel = Logger.getLogger(TableTag.class).getLevel();

        try
        {
            // disable log
            Logger.getLogger(TableTag.class).setLevel(Level.OFF);

            // check if page need a filter (unfiltered request)
            runner.getResponse(request);
            Assert.fail("Request works also without a filter. Can't test it properly.");
        }
        catch (HttpInternalErrorException e)
        {
            // it's ok
        }

        // reset log
        Logger.getLogger(TableTag.class).setLevel(previousLevel);

        request = new GetMethodWebRequest(getJspUrl(getJspName()));
        request.setParameter(mediaParameter, Integer.toString(MediaTypeEnum.XML.getCode()));

        // this enable the filter!
        request.setParameter(TableTagParameters.PARAMETER_EXPORTING, "1");

        WebResponse response = runner.getResponse(request);

        Assert.assertEquals("Expected a different content type.", "text/xml", response.getContentType());
    }

}