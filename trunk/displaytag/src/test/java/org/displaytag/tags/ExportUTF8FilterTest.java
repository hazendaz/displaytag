package org.displaytag.tags;

import org.displaytag.filter.MockFilterSupport;


/**
 * Test for #968559.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ExportUTF8FilterTest extends ExportUTF8Test
{

    /**
     * Instantiates a new test case.
     * @param name test name
     */
    public ExportUTF8FilterTest(String name)
    {
        super(name);
    }

    /**
     * @see org.displaytag.test.DisplaytagCase#getJspName()
     */
    public String getJspName()
    {
        return "utf8.jsp" + MockFilterSupport.FILTERED_EXTENSION;
    }

}