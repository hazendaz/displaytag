package org.displaytag.tags.el;

import org.displaytag.tags.ColumnPoolingTest;


/**
 * Tests for column pooling in tomcat.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class ELColumnPoolingTest extends ColumnPoolingTest
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ELColumnPoolingTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/el/columnpooling.jsp";
    }

}