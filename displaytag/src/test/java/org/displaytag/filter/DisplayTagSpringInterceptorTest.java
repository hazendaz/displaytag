/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class DisplayTagSpringInterceptorTest {

    @Test
    void testNoExportParameterPassesThroughAndLifecycleMethodsAreNoOps() throws Exception {
        final DisplayTagSpringInterceptor interceptor = new DisplayTagSpringInterceptor();
        interceptor.setBuffer(false);

        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();

        Assertions.assertTrue(interceptor.preHandle(request, response, new Object()));
        interceptor.postHandle(request, response, new Object(), null);
        interceptor.afterCompletion(request, response, new Object(), null);
    }
}
