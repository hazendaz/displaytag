package org.displaytag.tags.el;

import org.displaytag.tags.GroupTest;


/**
 * Tests for basic displaytag functionalities.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELGroupTest extends GroupTest
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ELGroupTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/el/group.jsp";
    }

}