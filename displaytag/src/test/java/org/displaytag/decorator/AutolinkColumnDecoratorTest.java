/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case for AutolinkColumnDecorator.
 */
class AutolinkColumnDecoratorTest {

    /**
     * Test for [952129] column:autolink throwing exception.
     */
    @Test
    void testLongTextWithLink() {
        final Object linked = new AutolinkColumnDecorator()
                .decorate("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                        + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                        + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. http://foo.bar.", null, null);

        Assertions.assertEquals("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. <a href=\"http://foo.bar.\">http://foo.bar.</a>",
                linked);
    }

    /**
     * Test for [952129] column:autolink throwing exception.
     */
    @Test
    void testLongTextWithEmail() {
        final Object linked = new AutolinkColumnDecorator()
                .decorate("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                        + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                        + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. foo@bar.com.", null, null);

        Assertions.assertEquals("A large string of text. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. "
                + "Foo bar. Foo bar. Foo bar. Foo bar. Foo bar. <a href=\"mailto:foo@bar.com.\">foo@bar.com.</a>",
                linked);
    }

    /**
     * Test for [952132 ] autolink garbling urls.
     */
    @Test
    void testGarbledUrl() {
        final Object linked = new AutolinkColumnDecorator().decorate("http://foo.bar cat http://stoat", null, null);

        Assertions.assertEquals(
                "<a href=\"http://foo.bar\">http://foo.bar</a> cat <a href=\"http://stoat\">http://stoat</a>", linked);
    }

    /**
     * Test simple link.
     */
    @Test
    void testSimpleLink() {
        final Object linked = new AutolinkColumnDecorator().decorate("http://foo.bar", null, null);

        Assertions.assertEquals("<a href=\"http://foo.bar\">http://foo.bar</a>", linked);
    }

    /**
     * Test simple https link.
     */
    @Test
    void testSimpleHttpsLink() {
        final Object linked = new AutolinkColumnDecorator().decorate("https://foo.bar", null, null);

        Assertions.assertEquals("<a href=\"https://foo.bar\">https://foo.bar</a>", linked);
    }

    /**
     * Test simple ftp link.
     */
    @Test
    void testSimpleFtpLink() {
        final Object linked = new AutolinkColumnDecorator().decorate("ftp://foo.bar", null, null);

        Assertions.assertEquals("<a href=\"ftp://foo.bar\">ftp://foo.bar</a>", linked);
    }

    /**
     * Test simple email.
     */
    @Test
    void testSimpleEmail() {
        final Object linked = new AutolinkColumnDecorator().decorate("foo@bar.com", null, null);
        Assertions.assertEquals("<a href=\"mailto:foo@bar.com\">foo@bar.com</a>", linked);
    }

    /**
     * Test simple link plus dot.
     */
    @Test
    void testSimpleLinkPlusDot() {
        final Object linked = new AutolinkColumnDecorator().decorate("http://foo.bar .", null, null);
        Assertions.assertEquals("<a href=\"http://foo.bar\">http://foo.bar</a> .", linked);
    }

    /**
     * Test no link.
     */
    @Test
    void testNoLink() {
        final Object linked = new AutolinkColumnDecorator().decorate("aa://bb", null, null);
        Assertions.assertEquals("aa://bb", linked);
    }

    /**
     * Test no link beginning.
     */
    @Test
    void testNoLinkBeginning() {
        final Object linked = new AutolinkColumnDecorator().decorate("://bb", null, null);
        Assertions.assertEquals("://bb", linked);
    }

}
