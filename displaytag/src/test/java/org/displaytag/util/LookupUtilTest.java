/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.util;

import java.util.List;
import java.util.Map;

import org.displaytag.exception.ObjectLookupException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockPageContext;

/**
 * Tests for {@link LookupUtil}.
 */
class LookupUtilTest {

    @Test
    void testGetBeanValueSimpleAndNested() throws Exception {
        final MockPageContext pageContext = new MockPageContext();
        pageContext.setAttribute("simple", "value");
        pageContext.setAttribute("bean", new ParentBean(new ChildBean("nested")));

        Assertions.assertEquals("value", LookupUtil.getBeanValue(pageContext, "simple", MockPageContext.PAGE_SCOPE));
        Assertions.assertEquals("nested",
                LookupUtil.getBeanValue(pageContext, "bean.child.value", MockPageContext.PAGE_SCOPE));
        Assertions.assertNull(LookupUtil.getBeanValue(pageContext, "missing.property", MockPageContext.PAGE_SCOPE));
    }

    @Test
    void testGetBeanPropertyAndGetPropertyForMapAndIndexedValues() throws Exception {
        final ParentBean bean = new ParentBean(new ChildBean("x"));
        bean.setList(List.of("zero", "one", "two"));

        Assertions.assertEquals("x", LookupUtil.getBeanProperty(bean, "child.value"));
        Assertions.assertEquals("one", LookupUtil.getProperty(bean, "list[1]"));
        Assertions.assertEquals("one", LookupUtil.getIndexedProperty(List.of("zero", "one", "two"), "[1]"));
        Assertions.assertEquals("two", LookupUtil.getIndexedProperty(new String[] { "zero", "one", "two" }, "[2]"));
        Assertions.assertEquals("mapped", LookupUtil.getProperty(Map.of("k", "mapped"), "k"));
    }

    @Test
    void testLookupFailuresAndNullChecks() {
        Assertions.assertThrows(NullPointerException.class, () -> LookupUtil.getBeanProperty(null, "x"));
        Assertions.assertThrows(NullPointerException.class, () -> LookupUtil.getBeanProperty(new Object(), null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> LookupUtil.getIndexedProperty(List.of("a"), "x"));
        Assertions.assertThrows(ObjectLookupException.class,
                () -> LookupUtil.getBeanProperty(new ParentBean(new ChildBean("x")), "missing"));
    }

    public static class ParentBean {
        private final ChildBean child;
        private List<String> list;

        ParentBean(final ChildBean child) {
            this.child = child;
        }

        public ChildBean getChild() {
            return this.child;
        }

        public List<String> getList() {
            return this.list;
        }

        public void setList(final List<String> list) {
            this.list = list;
        }
    }

    public static class ChildBean {
        private final String value;

        ChildBean(final String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }
}
