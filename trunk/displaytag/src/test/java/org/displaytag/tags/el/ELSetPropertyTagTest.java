package org.displaytag.tags.el;

import org.displaytag.tags.SetPropertyTagTest;


/**
 * Tests for setProperty tag.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELSetPropertyTagTest extends SetPropertyTagTest
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ELSetPropertyTagTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/el/setproperty.jsp";
    }

}