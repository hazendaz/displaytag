package org.displaytag.tags;

import junit.framework.Test;

import org.apache.cactus.JspTestCase;
import org.apache.cactus.WebResponse;


/**
 * Basic test case to be used in displaytag project.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public abstract class DisplaytagTestCase extends JspTestCase
{

    /**
     * Instantiate a new test case.
     */
    public DisplaytagTestCase()
    {
        super();
    }

    /**
     * Instantiate a new test case.
     * @param name test name
     */
    public DisplaytagTestCase(String name)
    {
        super(name);
    }

    /**
     * Instantiate a new test case.
     * @param name test name
     * @param test Test instance
     */
    public DisplaytagTestCase(String name, Test test)
    {
        super(name, test);
    }


    /**
     * Convenience function that asserts that a substring can be found in a the returned HTTP response body.
     * @param theResponse the response from the server side.
     * @param theSubstring the substring to look for
     */
    public void assertContains(WebResponse theResponse, String theSubstring)
    {
        String target = theResponse.getText();
        if (target.indexOf(theSubstring) < 0)
        {
            fail("Response did not contain the substring: [" + theSubstring + "]");
        }
    }

    /**
     * Convenience function that asserts that a substring can be found in a the returned HTTP response body.
     * @param theResponse the response from the server side.
     * @param theSubstring the substring to look for
     */
    public void assertDoesNotContain(WebResponse theResponse, String theSubstring)
    {
        String target = theResponse.getText();
        if (target.indexOf(theSubstring) > -1)
        {
            fail("Response did not contain the substring: [" + theSubstring + "]");
        }
    }

}
