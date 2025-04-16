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
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.portlet.BaseURL;
import javax.portlet.PortletSecurityException;

import org.junit.jupiter.api.Assertions;
import org.springframework.util.StringUtils;

/**
 * Mock implementation of the {@link javax.portlet.BaseURL} interface.
 *
 * @author Juergen Hoeller
 *
 * @since 3.0
 */
public abstract class MockBaseURL implements BaseURL {

    /** The Constant URL_TYPE_RENDER. */
    public static final String URL_TYPE_RENDER = "render";

    /** The Constant URL_TYPE_ACTION. */
    public static final String URL_TYPE_ACTION = "action";

    /** The Constant ENCODING. */
    private static final Charset ENCODING = StandardCharsets.UTF_8;

    /** The parameters. */
    protected final Map<String, String[]> parameters = new LinkedHashMap<String, String[]>();

    /** The secure. */
    private boolean secure = false;

    /** The properties. */
    private final Map<String, String[]> properties = new LinkedHashMap<String, String[]>();

    // ---------------------------------------------------------------------
    // BaseURL methods
    // ---------------------------------------------------------------------

    @Override
    public void setParameter(String key, String value) {
        Assertions.assertNotNull(key, "Parameter key must be null");
        Assertions.assertNotNull(value, "Parameter value must not be null");
        this.parameters.put(key, new String[] { value });
    }

    @Override
    public void setParameter(String key, String[] values) {
        Assertions.assertNotNull(key, "Parameter key must be null");
        Assertions.assertNotNull(values, "Parameter values must not be null");
        this.parameters.put(key, values);
    }

    @Override
    public void setParameters(Map<String, String[]> parameters) {
        Assertions.assertNotNull(parameters, "Parameters Map must not be null");
        this.parameters.clear();
        this.parameters.putAll(parameters);
    }

    /**
     * Gets the parameter names.
     *
     * @return the parameter names
     */
    public Set<String> getParameterNames() {
        return this.parameters.keySet();
    }

    /**
     * Gets the parameter.
     *
     * @param name
     *            the name
     *
     * @return the parameter
     */
    public String getParameter(String name) {
        String[] arr = this.parameters.get(name);
        return (arr != null && arr.length > 0 ? arr[0] : null);
    }

    /**
     * Gets the parameter values.
     *
     * @param name
     *            the name
     *
     * @return the parameter values
     */
    public String[] getParameterValues(String name) {
        return this.parameters.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return Collections.unmodifiableMap(this.parameters);
    }

    @Override
    public void setSecure(boolean secure) throws PortletSecurityException {
        this.secure = secure;
    }

    /**
     * Checks if is secure.
     *
     * @return true, if is secure
     */
    public boolean isSecure() {
        return this.secure;
    }

    @Override
    public void write(Writer out) throws IOException {
        out.write(toString());
    }

    @Override
    public void write(Writer out, boolean escapeXML) throws IOException {
        out.write(toString());
    }

    @Override
    public void addProperty(String key, String value) {
        String[] values = this.properties.get(key);
        if (values != null) {
            this.properties.put(key, StringUtils.addStringToArray(values, value));
        } else {
            this.properties.put(key, new String[] { value });
        }
    }

    @Override
    public void setProperty(String key, String value) {
        this.properties.put(key, new String[] { value });
    }

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    public Map<String, String[]> getProperties() {
        return Collections.unmodifiableMap(this.properties);
    }

    /**
     * Encode parameter.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     *
     * @return the string
     */
    protected String encodeParameter(String name, String value) {
        return URLEncoder.encode(name, ENCODING) + "=" + URLEncoder.encode(value, ENCODING);
    }

    /**
     * Encode parameter.
     *
     * @param name
     *            the name
     * @param values
     *            the values
     *
     * @return the string
     */
    protected String encodeParameter(String name, String[] values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, n = values.length; i < n; i++) {
            sb.append(i > 0 ? ";" : "").append(URLEncoder.encode(name, ENCODING)).append("=")
                    .append(URLEncoder.encode(values[i], ENCODING));
        }
        return sb.toString();
    }

}
