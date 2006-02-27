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
        String linked = new AutolinkColumnDecorator()
            .decorate("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
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
        String linked = new AutolinkColumnDecorator()
            .decorate("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
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
        String linked = new AutolinkColumnDecorator().decorate("http://foo.bar cat http://stoat");

        assertEquals(
            "<a href=\"http://foo.bar\">http://foo.bar</a> cat <a href=\"http://stoat\">http://stoat</a>",
            linked);
    }

    /**
     * Test simple link.
     */
    public void testSimpleLink()
    {
        String linked = new AutolinkColumnDecorator().decorate("http://foo.bar");

        assertEquals("<a href=\"http://foo.bar\">http://foo.bar</a>", linked);
    }

    /**
     * Test simple https link.
     */
    public void testSimpleHttpsLink()
    {
        String linked = new AutolinkColumnDecorator().decorate("https://foo.bar");

        assertEquals("<a href=\"https://foo.bar\">https://foo.bar</a>", linked);
    }

    /**
     * Test simple ftp link.
     */
    public void testSimpleFtpLink()
    {
        String linked = new AutolinkColumnDecorator().decorate("ftp://foo.bar");

        assertEquals("<a href=\"ftp://foo.bar\">ftp://foo.bar</a>", linked);
    }

    /**
     * Test simple email.
     */
    public void testSimpleEmail()
    {
        String linked = new AutolinkColumnDecorator().decorate("foo@bar.com");
        assertEquals("<a href=\"mailto:foo@bar.com\">foo@bar.com</a>", linked);
    }

    /**
     * Test simple link plus dot.
     */
    public void testSimpleLinkPlusDot()
    {
        String linked = new AutolinkColumnDecorator().decorate("http://foo.bar .");
        assertEquals("<a href=\"http://foo.bar\">http://foo.bar</a> .", linked);
    }

    /**
     * Test no link.
     */
    public void testNoLink()
    {
        String linked = new AutolinkColumnDecorator().decorate("aa://bb");
        assertEquals("aa://bb", linked);
    }

    /**
     * Test no link beginning.
     */
    public void testNoLinkBeginning()
    {
        String linked = new AutolinkColumnDecorator().decorate("://bb");
        assertEquals("://bb", linked);
    }

}