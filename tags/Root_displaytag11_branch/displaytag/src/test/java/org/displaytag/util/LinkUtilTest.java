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
        String linked =
            LinkUtil.autoLink(
                "A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                    + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                    + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. http://foo.bar.");

        assertEquals(
            "A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. <a href=\"http://foo.bar.\">http://foo.bar.</a>",
            linked);
    }

    /**
     * Test for [952129] column:autolink throwing exception.
     */
    public void testLongTextWithEmail()
    {
        String linked =
            LinkUtil.autoLink(
                "A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                    + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                    + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. foo@bar.com.");

        assertEquals(
            "A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. <a href=\"mailto:foo@bar.com.\">foo@bar.com.</a>",
            linked);
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

}