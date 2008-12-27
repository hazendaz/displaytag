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

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import junit.framework.TestCase;

import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;


/**
 * @author Eric Dalquist <a href="mailto:edalquist@unicon.net">edalquist@unicon.net</a>
 * @version $Id$
 */
public class PortletHrefTest extends TestCase
{

    /**
     * @see junit.framework.TestCase#getName()
     */
    public String getName()
    {
        return "PortletHref Test";
    }

    public void testConstructor()
    {
        try
        {
            new PortletHref(null, null);
            fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }

        try
        {
            new PortletHref(new MockPortletRequest(), null);
            fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }

        try
        {
            new PortletHref(null, new MockRenderResponse());
            fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }

        new PortletHref(new MockPortletRequest(), new MockRenderResponse());
    }

    public void testUrlTypeParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        assertFalse(href.isAction());

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        assertTrue(href.isAction());

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_RENDER);
        assertFalse(href.isAction());

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        assertTrue(href.isAction());

        try
        {
            href.addParameter(PortletHref.PARAM_TYPE, null);
            fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }

        try
        {
            href.addParameter(PortletHref.PARAM_TYPE, "");
            fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }

        try
        {
            href.addParameter(PortletHref.PARAM_TYPE, "InvalidUrlType");
            fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }
    }

    public void testSecureParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        assertFalse(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, Boolean.TRUE.toString());
        assertTrue(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "true");
        assertTrue(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "TRUE");
        assertTrue(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "yes");
        assertFalse(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, null);
        assertFalse(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "false");
        assertFalse(href.isRequestedSecure());
    }

    public void testPortletModeParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        assertNull(href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, "view");
        assertEquals(new PortletMode("view"), href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, "help");
        assertEquals(new PortletMode("help"), href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, "edit");
        assertEquals(new PortletMode("edit"), href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, null);
        assertNull(href.getRequestedMode());

        try
        {
            href.addParameter(PortletHref.PARAM_MODE, "info");
            fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }
    }

    public void testWindowStateParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        assertNull(href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, "normal");
        assertEquals(new WindowState("normal"), href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, "minimized");
        assertEquals(new WindowState("minimized"), href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        assertEquals(new WindowState("maximized"), href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, null);
        assertNull(href.getRequestedState());

        try
        {
            href.addParameter(PortletHref.PARAM_STATE, "exclusive");
            fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }
    }

    public void testInvalidPrefixUseParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        try
        {
            href.addParameter("portlet:WindowState", "exclusive");
            fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }
    }

    public void testAddParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        href.addParameter(PortletHref.PARAM_MODE, "help");
        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        href.addParameter(PortletHref.PARAM_SECURE, "true");
        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        href.addParameter("SINGLE_PARAM", "VAL1");
        href.addParameter("INT_PARAM", 31337);

        assertTrue(href.isRequestedSecure());
        assertEquals(new PortletMode("help"), href.getRequestedMode());
        assertEquals(new WindowState("maximized"), href.getRequestedState());
        assertTrue(href.isAction());

        final Map actualParams = href.getParameterMap();
        assertEquals(2, actualParams.size());
        assertEquals(actualParams.get("SINGLE_PARAM"), "VAL1");
        assertEquals(actualParams.get("INT_PARAM"), "31337");
    }

    public void testAddParameterMap()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        final Map params = new HashMap();
        params.put(PortletHref.PARAM_MODE, "help");
        params.put(PortletHref.PARAM_STATE, "maximized");
        params.put(PortletHref.PARAM_SECURE, "true");
        params.put(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        params.put("SINGLE_PARAM", "VAL1");
        final String[] multiParam = new String[]{"VAL2", "VAL3"};
        params.put("MULTI_PARAM", multiParam);
        params.put("INT_PARAM", new Integer("31337"));

        href.addParameterMap(params);

        assertTrue(href.isRequestedSecure());
        assertEquals(new PortletMode("help"), href.getRequestedMode());
        assertEquals(new WindowState("maximized"), href.getRequestedState());
        assertTrue(href.isAction());

        final Map actualParams = href.getParameterMap();
        assertEquals(3, actualParams.size());
        assertEquals(actualParams.get("SINGLE_PARAM"), "VAL1");
        assertEquals(actualParams.get("INT_PARAM"), "31337");

        final String[] actualMultiParam = (String[]) actualParams.get("MULTI_PARAM");
        assertEquals(multiParam.length, actualMultiParam.length);
        assertEquals(multiParam[0], actualMultiParam[0]);
        assertEquals(multiParam[1], actualMultiParam[1]);
    }

    public void testSetParameterMap()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        href.addParameter("ORIGINAL_PARAM", "ORIGNAL_VALUE");

        final Map params = new HashMap();
        params.put(PortletHref.PARAM_MODE, "help");
        params.put(PortletHref.PARAM_STATE, "maximized");
        params.put(PortletHref.PARAM_SECURE, "true");
        params.put(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        params.put("SINGLE_PARAM", "VAL1");
        final String[] multiParam = new String[]{"VAL2", "VAL3"};
        params.put("MULTI_PARAM", multiParam);
        params.put("INT_PARAM", new Integer("31337"));

        href.setParameterMap(params);

        assertTrue(href.isRequestedSecure());
        assertEquals(new PortletMode("help"), href.getRequestedMode());
        assertEquals(new WindowState("maximized"), href.getRequestedState());
        assertTrue(href.isAction());

        final Map actualParams = href.getParameterMap();
        assertEquals(3, actualParams.size());
        assertEquals(actualParams.get("SINGLE_PARAM"), "VAL1");
        assertEquals(actualParams.get("INT_PARAM"), "31337");
        assertNull(actualParams.get("ORIGINAL_PARAM"));

        final String[] actualMultiParam = (String[]) actualParams.get("MULTI_PARAM");
        assertEquals(multiParam.length, actualMultiParam.length);
        assertEquals(multiParam[0], actualMultiParam[0]);
        assertEquals(multiParam[1], actualMultiParam[1]);
    }

    public void testRemoveParameter()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        href.addParameter("SINGLE_PARAM", "VAL1");

        final Map actualParams = href.getParameterMap();
        assertEquals(1, actualParams.size());
        assertEquals(actualParams.get("SINGLE_PARAM"), "VAL1");

        href.removeParameter("SINGLE_PARAM");

        final Map actualParams2 = href.getParameterMap();
        assertEquals(0, actualParams2.size());
        assertNull(actualParams2.get("SINGLE_PARAM"));
    }

    public void testClone()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        href.addParameter(PortletHref.PARAM_MODE, "help");
        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        href.addParameter(PortletHref.PARAM_SECURE, "true");
        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        href.addParameter("SINGLE_PARAM", "VAL1");
        href.addParameter("INT_PARAM", 31337);

        assertTrue(href.isRequestedSecure());
        assertEquals(new PortletMode("help"), href.getRequestedMode());
        assertEquals(new WindowState("maximized"), href.getRequestedState());
        assertTrue(href.isAction());

        final Map actualParams = href.getParameterMap();
        assertEquals(2, actualParams.size());
        assertEquals(actualParams.get("SINGLE_PARAM"), "VAL1");
        assertEquals(actualParams.get("INT_PARAM"), "31337");

        final PortletHref href2 = (PortletHref) href.clone();
        assertTrue(href != href2);
        assertEquals(href, href2);
        assertEquals(href.hashCode(), href2.hashCode());
    }

    public void testBaseUrl()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        href.addParameter(PortletHref.PARAM_MODE, "help");
        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        href.addParameter(PortletHref.PARAM_SECURE, "true");
        href.addParameter("SINGLE_PARAM", "VAL1");

        final String baseRenderUrl = href.getBaseUrl();
        assertEquals("http://localhost/mockportlet?urlType=render", baseRenderUrl);

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);

        final String baseActionUrl = href.getBaseUrl();
        assertEquals("http://localhost/mockportlet?urlType=action", baseActionUrl);
    }

    public void testFullUrl()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        final String urlString1 = href.toString();
        assertEquals("http://localhost/mockportlet?urlType=render", urlString1);

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        final String urlString2 = href.toString();
        assertEquals("http://localhost/mockportlet?urlType=action", urlString2);

        href.addParameter(PortletHref.PARAM_SECURE, Boolean.TRUE.toString());
        final String urlString3 = href.toString();
        assertEquals("https://localhost/mockportlet?urlType=action", urlString3);

        // This code should work but the MockPortletURL provided by spring doesn't follow the
        // interface docs on invalid modes/states
        // href.setRequestedState(new WindowState("exclusive"));
        // try {
        // href.toString();
        // fail("IllegalStateException should have been thrown.");
        // }
        // catch (IllegalStateException ise) {
        // //expected
        // }

        href.addParameter(PortletHref.PARAM_STATE, "normal");
        final String urlString7 = href.toString();
        assertEquals("https://localhost/mockportlet?urlType=action;windowState=normal", urlString7);

        href.addParameter(PortletHref.PARAM_STATE, "minimized");
        final String urlString8 = href.toString();
        assertEquals("https://localhost/mockportlet?urlType=action;windowState=minimized", urlString8);

        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        final String urlString9 = href.toString();
        assertEquals("https://localhost/mockportlet?urlType=action;windowState=maximized", urlString9);

        // This code should work but the MockPortletURL provided by spring doesn't follow the
        // interface docs on invalid modes/states
        // href.setRequestedMode(new PortletMode("info"));
        // try {
        // href.toString();
        // fail("IllegalStateException should have been thrown.");
        // }
        // catch (IllegalStateException ise) {
        // //expected
        // }

        href.addParameter(PortletHref.PARAM_MODE, "view");
        final String urlString4 = href.toString();
        assertEquals("https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=view", urlString4);

        href.addParameter(PortletHref.PARAM_MODE, "help");
        final String urlString5 = href.toString();
        assertEquals("https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=help", urlString5);

        href.addParameter(PortletHref.PARAM_MODE, "edit");
        final String urlString6 = href.toString();
        assertEquals("https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit", urlString6);

        href.addParameter("SINGLE_PARAM", "VAL");
        final String urlString10 = href.toString();
        assertEquals(
            "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit;param_SINGLE_PARAM=VAL",
            urlString10);

        final Map paramMap = new HashMap();
        paramMap.put("MULTI_PARAM", new String[]{"VAL1", "VAL2"});
        href.addParameterMap(paramMap);
        final String urlString11 = href.toString();
        assertEquals(
            "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit;param_MULTI_PARAM=VAL1;param_MULTI_PARAM=VAL2;param_SINGLE_PARAM=VAL",
            urlString11);

        href.setAnchor("ANCHOR");
        final String urlString12 = href.toString();
        assertEquals(
            "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit;param_MULTI_PARAM=VAL1;param_MULTI_PARAM=VAL2;param_SINGLE_PARAM=VAL#ANCHOR",
            urlString12);
    }
}