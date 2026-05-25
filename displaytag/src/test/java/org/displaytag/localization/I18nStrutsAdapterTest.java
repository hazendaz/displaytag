/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.localization;

import jakarta.servlet.jsp.PageContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.struts.Globals;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;

class I18nStrutsAdapterTest {

    @Test
    void testResolveLocaleFromSessionAndRequest() {
        final I18nStrutsAdapter adapter = new I18nStrutsAdapter();
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addPreferredLocale(Locale.CANADA_FRENCH);
        final MockPageContext pageContext = new MockPageContext(new MockServletContext(), request);

        Assertions.assertEquals(Locale.CANADA_FRENCH, adapter.resolveLocale(pageContext));

        request.getSession(true).setAttribute(Globals.LOCALE_KEY, Locale.ITALIAN);
        Assertions.assertEquals(Locale.ITALIAN, adapter.resolveLocale(pageContext));
    }

    @Test
    void testGetResourceLookupPathsAndUndefinedFallback() {
        final I18nStrutsAdapter adapter = new I18nStrutsAdapter();
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addPreferredLocale(Locale.US);
        final MockPageContext pageContext = new MockPageContext(new MockServletContext(), request);
        final TestMessageResources requestResources = new TestMessageResources(Map.of("title", "request-title"));

        pageContext.setAttribute(Globals.MESSAGES_KEY, requestResources, PageContext.REQUEST_SCOPE);
        Assertions.assertEquals("request-title", adapter.getResource("title", "ignored", null, pageContext));

        pageContext.removeAttribute(Globals.MESSAGES_KEY, PageContext.REQUEST_SCOPE);
        final ModuleConfig moduleConfig = (ModuleConfig) Proxy.newProxyInstance(ModuleConfig.class.getClassLoader(),
                new Class<?>[] { ModuleConfig.class }, new ModuleConfigHandler("/module"));
        request.setAttribute(Globals.MODULE_KEY, moduleConfig);
        pageContext.setAttribute(Globals.MESSAGES_KEY + "/module",
                new TestMessageResources(Map.of("title", "module-title")), PageContext.APPLICATION_SCOPE);
        Assertions.assertEquals("module-title", adapter.getResource("title", "ignored", null, pageContext));

        pageContext.removeAttribute(Globals.MESSAGES_KEY + "/module", PageContext.APPLICATION_SCOPE);
        Assertions.assertEquals(I18nStrutsAdapter.UNDEFINED_KEY + "missing" + I18nStrutsAdapter.UNDEFINED_KEY,
                adapter.getResource("missing", "ignored", null, pageContext));
        Assertions.assertNull(adapter.getResource(null, "missingDefault", null, pageContext));
    }

    private static class TestMessageResources extends MessageResources {
        private static final long serialVersionUID = 1L;

        private final Map<String, String> messages = new HashMap<>();

        TestMessageResources(final Map<String, String> messages) {
            super(null, "test");
            this.messages.putAll(messages);
        }

        @Override
        public String getMessage(final Locale locale, final String key) {
            return this.messages.get(key);
        }
    }

    private static class ModuleConfigHandler implements InvocationHandler {
        private final String prefix;

        ModuleConfigHandler(final String prefix) {
            this.prefix = prefix;
        }

        @Override
        public Object invoke(final Object proxy, final java.lang.reflect.Method method, final Object[] args) {
            if ("getPrefix".equals(method.getName())) {
                return this.prefix;
            }
            if ("setPrefix".equals(method.getName())) {
                return null;
            }
            if ("getConfigured".equals(method.getName())) {
                return Boolean.TRUE;
            }
            if ("equals".equals(method.getName())) {
                return proxy == args[0];
            }
            if ("hashCode".equals(method.getName())) {
                return System.identityHashCode(proxy);
            }
            return null;
        }
    }
}
