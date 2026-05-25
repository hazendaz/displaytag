/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.decorator;

import java.util.List;

import org.displaytag.model.TableModel;
import org.displaytag.properties.TableProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;

class CheckboxTableDecoratorTest {

    @Test
    void testCheckboxCheckedAndFinishWritesHiddenInputs() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("selected", "1", "3");
        final MockPageContext pageContext = new MockPageContext(new MockServletContext(), request);

        final TableModel model = new TableModel(TableProperties.getInstance(null), "test", null);
        final CheckboxTableDecorator decorator = new CheckboxTableDecorator();
        decorator.setFieldName("selected");
        decorator.setId("id");
        decorator.init(pageContext, List.of(new RowBean("1"), new RowBean("2")), model);

        decorator.initRow(new RowBean("1"), 0, 0);
        final String checked = decorator.getCheckbox();
        Assertions.assertTrue(checked.contains("value=\"1\""));
        Assertions.assertTrue(checked.contains("checked=\"checked\""));

        decorator.initRow(new RowBean("2"), 1, 1);
        final String unchecked = decorator.getCheckbox();
        Assertions.assertTrue(unchecked.contains("value=\"2\""));
        Assertions.assertFalse(unchecked.contains("checked=\"checked\""));

        decorator.finish();
        Assertions.assertTrue(pageContext.getContentAsString().contains("name=\"selected\" value=\"3\""));
    }

    @Test
    void testFinishWithNoCheckedValues() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockPageContext pageContext = new MockPageContext(new MockServletContext(), request);

        final TableModel model = new TableModel(TableProperties.getInstance(null), "test", null);
        final CheckboxTableDecorator decorator = new CheckboxTableDecorator();
        decorator.init(pageContext, List.of(new RowBean("7")), model);
        decorator.initRow(new RowBean("7"), 0, 0);

        Assertions.assertFalse(decorator.getCheckbox().contains("checked=\"checked\""));
        decorator.finish();
        Assertions.assertEquals("", pageContext.getContentAsString());
    }

    public static class RowBean {
        private final String id;

        RowBean(final String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }
    }
}
