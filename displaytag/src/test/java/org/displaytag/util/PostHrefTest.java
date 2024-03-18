/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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

import org.displaytag.test.URLAssertions;
import org.junit.jupiter.api.Test;

/**
 * The Class PostHrefTest.
 *
 * @author fgiust
 *
 * @version $Revision$ ($Author$)
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
