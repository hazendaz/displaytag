package org.displaytag.tags;

import org.displaytag.test.DisplaytagCase;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/* 
 * Date: Sep 19, 2004
 * Time: 6:26:05 PM
 * $Header$
 */

/**
 * Verify that the TableProperties will show values from the proper locale.
 * @author rapruitt
 */
public class I18nPropertiesTest extends DisplaytagCase
{
    /**
     * No results for an en locale.
     */
    private String noResutsMsg_default = "There are no results.";
    /**
     * No results for an it locale.
     */
    private String noResultsMsg_it = "Non ci sono risultati.";

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public I18nPropertiesTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "i18nProperties.jsp";
    }

    /**
     * Check that the "no results" property is loaded from the correct locale file.
     * @param jspName jsp name, with full path
     * @throws Exception any axception thrown during test.
     */
    public void doTest(String jspName) throws Exception
    {

        WebRequest request = new GetMethodWebRequest(jspName);
        request.setHeaderField("Accept-Language","en-us,en;q=0.5");

        WebResponse response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        assertTrue(response.getText().indexOf(noResutsMsg_default) > -1 );
        assertTrue(response.getText().indexOf(noResultsMsg_it) == -1 );


        // Now, with an Italian locale.
        request = new GetMethodWebRequest(jspName);
        request.setHeaderField("Accept-Language","it-it,it;q=0.5");

        response = runner.getResponse(request);

        if (log.isDebugEnabled())
        {
            log.debug("RESPONSE: " + response.getText());
        }

        assertTrue(response.getText().indexOf(noResultsMsg_it) > -1 );
        assertTrue(response.getText().indexOf(noResutsMsg_default) == -1 );
    }
}
