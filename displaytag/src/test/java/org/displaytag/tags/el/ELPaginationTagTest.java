package org.displaytag.tags.el;

import org.displaytag.tags.PaginationTest;


/**
 * Tests for pagination.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELPaginationTagTest extends PaginationTest
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ELPaginationTagTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/el/pagination.jsp";
    }

}