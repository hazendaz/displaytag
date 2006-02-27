package org.displaytag.decorator;

import junit.framework.TestCase;


/**
 * Test case for AutolinkColumnDecorator.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class AutolinkColumnDecoratorTest extends TestCase
{

    /**
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return getClass().getName() + "." + super.getName();
    }

    /**
     * Test for [952129] column:autolink throwing exception.
     */
    public void testLongTextWithLink()
    {
        Object linked = new AutolinkColumnDecorator().decorate(
            "A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. http://foo.bar.",
            null,
            null);

        assertEquals("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. <a href=\"http://foo.bar.\">http://foo.bar.</a>", linked);
    }

    /**
     * Test for [952129] column:autolink throwing exception.
     */
    public void testLongTextWithEmail()
    {
        Object linked = new AutolinkColumnDecorator().decorate(
            "A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. foo@bar.com.",
            null,
            null);

        assertEquals("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
            + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. <a href=\"mailto:foo@bar.com.\">foo@bar.com.</a>", linked);
    }

    /**
     * Test for [952132 ] autolink garbling urls.
     */
    public void testGarbledUrl()
    {
        Object linked = new AutolinkColumnDecorator().decorate("http://foo.bar cat http://stoat", null, null);

        assertEquals(
            "<a href=\"http://foo.bar\">http://foo.bar</a> cat <a href=\"http://stoat\">http://stoat</a>",
            linked);
    }

    /**
     * Test simple link.
     */
    public void testSimpleLink()
    {
        Object linked = new AutolinkColumnDecorator().decorate("http://foo.bar", null, null);

        assertEquals("<a href=\"http://foo.bar\">http://foo.bar</a>", linked);
    }

    /**
     * Test simple https link.
     */
    public void testSimpleHttpsLink()
    {
        Object linked = new AutolinkColumnDecorator().decorate("https://foo.bar", null, null);

        assertEquals("<a href=\"https://foo.bar\">https://foo.bar</a>", linked);
    }

    /**
     * Test simple ftp link.
     */
    public void testSimpleFtpLink()
    {
        Object linked = new AutolinkColumnDecorator().decorate("ftp://foo.bar", null, null);

        assertEquals("<a href=\"ftp://foo.bar\">ftp://foo.bar</a>", linked);
    }

    /**
     * Test simple email.
     */
    public void testSimpleEmail()
    {
        Object linked = new AutolinkColumnDecorator().decorate("foo@bar.com", null, null);
        assertEquals("<a href=\"mailto:foo@bar.com\">foo@bar.com</a>", linked);
    }

    /**
     * Test simple link plus dot.
     */
    public void testSimpleLinkPlusDot()
    {
        Object linked = new AutolinkColumnDecorator().decorate("http://foo.bar .", null, null);
        assertEquals("<a href=\"http://foo.bar\">http://foo.bar</a> .", linked);
    }

    /**
     * Test no link.
     */
    public void testNoLink()
    {
        Object linked = new AutolinkColumnDecorator().decorate("aa://bb", null, null);
        assertEquals("aa://bb", linked);
    }

    /**
     * Test no link beginning.
     */
    public void testNoLinkBeginning()
    {
        Object linked = new AutolinkColumnDecorator().decorate("://bb", null, null);
        assertEquals("://bb", linked);
    }

}