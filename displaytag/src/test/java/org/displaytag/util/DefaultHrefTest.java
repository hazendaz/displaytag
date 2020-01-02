/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.util;

import java.util.HashMap;
import java.util.Map;

import org.displaytag.test.URLAssert;
import org.junit.Assert;
import org.junit.Test;


/**
 * Test case for org.displaytag.util.Href.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DefaultHrefTest
{

    // /**
    // * @see junit.framework.TestCase#getName()
    // */
    // @Override
    // public String getName()
    // {
    // return getClass().getName() + "." + super.getName();
    // }

    /**
     * Test a simple URL without parameters.
     */
    @Test
    public final void testSimpleHref()
    {
        String url = "http://www.displaytag.org/displaytag";
        Href href = new DefaultHref(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing parameters.
     */
    @Test
    public final void testHrefWithParameters()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2";
        Href href = new DefaultHref(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing parameters without values.
     */
    @Test
    public final void testHrefParamWithoutValue()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1";
        Href href = new DefaultHref(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing multiple parameters (some of them without values).
     */
    @Test
    public final void testHrefMultipleParamWithoutValue()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1&param2=2";
        Href href = new DefaultHref(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing parameters with multiple values.
     */
    @Test
    public final void testHrefWithMultipleParameters()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2&param2=3&param2=4&param2=";
        Href href = new DefaultHref(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for urls containing anchors.
     */
    @Test
    public final void testHrefWithAnchor()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp#thisanchor";
        Href href = new DefaultHref(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test href with empty anchor.
     */
    @Test
    public final void testHrefWithEmptyAnchor()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#";
        Href href = new DefaultHref(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for urls containin anchors and parameters.
     */
    @Test
    public final void testHrefWithAnchorAndParameters()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new DefaultHref(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test for urls containing quotes.
     */
    @Test
    public final void testHrefWithQuotes()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=aquote'test";
        Href href = new DefaultHref(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * Test the generation of an Href object from another Href.
     */
    @Test
    public final void testHrefCopy()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new DefaultHref(url);
        Href copy = (Href) href.clone();
        URLAssert.assertEquals(copy.toString(), href.toString());
    }

    /**
     * Test the clone() implementation.
     */
    @Test
    public final void testClone()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new DefaultHref(url);
        Href clone = (Href) href.clone();
        Assert.assertEquals(href, clone);

        clone.addParameter("onlyinclone", "1");
        Assert.assertFalse(href.equals(clone));
    }

    /**
     * Tests the equals() implementation.
     */
    @Test
    public final void testEquals()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new DefaultHref(url);
        Href href2 = new DefaultHref(url);
        Assert.assertEquals(href, href2);
    }

    /**
     * Test for added parameters.
     */
    @Test
    public final void testAddParameter()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new DefaultHref(url);
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
    @Test
    public final void testSetParameterMap()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp#thisanchor";
        Href href = new DefaultHref(url);

        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("new1", new String[]{"new1value"});
        parameterMap.put("new2", new String[]{"new2value"});
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
    @Test
    public final void testAddParameterMap()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1#thisanchor";
        Href href = new DefaultHref(url);

        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("new1", new String[]{"new1value"});
        parameterMap.put("new2", new String[]{"new2value"});
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
    @Test
    public final void testAddParameterMapMultiValue()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp";
        Href href = new DefaultHref(url);

        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("param1", new String[]{"à", "<"});
        href.addParameterMap(parameterMap);

        String newUrl = href.toString();
        Assert.assertEquals("http://www.displaytag.org/displaytag/index.jsp?param1=%C3%A0&amp;param1=%3C", newUrl);

    }

    /**
     * test for addParameterMap() with overriding parameters.
     */
    @Test
    public final void testAddParameterMapOverridingParameters()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=original#thisanchor";
        Href href = new DefaultHref(url);

        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("param1", new String[]{"original"});
        parameterMap.put("new1", new String[]{"new1value"});
        href.addParameterMap(parameterMap);

        String newUrl = href.toString();
        URLAssert.assertEquals(
            "http://www.displaytag.org/displaytag/index.jsp?param1=original&new1=new1value#thisanchor",
            newUrl);

    }

    /**
     * test for base url extraction.
     */
    @Test
    public final void testGetBaseUrl()
    {
        String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        Href href = new DefaultHref(url);
        Assert.assertEquals(href.getBaseUrl(), "http://www.displaytag.org/displaytag/index.jsp");
    }

    /**
     * Complex test.
     */
    @Test
    public final void testComplex()
    {
        String url = "http://www.displaytag.org/EProcurement/do/searchWorkflowAction?initiator=AVINASH&wfid="
            + "&approvedTDate=&initiatedFDate=&status=default&d-3824-p=2&initiatedTDate=04/28/2004"
            + "&approvedFDate=&method=search&approver=";
        Href href = new DefaultHref(url);
        String newUrl = href.toString();
        URLAssert.assertEquals(url, newUrl);
    }

    /**
     * test for url without base.
     */
    @Test
    public final void testNoBaseUrl()
    {
        String url = "?param1=1&param2=2#thisanchor";
        Href href = new DefaultHref(url);
        Assert.assertEquals(href.getBaseUrl(), "");
        URLAssert.assertEquals(url, href.toString());
    }

    /**
     * Testadd parameter map.
     */
    public void testaddParameterMap()
    {
        Map<String, String[]> parametersMap = new HashMap<>();

        parametersMap.put("modifiedArray", new String[]{"a&nbspb", "c&nbspd"});

        DefaultHref defaultHref = new DefaultHref("");
        defaultHref.addParameterMap(parametersMap);
        String[] modifiedArray = parametersMap.get("modifiedArray");
        Assert.assertEquals(modifiedArray[0], "a&nbspb");
        Assert.assertEquals(modifiedArray[1], "c&nbspd");
    }

}
