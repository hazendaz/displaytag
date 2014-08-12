/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
package org.displaytag.portlet;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;


/**
 * @author Eric Dalquist <a href="mailto:edalquist@unicon.net">edalquist@unicon.net</a>
 * @version $Id$
 */
public class PortletRequestHelperTest
{

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

    @Test
    public void testBasicPageContext()
    {
        final MockPageContext pageContext = new MockPageContext();

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, new MockPortletRequest());
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());

        new PortletRequestHelper(pageContext);
    }

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
        Assert.assertEquals(new Integer(31337), intVal);

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
