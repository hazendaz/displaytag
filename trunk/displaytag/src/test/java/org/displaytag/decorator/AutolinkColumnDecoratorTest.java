/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.decorator;

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