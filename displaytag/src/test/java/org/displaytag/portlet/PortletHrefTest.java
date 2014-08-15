/**
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

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.mock.web.portlet.MockRenderResponse;


/**
 * @author Eric Dalquist <a href="mailto:edalquist@unicon.net">edalquist@unicon.net</a>
 * @version $Id$
 */
public class PortletHrefTest
{

    @Test
    public void testConstructor()
    {
        try
        {
            new PortletHref(null, null);
            Assert.fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }

        try
        {
            new PortletHref(new MockPortletRequest(), null);
            Assert.fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }

        try
        {
            new PortletHref(null, new MockRenderResponse());
            Assert.fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }

        new PortletHref(new MockPortletRequest(), new MockRenderResponse());
    }

    @Test
    public void testUrlTypeParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        Assert.assertFalse(href.isAction());

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        Assert.assertTrue(href.isAction());

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_RENDER);
        Assert.assertFalse(href.isAction());

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        Assert.assertTrue(href.isAction());

        try
        {
            href.addParameter(PortletHref.PARAM_TYPE, null);
            Assert.fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }

        try
        {
            href.addParameter(PortletHref.PARAM_TYPE, "");
            Assert.fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }

        try
        {
            href.addParameter(PortletHref.PARAM_TYPE, "InvalidUrlType");
            Assert.fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }
    }

    @Test
    public void testSecureParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        Assert.assertFalse(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, Boolean.TRUE.toString());
        Assert.assertTrue(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "true");
        Assert.assertTrue(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "TRUE");
        Assert.assertTrue(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "yes");
        Assert.assertFalse(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, null);
        Assert.assertFalse(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "false");
        Assert.assertFalse(href.isRequestedSecure());
    }

    @Test
    public void testPortletModeParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        Assert.assertNull(href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, "view");
        Assert.assertEquals(new PortletMode("view"), href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, "help");
        Assert.assertEquals(new PortletMode("help"), href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, "edit");
        Assert.assertEquals(new PortletMode("edit"), href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, null);
        Assert.assertNull(href.getRequestedMode());

        try
        {
            href.addParameter(PortletHref.PARAM_MODE, "info");
            Assert.fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }
    }

    @Test
    public void testWindowStateParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        Assert.assertNull(href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, "normal");
        Assert.assertEquals(new WindowState("normal"), href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, "minimized");
        Assert.assertEquals(new WindowState("minimized"), href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        Assert.assertEquals(new WindowState("maximized"), href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, null);
        Assert.assertNull(href.getRequestedState());

        try
        {
            href.addParameter(PortletHref.PARAM_STATE, "exclusive");
            Assert.fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }
    }

    @Test
    public void testInvalidPrefixUseParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        try
        {
            href.addParameter("portlet:WindowState", "exclusive");
            Assert.fail("IllegalArgumentException should have been thrown");
        }
        catch (IllegalArgumentException iae)
        {
            // expected
        }
    }

    @Test
    public void testAddParameters()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        href.addParameter(PortletHref.PARAM_MODE, "help");
        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        href.addParameter(PortletHref.PARAM_SECURE, "true");
        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        href.addParameter("SINGLE_PARAM", "VAL1");
        href.addParameter("INT_PARAM", 31337);

        Assert.assertTrue(href.isRequestedSecure());
        Assert.assertEquals(new PortletMode("help"), href.getRequestedMode());
        Assert.assertEquals(new WindowState("maximized"), href.getRequestedState());
        Assert.assertTrue(href.isAction());

        final Map<String, String[]> actualParams = href.getParameterMap();
        Assert.assertEquals(2, actualParams.size());
        Assert.assertArrayEquals(new String[]{"VAL1"}, actualParams.get("SINGLE_PARAM"));
        Assert.assertArrayEquals(new String[]{"31337"}, actualParams.get("INT_PARAM"));
    }

    @Test
    public void testAddParameterMap()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        final Map<String, String[]> params = new HashMap<String, String[]>();
        params.put(PortletHref.PARAM_MODE, new String[]{"help"});
        params.put(PortletHref.PARAM_STATE, new String[]{"maximized"});
        params.put(PortletHref.PARAM_SECURE, new String[]{"true"});
        params.put(PortletHref.PARAM_TYPE, new String[]{PortletHref.TYPE_ACTION});
        params.put("SINGLE_PARAM", new String[]{"VAL1"});
        final String[] multiParam = new String[]{"VAL2", "VAL3"};
        params.put("MULTI_PARAM", multiParam);
        params.put("INT_PARAM", new String[]{"31337"});

        href.addParameterMap(params);

        Assert.assertTrue(href.isRequestedSecure());
        Assert.assertEquals(new PortletMode("help"), href.getRequestedMode());
        Assert.assertEquals(new WindowState("maximized"), href.getRequestedState());
        Assert.assertTrue(href.isAction());

        final Map<String, String[]> actualParams = href.getParameterMap();
        Assert.assertEquals(3, actualParams.size());
        Assert.assertArrayEquals(new String[]{"VAL1"}, actualParams.get("SINGLE_PARAM"));
        Assert.assertArrayEquals(new String[]{"31337"}, actualParams.get("INT_PARAM"));

        final String[] actualMultiParam = actualParams.get("MULTI_PARAM");
        Assert.assertEquals(multiParam.length, actualMultiParam.length);
        Assert.assertEquals(multiParam[0], actualMultiParam[0]);
        Assert.assertEquals(multiParam[1], actualMultiParam[1]);
    }

    @Test
    public void testSetParameterMap()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        href.addParameter("ORIGINAL_PARAM", "ORIGNAL_VALUE");

        final Map<String, String[]> params = new HashMap<String, String[]>();
        params.put(PortletHref.PARAM_MODE, new String[]{"help"});
        params.put(PortletHref.PARAM_STATE, new String[]{"maximized"});
        params.put(PortletHref.PARAM_SECURE, new String[]{"true"});
        params.put(PortletHref.PARAM_TYPE, new String[]{PortletHref.TYPE_ACTION});
        params.put("SINGLE_PARAM", new String[]{"VAL1"});
        final String[] multiParam = new String[]{"VAL2", "VAL3"};
        params.put("MULTI_PARAM", multiParam);
        params.put("INT_PARAM", new String[]{"31337"});

        href.setParameterMap(params);

        Assert.assertTrue(href.isRequestedSecure());
        Assert.assertEquals(new PortletMode("help"), href.getRequestedMode());
        Assert.assertEquals(new WindowState("maximized"), href.getRequestedState());
        Assert.assertTrue(href.isAction());

        final Map<String, String[]> actualParams = href.getParameterMap();
        Assert.assertEquals(3, actualParams.size());
        Assert.assertArrayEquals(new String[]{"VAL1"}, actualParams.get("SINGLE_PARAM"));
        Assert.assertArrayEquals(new String[]{"31337"}, actualParams.get("INT_PARAM"));
        Assert.assertNull(actualParams.get("ORIGINAL_PARAM"));

        final String[] actualMultiParam = actualParams.get("MULTI_PARAM");
        Assert.assertEquals(multiParam.length, actualMultiParam.length);
        Assert.assertEquals(multiParam[0], actualMultiParam[0]);
        Assert.assertEquals(multiParam[1], actualMultiParam[1]);
    }

    @Test
    public void testRemoveParameter()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        href.addParameter("SINGLE_PARAM", "VAL1");

        final Map<String, String[]> actualParams = href.getParameterMap();
        Assert.assertEquals(1, actualParams.size());
        Assert.assertArrayEquals(new String[]{"VAL1"}, actualParams.get("SINGLE_PARAM"));

        href.removeParameter("SINGLE_PARAM");

        final Map<String, String[]> actualParams2 = href.getParameterMap();
        Assert.assertEquals(0, actualParams2.size());
        Assert.assertNull(actualParams2.get("SINGLE_PARAM"));
    }

    @Test
    public void testClone()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        href.addParameter(PortletHref.PARAM_MODE, "help");
        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        href.addParameter(PortletHref.PARAM_SECURE, "true");
        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        href.addParameter("SINGLE_PARAM", "VAL1");
        href.addParameter("INT_PARAM", 31337);

        Assert.assertTrue(href.isRequestedSecure());
        Assert.assertEquals(new PortletMode("help"), href.getRequestedMode());
        Assert.assertEquals(new WindowState("maximized"), href.getRequestedState());
        Assert.assertTrue(href.isAction());

        final Map<String, String[]> actualParams = href.getParameterMap();
        Assert.assertEquals(2, actualParams.size());
        Assert.assertArrayEquals(new String[]{"VAL1"}, actualParams.get("SINGLE_PARAM"));
        Assert.assertArrayEquals(new String[]{"31337"}, actualParams.get("INT_PARAM"));

        final PortletHref href2 = (PortletHref) href.clone();
        Assert.assertTrue(href != href2);
        Assert.assertEquals(href, href2);
        Assert.assertEquals(href.hashCode(), href2.hashCode());
    }

    @Test
    public void testBaseUrl()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        href.addParameter(PortletHref.PARAM_MODE, "help");
        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        href.addParameter(PortletHref.PARAM_SECURE, "true");
        href.addParameter("SINGLE_PARAM", "VAL1");

        final String baseRenderUrl = href.getBaseUrl();
        Assert.assertEquals("http://localhost/mockportlet?urlType=render", baseRenderUrl);

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);

        final String baseActionUrl = href.getBaseUrl();
        Assert.assertEquals("http://localhost/mockportlet?urlType=action", baseActionUrl);
    }

    @Test
    public void testFullUrl()
    {
        final PortletHref href = new PortletHref(new MockPortletRequest(), new MockRenderResponse());

        final String urlString1 = href.toString();
        Assert.assertEquals("http://localhost/mockportlet?urlType=render", urlString1);

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        final String urlString2 = href.toString();
        Assert.assertEquals("http://localhost/mockportlet?urlType=action", urlString2);

        href.addParameter(PortletHref.PARAM_SECURE, Boolean.TRUE.toString());
        final String urlString3 = href.toString();
        Assert.assertEquals("https://localhost/mockportlet?urlType=action", urlString3);

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
        Assert.assertEquals("https://localhost/mockportlet?urlType=action;windowState=normal", urlString7);

        href.addParameter(PortletHref.PARAM_STATE, "minimized");
        final String urlString8 = href.toString();
        Assert.assertEquals("https://localhost/mockportlet?urlType=action;windowState=minimized", urlString8);

        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        final String urlString9 = href.toString();
        Assert.assertEquals("https://localhost/mockportlet?urlType=action;windowState=maximized", urlString9);

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
        Assert.assertEquals(
            "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=view",
            urlString4);

        href.addParameter(PortletHref.PARAM_MODE, "help");
        final String urlString5 = href.toString();
        Assert.assertEquals(
            "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=help",
            urlString5);

        href.addParameter(PortletHref.PARAM_MODE, "edit");
        final String urlString6 = href.toString();
        Assert.assertEquals(
            "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit",
            urlString6);

        href.addParameter("SINGLE_PARAM", "VAL");
        final String urlString10 = href.toString();
        Assert
            .assertEquals(
                "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit;param_SINGLE_PARAM=VAL",
                urlString10);

        final Map<String, String[]> paramMap = new HashMap<String, String[]>();
        paramMap.put("MULTI_PARAM", new String[]{"VAL1", "VAL2"});
        href.addParameterMap(paramMap);
        final String urlString11 = href.toString();
        Assert
            .assertEquals(
                "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit;param_SINGLE_PARAM=VAL;param_MULTI_PARAM=VAL1;param_MULTI_PARAM=VAL2",
                urlString11);

        href.setAnchor("ANCHOR");
        final String urlString12 = href.toString();
        Assert
            .assertEquals(
                "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit;param_SINGLE_PARAM=VAL;param_MULTI_PARAM=VAL1;param_MULTI_PARAM=VAL2#ANCHOR",
                urlString12);
    }
}