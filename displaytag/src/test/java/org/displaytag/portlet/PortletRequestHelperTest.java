/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.portlet;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockPageContext;

import mockit.Expectations;
import mockit.Mocked;

/**
 * The Class PortletRequestHelperTest.
 *
 * @author Eric Dalquist <a href="mailto:edalquist@unicon.net">edalquist@unicon.net</a>
 *
 * @version $Id$
 */
class PortletRequestHelperTest {

    @Mocked
    PortletRequest portletRequest;

    @Mocked
    MimeResponse portletResponse;

    /**
     * Test null page context.
     */
    @Test
    void testNullPageContext() {
        try {
            new PortletRequestHelper(null);
            Assertions.fail("IllegalArgumentException should have been thrown");
        } catch (final IllegalArgumentException iae) {
            // expected
        }
    }

    /**
     * Test empty page context.
     */
    @Test
    void testEmptyPageContext() {
        final MockPageContext pageContext = new MockPageContext();

        try {
            new PortletRequestHelper(pageContext);
            Assertions.fail("IllegalStateException should have been thrown");
        } catch (final IllegalStateException ise) {
            // expected
        }

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, portletRequest);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, null);
        try {
            new PortletRequestHelper(pageContext);
            Assertions.fail("IllegalStateException should have been thrown");
        } catch (final IllegalStateException ise) {
            // expected
        }

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, null);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, portletResponse);
        try {
            new PortletRequestHelper(pageContext);
            Assertions.fail("IllegalStateException should have been thrown");
        } catch (final IllegalStateException ise) {
            // expected
        }
    }

    /**
     * Test basic page context.
     */
    @Test
    void testBasicPageContext() {
        final MockPageContext pageContext = new MockPageContext();

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, portletRequest);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, portletResponse);

        Assertions.assertNotNull(new PortletRequestHelper(pageContext));
    }

    /**
     * Test request parameters.
     */
    @Test
    void testRequestParameters() {
        final MockPageContext pageContext = new MockPageContext();

        final Map<String, String[]> parameterMap = new HashMap<String, String[]>();
        parameterMap.put("STRING_PARAM", new String [] { "STRING_VALUE" });
        parameterMap.put("INTEGER_PARAM", new String [] { "31337" });

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, portletRequest);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, portletResponse);

        final PortletRequestHelper helper = new PortletRequestHelper(pageContext);

        new Expectations() {
            {
                helper.getParameterMap();
                result = parameterMap;
            }
        };

        final String strVal = helper.getParameter("STRING_PARAM");
//        Assertions.assertEquals("STRING_VALUE", strVal);

        final Integer intVal = helper.getIntParameter("INTEGER_PARAM");
//        Assertions.assertEquals(Integer.valueOf(31337), intVal);

        final Integer nullIntVal = helper.getIntParameter("STRING_PARAM");
//        Assertions.assertNull(nullIntVal);

        final Map<String, String[]> params = helper.getParameterMap();
        Assertions.assertEquals(2, params.size());

        final String[] expextedStrArryVal = { "STRING_VALUE" };
        final String[] strArryVal = params.get("STRING_PARAM");
        Assertions.assertEquals(expextedStrArryVal.length, strArryVal.length);
        Assertions.assertEquals(expextedStrArryVal[0], strArryVal[0]);

        final String[] expextedIntArryVal = { "31337" };
        final String[] intArryVal = params.get("INTEGER_PARAM");
        Assertions.assertEquals(expextedIntArryVal.length, intArryVal.length);
        Assertions.assertEquals(expextedIntArryVal[0], intArryVal[0]);
    }

    /**
     * Test create empty href.
     */
    @Test
    void testCreateEmptyHref() {
        final MockPageContext pageContext = new MockPageContext();

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, portletRequest);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, portletResponse);

        final PortletRequestHelper helper = new PortletRequestHelper(pageContext);

        final PortletHref ref = (PortletHref) helper.getHref();

        final Map<String, String[]> params = ref.getParameterMap();
        Assertions.assertEquals(0, params.size());

        Assertions.assertNull(ref.getAnchor());
        Assertions.assertNull(ref.getRequestedMode());
        Assertions.assertNull(ref.getRequestedState());

        Assertions.assertFalse(ref.isRequestedSecure());
    }

    /**
     * Test create secure href.
     */
    @Test
    void testCreateSecureHref() {
        final MockPageContext pageContext = new MockPageContext();

        portletRequest.setAttribute("secure", true);

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, portletRequest);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, portletResponse);

        final PortletRequestHelper helper = new PortletRequestHelper(pageContext);

        final PortletHref ref = (PortletHref) helper.getHref();

        final Map<String, String[]> params = ref.getParameterMap();
        Assertions.assertEquals(0, params.size());

        Assertions.assertNull(ref.getAnchor());
        Assertions.assertNull(ref.getRequestedMode());
        Assertions.assertNull(ref.getRequestedState());

        Assertions.assertTrue(ref.isRequestedSecure());
    }

    /**
     * Test parameterized href.
     */
    @Test
    void testParameterizedHref() {
        final MockPageContext pageContext = new MockPageContext();

        final Map<String, String[]> parameterMap = new HashMap<String, String[]>();
        parameterMap.put("STRING_PARAM", new String [] { "STRING_VALUE" });
        parameterMap.put("INTEGER_PARAM", new String [] { "31337" });

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, portletRequest);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, portletResponse);

        final PortletRequestHelper helper = new PortletRequestHelper(pageContext);

        final PortletHref ref = (PortletHref) helper.getHref();

        new Expectations() {
            {
                ref.getParameterMap();
                result = parameterMap;
            }
        };

        final Map<String, String[]> params = ref.getParameterMap();
        Assertions.assertEquals(2, params.size());

        final String[] expextedStrArryVal = { "STRING_VALUE" };
        final String[] strArryVal = params.get("STRING_PARAM");
        Assertions.assertEquals(expextedStrArryVal.length, strArryVal.length);
        Assertions.assertEquals(expextedStrArryVal[0], strArryVal[0]);

        final String[] expextedIntArryVal = { "31337" };
        final String[] intArryVal = params.get("INTEGER_PARAM");
        Assertions.assertEquals(expextedIntArryVal.length, intArryVal.length);
        Assertions.assertEquals(expextedIntArryVal[0], intArryVal[0]);

        Assertions.assertNull(ref.getAnchor());
        Assertions.assertNull(ref.getRequestedMode());
        Assertions.assertNull(ref.getRequestedState());

        Assertions.assertFalse(ref.isRequestedSecure());
    }
}
