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
 * Tests for {@link Anchor}.
 */
class AnchorTest {

    @Test
    void testOpenTagWithoutHrefOrAttributes() {
        final Anchor anchor = new Anchor(null, "text");

        Assertions.assertEquals("\n<a>", anchor.getOpenTag());
        Assertions.assertEquals("</a>", anchor.getCloseTag());
    }

    @Test
    void testOpenTagWithHrefAndAttributes() {
        final Anchor anchor = new Anchor(new DefaultHref("http://www.displaytag.org/displaytag"), "link");
        anchor.setClass("display");
        anchor.setStyle("font-weight:bold");
        anchor.setTitle("Displaytag");

        final String openTag = anchor.getOpenTag();

        Assertions.assertTrue(openTag.startsWith("\n<a href=\"http://www.displaytag.org/displaytag\""));
        Assertions.assertTrue(openTag.contains(" class=\"display\""));
        Assertions.assertTrue(openTag.contains(" style=\"font-weight:bold\""));
        Assertions.assertTrue(openTag.contains(" title=\"Displaytag\""));
        Assertions.assertTrue(openTag.endsWith(">"));
    }

    @Test
    void testToStringReflectsUpdatedTextAndHref() {
        final Anchor anchor = new Anchor(null, "initial");

        anchor.setHref(new DefaultHref("http://www.displaytag.org/displaytag/index.jsp?x=1"));
        anchor.setText("updated");

        final String rendered = anchor.toString();

        Assertions.assertTrue(rendered.startsWith("\n<a href=\"http://www.displaytag.org/displaytag/index.jsp?x=1\">"));
        Assertions.assertTrue(rendered.endsWith("updated</a>"));
    }
}
