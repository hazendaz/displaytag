/*
 * Copyright 2002-2013 the original author or authors.
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

import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortalContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderParameters;
import javax.portlet.WindowState;
import javax.servlet.http.Cookie;

import org.junit.jupiter.api.Assertions;
import org.springframework.util.CollectionUtils;

/**
 * Mock implementation of the {@link javax.portlet.PortletRequest} interface.
 *
 * @author John A. Lewis
 * @author Juergen Hoeller
 *
 * @since 2.0
 */
public class MockPortletRequest implements PortletRequest {

    /** The active. */
    private boolean active = true;

    /** The portal context. */
    private final PortalContext portalContext;

    /** The portlet context. */
    private final PortletContext portletContext;

    /** The session. */
    private PortletSession session;

    /** The window state. */
    private WindowState windowState = WindowState.NORMAL;

    /** The portlet mode. */
    private PortletMode portletMode = PortletMode.VIEW;

    /** The portlet preferences. */
    private PortletPreferences portletPreferences = new MockPortletPreferences();

    /** The properties. */
    private final Map<String, List<String>> properties = new LinkedHashMap<String, List<String>>();

    /** The attributes. */
    private final Map<String, Object> attributes = new LinkedHashMap<String, Object>();

    /** The parameters. */
    private final Map<String, String[]> parameters = new LinkedHashMap<String, String[]>();

    /** The auth type. */
    private String authType = null;

    /** The context path. */
    private String contextPath = "";

    /** The remote user. */
    private String remoteUser = null;

    /** The user principal. */
    private Principal userPrincipal = null;

    /** The user roles. */
    private final Set<String> userRoles = new HashSet<String>();

    /** The secure. */
    private boolean secure = false;

    /** The requested session id valid. */
    private boolean requestedSessionIdValid = true;

    /** The response content types. */
    private final List<String> responseContentTypes = new LinkedList<String>();

    /** The locales. */
    private final List<Locale> locales = new LinkedList<Locale>();

    /** The scheme. */
    private String scheme = "http";

    /** The server name. */
    private String serverName = "localhost";

    /** The server port. */
    private int serverPort = 80;

    /** The window ID. */
    private String windowID;

    /** The cookies. */
    private Cookie[] cookies;

    /** The public parameter names. */
    private final Set<String> publicParameterNames = new HashSet<String>();

    /**
     * Create a new MockPortletRequest with a default {@link MockPortalContext} and a default
     * {@link MockPortletContext}.
     *
     * @see MockPortalContext
     * @see MockPortletContext
     */
    public MockPortletRequest() {
        this(null, null);
    }

    /**
     * Create a new MockPortletRequest with a default {@link MockPortalContext}.
     *
     * @param portletContext
     *            the PortletContext that the request runs in
     *
     * @see MockPortalContext
     */
    public MockPortletRequest(PortletContext portletContext) {
        this(null, portletContext);
    }

    /**
     * Create a new MockPortletRequest.
     *
     * @param portalContext
     *            the PortalContext that the request runs in
     * @param portletContext
     *            the PortletContext that the request runs in
     */
    public MockPortletRequest(PortalContext portalContext, PortletContext portletContext) {
        this.portalContext = (portalContext != null ? portalContext : new MockPortalContext());
        this.portletContext = (portletContext != null ? portletContext : new MockPortletContext());
        this.responseContentTypes.add("text/html");
        this.locales.add(Locale.ENGLISH);
        this.attributes.put(LIFECYCLE_PHASE, getLifecyclePhase());
    }

    // ---------------------------------------------------------------------
    // Lifecycle methods
    // ---------------------------------------------------------------------

    /**
     * Return the Portlet 2.0 lifecycle id for the current phase.
     *
     * @return the lifecycle phase
     */
    protected String getLifecyclePhase() {
        return null;
    }

