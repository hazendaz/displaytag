package org.displaytag.util;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.displaytag.test.URLAssert;


/**
 * Test case for org.displaytag.util.Href.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class HrefTest extends TestCase
{

    /**
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return getClass().getName() + "." + super.getName();
    }

    /**
     * Test a simple URL without parameters.
     */
    public final void testSimpleHref()
    {
        String url = "http://www.displaytag.org/displaytag";
        Href href = new Href(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing parameters.
     */
    public final void testHrefWithParameters()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2";
        Href href = new Href(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing parameters without values.
     */
    public final void testHrefParamWithoutValue()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1";
        Href href = new Href(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing multiple parameters (some of them without values).
     */
    public final void testHrefMultipleParamWithoutValue()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1&param2=2";
        Href href = new Href(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing parameters with multiple values.
     */
    public final void testHrefWithMultipleParameters()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2&param2=3&param2=4&param2=";
        Href href = new Href(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for urls containing anchors.
     */
    public final void testHrefWithAnchor()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp#thisanchor";
        Href href = new Href(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test href with empty anchor.
     */
    public final void testHrefWithEmptyAnchor()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#";
        Href href = new Href(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for urls containin anchors and parameters.
     */
    public final void testHrefWithAnchorAndParameters()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new Href(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for urls containing quotes.
     */
    public final void testHrefWithQuotes()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=aquote'test";
        Href href = new Href(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test the generation of an Href object from another Href.
     */
    public final void testHrefCopy()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new Href(url);
        Href copy = new Href(href);
        URLAssert.assertEquals(copy.toString(), href.toString());
    }

    /**
     * Test the clone() implementation.
     */
    public final void testClone()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new Href(url);
        Href clone = (Href) href.clone();
        assertEquals(href, clone);

        clone.addParameter("onlyinclone", "1");
        assertFalse(href.equals(clone));
    }

    /**
     * Tests the equals() implementation.
     */
    public final void testEquals()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new Href(url);
        Href href2 = new Href(url);
        assertEquals(href, href2);
    }

    /**
     * Test for added parameters.
     */
    public final void testAddParameter()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new Href(url);
        href.addParameter("param3", "value3");
        href.addParameter("param4", 4);
        String newUrl = href.toString();
        URLAssert.assertEquals(
            "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2&param3=value3&param4=4#thisanchor",
            newUrl);
    }

    /**
     * test for setParameterMap().
     */
    public final void testSetParameterMap()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp#thisanchor";
        Href href = new Href(url);

        Map parameterMap = new HashMap();
        parameterMap.put("new1", "new1value");
        parameterMap.put("new2", "new2value");
        parameterMap.put("new3", null);
        href.setParameterMap(parameterMap);

        String newUrl = href.toString();
        URLAssert.assertEquals(
            "http://www.displaytag.org/displaytag/index.jsp?new1=new1value&new2=new2value&new3=#thisanchor",
            newUrl);
    }

    /**
     * test for addParameterMap().
     */
    public final void testAddParameterMap()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1#thisanchor";
        Href href = new Href(url);

        Map parameterMap = new HashMap();
        parameterMap.put("new1", "new1value");
        parameterMap.put("new2", "new2value");
        parameterMap.put("new3", null);
        href.addParameterMap(parameterMap);

        String newUrl = href.toString();
        URLAssert.assertEquals(
            "http://www.displaytag.org/displaytag/index.jsp?param1=1&new1=new1value&new2=new2value&new3=#thisanchor",
            newUrl);

    }

    /**
     * test for addParameterMap().
     */
    public final void testAddParameterMapMultiValue()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp";
        Href href = new Href(url);

        Map parameterMap = new HashMap();
        parameterMap.put("param1", new String[]{"à", "<"});
        href.addParameterMap(parameterMap);

        String newUrl = href.toString();
        assertEquals("http://www.displaytag.org/displaytag/index.jsp?param1=&agrave;&amp;param1=&lt;", newUrl);

    }

    /**
     * test for addParameterMap() with overriding parameters.
     */
    public final void testAddParameterMapOverridingParameters()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=original#thisanchor";
        Href href = new Href(url);

        Map parameterMap = new HashMap();
        parameterMap.put("param1", "original");
        parameterMap.put("new1", "new1value");
        href.addParameterMap(parameterMap);

        String newUrl = href.toString();
        URLAssert.assertEquals(
            "http://www.displaytag.org/displaytag/index.jsp?param1=original&new1=new1value#thisanchor",
            newUrl);

    }

    /**
     * test for base url extraction.
     */
    public final void testGetBaseUrl()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new Href(url);
        assertEquals(href.getBaseUrl(), "http://www.displaytag.org/displaytag/index.jsp");
    }

    /**
     * Complex test.
     */
    public final void testComplex()
    {
        String url = "http://www.displaytag.org/EProcurement/do/searchWorkflowAction?initiator=AVINASH&wfid="
            + "&approvedTDate=&initiatedFDate=&status=default&d-3824-p=2&initiatedTDate=04/28/2004"
            + "&approvedFDate=&method=search&approver=";
        Href href = new Href(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * test for url without base.
     */
    public final void testNoBaseUrl()
    {
        String url = "?param1=1&param2=2#thisanchor";
        Href href = new Href(url);
        assertEquals(href.getBaseUrl(), "");
        URLAssert.assertEquals(url, href.toString());
    }

}