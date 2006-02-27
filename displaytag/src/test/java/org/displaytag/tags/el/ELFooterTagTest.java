package org.displaytag.tags.el;

import org.displaytag.tags.FooterTagTest;


/**
 * Tests for table footer.
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class ELFooterTagTest extends FooterTagTest
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ELFooterTagTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "http://localhost/el/footer.jsp";
    }

}