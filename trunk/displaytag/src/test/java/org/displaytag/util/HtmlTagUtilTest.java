package org.displaytag.util;

import junit.framework.TestCase;


/**
 * Test case for org.displaytag.util.HtmlTagUtil.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class HtmlTagUtilTest extends TestCase
{

    /**
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return getClass().getName() + "." + super.getName();
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlTagsSimple()
    {
        assertEquals("well done", HtmlTagUtil.stripHTMLTags("<strong>well</strong> done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlTagsNoHtml()
    {
        assertEquals("well done", HtmlTagUtil.stripHTMLTags("well done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlTagsWithQuote()
    {
        assertEquals("&quot;well&quot; done", HtmlTagUtil.stripHTMLTags("\"well\" done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlTagsMultiple()
    {
        assertEquals("well done again", HtmlTagUtil
            .stripHTMLTags("<a href=\"go\"><strong>well <em>done</em></strong> again</a>"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlUnbalancedOpenStart()
    {
        assertEquals("", HtmlTagUtil.stripHTMLTags("<well done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlUnbalancedOpenMiddle()
    {
        assertEquals("well ", HtmlTagUtil.stripHTMLTags("well <done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlUnbalancedClosed()
    {
        assertEquals("well> done", HtmlTagUtil.stripHTMLTags("well> done"));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthNoHtml()
    {
        assertEquals("well...", HtmlTagUtil.abbreviateHtmlString("well done", 4, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthShorter()
    {
        assertEquals("well done", HtmlTagUtil.abbreviateHtmlString("well done", 9, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthHtmlShorter()
    {
        assertEquals("<strong>well</strong> done", HtmlTagUtil.abbreviateHtmlString(
            "<strong>well</strong> done",
            10,
            false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthEntity()
    {
        assertEquals("&amp; wel...", HtmlTagUtil.abbreviateHtmlString("&amp; well done", 5, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthBrokenEntity()
    {
        assertEquals("&amp;...", HtmlTagUtil.abbreviateHtmlString("&amp; well done", 1, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthUnescapedAmpersand()
    {
        assertEquals("& well d...", HtmlTagUtil.abbreviateHtmlString("& well done", 8, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthHtmlSimple()
    {
        assertEquals("<strong>well...</strong>", HtmlTagUtil.abbreviateHtmlString(
            "<strong>well done</strong>",
            4,
            false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthHtmlNoClosingTag()
    {
        assertEquals("well<br> ...", HtmlTagUtil.abbreviateHtmlString("well<br> done", 5, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthHtmlEndingTag()
    {
        assertEquals("well<br>", HtmlTagUtil.abbreviateHtmlString("well<br>", 5, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthHtmlEmptyTag()
    {
        assertEquals("well <br/> ...", HtmlTagUtil.abbreviateHtmlString("well <br/> done", 6, false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthHtmlComplex()
    {
        assertEquals("<a href=\"link\">well...</a>", HtmlTagUtil.abbreviateHtmlString(
            "<a href=\"link\">well <strong>done</strong></a>",
            4,
            false));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByNumberOfWordsNoHtml()
    {
        assertEquals("well...", HtmlTagUtil.abbreviateHtmlString("well done", 1, true));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByNumberOfWordsShorter()
    {
        assertEquals("well done", HtmlTagUtil.abbreviateHtmlString("well done", 2, true));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByNumberOfWordsHtmlShorter()
    {
        assertEquals("<strong>well</strong> done", HtmlTagUtil.abbreviateHtmlString(
            "<strong>well</strong> done",
            3,
            true));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByLengthNumberOfWordsHtmlSimple()
    {
        assertEquals("<strong>well...</strong>", HtmlTagUtil
            .abbreviateHtmlString("<strong>well done</strong>", 1, true));
    }

    /**
     * Test for the abbreviateHtmlString() method.
     */
    public void testAbbreviateHtmlStringByNumberOfWordsHtmlComplex()
    {
        assertEquals("<a href=\"link\">well...</a>", HtmlTagUtil.abbreviateHtmlString(
            "<a href=\"link\">well <strong>done</strong></a>",
            1,
            true));
    }

}