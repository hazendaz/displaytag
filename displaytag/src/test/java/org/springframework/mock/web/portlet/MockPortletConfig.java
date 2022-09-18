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

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.junit.jupiter.api.Assertions;

/**
 * Mock implementation of the {@link javax.portlet.PortletConfig} interface.
 *
 * @author John A. Lewis
 * @author Juergen Hoeller
 * @since 2.0
 */
public class MockPortletConfig implements PortletConfig {

	/** The portlet context. */
	private final PortletContext portletContext;

	/** The portlet name. */
	private final String portletName;

	/** The resource bundles. */
	private final Map<Locale, ResourceBundle> resourceBundles = new HashMap<Locale, ResourceBundle>();

	/** The init parameters. */
	private final Map<String, String> initParameters = new LinkedHashMap<String, String>();

	/** The public render parameter names. */
	private final Set<String> publicRenderParameterNames = new LinkedHashSet<String>();

	/** The default namespace. */
	private String defaultNamespace = XMLConstants.NULL_NS_URI;

	/** The publishing event Q names. */
	private final Set<QName> publishingEventQNames = new LinkedHashSet<QName>();

	/** The processing event Q names. */
	private final Set<QName> processingEventQNames = new LinkedHashSet<QName>();

	/** The supported locales. */
	private final Set<Locale> supportedLocales = new LinkedHashSet<Locale>();

	/** The container runtime options. */
	private final Map<String, String[]> containerRuntimeOptions = new LinkedHashMap<String, String[]>();


	/**
	 * Create a new MockPortletConfig with a default {@link MockPortletContext}.
	 */
	public MockPortletConfig() {
		this(null, "");
	}

	/**
	 * Create a new MockPortletConfig with a default {@link MockPortletContext}.
	 * @param portletName the name of the portlet
	 */
	public MockPortletConfig(String portletName) {
		this(null, portletName);
	}

	/**
	 * Create a new MockPortletConfig.
	 * @param portletContext the PortletContext that the portlet runs in
	 */
	public MockPortletConfig(PortletContext portletContext) {
		this(portletContext, "");
	}

	/**
	 * Create a new MockPortletConfig.
	 * @param portletContext the PortletContext that the portlet runs in
	 * @param portletName the name of the portlet
	 */
	public MockPortletConfig(PortletContext portletContext, String portletName) {
		this.portletContext = (portletContext != null ? portletContext : new MockPortletContext());
		this.portletName = portletName;
	}


	@Override
	public String getPortletName() {
		return this.portletName;
	}

	@Override
	public PortletContext getPortletContext() {
		return this.portletContext;
	}

	/**
	 * Sets the resource bundle.
	 *
	 * @param locale the locale
	 * @param resourceBundle the resource bundle
	 */
	public void setResourceBundle(Locale locale, ResourceBundle resourceBundle) {
		Assertions.assertNotNull(locale, "Locale must not be null");
		this.resourceBundles.put(locale, resourceBundle);
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		Assertions.assertNotNull(locale, "Locale must not be null");
		return this.resourceBundles.get(locale);
	}

	/**
	 * Adds the init parameter.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public void addInitParameter(String name, String value) {
		Assertions.assertNotNull(name, "Parameter name must not be null");
		this.initParameters.put(name, value);
	}

	@Override
	public String getInitParameter(String name) {
		Assertions.assertNotNull(name, "Parameter name must not be null");
		return this.initParameters.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration(this.initParameters.keySet());
	}

	/**
	 * Adds the public render parameter name.
	 *
	 * @param name the name
	 */
	public void addPublicRenderParameterName(String name) {
		this.publicRenderParameterNames.add(name);
	}

	@Override
	public Enumeration<String> getPublicRenderParameterNames() {
		return Collections.enumeration(this.publicRenderParameterNames);
	}

	/**
	 * Sets the default namespace.
	 *
	 * @param defaultNamespace the new default namespace
	 */
	public void setDefaultNamespace(String defaultNamespace) {
		this.defaultNamespace = defaultNamespace;
	}

	@Override
	public String getDefaultNamespace() {
		return this.defaultNamespace;
	}

	/**
	 * Adds the publishing event Q name.
	 *
	 * @param name the name
	 */
	public void addPublishingEventQName(QName name) {
		this.publishingEventQNames.add(name);
	}

	@Override
	public Enumeration<QName> getPublishingEventQNames() {
		return Collections.enumeration(this.publishingEventQNames);
	}

	/**
	 * Adds the processing event Q name.
	 *
	 * @param name the name
	 */
	public void addProcessingEventQName(QName name) {
		this.processingEventQNames.add(name);
	}

	@Override
	public Enumeration<QName> getProcessingEventQNames() {
		return Collections.enumeration(this.processingEventQNames);
	}

	/**
	 * Adds the supported locale.
	 *
	 * @param locale the locale
	 */
	public void addSupportedLocale(Locale locale) {
		this.supportedLocales.add(locale);
	}

	@Override
	public Enumeration<Locale> getSupportedLocales() {
		return Collections.enumeration(this.supportedLocales);
	}

	/**
	 * Adds the container runtime option.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void addContainerRuntimeOption(String key, String value) {
		this.containerRuntimeOptions.put(key, new String[] {value});
	}

	/**
	 * Adds the container runtime option.
	 *
	 * @param key the key
	 * @param values the values
	 */
	public void addContainerRuntimeOption(String key, String[] values) {
		this.containerRuntimeOptions.put(key, values);
	}

	@Override
	public Map<String, String[]> getContainerRuntimeOptions() {
		return Collections.unmodifiableMap(this.containerRuntimeOptions);
	}

  @Override
  public Enumeration<PortletMode> getPortletModes(String mimeType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Enumeration<WindowState> getWindowStates(String mimeType) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Map<String, QName> getPublicRenderParameterDefinitions() {
    // TODO Auto-generated method stub
    return null;
  }

}