    /**
     * Return whether this request is still active (that is, not completed yet).
     *
     * @return true, if is active
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Mark this request as completed.
     */
    public void close() {
        this.active = false;
    }

    /**
     * Check whether this request is still active (that is, not completed yet), throwing an IllegalStateException if not
     * active anymore.
     *
     * @throws IllegalStateException
     *             the illegal state exception
     */
    protected void checkActive() throws IllegalStateException {
        if (!this.active) {
            throw new IllegalStateException("Request is not active anymore");
        }
    }

    // ---------------------------------------------------------------------
    // PortletRequest methods
    // ---------------------------------------------------------------------

    @Override
    public boolean isWindowStateAllowed(WindowState windowState) {
        return CollectionUtils.contains(this.portalContext.getSupportedWindowStates(), windowState);
    }

    @Override
    public boolean isPortletModeAllowed(PortletMode portletMode) {
        return CollectionUtils.contains(this.portalContext.getSupportedPortletModes(), portletMode);
    }

    /**
     * Sets the portlet mode.
     *
     * @param portletMode
     *            the new portlet mode
     */
    public void setPortletMode(PortletMode portletMode) {
        Assertions.assertNotNull(portletMode, "PortletMode must not be null");
        this.portletMode = portletMode;
    }

    @Override
    public PortletMode getPortletMode() {
        return this.portletMode;
    }

    /**
     * Sets the window state.
     *
     * @param windowState
     *            the new window state
     */
    public void setWindowState(WindowState windowState) {
        Assertions.assertNotNull(windowState, "WindowState must not be null");
        this.windowState = windowState;
    }

    @Override
    public WindowState getWindowState() {
        return this.windowState;
    }

    /**
     * Sets the preferences.
     *
     * @param preferences
     *            the new preferences
     */
    public void setPreferences(PortletPreferences preferences) {
        Assertions.assertNotNull(preferences, "PortletPreferences must not be null");
        this.portletPreferences = preferences;
    }

    @Override
    public PortletPreferences getPreferences() {
        return this.portletPreferences;
    }

    /**
     * Sets the session.
     *
     * @param session
     *            the new session
     */
    public void setSession(PortletSession session) {
        this.session = session;
        if (session instanceof MockPortletSession) {
            MockPortletSession mockSession = ((MockPortletSession) session);
            mockSession.access();
        }
    }

    @Override
    public PortletSession getPortletSession() {
        return getPortletSession(true);
    }

    @Override
    public PortletSession getPortletSession(boolean create) {
        checkActive();
        // Reset session if invalidated.
        if (this.session instanceof MockPortletSession && ((MockPortletSession) this.session).isInvalid()) {
            this.session = null;
        }
        // Create new session if necessary.
        if (this.session == null && create) {
            this.session = new MockPortletSession(this.portletContext);
        }
        return this.session;
    }

    /**
     * Set a single value for the specified property.
     * <p>
     * If there are already one or more values registered for the given property key, they will be replaced.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void setProperty(String key, String value) {
        Assertions.assertNotNull(key, "Property key must not be null");
        List<String> list = new LinkedList<String>();
        list.add(value);
        this.properties.put(key, list);
    }

    /**
     * Add a single value for the specified property.
     * <p>
     * If there are already one or more values registered for the given property key, the given value will be added to
     * the end of the list.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void addProperty(String key, String value) {
        Assertions.assertNotNull(key, "Property key must not be null");
        List<String> oldList = this.properties.get(key);
        if (oldList != null) {
            oldList.add(value);
        } else {
            List<String> list = new LinkedList<String>();
            list.add(value);
            this.properties.put(key, list);
        }
    }

    @Override
    public String getProperty(String key) {
        Assertions.assertNotNull(key, "Property key must not be null");
        List<String> list = this.properties.get(key);
        return (list != null && list.size() > 0 ? list.get(0) : null);
    }

    @Override
    public Enumeration<String> getProperties(String key) {
        Assertions.assertNotNull(key, "property key must not be null");
        return Collections.enumeration(this.properties.get(key));
    }

    @Override
    public Enumeration<String> getPropertyNames() {
        return Collections.enumeration(this.properties.keySet());
    }

    @Override
    public PortalContext getPortalContext() {
        return this.portalContext;
    }

    /**
     * Sets the auth type.
     *
     * @param authType
     *            the new auth type
     */
    public void setAuthType(String authType) {
        this.authType = authType;
    }

