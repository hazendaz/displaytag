package org.displaytag.tags.el;

import org.displaytag.tags.EmptyTableTest;


/**
 * Tests with a null list.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELEmptyTableTest extends EmptyTableTest
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ELEmptyTableTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/el/empty.jsp";
    }

}