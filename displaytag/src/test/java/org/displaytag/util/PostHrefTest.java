/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import org.displaytag.test.URLAssertions;
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

}