    @Override
    public String getAuthType() {
        return this.authType;
    }

    /**
     * Sets the context path.
     *
     * @param contextPath
     *            the new context path
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public String getContextPath() {
        return this.contextPath;
    }

    /**
     * Sets the remote user.
     *
     * @param remoteUser
     *            the new remote user
     */
    public void setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
    }

    @Override
    public String getRemoteUser() {
        return this.remoteUser;
    }

    /**
     * Sets the user principal.
     *
     * @param userPrincipal
     *            the new user principal
     */
    public void setUserPrincipal(Principal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    @Override
    public Principal getUserPrincipal() {
        return this.userPrincipal;
    }

    /**
     * Adds the user role.
     *
     * @param role
     *            the role
     */
    public void addUserRole(String role) {
        this.userRoles.add(role);
    }

    @Override
    public boolean isUserInRole(String role) {
        return this.userRoles.contains(role);
    }

    @Override
    public Object getAttribute(String name) {
        checkActive();
        return this.attributes.get(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        checkActive();
        return Collections.enumeration(new LinkedHashSet<String>(this.attributes.keySet()));
    }

    /**
     * Sets the parameters.
     *
     * @param parameters
     *            the parameters
     */
    public void setParameters(Map<String, String[]> parameters) {
        Assertions.assertNotNull(parameters, "Parameters Map must not be null");
        this.parameters.clear();
        this.parameters.putAll(parameters);
    }

    /**
     * Sets the parameter.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public void setParameter(String key, String value) {
        Assertions.assertNotNull(key, "Parameter key must be null");
        Assertions.assertNotNull(value, "Parameter value must not be null");
        this.parameters.put(key, new String[] { value });
    }

    /**
     * Sets the parameter.
     *
     * @param key
     *            the key
     * @param values
     *            the values
     */
    public void setParameter(String key, String[] values) {
        Assertions.assertNotNull(key, "Parameter key must be null");
        Assertions.assertNotNull(values, "Parameter values must not be null");
        this.parameters.put(key, values);
    }

    /**
     * Adds the parameter.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public void addParameter(String name, String value) {
        addParameter(name, new String[] { value });
    }

    /**
     * Adds the parameter.
     *
     * @param name
     *            the name
     * @param values
     *            the values
     */
    public void addParameter(String name, String[] values) {
        String[] oldArr = this.parameters.get(name);
        if (oldArr != null) {
            String[] newArr = new String[oldArr.length + values.length];
            System.arraycopy(oldArr, 0, newArr, 0, oldArr.length);
            System.arraycopy(values, 0, newArr, oldArr.length, values.length);
            this.parameters.put(name, newArr);
        } else {
            this.parameters.put(name, values);
        }
    }

    @Override
    public String getParameter(String name) {
        String[] arr = this.parameters.get(name);
        return (arr != null && arr.length > 0 ? arr[0] : null);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(this.parameters.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.parameters.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(this.parameters);
    }

    /**
     * Sets the secure.
     *
     * @param secure
     *            the new secure
     */
    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    @Override
    public boolean isSecure() {
        return this.secure;
    }

    @Override
    public void setAttribute(String name, Object value) {
        checkActive();
        if (value != null) {
            this.attributes.put(name, value);
        } else {
            this.attributes.remove(name);
        }
    }

    @Override
    public void removeAttribute(String name) {
        checkActive();
        this.attributes.remove(name);
    }

    @Override
    public String getRequestedSessionId() {
        PortletSession session = this.getPortletSession();
        return (session != null ? session.getId() : null);
    }

    /**
     * Sets the requested session id valid.
     *
     * @param requestedSessionIdValid
     *            the new requested session id valid
     */
    public void setRequestedSessionIdValid(boolean requestedSessionIdValid) {
        this.requestedSessionIdValid = requestedSessionIdValid;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return this.requestedSessionIdValid;
    }

    /**
     * Adds the response content type.
     *
     * @param responseContentType
     *            the response content type
     */
    public void addResponseContentType(String responseContentType) {
        this.responseContentTypes.add(responseContentType);
    }

    /**
     * Adds the preferred response content type.
     *
     * @param responseContentType
     *            the response content type
     */
    public void addPreferredResponseContentType(String responseContentType) {
        this.responseContentTypes.add(0, responseContentType);
    }

    @Override
    public String getResponseContentType() {
        return this.responseContentTypes.get(0);
    }

    @Override
    public Enumeration<String> getResponseContentTypes() {
        return Collections.enumeration(this.responseContentTypes);
    }

    /**
     * Adds the locale.
     *
     * @param locale
     *            the locale
     */
    public void addLocale(Locale locale) {
        this.locales.add(locale);
    }

    /**
     * Adds the preferred locale.
     *
     * @param locale
     *            the locale
     */
    public void addPreferredLocale(Locale locale) {
        this.locales.add(0, locale);
    }

    @Override
    public Locale getLocale() {
        return this.locales.get(0);
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return Collections.enumeration(this.locales);
    }

    /**
     * Sets the scheme.
     *
     * @param scheme
     *            the new scheme
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public String getScheme() {
        return this.scheme;
    }

    /**
     * Sets the server name.
     *
     * @param serverName
     *            the new server name
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String getServerName() {
        return this.serverName;
    }

    /**
     * Sets the server port.
     *
     * @param serverPort
     *            the new server port
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public int getServerPort() {
        return this.serverPort;
    }

    /**
     * Sets the window ID.
     *
     * @param windowID
     *            the new window ID
     */
    public void setWindowID(String windowID) {
        this.windowID = windowID;
    }

    @Override
    public String getWindowID() {
        return this.windowID;
    }

    /**
     * Sets the cookies.
     *
     * @param cookies
     *            the new cookies
     */
    public void setCookies(Cookie... cookies) {
        this.cookies = cookies;
    }

    @Override
    public Cookie[] getCookies() {
        return this.cookies;
    }

    @Override
    public Map<String, String[]> getPrivateParameterMap() {
        if (!this.publicParameterNames.isEmpty()) {
            Map<String, String[]> filtered = new LinkedHashMap<String, String[]>();
            for (String key : this.parameters.keySet()) {
                if (!this.publicParameterNames.contains(key)) {
                    filtered.put(key, this.parameters.get(key));
                }
            }
            return filtered;
        } else {
            return Collections.unmodifiableMap(this.parameters);
        }
    }

    @Override
    public Map<String, String[]> getPublicParameterMap() {
        if (!this.publicParameterNames.isEmpty()) {
            Map<String, String[]> filtered = new LinkedHashMap<String, String[]>();
            for (String key : this.parameters.keySet()) {
                if (this.publicParameterNames.contains(key)) {
                    filtered.put(key, this.parameters.get(key));
                }
            }
            return filtered;
        } else {
            return Collections.emptyMap();
        }
    }

    /**
     * Register public parameter.
     *
     * @param name
     *            the name
     */
    public void registerPublicParameter(String name) {
        this.publicParameterNames.add(name);
    }

    @Override
    public RenderParameters getRenderParameters() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PortletContext getPortletContext() {
        return this.portletContext;
    }

    @Override
    public String getUserAgent() {
        // TODO Auto-generated method stub
        return null;
    }

}
