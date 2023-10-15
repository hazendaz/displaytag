/*
 * Copyright (C) 2002-2025 Fabrizio Giustina, the Displaytag team
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class PostHref.
 */
public class PostHref implements Href {

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = 899149338534L;

    /** The parent. */
    private Href parent;

    /** The form. */
    private final String form;

    /**
     * Instantiates a new post href.
     *
     * @param parent
     *            the parent
     * @param form
     *            the form
     */
    public PostHref(final Href parent, final String form) {
        this.parent = parent;
        this.form = form;
    }

    /**
     * Adds the parameter.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     *
     * @return the href
     *
     * @see org.displaytag.util.Href#addParameter(java.lang.String, java.lang.Object)
     */
    @Override
    public Href addParameter(final String name, final Object value) {
        this.parent.addParameter(name, value);
        return this;
    }

    /**
     * Adds the parameter.
     *
     * @param name
     *            the name
     * @param value
     *            the value
     *
     * @return the href
     *
     * @see org.displaytag.util.Href#addParameter(java.lang.String, int)
     */
    @Override
    public Href addParameter(final String name, final int value) {
        this.parent.addParameter(name, value);
        return this;
    }

    /**
     * Adds the parameter map.
     *
     * @param parametersMap
     *            the parameters map
     *
     * @see org.displaytag.util.Href#addParameterMap(java.util.Map)
     */
    @Override
    public void addParameterMap(final Map<String, String[]> parametersMap) {
        this.parent.addParameterMap(parametersMap);
    }

    /**
     * Equals.
     *
     * @param object
     *            the object
     *
     * @return true, if successful
     *
     * @see org.displaytag.util.Href#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object object) {
        return this.parent.equals(object);
    }

    /**
     * Gets the anchor.
     *
     * @return the anchor
     *
     * @see org.displaytag.util.Href#getAnchor()
     */
    @Override
    public String getAnchor() {
        return this.parent.getAnchor();
    }

    /**
     * Gets the base url.
     *
     * @return the base url
     *
     * @see org.displaytag.util.Href#getBaseUrl()
     */
    @Override
    public String getBaseUrl() {
        return this.parent.getBaseUrl();
    }

    /**
     * Gets the parameter map.
     *
     * @return the parameter map
     *
     * @see org.displaytag.util.Href#getParameterMap()
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        return this.parent.getParameterMap();
    }

    /**
     * Removes the parameter.
     *
     * @param name
     *            the name
     *
     * @see org.displaytag.util.Href#removeParameter(java.lang.String)
     */
    @Override
    public void removeParameter(final String name) {
        this.parent.removeParameter(name);
    }

    /**
     * Sets the anchor.
     *
     * @param name
     *            the new anchor
     *
     * @see org.displaytag.util.Href#setAnchor(java.lang.String)
     */
    @Override
    public void setAnchor(final String name) {
        this.parent.setAnchor(name);
    }

    /**
     * Sets the full url.
     *
     * @param url
     *            the new full url
     *
     * @see org.displaytag.util.Href#setFullUrl(java.lang.String)
     */
    @Override
    public void setFullUrl(final String url) {
        this.parent.setFullUrl(url);
    }

    /**
     * Sets the parameter map.
     *
     * @param parametersMap
     *            the parameters map
     *
     * @see org.displaytag.util.Href#setParameterMap(java.util.Map)
     */
    @Override
    public void setParameterMap(final Map<String, String[]> parametersMap) {
        this.parent.setParameterMap(parametersMap);
    }

    /**
     * To string.
     *
     * @return the string
     *
     * @see org.displaytag.util.Href#toString()
     */
    @Override
    public String toString() {

        final StringBuilder buffer = new StringBuilder(30);

        buffer.append("javascript:displaytagform('");
        buffer.append(this.form);
        buffer.append("',[");

        final Map<String, String[]> parameters = this.getParameterMap();

        final Set<Entry<String, String[]>> parameterSet = parameters.entrySet();

        final Iterator<Entry<String, String[]>> iterator = parameterSet.iterator();

        while (iterator.hasNext()) {
            // {f:'param1',v:'1'},
            final Entry<String, String[]> entry = iterator.next();

            final Object key = entry.getKey();
            final Object value = entry.getValue();

            buffer.append("{f:'");
            buffer.append(this.esc(key));
            buffer.append("',v:");

            if (value != null && value.getClass().isArray()) {
                final Object[] values = (Object[]) value;

                if (values.length > 1) {
                    buffer.append("[");
                }
                for (int i = 0; i < values.length; i++) {
                    if (i > 0) {
                        buffer.append(",");
                    }

                    buffer.append("'");
                    buffer.append(this.esc(values[i]));
                    buffer.append("'");
                }
                if (values.length > 1) {
                    buffer.append("]");
                }
            } else {
                buffer.append("'");
                buffer.append(this.esc(value));
                buffer.append("'");
            }

            buffer.append("}");

            if (iterator.hasNext()) {
                buffer.append(",");
            }
        }

        buffer.append("])");
        return buffer.toString();
    }

    /**
     * Esc.
     *
     * @param value
     *            the value
     *
     * @return the string
     */
    private String esc(final Object value) {
        String param = URLDecoder.decode(value != null ? value.toString() : StringUtils.EMPTY, StandardCharsets.UTF_8);
        param = StringUtils.replace(param, "'", "\\'");
        return StringUtils.replace(param, "\"", "%22");
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
        final PostHref href;
        try {
            href = (PostHref) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        href.parent = (Href) this.parent.clone();

        return href;
    }

}
