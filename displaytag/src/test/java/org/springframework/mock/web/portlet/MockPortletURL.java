/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.mock.web.portlet;

import java.io.IOException;
import java.util.Map;

import javax.portlet.ActionURL;
import javax.portlet.MutableActionParameters;
import javax.portlet.MutableRenderParameters;
import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletURL;
import javax.portlet.RenderURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.portlet.annotations.PortletSerializable;

import org.junit.jupiter.api.Assertions;
import org.springframework.util.CollectionUtils;

/**
 * Mock implementation of the {@link javax.portlet.PortletURL} interface.
 *
 * @author John A. Lewis
 * @author Juergen Hoeller
 *
 * @since 2.0
 */
public class MockPortletURL extends MockBaseURL implements PortletURL, RenderURL, ActionURL {

    /** The Constant URL_TYPE_RENDER. */
    public static final String URL_TYPE_RENDER = "render";

    /** The Constant URL_TYPE_ACTION. */
    public static final String URL_TYPE_ACTION = "action";

    /** The portal context. */
    private final PortalContext portalContext;

    /** The url type. */
    private final String urlType;

    /** The window state. */
    private WindowState windowState;

    /** The portlet mode. */
    private PortletMode portletMode;

    /**
     * Create a new MockPortletURL for the given URL type.
     *
     * @param portalContext
     *            the PortalContext defining the supported PortletModes and WindowStates
     * @param urlType
     *            the URL type, for example "render" or "action"
     *
     * @see #URL_TYPE_RENDER
     * @see #URL_TYPE_ACTION
     */
    public MockPortletURL(PortalContext portalContext, String urlType) {
        Assertions.assertNotNull(portalContext, "PortalContext is required");
        this.portalContext = portalContext;
        this.urlType = urlType;
    }

    // ---------------------------------------------------------------------
    // PortletURL methods
    // ---------------------------------------------------------------------

    @Override
    public void setWindowState(WindowState windowState) throws WindowStateException {
        if (!CollectionUtils.contains(this.portalContext.getSupportedWindowStates(), windowState)) {
            throw new WindowStateException("WindowState not supported", windowState);
        }
        this.windowState = windowState;
    }

    @Override
    public WindowState getWindowState() {
        return this.windowState;
    }

    @Override
    public void setPortletMode(PortletMode portletMode) throws PortletModeException {
        if (!CollectionUtils.contains(this.portalContext.getSupportedPortletModes(), portletMode)) {
            throw new PortletModeException("PortletMode not supported", portletMode);
        }
        this.portletMode = portletMode;
    }

    @Override
    public PortletMode getPortletMode() {
        return this.portletMode;
    }

    @Override
    public void removePublicRenderParameter(String name) {
        this.parameters.remove(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(encodeParameter("urlType", this.urlType));
        if (this.windowState != null) {
            sb.append(";").append(encodeParameter("windowState", this.windowState.toString()));
        }
        if (this.portletMode != null) {
            sb.append(";").append(encodeParameter("portletMode", this.portletMode.toString()));
        }
        for (Map.Entry<String, String[]> entry : this.parameters.entrySet()) {
            sb.append(";").append(encodeParameter("param_" + entry.getKey(), entry.getValue()));
        }
        return (isSecure() ? "https:" : "http:") + "//localhost/mockportlet?" + sb.toString();
    }

    @Override
    public Appendable append(Appendable out) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Appendable append(Appendable out, boolean escapeXML) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MutableRenderParameters getRenderParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setBeanParameter(PortletSerializable bean) {
        // TODO Auto-generated method stub

    }

    @Override
    public MutableActionParameters getActionParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setFragmentIdentifier(String fragment) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getFragmentIdentifier() {
        // TODO Auto-generated method stub
        return null;
    }

}
