package org.displaytag.util;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Test case for org.displaytag.util.LinkUtil.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class LinkUtilTest extends TestCase
{

    /**
     * logger.
     */
    private static Log log = LogFactory.getLog(LinkUtilTest.class);

    /**
     * instantiate a new test.
     * @param name test name
     */
    public LinkUtilTest(String name)
    {
        super(name);
    }

    /**
     * Test for [952129] column:autolink throwing exception.
     */
    public void testLongTextWithLink()
    {
        String linked = LinkUtil.autoLink("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. http://foo.bar.");

        assertEquals("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. <a href=\"http://foo.bar.\">http://foo.bar.</a>", linked);
    }

    /**
     * Test for [952129] column:autolink throwing exception.
     */
    public void testLongTextWithEmail()
    {
        String linked = LinkUtil.autoLink("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. foo@bar.com.");

        assertEquals("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. <a href=\"mailto:foo@bar.com.\">foo@bar.com.</a>", linked);
    }

    /**
     * Test for [952132 ] autolink garbling urls.
     */
    public void testGarbledUrl()
    {
        String linked = LinkUtil.autoLink("http://foo.bar cat http://stoat");

        log.debug(linked);

        assertEquals(
            "<a href=\"http://foo.bar\">http://foo.bar</a> cat <a href=\"http://stoat\">http://stoat</a>",
            linked);
    }

    /**
     * Test simple link.
     */
    public void testSimpleLink()
    {
        String linked = LinkUtil.autoLink("http://foo.bar");

        log.debug(linked);

        assertEquals("<a href=\"http://foo.bar\">http://foo.bar</a>", linked);
    }

    /**
     * Test simple email.
     */
    public void testSimpleEmail()
    {
        String linked = LinkUtil.autoLink("foo@bar.com");

        log.debug(linked);

        assertEquals("<a href=\"mailto:foo@bar.com\">foo@bar.com</a>", linked);
    }

    /**
     * Test simple link plus dot.
     */
    public void testSimpleLinkPlusDot()
    {
        String linked = LinkUtil.autoLink("http://foo.bar .");

        log.debug(linked);

        assertEquals("<a href=\"http://foo.bar\">http://foo.bar</a> .", linked);
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlTagsSimple()
    {
        assertEquals("well done", LinkUtil.stripHTMLTags("<strong>well</strong> done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlTagsNoHtml()
    {
        assertEquals("well done", LinkUtil.stripHTMLTags("well done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlTagsMultiple()
    {
        assertEquals("well done again", LinkUtil
            .stripHTMLTags("<a href=\"go\"><strong>well <em>done</em></strong> again</a>"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlUnbalancedOpen()
    {
        assertEquals("<well done", LinkUtil.stripHTMLTags("<well done"));
    }

    /**
     * Test for the stripHTMLTags() method.
     */
    public void testStripHtmlUnbalancedClosed()
    {
        assertEquals("well> done", LinkUtil.stripHTMLTags("well> done"));
    }

    /**
     * Test for the abbreviateHtmlStringByLength() method.
     */
    public void testAbbreviateHtmlStringByLengthNoHtml()
    {
        assertEquals("well...", LinkUtil.abbreviateHtmlString("well done", 4, false));
    }

    /**
     * Test for the abbreviateHtmlStringByLength() method.
     */
    public void testAbbreviateHtmlStringByLengthShorter()
    {
        assertEquals("well done", LinkUtil.abbreviateHtmlString("well done", 9, false));
    }

    /**
     * Test for the abbreviateHtmlStringByLength() method.
     */
    public void testAbbreviateHtmlStringByLengthHtmlShorter()
    {
        assertEquals("<strong>well</strong> done", LinkUtil.abbreviateHtmlString(
            "<strong>well</strong> done",
            10,
            false));
    }

    /**
     * Test for the abbreviateHtmlStringHtmlSimple() method.
     */
    public void testAbbreviateHtmlStringByLengthHtmlSimple()
    {
        assertEquals("<strong>well...</strong>", LinkUtil.abbreviateHtmlString("<strong>well done</strong>", 4, false));
    }

    /**
     * Test for the abbreviateHtmlStringHtmlSimple() method.
     */
    public void testAbbreviateHtmlStringByLengthHtmlComplex()
    {
        assertEquals("<a href=\\\"link\\\">well...</a>", LinkUtil.abbreviateHtmlString(
            "<a href=\"link\">well <strong>done<strong></a>",
            4,
            false));
    }

    /**
     * Test for the abbreviateHtmlStringByNumberOfWords() method.
     */
    public void testAbbreviateHtmlStringByNumberOfWordsNoHtml()
    {
        assertEquals("well...", LinkUtil.abbreviateHtmlString("well done", 1, true));
    }

    /**
     * Test for the abbreviateHtmlStringByNumberOfWords() method.
     */
    public void testAbbreviateHtmlStringByNumberOfWordsShorter()
    {
        assertEquals("well done", LinkUtil.abbreviateHtmlString("well done", 2, true));
    }

    /**
     * Test for the abbreviateHtmlStringByNumberOfWords() method.
     */
    public void testAbbreviateHtmlStringByNumberOfWordsHtmlShorter()
    {
        assertEquals("<strong>well</strong> done", LinkUtil.abbreviateHtmlString("<strong>well</strong> done", 3, true));
    }

    /**
     * Test for the abbreviateHtmlStringByNumberOfWords() method.
     */
    public void testAbbreviateHtmlStringByLengthNumberOfWordsHtmlSimple()
    {
        assertEquals("<strong>well...</strong>", LinkUtil.abbreviateHtmlString("<strong>well done</strong>", 1, true));
    }

    /**
     * Test for the abbreviateHtmlStringByNumberOfWords() method.
     */
    public void testAbbreviateHtmlStringByNumberOfWordsHtmlComplex()
    {
        assertEquals("<a href=\\\"link\\\">well...</a>", LinkUtil.abbreviateHtmlString(
            "<a href=\"link\">well <strong>done<strong></a>",
            1,
            true));
    }

}