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
package org.displaytag.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * The Class DefaultHref.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class DefaultHref implements Href {

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    /**
     * Base url for the href.
     */
    private String url;

    /**
     * Url parameters.
     */
    private Map<String, String[]> parameters;

    /**
     * Anchor (to be added at the end of URL).
     */
    private String anchor;

    /**
     * Construct a new Href parsing a URL. Parameters are stripped from the base url and saved in the parameters map.
     *
     * @param baseUrl
     *            String
     */
    public DefaultHref(final String baseUrl) {
        this.parameters = new LinkedHashMap<>();
        this.setFullUrl(baseUrl);
    }

    /**
     * Sets the full url.
     *
     * @param baseUrl
     *            the new full url
     *
     * @see org.displaytag.util.Href#setFullUrl(java.lang.String)
     */
    @Override
    public void setFullUrl(final String baseUrl) {
        this.url = null;
        this.anchor = null;
        String noAnchorUrl;

        final int anchorposition = baseUrl.indexOf('#');

        // extract anchor from url
        if (anchorposition != -1) {
            noAnchorUrl = baseUrl.substring(0, anchorposition);
            this.anchor = baseUrl.substring(anchorposition + 1);
        } else {
            noAnchorUrl = baseUrl;
        }

        if (noAnchorUrl.indexOf('?') == -1) {
            // simple url, no parameters
            this.url = noAnchorUrl;
            return;
        }

        // the Url already has parameters, put them in the parameter Map
        final StringTokenizer tokenizer = new StringTokenizer(noAnchorUrl, "?"); //$NON-NLS-1$

        if (baseUrl.startsWith("?")) //$NON-NLS-1$
        {
            // support fake URI's which are just parameters to use with the current uri
            this.url = TagConstants.EMPTY_STRING;
        } else {
            // base url (before "?")
            this.url = tokenizer.nextToken();
        }

        if (!tokenizer.hasMoreTokens()) {
            return;
        }

        // process parameters
        final StringTokenizer paramTokenizer = new StringTokenizer(tokenizer.nextToken(), "&"); //$NON-NLS-1$

        // split parameters (key=value)
        while (paramTokenizer.hasMoreTokens()) {
            // split key and value ...
            final String[] keyValue = StringUtils.split(paramTokenizer.nextToken(), '=');

            // encode name/value to prevent css
            final String decodedkey = this.decodeParam(keyValue[0]);
            final String decodedvalue = this.decodeParam(keyValue.length > 1 ? keyValue[1] : StringUtils.EMPTY);

            if (!this.parameters.containsKey(decodedkey)) {
                // ... and add it to the map
                this.parameters.put(decodedkey, new String[] { decodedvalue });
            } else {
                // additional value for an existing parameter
                final String[] previousValue = this.parameters.get(decodedkey);

                final String[] newArray = new String[previousValue.length + 1];

                int j;

                for (j = 0; j < previousValue.length; j++) {
                    newArray[j] = previousValue[j];
                }

                newArray[j] = decodedvalue;
                this.parameters.put(decodedkey, newArray);

            }
        }
    }

    /**
     * Adds a parameter to the href.
     *
     * @param key
     *            String
     * @param value
     *            Object
     *
     * @return this Href instance, useful for concatenation.
     */
    @Override
    public Href addParameter(final String key, final Object value) {
        this.parameters.put(this.decodeParam(key), new String[] { this.decodeParam(value) });
        return this;
    }

    /**
     * Removes a parameter from the href.
     *
     * @param key
     *            String
     */
    @Override
    public void removeParameter(final String key) {
        this.parameters.remove(this.decodeParam(key));
    }

    /**
     * Adds an int parameter to the href.
     *
     * @param key
     *            String
     * @param value
     *            int
     *
     * @return this Href instance, useful for concatenation.
     */
    @Override
    public Href addParameter(final String key, final int value) {
        this.parameters.put(this.decodeParam(key), new String[] { Integer.toString(value) });
        return this;
    }

    /**
     * Getter for the map containing link parameters. The returned map is always a copy and not the original instance.
     *
     * @return parameter Map (copy)
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        final Map<String, String[]> copyMap = new LinkedHashMap<>(this.parameters);
        return copyMap;
    }

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Any parameter already present in the href object is removed.
     *
     * @param parametersMap
     *            Map containing parameters
     */
    @Override
    public void setParameterMap(final Map<String, String[]> parametersMap) {
        // create a new HashMap
        this.parameters = new HashMap<>(parametersMap.size());

        // copy the parameters
        this.addParameterMap(parametersMap);
    }

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Parameters in the original href are kept and not overridden.
     *
     * @param parametersMap
     *            Map containing parameters
     */
    @Override
    public void addParameterMap(final Map<String, String[]> parametersMap) {
        // handle nulls
        if (parametersMap == null) {
            return;
        }

        // copy value, escaping html
        final Iterator<Entry<String, String[]>> mapIterator = parametersMap.entrySet().iterator();
        while (mapIterator.hasNext()) {
            final Entry<String, String[]> entry = mapIterator.next();
            final String key = this.decodeParam(entry.getKey());

            // don't overwrite parameters
            if (!this.parameters.containsKey(key)) {
                final String[] value = entry.getValue();

                if (value != null) {
                    String[] values;
                    // check mantained for binary compatibility with displaytag 1.2
                    if (value.getClass().isArray()) {
                        values = value;
                        for (int i = 0; i < values.length; i++) {
                            values[i] = this.decodeParam(values[i]);
                        }
                    } else {
                        values = new String[] { this.decodeParam(value) };
                    }

                    this.parameters.put(key, values);
                } else {
                    this.parameters.put(key, new String[0]);
                }
            }
        }
    }

    /**
     * Getter for the base url (without parameters).
     *
     * @return String
     */
    @Override
    public String getBaseUrl() {
        return this.url;
    }

    /**
     * Returns the URI anchor.
     *
     * @return anchor or <code>null</code> if no anchor has been set.
     */
    @Override
    public String getAnchor() {
        return this.anchor;
    }

    /**
     * Setter for the URI anchor.
     *
     * @param name
     *            string to be used as anchor name (without #).
     */
    @Override
    public void setAnchor(final String name) {
        this.anchor = name;
    }

    /**
     * toString: output the full url with parameters.
     *
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder(30);

        buffer.append(this.url);

        if (this.parameters.size() > 0) {
            buffer.append('?');
            final Set<Entry<String, String[]>> parameterSet = this.parameters.entrySet();

            final Iterator<Entry<String, String[]>> iterator = parameterSet.iterator();

            while (iterator.hasNext()) {
                final Entry<String, String[]> entry = iterator.next();

                final Object key = entry.getKey();
                final Object value = entry.getValue();

                if (value == null) {
                    buffer.append(this.encodeParam(key)).append('='); // no value
                } else if (value.getClass().isArray()) {
                    final Object[] values = (Object[]) value;
                    if (values.length == 0) {
                        buffer.append(this.encodeParam(key)).append('='); // no value
                    } else {
                        for (int i = 0; i < values.length; i++) {
                            if (i > 0) {
                                buffer.append(TagConstants.AMPERSAND);
                            }

                            buffer.append(this.encodeParam(key)).append('=').append(this.encodeParam(values[i]));
                        }
                    }
                } else {
                    buffer.append(this.encodeParam(key)).append('=').append(this.encodeParam(value));
                }

                if (iterator.hasNext()) {
                    buffer.append(TagConstants.AMPERSAND);
                }
            }
        }

        if (this.anchor != null) {
            buffer.append('#');
            buffer.append(this.anchor);
        }

        return buffer.toString();
    }

    /**
     * Encode param.
     *
     * @param param
     *            the param
     *
     * @return the string
     */
    private String encodeParam(final Object param) {
        if (param == null) {
            return StringUtils.EMPTY;
        }
        try {
            return URLEncoder.encode(param.toString(), "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            // should never happen
            throw new RuntimeException(e);
        }
    }

    /**
     * Decode param.
     *
     * @param param
     *            the param
     *
     * @return the string
     */
    private String decodeParam(final Object param) {
        if (param == null) {
            return StringUtils.EMPTY;
        }
        try {
            return URLDecoder.decode(param.toString(), "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            // should never happen
            throw new RuntimeException(e);
        }
    }

    /**
     * Clone.
     *
     * @return the object
     *
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() {
        final DefaultHref href;
        try {
            href = (DefaultHref) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new RuntimeException(e); // should never happen
        }

        href.parameters = new LinkedHashMap<>(this.parameters);
        return href;
    }

    /**
     * Equals.
     *
     * @param object
     *            the object
     *
     * @return true, if successful
     *
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof DefaultHref)) {
            return false;
        }
        final DefaultHref rhs = (DefaultHref) object;

        // "parameters" can't be added directly, since equals on HashMap doesn't return true with equal key/values
        return new EqualsBuilder().append(this.parameters.keySet(), rhs.parameters.keySet())
                .append(this.parameters.values().toArray(), rhs.parameters.values().toArray()).append(this.url, rhs.url)
                .append(this.anchor, rhs.anchor).isEquals();
    }

    /**
     * Hash code.
     *
     * @return the int
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(1313733113, -431360889).append(this.parameters.keySet())
                .append(this.parameters.values().toArray()).append(this.url).append(this.anchor).toHashCode();
    }
}
