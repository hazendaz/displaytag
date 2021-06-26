/*
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
package org.displaytag.portlet;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;


/**
 * The Class PortletRequestHelperTest.
 *
 * @author Eric Dalquist <a href="mailto:edalquist@unicon.net">edalquist@unicon.net</a>
 * @version $Id$
 */
public class PortletRequestHelperTest
{

    /**
     * Test null page context.
     */
    @Test
    public void testNullPageContext()
    {
        try
        {
            new PortletRequestHelper(null);
            Assert.fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }
    }

    /**
     * Test empty page context.
     */
    @Test
    public void testEmptyPageContext()
    {
        final MockPageContext pageContext = new MockPageContext();

        try
        {
            new PortletRequestHelper(pageContext);
            Assert.fail("IllegalStateException should have been thrown");
        }
        catch (IllegalStateException ise)
        {
            // expected
        }

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, new MockPortletRequest());
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, null);
        try
        {
            new PortletRequestHelper(pageContext);
            Assert.fail("IllegalStateException should have been thrown");
        }
        catch (IllegalStateException ise)
        {
            // expected
        }

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, null);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());
        try
        {
            new PortletRequestHelper(pageContext);
            Assert.fail("IllegalStateException should have been thrown");
        }
        catch (IllegalStateException ise)
        {
            // expected
        }
    }

    /**
     * Test basic page context.
     */
    @Test
    public void testBasicPageContext()
    {
        final MockPageContext pageContext = new MockPageContext();

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, new MockPortletRequest());
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());

        new PortletRequestHelper(pageContext);
    }

    /**
     * Test request parameters.
     */
    @Test
    public void testRequestParameters()
    {
        final MockPageContext pageContext = new MockPageContext();
        final MockPortletRequest request = new MockPortletRequest();

        request.setParameter("STRING_PARAM", "STRING_VALUE");
        request.setParameter("INTEGER_PARAM", "31337");

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, request);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());

        final PortletRequestHelper helper = new PortletRequestHelper(pageContext);

        final String strVal = helper.getParameter("STRING_PARAM");
        Assert.assertEquals("STRING_VALUE", strVal);

        final Integer intVal = helper.getIntParameter("INTEGER_PARAM");
        Assert.assertEquals(Integer.valueOf(31337), intVal);

        final Integer nullIntVal = helper.getIntParameter("STRING_PARAM");
        Assert.assertNull(nullIntVal);

        final Map<String, String[]> params = helper.getParameterMap();
        Assert.assertEquals(2, params.size());

        final String[] expextedStrArryVal = new String[]{"STRING_VALUE"};
        final String[] strArryVal = params.get("STRING_PARAM");
        Assert.assertEquals(expextedStrArryVal.length, strArryVal.length);
        Assert.assertEquals(expextedStrArryVal[0], strArryVal[0]);

        final String[] expextedIntArryVal = new String[]{"31337"};
        final String[] intArryVal = params.get("INTEGER_PARAM");
        Assert.assertEquals(expextedIntArryVal.length, intArryVal.length);
        Assert.assertEquals(expextedIntArryVal[0], intArryVal[0]);
    }

    /**
     * Test create empty href.
     */
    @Test
    public void testCreateEmptyHref()
    {
        final MockPageContext pageContext = new MockPageContext();

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, new MockPortletRequest());
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());

        final PortletRequestHelper helper = new PortletRequestHelper(pageContext);

        final PortletHref ref = (PortletHref) helper.getHref();

        final Map<String, String[]> params = ref.getParameterMap();
        Assert.assertEquals(0, params.size());

        Assert.assertNull(ref.getAnchor());
        Assert.assertNull(ref.getRequestedMode());
        Assert.assertNull(ref.getRequestedState());

        Assert.assertFalse(ref.isRequestedSecure());
    }

    /**
     * Test create secure href.
     */
    @Test
    public void testCreateSecureHref()
    {
        final MockPageContext pageContext = new MockPageContext();
        final MockPortletRequest request = new MockPortletRequest();

        request.setSecure(true);

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, request);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());

        final PortletRequestHelper helper = new PortletRequestHelper(pageContext);

        final PortletHref ref = (PortletHref) helper.getHref();

        final Map<String, String[]> params = ref.getParameterMap();
        Assert.assertEquals(0, params.size());

        Assert.assertNull(ref.getAnchor());
        Assert.assertNull(ref.getRequestedMode());
        Assert.assertNull(ref.getRequestedState());

        Assert.assertTrue(ref.isRequestedSecure());
    }

    /**
     * Test parameterized href.
     */
    @Test
    public void testParameterizedHref()
    {
        final MockPageContext pageContext = new MockPageContext();
        final MockPortletRequest request = new MockPortletRequest();

        request.setParameter("STRING_PARAM", "STRING_VALUE");
        request.setParameter("INTEGER_PARAM", "31337");

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, request);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());

        final PortletRequestHelper helper = new PortletRequestHelper(pageContext);

        final PortletHref ref = (PortletHref) helper.getHref();

        final Map<String, String[]> params = ref.getParameterMap();
        Assert.assertEquals(2, params.size());

        final String[] expextedStrArryVal = new String[]{"STRING_VALUE"};
        final String[] strArryVal = params.get("STRING_PARAM");
        Assert.assertEquals(expextedStrArryVal.length, strArryVal.length);
        Assert.assertEquals(expextedStrArryVal[0], strArryVal[0]);

        final String[] expextedIntArryVal = new String[]{"31337"};
        final String[] intArryVal = params.get("INTEGER_PARAM");
        Assert.assertEquals(expextedIntArryVal.length, intArryVal.length);
        Assert.assertEquals(expextedIntArryVal[0], intArryVal[0]);

        Assert.assertNull(ref.getAnchor());
        Assert.assertNull(ref.getRequestedMode());
        Assert.assertNull(ref.getRequestedState());

        Assert.assertFalse(ref.isRequestedSecure());
    }
}
