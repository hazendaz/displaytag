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

import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mockit.Expectations;
import mockit.Mocked;

/**
 * The Class PortletHrefTest.
 *
 * @author Eric Dalquist <a href="mailto:edalquist@unicon.net">edalquist@unicon.net</a>
 *
 * @version $Id$
 */
class PortletHrefTest {

    @Mocked
    PortletRequest portletRequest;

    @Mocked
    MimeResponse portletResponse;

    /**
     * Test constructor.
     */
    @Test
    void testConstructor() {
        try {
            new PortletHref(null, null);
            Assertions.fail("IllegalArgumentException should have been thrown");
        } catch (final IllegalArgumentException iae) {
            // expected
        }

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new PortletHref(portletRequest, null);
        }, "IllegalArgumentException should have been thrown");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new PortletHref(null, portletResponse);
        }, "IllegalArgumentException should have been thrown");

        new PortletHref(portletRequest, portletResponse);
    }

    /**
     * Test url type parameters.
     */
    @Test
    void testUrlTypeParameters() {
        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        Assertions.assertFalse(href.isAction());

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        Assertions.assertTrue(href.isAction());

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_RENDER);
        Assertions.assertFalse(href.isAction());

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        Assertions.assertTrue(href.isAction());

        try {
            href.addParameter(PortletHref.PARAM_TYPE, null);
            Assertions.fail("IllegalArgumentException should have been thrown");
        } catch (final IllegalArgumentException iae) {
            // expected
        }

        try {
            href.addParameter(PortletHref.PARAM_TYPE, "");
            Assertions.fail("IllegalArgumentException should have been thrown");
        } catch (final IllegalArgumentException iae) {
            // expected
        }

        try {
            href.addParameter(PortletHref.PARAM_TYPE, "InvalidUrlType");
            Assertions.fail("IllegalArgumentException should have been thrown");
        } catch (final IllegalArgumentException iae) {
            // expected
        }
    }

    /**
     * Test secure parameters.
     */
    @Test
    void testSecureParameters() {
        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        Assertions.assertFalse(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, Boolean.TRUE.toString());
        Assertions.assertTrue(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "true");
        Assertions.assertTrue(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "TRUE");
        Assertions.assertTrue(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "yes");
        Assertions.assertFalse(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, null);
        Assertions.assertFalse(href.isRequestedSecure());

        href.addParameter(PortletHref.PARAM_SECURE, "false");
        Assertions.assertFalse(href.isRequestedSecure());
    }

    /**
     * Test portlet mode parameters.
     */
    @Test
    void testPortletModeParameters() {
        new Expectations() {
            {
                portletRequest.isPortletModeAllowed(this.withInstanceOf(PortletMode.class));
                result = true;
            }
        };

        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        Assertions.assertNull(href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, "view");
        Assertions.assertEquals(new PortletMode("view"), href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, "help");
        Assertions.assertEquals(new PortletMode("help"), href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, "edit");
        Assertions.assertEquals(new PortletMode("edit"), href.getRequestedMode());

        href.addParameter(PortletHref.PARAM_MODE, null);
        Assertions.assertNull(href.getRequestedMode());

        new Expectations() {
            {
                portletRequest.isPortletModeAllowed(this.withInstanceOf(PortletMode.class));
                result = false;
            }
        };

        try {
            href.addParameter(PortletHref.PARAM_MODE, "info");
            Assertions.fail("IllegalArgumentException should have been thrown");
        } catch (final IllegalArgumentException iae) {
            // expected
        }
    }

    /**
     * Test window state parameters.
     */
    @Test
    void testWindowStateParameters() {
        new Expectations() {
            {
                portletRequest.isWindowStateAllowed(this.withInstanceOf(WindowState.class));
                result = true;
            }
        };

        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        Assertions.assertNull(href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, "normal");
        Assertions.assertEquals(new WindowState("normal"), href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, "minimized");
        Assertions.assertEquals(new WindowState("minimized"), href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        Assertions.assertEquals(new WindowState("maximized"), href.getRequestedState());

        href.addParameter(PortletHref.PARAM_STATE, null);
        Assertions.assertNull(href.getRequestedState());

        new Expectations() {
            {
                portletRequest.isWindowStateAllowed(this.withInstanceOf(WindowState.class));
                result = false;
            }
        };

        try {
            href.addParameter(PortletHref.PARAM_STATE, "exclusive");
            Assertions.fail("IllegalArgumentException should have been thrown");
        } catch (final IllegalArgumentException iae) {
            // expected
        }
    }

    /**
     * Test invalid prefix use parameters.
     */
    @Test
    void testInvalidPrefixUseParameters() {
        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        try {
            href.addParameter("portlet:WindowState", "exclusive");
            Assertions.fail("IllegalArgumentException should have been thrown");
        } catch (final IllegalArgumentException iae) {
            // expected
        }
    }

    /**
     * Test add parameters.
     */
    @Test
    void testAddParameters() {
        new Expectations() {
            {
                portletRequest.isPortletModeAllowed(this.withInstanceOf(PortletMode.class));
                result = true;

                portletRequest.isWindowStateAllowed(this.withInstanceOf(WindowState.class));
                result = true;
            }
        };

        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        href.addParameter(PortletHref.PARAM_MODE, "help");
        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        href.addParameter(PortletHref.PARAM_SECURE, "true");
        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        href.addParameter("SINGLE_PARAM", "VAL1");
        href.addParameter("INT_PARAM", 31337);

        Assertions.assertTrue(href.isRequestedSecure());
        Assertions.assertEquals(new PortletMode("help"), href.getRequestedMode());
        Assertions.assertEquals(new WindowState("maximized"), href.getRequestedState());
        Assertions.assertTrue(href.isAction());

        final Map<String, String[]> actualParams = href.getParameterMap();
        Assertions.assertEquals(2, actualParams.size());
        Assertions.assertArrayEquals(new String[] { "VAL1" }, actualParams.get("SINGLE_PARAM"));
        Assertions.assertArrayEquals(new String[] { "31337" }, actualParams.get("INT_PARAM"));
    }

    /**
     * Test add parameter map.
     */
    @Test
    void testAddParameterMap() {
        new Expectations() {
            {
                portletRequest.isPortletModeAllowed(this.withInstanceOf(PortletMode.class));
                result = true;

                portletRequest.isWindowStateAllowed(this.withInstanceOf(WindowState.class));
                result = true;
            }
        };

        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        final Map<String, String[]> params = new HashMap<>();
        params.put(PortletHref.PARAM_MODE, new String[] { "help" });
        params.put(PortletHref.PARAM_STATE, new String[] { "maximized" });
        params.put(PortletHref.PARAM_SECURE, new String[] { "true" });
        params.put(PortletHref.PARAM_TYPE, new String[] { PortletHref.TYPE_ACTION });
        params.put("SINGLE_PARAM", new String[] { "VAL1" });
        final String[] multiParam = { "VAL2", "VAL3" };
        params.put("MULTI_PARAM", multiParam);
        params.put("INT_PARAM", new String[] { "31337" });

        href.addParameterMap(params);

        Assertions.assertTrue(href.isRequestedSecure());
        Assertions.assertEquals(new PortletMode("help"), href.getRequestedMode());
        Assertions.assertEquals(new WindowState("maximized"), href.getRequestedState());
        Assertions.assertTrue(href.isAction());

        final Map<String, String[]> actualParams = href.getParameterMap();
        Assertions.assertEquals(3, actualParams.size());
        Assertions.assertArrayEquals(new String[] { "VAL1" }, actualParams.get("SINGLE_PARAM"));
        Assertions.assertArrayEquals(new String[] { "31337" }, actualParams.get("INT_PARAM"));

        final String[] actualMultiParam = actualParams.get("MULTI_PARAM");
        Assertions.assertEquals(multiParam.length, actualMultiParam.length);
        Assertions.assertEquals(multiParam[0], actualMultiParam[0]);
        Assertions.assertEquals(multiParam[1], actualMultiParam[1]);
    }

    /**
     * Test set parameter map.
     */
    @Test
    void testSetParameterMap() {
        new Expectations() {
            {
                portletRequest.isPortletModeAllowed(this.withInstanceOf(PortletMode.class));
                result = true;

                portletRequest.isWindowStateAllowed(this.withInstanceOf(WindowState.class));
                result = true;
            }
        };

        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        href.addParameter("ORIGINAL_PARAM", "ORIGNAL_VALUE");

        final Map<String, String[]> params = new HashMap<>();
        params.put(PortletHref.PARAM_MODE, new String[] { "help" });
        params.put(PortletHref.PARAM_STATE, new String[] { "maximized" });
        params.put(PortletHref.PARAM_SECURE, new String[] { "true" });
        params.put(PortletHref.PARAM_TYPE, new String[] { PortletHref.TYPE_ACTION });
        params.put("SINGLE_PARAM", new String[] { "VAL1" });
        final String[] multiParam = { "VAL2", "VAL3" };
        params.put("MULTI_PARAM", multiParam);
        params.put("INT_PARAM", new String[] { "31337" });

        href.setParameterMap(params);

        Assertions.assertTrue(href.isRequestedSecure());
        Assertions.assertEquals(new PortletMode("help"), href.getRequestedMode());
        Assertions.assertEquals(new WindowState("maximized"), href.getRequestedState());
        Assertions.assertTrue(href.isAction());

        final Map<String, String[]> actualParams = href.getParameterMap();
        Assertions.assertEquals(3, actualParams.size());
        Assertions.assertArrayEquals(new String[] { "VAL1" }, actualParams.get("SINGLE_PARAM"));
        Assertions.assertArrayEquals(new String[] { "31337" }, actualParams.get("INT_PARAM"));
        Assertions.assertNull(actualParams.get("ORIGINAL_PARAM"));

        final String[] actualMultiParam = actualParams.get("MULTI_PARAM");
        Assertions.assertEquals(multiParam.length, actualMultiParam.length);
        Assertions.assertEquals(multiParam[0], actualMultiParam[0]);
        Assertions.assertEquals(multiParam[1], actualMultiParam[1]);
    }

    /**
     * Test remove parameter.
     */
    @Test
    void testRemoveParameter() {
        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        href.addParameter("SINGLE_PARAM", "VAL1");

        final Map<String, String[]> actualParams = href.getParameterMap();
        Assertions.assertEquals(1, actualParams.size());
        Assertions.assertArrayEquals(new String[] { "VAL1" }, actualParams.get("SINGLE_PARAM"));

        href.removeParameter("SINGLE_PARAM");

        final Map<String, String[]> actualParams2 = href.getParameterMap();
        Assertions.assertEquals(0, actualParams2.size());
        Assertions.assertNull(actualParams2.get("SINGLE_PARAM"));
    }

    /**
     * Test clone.
     */
    @Test
    void testClone() {
        new Expectations() {
            {
                portletRequest.isPortletModeAllowed(this.withInstanceOf(PortletMode.class));
                result = true;

                portletRequest.isWindowStateAllowed(this.withInstanceOf(WindowState.class));
                result = true;
            }
        };

        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        href.addParameter(PortletHref.PARAM_MODE, "help");
        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        href.addParameter(PortletHref.PARAM_SECURE, "true");
        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        href.addParameter("SINGLE_PARAM", "VAL1");
        href.addParameter("INT_PARAM", 31337);

        Assertions.assertTrue(href.isRequestedSecure());
        Assertions.assertEquals(new PortletMode("help"), href.getRequestedMode());
        Assertions.assertEquals(new WindowState("maximized"), href.getRequestedState());
        Assertions.assertTrue(href.isAction());

        final Map<String, String[]> actualParams = href.getParameterMap();
        Assertions.assertEquals(2, actualParams.size());
        Assertions.assertArrayEquals(new String[] { "VAL1" }, actualParams.get("SINGLE_PARAM"));
        Assertions.assertArrayEquals(new String[] { "31337" }, actualParams.get("INT_PARAM"));

        final PortletHref href2 = (PortletHref) href.clone();
        Assertions.assertNotSame(href, href2);
        Assertions.assertEquals(href, href2);
        Assertions.assertEquals(href.hashCode(), href2.hashCode());
    }

    /**
     * Test base url.
     */
    @Test
    void testBaseUrl() {
        new Expectations() {
            {
                portletRequest.isPortletModeAllowed(this.withInstanceOf(PortletMode.class));
                result = true;

                portletRequest.isWindowStateAllowed(this.withInstanceOf(WindowState.class));
                result = true;
            }
        };

        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        href.addParameter(PortletHref.PARAM_MODE, "help");
        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        href.addParameter(PortletHref.PARAM_SECURE, "true");
        href.addParameter("SINGLE_PARAM", "VAL1");

        final String baseRenderUrl = href.getBaseUrl();
        Assertions.assertEquals("http://localhost/mockportlet?urlType=render", baseRenderUrl);

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);

        final String baseActionUrl = href.getBaseUrl();
        Assertions.assertEquals("http://localhost/mockportlet?urlType=action", baseActionUrl);
    }

    /**
     * Test full url.
     */
    @Test
    void testFullUrl() {
        final PortletHref href = new PortletHref(portletRequest, portletResponse);

        final String urlString1 = href.toString();
        Assertions.assertEquals("http://localhost/mockportlet?urlType=render", urlString1);

        href.addParameter(PortletHref.PARAM_TYPE, PortletHref.TYPE_ACTION);
        final String urlString2 = href.toString();
        Assertions.assertEquals("http://localhost/mockportlet?urlType=action", urlString2);

        href.addParameter(PortletHref.PARAM_SECURE, Boolean.TRUE.toString());
        final String urlString3 = href.toString();
        Assertions.assertEquals("https://localhost/mockportlet?urlType=action", urlString3);

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
        Assertions.assertEquals("https://localhost/mockportlet?urlType=action;windowState=normal", urlString7);

        href.addParameter(PortletHref.PARAM_STATE, "minimized");
        final String urlString8 = href.toString();
        Assertions.assertEquals("https://localhost/mockportlet?urlType=action;windowState=minimized", urlString8);

        href.addParameter(PortletHref.PARAM_STATE, "maximized");
        final String urlString9 = href.toString();
        Assertions.assertEquals("https://localhost/mockportlet?urlType=action;windowState=maximized", urlString9);

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
        Assertions.assertEquals("https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=view",
                urlString4);

        href.addParameter(PortletHref.PARAM_MODE, "help");
        final String urlString5 = href.toString();
        Assertions.assertEquals("https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=help",
                urlString5);

        href.addParameter(PortletHref.PARAM_MODE, "edit");
        final String urlString6 = href.toString();
        Assertions.assertEquals("https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit",
                urlString6);

        href.addParameter("SINGLE_PARAM", "VAL");
        final String urlString10 = href.toString();
        Assertions.assertEquals(
                "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit;param_SINGLE_PARAM=VAL",
                urlString10);

        final Map<String, String[]> paramMap = new HashMap<>();
        paramMap.put("MULTI_PARAM", new String[] { "VAL1", "VAL2" });
        href.addParameterMap(paramMap);
        final String urlString11 = href.toString();
        Assertions.assertEquals(
                "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit;param_SINGLE_PARAM=VAL;param_MULTI_PARAM=VAL1;param_MULTI_PARAM=VAL2",
                urlString11);

        href.setAnchor("ANCHOR");
        final String urlString12 = href.toString();
        Assertions.assertEquals(
                "https://localhost/mockportlet?urlType=action;windowState=maximized;portletMode=edit;param_SINGLE_PARAM=VAL;param_MULTI_PARAM=VAL1;param_MULTI_PARAM=VAL2#ANCHOR",
                urlString12);
    }
}
