/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for org.displaytag.util.HtmlTagUtil.
 */
class HtmlTagUtilTest {

    /**
     * Test for the stripHTMLTags() method.
     */
    @Test
    void testStripHtmlTagsSimple() {
        Assertions.assertEquals("well done", HtmlTagUtil.stripHTMLTags("<strong>well</strong> done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    @Test
    void testStripHtmlTagsNoHtml() {
        Assertions.assertEquals("well done", HtmlTagUtil.stripHTMLTags("well done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    @Test
    void testStripHtmlTagsWithQuote() {
        Assertions.assertEquals("&quot;well&quot; done", HtmlTagUtil.stripHTMLTags("\"well\" done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    @Test
    void testStripHtmlTagsMultiple() {
        Assertions.assertEquals("well done again",
                HtmlTagUtil.stripHTMLTags("<a href=\"go\"><strong>well <em>done</em></strong> again</a>"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    @Test
    void testStripHtmlUnbalancedOpenStart() {
        Assertions.assertEquals("", HtmlTagUtil.stripHTMLTags("<well done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    @Test
    void testStripHtmlUnbalancedOpenMiddle() {
        Assertions.assertEquals("well ", HtmlTagUtil.stripHTMLTags("well <done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    @Test
    void testStripHtmlUnbalancedClosed() {
        Assertions.assertEquals("well> done", HtmlTagUtil.stripHTMLTags("well> done"));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthNoHtml() {
        Assertions.assertEquals("well...", HtmlTagUtil.abbreviateHtmlString("well done", 4, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthShorter() {
        Assertions.assertEquals("well done", HtmlTagUtil.abbreviateHtmlString("well done", 9, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthHtmlShorter() {
        Assertions.assertEquals("<strong>well</strong> done",
                HtmlTagUtil.abbreviateHtmlString("<strong>well</strong> done", 10, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthEntity() {
        Assertions.assertEquals("&amp; wel...", HtmlTagUtil.abbreviateHtmlString("&amp; well done", 5, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthBrokenEntity() {
        Assertions.assertEquals("&amp;...", HtmlTagUtil.abbreviateHtmlString("&amp; well done", 1, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthUnescapedAmpersand() {
        Assertions.assertEquals("& well d...", HtmlTagUtil.abbreviateHtmlString("& well done", 8, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthHtmlSimple() {
        Assertions.assertEquals("<strong>well...</strong>",
                HtmlTagUtil.abbreviateHtmlString("<strong>well done</strong>", 4, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthHtmlNoClosingTag() {
        Assertions.assertEquals("well<br> ...", HtmlTagUtil.abbreviateHtmlString("well<br> done", 5, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthHtmlEndingTag() {
        Assertions.assertEquals("well<br>", HtmlTagUtil.abbreviateHtmlString("well<br>", 5, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthHtmlEmptyTag() {
        Assertions.assertEquals("well <br/> ...", HtmlTagUtil.abbreviateHtmlString("well <br/> done", 6, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthHtmlComplex() {
        Assertions.assertEquals("<a href=\"link\">well...</a>",
                HtmlTagUtil.abbreviateHtmlString("<a href=\"link\">well <strong>done</strong></a>", 4, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByNumberOfWordsNoHtml() {
        Assertions.assertEquals("well...", HtmlTagUtil.abbreviateHtmlString("well done", 1, true));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByNumberOfWordsShorter() {
        Assertions.assertEquals("well done", HtmlTagUtil.abbreviateHtmlString("well done", 2, true));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByNumberOfWordsHtmlShorter() {
        Assertions.assertEquals("<strong>well</strong> done",
                HtmlTagUtil.abbreviateHtmlString("<strong>well</strong> done", 3, true));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByLengthNumberOfWordsHtmlSimple() {
        Assertions.assertEquals("<strong>well...</strong>",
                HtmlTagUtil.abbreviateHtmlString("<strong>well done</strong>", 1, true));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    @Test
    void testAbbreviateHtmlStringByNumberOfWordsHtmlComplex() {
        Assertions.assertEquals("<a href=\"link\">well...</a>",
                HtmlTagUtil.abbreviateHtmlString("<a href=\"link\">well <strong>done</strong></a>", 1, true));
    }

}
