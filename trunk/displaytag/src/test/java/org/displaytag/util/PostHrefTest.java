package org.displaytag.util;

import junit.framework.TestCase;

import org.displaytag.test.URLAssert;
import org.junit.Test;


/**
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class PostHrefTest
{

    /**
     * Test for URLs containing parameters.
     */
    @Test
    public final void testHrefWithParameters()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2";
        Href href = new PostHref(new DefaultHref(url), "frm");
        String newUrl = href.toString();
        URLAssert.assertEquals("javascript:displaytagform('frm',[{f:'param1',v:'1'},{f:'param2',v:'2'}])", newUrl);
    }

    /**
     * Test for URLs containing parameters.
     */
    @Test
    public final void testHrefWithParametersToBeEscaped()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=a'a&param2=2";
        Href href = new PostHref(new DefaultHref(url), "frm");
        String newUrl = href.toString();
        URLAssert.assertEquals("javascript:displaytagform('frm',[{f:'param1',v:'a\\'a'},{f:'param2',v:'2'}])", newUrl);
    }

}
