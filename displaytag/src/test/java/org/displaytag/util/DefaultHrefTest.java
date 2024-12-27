/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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

import org.displaytag.test.URLAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for org.displaytag.util.Href.
 */
class DefaultHrefTest {

    /**
     * Test a simple URL without parameters.
     */
    @Test
    void testSimpleHref() {
        final String url = "http://www.displaytag.org/displaytag";
        final Href href = new DefaultHref(url);
        final String newUrl = href.toString();
        URLAssertions.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing parameters.
     */
    @Test
    void testHrefWithParameters() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2";
        final Href href = new DefaultHref(url);
        final String newUrl = href.toString();
        URLAssertions.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing parameters without values.
     */
    @Test
    void testHrefParamWithoutValue() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1";
        final Href href = new DefaultHref(url);
        final String newUrl = href.toString();
        URLAssertions.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing multiple parameters (some of them without values).
     */
    @Test
    void testHrefMultipleParamWithoutValue() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1&param2=2";
        final Href href = new DefaultHref(url);
        final String newUrl = href.toString();
        URLAssertions.assertEquals(url, newUrl);
    }

    /**
     * Test for URLs containing parameters with multiple values.
     */
    @Test
    void testHrefWithMultipleParameters() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2&param2=3&param2=4&param2=";
        final Href href = new DefaultHref(url);
        final String newUrl = href.toString();
        URLAssertions.assertEquals(url, newUrl);
    }

    /**
     * Test for urls containing anchors.
     */
    @Test
    void testHrefWithAnchor() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp#thisanchor";
        final Href href = new DefaultHref(url);
        final String newUrl = href.toString();
        URLAssertions.assertEquals(url, newUrl);
    }

    /**
     * Test href with empty anchor.
     */
    @Test
    void testHrefWithEmptyAnchor() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#";
        final Href href = new DefaultHref(url);
        final String newUrl = href.toString();
        URLAssertions.assertEquals(url, newUrl);
    }

    /**
     * Test for urls containin anchors and parameters.
     */
    @Test
    void testHrefWithAnchorAndParameters() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        final Href href = new DefaultHref(url);
        final String newUrl = href.toString();
        URLAssertions.assertEquals(url, newUrl);
    }

    /**
     * Test for urls containing quotes.
     */
    @Test
    void testHrefWithQuotes() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=aquote'test";
        final Href href = new DefaultHref(url);
        final String newUrl = href.toString();
        URLAssertions.assertEquals(url, newUrl);
    }

    /**
     * Test the generation of an Href object from another Href.
     */
    @Test
    void testHrefCopy() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        final Href href = new DefaultHref(url);
        final Href copy = (Href) href.clone();
        URLAssertions.assertEquals(copy.toString(), href.toString());
    }

    /**
     * Test the clone() implementation.
     */
    @Test
    void testClone() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        final Href href = new DefaultHref(url);
        final Href clone = (Href) href.clone();
        Assertions.assertEquals(href, clone);

        clone.addParameter("onlyinclone", "1");
        Assertions.assertNotEquals(href, clone);
    }

    /**
     * Tests the equals() implementation.
     */
    @Test
    void testEquals() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        final Href href = new DefaultHref(url);
        final Href href2 = new DefaultHref(url);
        Assertions.assertEquals(href, href2);
    }

    /**
     * Test for added parameters.
     */
    @Test
    void testAddParameter() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        final Href href = new DefaultHref(url);
        href.addParameter("param3", "value3");
        href.addParameter("param4", 4);
        final String newUrl = href.toString();
        URLAssertions.assertEquals(
                "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2&param3=value3&param4=4#thisanchor",
                newUrl);
    }

    /**
     * test for setParameterMap().
     */
    @Test
    void testSetParameterMap() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp#thisanchor";
        final Href href = new DefaultHref(url);

        final Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("new1", new String[] { "new1value" });
        parameterMap.put("new2", new String[] { "new2value" });
        parameterMap.put("new3", null);
        href.setParameterMap(parameterMap);

        final String newUrl = href.toString();
        URLAssertions.assertEquals(
                "http://www.displaytag.org/displaytag/index.jsp?new1=new1value&new2=new2value&new3=#thisanchor",
                newUrl);
    }

    /**
     * test for addParameterMap().
     */
    @Test
    void testAddParameterMap() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1#thisanchor";
        final Href href = new DefaultHref(url);

        final Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("new1", new String[] { "new1value" });
        parameterMap.put("new2", new String[] { "new2value" });
        parameterMap.put("new3", null);
        href.addParameterMap(parameterMap);

        final String newUrl = href.toString();
        URLAssertions.assertEquals(
                "http://www.displaytag.org/displaytag/index.jsp?param1=1&new1=new1value&new2=new2value&new3=#thisanchor",
                newUrl);

    }

    /**
     * test for addParameterMap().
     */
    @Test
    void testAddParameterMapMultiValue() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp";
        final Href href = new DefaultHref(url);

        final Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("param1", new String[] { "à", "<" });
        href.addParameterMap(parameterMap);

        final String newUrl = href.toString();
        Assertions.assertEquals("http://www.displaytag.org/displaytag/index.jsp?param1=%C3%A0&amp;param1=%3C", newUrl);

    }

    /**
     * test for addParameterMap() with overriding parameters.
     */
    @Test
    void testAddParameterMapOverridingParameters() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=original#thisanchor";
        final Href href = new DefaultHref(url);

        final Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("param1", new String[] { "original" });
        parameterMap.put("new1", new String[] { "new1value" });
        href.addParameterMap(parameterMap);

        final String newUrl = href.toString();
        URLAssertions.assertEquals(
                "http://www.displaytag.org/displaytag/index.jsp?param1=original&new1=new1value#thisanchor", newUrl);

    }

    /**
     * test for base url extraction.
     */
    @Test
    void testGetBaseUrl() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2#thisanchor";
        final Href href = new DefaultHref(url);
        Assertions.assertEquals("http://www.displaytag.org/displaytag/index.jsp", href.getBaseUrl());
    }

    /**
     * Complex test.
     */
    @Test
    void testComplex() {
        final String url = "http://www.displaytag.org/EProcurement/do/searchWorkflowAction?initiator=AVINASH&wfid="
                + "&approvedTDate=&initiatedFDate=&status=default&d-3824-p=2&initiatedTDate=04/28/2004"
                + "&approvedFDate=&method=search&approver=";
        final Href href = new DefaultHref(url);
        final String newUrl = href.toString();
        URLAssertions.assertEquals(url, newUrl);
    }

    /**
     * test for url without base.
     */
    @Test
    void testNoBaseUrl() {
        final String url = "?param1=1&param2=2#thisanchor";
        final Href href = new DefaultHref(url);
        Assertions.assertEquals("", href.getBaseUrl());
        URLAssertions.assertEquals(url, href.toString());
    }

    /**
     * Testadd parameter map.
     */
    public void testaddParameterMap() {
        final Map<String, String[]> parametersMap = new HashMap<>();

        parametersMap.put("modifiedArray", new String[] { "a&nbspb", "c&nbspd" });

        final DefaultHref defaultHref = new DefaultHref("");
        defaultHref.addParameterMap(parametersMap);
        final String[] modifiedArray = parametersMap.get("modifiedArray");
        Assertions.assertEquals("a&nbspb", modifiedArray[0]);
        Assertions.assertEquals("c&nbspd", modifiedArray[1]);
    }

}
