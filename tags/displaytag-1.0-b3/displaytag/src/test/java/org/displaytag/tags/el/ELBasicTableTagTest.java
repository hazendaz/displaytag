package org.displaytag.tags.el;

import org.displaytag.tags.BasicTableTagTest;


/**
 * Tests for basic displaytag functionalities.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class ELBasicTableTagTest extends BasicTableTagTest
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ELBasicTableTagTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/el/autocolumns.jsp";
    }
}