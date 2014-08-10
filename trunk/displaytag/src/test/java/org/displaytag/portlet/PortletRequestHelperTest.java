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

import junit.framework.TestCase;

import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;


/**
 * @author Eric Dalquist <a href="mailto:edalquist@unicon.net">edalquist@unicon.net</a>
 * @version $Id$
 */
public class PortletRequestHelperTest extends TestCase
{

    /**
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return "PortletRequestHelper Test";
    }

    public void testNullPageContext()
    {
        try
        {
            new PortletRequestHelper(null);
            fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }
    }

    public void testEmptyPageContext()
    {
        final MockPageContext pageContext = new MockPageContext();

        try
        {
            new PortletRequestHelper(pageContext);
            fail("IllegalStateException should have been thrown");
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
            fail("IllegalStateException should have been thrown");
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
            fail("IllegalStateException should have been thrown");
        }
        catch (IllegalStateException ise)
        {
            // expected
        }
    }

    public void testBasicPageContext()
    {
        final MockPageContext pageContext = new MockPageContext();

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, new MockPortletRequest());
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());

        new PortletRequestHelper(pageContext);
    }

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
        assertEquals("STRING_VALUE", strVal);

        final Integer intVal = helper.getIntParameter("INTEGER_PARAM");
        assertEquals(new Integer(31337), intVal);

        final Integer nullIntVal = helper.getIntParameter("STRING_PARAM");
        assertNull(nullIntVal);

        final Map params = helper.getParameterMap();
        assertEquals(2, params.size());

        final String[] expextedStrArryVal = new String[]{"STRING_VALUE"};
        final String[] strArryVal = (String[]) params.get("STRING_PARAM");
        assertEquals(expextedStrArryVal.length, strArryVal.length);
        assertEquals(expextedStrArryVal[0], strArryVal[0]);

        final String[] expextedIntArryVal = new String[]{"31337"};
        final String[] intArryVal = (String[]) params.get("INTEGER_PARAM");
        assertEquals(expextedIntArryVal.length, intArryVal.length);
        assertEquals(expextedIntArryVal[0], intArryVal[0]);
    }

    public void testCreateEmptyHref()
    {
        final MockPageContext pageContext = new MockPageContext();

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, new MockPortletRequest());
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());

        final PortletRequestHelper helper = new PortletRequestHelper(pageContext);

        final PortletHref ref = (PortletHref) helper.getHref();

        final Map params = ref.getParameterMap();
        assertEquals(0, params.size());

        assertNull(ref.getAnchor());
        assertNull(ref.getRequestedMode());
        assertNull(ref.getRequestedState());

        assertFalse(ref.isRequestedSecure());
    }

    public void testCreateSecureHref()
    {
        final MockPageContext pageContext = new MockPageContext();
        final MockPortletRequest request = new MockPortletRequest();

        request.setSecure(true);

        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_REQUEST, request);
        pageContext.setAttribute(PortletRequestHelper.JAVAX_PORTLET_RESPONSE, new MockRenderResponse());

        final PortletRequestHelper helper = new PortletRequestHelper(pageContext);

        final PortletHref ref = (PortletHref) helper.getHref();

        final Map params = ref.getParameterMap();
        assertEquals(0, params.size());

        assertNull(ref.getAnchor());
        assertNull(ref.getRequestedMode());
        assertNull(ref.getRequestedState());

        assertTrue(ref.isRequestedSecure());
    }

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

        final Map params = ref.getParameterMap();
        assertEquals(2, params.size());

        final String[] expextedStrArryVal = new String[]{"STRING_VALUE"};
        final String[] strArryVal = (String[]) params.get("STRING_PARAM");
        assertEquals(expextedStrArryVal.length, strArryVal.length);
        assertEquals(expextedStrArryVal[0], strArryVal[0]);

        final String[] expextedIntArryVal = new String[]{"31337"};
        final String[] intArryVal = (String[]) params.get("INTEGER_PARAM");
        assertEquals(expextedIntArryVal.length, intArryVal.length);
        assertEquals(expextedIntArryVal[0], intArryVal[0]);

        assertNull(ref.getAnchor());
        assertNull(ref.getRequestedMode());
        assertNull(ref.getRequestedState());

        assertFalse(ref.isRequestedSecure());
    }
}
