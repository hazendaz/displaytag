/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import java.util.Map;

import org.displaytag.test.URLAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The Class PostHrefTest.
 */
class PostHrefTest {

    /**
     * Test for URLs containing parameters.
     */
    @Test
    void testHrefWithParameters() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=1&param2=2";
        final Href href = new PostHref(new DefaultHref(url), "frm");
        final String newUrl = href.toString();
        URLAssertions.assertEquals("javascript:displaytagform('frm',[{f:'param1',v:'1'},{f:'param2',v:'2'}])", newUrl);
    }

    /**
     * Test for URLs containing parameters.
     */
    @Test
    void testHrefWithParametersToBeEscaped() {
        final String url = "http://www.displaytag.org/displaytag/index.jsp?param1=a'a&param2=2";
        final Href href = new PostHref(new DefaultHref(url), "frm");
        final String newUrl = href.toString();
        URLAssertions.assertEquals("javascript:displaytagform('frm',[{f:'param1',v:'a\\'a'},{f:'param2',v:'2'}])",
                newUrl);
    }

    @Test
    void testDelegatingMethodsAndClone() {
        final DefaultHref parent = new DefaultHref("http://www.displaytag.org/displaytag/index.jsp?x=1#a");
        final PostHref href = new PostHref(parent, "frm");

        href.addParameter("new", "v");
        href.addParameter("num", 3);
        href.removeParameter("x");
        href.setAnchor("b");
        href.setFullUrl("http://www.displaytag.org/displaytag/index.jsp?y=2");
        href.setParameterMap(Map.of("k", new String[] { "v1", "v2" }));
        href.addParameterMap(Map.of("k2", new String[] { "v3" }));

        Assertions.assertEquals("http://www.displaytag.org/displaytag/index.jsp", href.getBaseUrl());
        Assertions.assertNull(href.getAnchor());
        Assertions.assertTrue(href.getParameterMap().containsKey("k"));
        Assertions.assertTrue(href.equals(parent));

        final Href cloned = (Href) href.clone();
        cloned.addParameter("onlyclone", "1");
        Assertions.assertNotEquals(href.toString(), cloned.toString());
    }

    @Test
    void testArrayAndQuoteEscapingInJavascriptOutput() {
        final DefaultHref parent = new DefaultHref("http://www.displaytag.org/displaytag/index.jsp");
        parent.setParameterMap(Map.of("a'b", new String[] { "one", "two\"2" }));

        final Href href = new PostHref(parent, "f");
        final String rendered = href.toString();

        Assertions.assertTrue(rendered.contains("{f:'a\\'b',v:['one','two%222']}"));
    }

}
