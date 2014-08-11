package org.displaytag.decorator;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;


/**
 * Test case for AutolinkColumnDecorator.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class AutolinkColumnDecoratorTest
{

    /**
     * Test for [952129] column:autolink throwing exception.
     */
    @Test
    public void testLongTextWithLink()
    {
        Object linked = new AutolinkColumnDecorator().decorate(
            "A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. http://foo.bar.",
            null,
            null);

        Assert.assertEquals("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. <a href=\"http://foo.bar.\">http://foo.bar.</a>", linked);
    }

    /**
     * Test for [952129] column:autolink throwing exception.
     */
    @Test
    public void testLongTextWithEmail()
    {
        Object linked = new AutolinkColumnDecorator().decorate(
            "A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. foo@bar.com.",
            null,
            null);

        Assert.assertEquals("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. <a href=\"mailto:foo@bar.com.\">foo@bar.com.</a>", linked);
    }

    /**
     * Test for [952132 ] autolink garbling urls.
     */
    @Test
    public void testGarbledUrl()
    {
        Object linked = new AutolinkColumnDecorator().decorate("http://foo.bar cat http://stoat", null, null);

        Assert.assertEquals(
            "<a href=\"http://foo.bar\">http://foo.bar</a> cat <a href=\"http://stoat\">http://stoat</a>",
            linked);
    }

    /**
     * Test simple link.
     */
    @Test
    public void testSimpleLink()
    {
        Object linked = new AutolinkColumnDecorator().decorate("http://foo.bar", null, null);

        Assert.assertEquals("<a href=\"http://foo.bar\">http://foo.bar</a>", linked);
    }

    /**
     * Test simple https link.
     */
    @Test
    public void testSimpleHttpsLink()
    {
        Object linked = new AutolinkColumnDecorator().decorate("https://foo.bar", null, null);

        Assert.assertEquals("<a href=\"https://foo.bar\">https://foo.bar</a>", linked);
    }

    /**
     * Test simple ftp link.
     */
    @Test
    public void testSimpleFtpLink()
    {
        Object linked = new AutolinkColumnDecorator().decorate("ftp://foo.bar", null, null);

        Assert.assertEquals("<a href=\"ftp://foo.bar\">ftp://foo.bar</a>", linked);
    }

    /**
     * Test simple email.
     */
    @Test
    public void testSimpleEmail()
    {
        Object linked = new AutolinkColumnDecorator().decorate("foo@bar.com", null, null);
        Assert.assertEquals("<a href=\"mailto:foo@bar.com\">foo@bar.com</a>", linked);
    }

    /**
     * Test simple link plus dot.
     */
    @Test
    public void testSimpleLinkPlusDot()
    {
        Object linked = new AutolinkColumnDecorator().decorate("http://foo.bar .", null, null);
        Assert.assertEquals("<a href=\"http://foo.bar\">http://foo.bar</a> .", linked);
    }

    /**
     * Test no link.
     */
    @Test
    public void testNoLink()
    {
        Object linked = new AutolinkColumnDecorator().decorate("aa://bb", null, null);
        Assert.assertEquals("aa://bb", linked);
    }

    /**
     * Test no link beginning.
     */
    @Test
    public void testNoLinkBeginning()
    {
        Object linked = new AutolinkColumnDecorator().decorate("://bb", null, null);
        Assert.assertEquals("://bb", linked);
    }

}