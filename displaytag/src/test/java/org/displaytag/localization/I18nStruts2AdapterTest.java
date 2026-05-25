/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.localization;

import jakarta.servlet.jsp.tagext.Tag;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.struts2.ActionContext;
import org.apache.struts2.locale.LocaleProvider;
import org.apache.struts2.text.TextProvider;
import org.apache.struts2.util.CompoundRoot;
import org.apache.struts2.util.ValueStack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;

class I18nStruts2AdapterTest {

    @Test
    void testResolveLocaleFromLocaleProviderAndFallback() {
        final I18nStruts2Adapter adapter = new I18nStruts2Adapter();
        final Locale originalDefault = Locale.getDefault();

        try {
            final LocaleProvider provider = (LocaleProvider) Proxy.newProxyInstance(
                    LocaleProvider.class.getClassLoader(), new Class<?>[] { LocaleProvider.class },
                    (proxy, method, args) -> {
                        if ("getLocale".equals(method.getName())) {
                            return Locale.JAPAN;
                        }
                        return null;
                    });

            final SimpleValueStack stack = new SimpleValueStack(List.of(provider));
            final ActionContext context = ActionContext.of().withValueStack(stack);
            stack.setActionContext(context);
            ActionContext.bind(context);

            Assertions.assertEquals(Locale.JAPAN, adapter.resolveLocale(new MockPageContext()));

            ActionContext.clear();
            Locale.setDefault(Locale.KOREA);
            final SimpleValueStack fallbackStack = new SimpleValueStack(List.of(new Object()));
            final ActionContext fallbackContext = ActionContext.of().withValueStack(fallbackStack);
            fallbackStack.setActionContext(fallbackContext);
            ActionContext.bind(fallbackContext);

            Assertions.assertEquals(Locale.KOREA, adapter.resolveLocale(new MockPageContext()));
        } finally {
            ActionContext.clear();
            Locale.setDefault(originalDefault);
        }
    }

    @Test
    void testGetResourceFromTextProviderAndUndefinedFallback() {
        final I18nStruts2Adapter adapter = new I18nStruts2Adapter();
        final TextProvider textProvider = (TextProvider) Proxy.newProxyInstance(TextProvider.class.getClassLoader(),
                new Class<?>[] { TextProvider.class }, new TextProviderHandler());
        final SimpleValueStack stack = new SimpleValueStack(List.of(textProvider));
        final ActionContext context = ActionContext.of().withValueStack(stack);
        stack.setActionContext(context);
        ActionContext.bind(context);

        try {
            final MockHttpServletRequest request = new MockHttpServletRequest();
            request.setAttribute(ValueStack.VALUE_STACK, stack);
            final MockPageContext pageContext = new MockPageContext(new MockServletContext(), request);

            Assertions.assertEquals("translated-title",
                    adapter.getResource("title", "default", (Tag) null, pageContext));
            Assertions.assertEquals(I18nStruts2Adapter.UNDEFINED_KEY + "missing" + I18nStruts2Adapter.UNDEFINED_KEY,
                    adapter.getResource("missing", "default", (Tag) null, pageContext));
            Assertions.assertNull(adapter.getResource(null, "default", (Tag) null, pageContext));
        } finally {
            ActionContext.clear();
        }
    }

    private static class TextProviderHandler implements InvocationHandler {
        @Override
        public Object invoke(final Object proxy, final java.lang.reflect.Method method, final Object[] args) {
            if ("getText".equals(method.getName()) && args != null && args.length >= 1 && args[0] instanceof String) {
                final String key = (String) args[0];
                if ("title".equals(key)) {
                    return "translated-title";
                }
                return null;
            }
            if ("hasKey".equals(method.getName())) {
                return Boolean.FALSE;
            }
            return null;
        }
    }

    private static class SimpleValueStack implements ValueStack {
        private final CompoundRoot root;
        private final Map<String, Object> context = new HashMap<>();
        private ActionContext actionContext;

        SimpleValueStack(final List<Object> objects) {
            this.root = new CompoundRoot(objects);
        }

        void setActionContext(final ActionContext actionContext) {
            this.actionContext = actionContext;
        }

        @Override
        public Map<String, Object> getContext() {
            return this.context;
        }

        @Override
        public ActionContext getActionContext() {
            return this.actionContext;
        }

        @Override
        public void setDefaultType(final Class defaultType) {
        }

        @Override
        public void setExprOverrides(final Map<Object, Object> overrides) {
        }

        @Override
        public Map<Object, Object> getExprOverrides() {
            return Map.of();
        }

        @Override
        public CompoundRoot getRoot() {
            return this.root;
        }

        @Override
        public void setValue(final String expr, final Object value) {
        }

        @Override
        public void setParameter(final String expr, final Object value) {
        }

        @Override
        public void setValue(final String expr, final Object value, final boolean throwExceptionOnFailure) {
        }

        @Override
        public String findString(final String expr) {
            return null;
        }

        @Override
        public String findString(final String expr, final boolean throwExceptionOnFailure) {
            return null;
        }

        @Override
        public Object findValue(final String expr) {
            return null;
        }

        @Override
        public Object findValue(final String expr, final boolean throwExceptionOnFailure) {
            return null;
        }

        @Override
        public Object findValue(final String expr, final Class asType) {
            return null;
        }

        @Override
        public Object findValue(final String expr, final Class asType, final boolean throwExceptionOnFailure) {
            return null;
        }

        @Override
        public Object peek() {
            return this.root.isEmpty() ? null : this.root.get(0);
        }

        @Override
        public Object pop() {
            return this.root.isEmpty() ? null : this.root.remove(0);
        }

        @Override
        public void push(final Object o) {
            this.root.add(0, o);
        }

        @Override
        public void set(final String key, final Object o) {
            this.context.put(key, o);
        }

        @Override
        public int size() {
            return this.root.size();
        }
    }
}
