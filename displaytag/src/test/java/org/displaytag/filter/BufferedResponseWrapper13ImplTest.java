/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.filter;

import java.io.PrintWriter;

import org.displaytag.tags.TableTagParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Tests for {@link BufferedResponseWrapper13Impl}.
 */
class BufferedResponseWrapper13ImplTest {

    @Test
    void testContentTypeEncodingAndBufferedWriters() throws Exception {
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final BufferedResponseWrapper13Impl wrapper = new BufferedResponseWrapper13Impl(response);

        wrapper.setContentType("text/plain; charset=UTF-8");
        wrapper.setCharacterEncoding("UTF-16");
        Assertions.assertEquals("text/plain", wrapper.getContentType());
        Assertions.assertEquals("UTF-16", wrapper.getCharacterEncoding());

        final PrintWriter writer = wrapper.getWriter();
        writer.write("hello");
        writer.flush();
        wrapper.getOutputStream().write('!');
        wrapper.flushBuffer();
        Assertions.assertTrue(wrapper.getContentAsString().contains("hello"));
    }

    @Test
    void testStateSwitchAndHeaderFiltering() throws Exception {
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final BufferedResponseWrapper13Impl wrapper = new BufferedResponseWrapper13Impl(response);

        wrapper.addHeader("x-header", "value");
        wrapper.addHeader("cache-control", "no-cache");
        Assertions.assertEquals("value", response.getHeader("x-header"));
        Assertions.assertNull(response.getHeader("cache-control"));

        wrapper.setDateHeader("x-date", 1L);
        wrapper.addDateHeader("x-date2", 2L);
        wrapper.setIntHeader("x-int", 3);
        wrapper.addIntHeader("x-int2", 4);
        wrapper.setHeader("x-set", "v");
        Assertions.assertEquals("v", response.getHeader("x-set"));

        wrapper.addHeader(TableTagParameters.PARAMETER_EXPORTING, "true");
        wrapper.setContentType("application/pdf");
        wrapper.setCharacterEncoding("UTF-8");
        wrapper.getOutputStream();
        Assertions.assertTrue(wrapper.isOutRequested());
    }
}
