package org.displaytag.tags.el;

import org.displaytag.tags.MediaSupportTest;


/**
 * Tests for "media" attribute.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class ELMediaSupportTest extends MediaSupportTest
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ELMediaSupportTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/el/media.jsp";
    }

}