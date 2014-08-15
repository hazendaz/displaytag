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

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class DefaultHref implements Href
{

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
     * @param baseUrl String
     */
    public DefaultHref(String baseUrl)
    {
        this.parameters = new LinkedHashMap<String, String[]>();
        setFullUrl(baseUrl);
    }

    /**
     * @see org.displaytag.util.Href#setFullUrl(java.lang.String)
     */
    @Override
    public void setFullUrl(String baseUrl)
    {
        this.url = null;
        this.anchor = null;
        String noAnchorUrl;

        int anchorposition = baseUrl.indexOf('#');

        // extract anchor from url
        if (anchorposition != -1)
        {
            noAnchorUrl = baseUrl.substring(0, anchorposition);
            this.anchor = baseUrl.substring(anchorposition + 1);
        }
        else
        {
            noAnchorUrl = baseUrl;
        }

        if (noAnchorUrl.indexOf('?') == -1)
        {
            // simple url, no parameters
            this.url = noAnchorUrl;
            return;
        }

        // the Url already has parameters, put them in the parameter Map
        StringTokenizer tokenizer = new StringTokenizer(noAnchorUrl, "?"); //$NON-NLS-1$

        if (baseUrl.startsWith("?")) //$NON-NLS-1$
        {
            // support fake URI's which are just parameters to use with the current uri
            url = TagConstants.EMPTY_STRING;
        }
        else
        {
            // base url (before "?")
            url = tokenizer.nextToken();
        }

        if (!tokenizer.hasMoreTokens())
        {
            return;
        }

        // process parameters
        StringTokenizer paramTokenizer = new StringTokenizer(tokenizer.nextToken(), "&"); //$NON-NLS-1$

        // split parameters (key=value)
        while (paramTokenizer.hasMoreTokens())
        {
            // split key and value ...
            String[] keyValue = StringUtils.split(paramTokenizer.nextToken(), '=');

            // encode name/value to prevent css
            String decodedkey = decodeParam(keyValue[0]);
            String decodedvalue = decodeParam(keyValue.length > 1 ? keyValue[1] : StringUtils.EMPTY);

            if (!this.parameters.containsKey(decodedkey))
            {
                // ... and add it to the map
                this.parameters.put(decodedkey, new String[]{decodedvalue});
            }
            else
            {
                // additional value for an existing parameter
                String[] previousValue = this.parameters.get(decodedkey);

                String[] newArray = new String[previousValue.length + 1];

                int j;

                for (j = 0; j < previousValue.length; j++)
                {
                    newArray[j] = previousValue[j];
                }

                newArray[j] = decodedvalue;
                this.parameters.put(decodedkey, newArray);

            }
        }
    }

    /**
     * Adds a parameter to the href.
     * @param key String
     * @param value Object
     * @return this Href instance, useful for concatenation.
     */
    @Override
    public Href addParameter(String key, Object value)
    {
        this.parameters.put(decodeParam(key), new String[]{decodeParam(value)});
        return this;
    }

    /**
     * Removes a parameter from the href.
     * @param name String
     */
    @Override
    public void removeParameter(String key)
    {
        this.parameters.remove(decodeParam(key));
    }

    /**
     * Adds an int parameter to the href.
     * @param key String
     * @param value int
     * @return this Href instance, useful for concatenation.
     */
    @Override
    public Href addParameter(String key, int value)
    {
        this.parameters.put(decodeParam(key), new String[]{Integer.toString(value)});
        return this;
    }

    /**
     * Getter for the map containing link parameters. The returned map is always a copy and not the original instance.
     * @return parameter Map (copy)
     */
    @Override
    public Map<String, String[]> getParameterMap()
    {
        Map<String, String[]> copyMap = new LinkedHashMap<String, String[]>(this.parameters.size());
        copyMap.putAll(this.parameters);
        return copyMap;
    }

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Any parameter already present in the href object is removed.
     * @param parametersMap Map containing parameters
     */
    @Override
    public void setParameterMap(Map<String, String[]> parametersMap)
    {
        // create a new HashMap
        this.parameters = new HashMap<String, String[]>(parametersMap.size());

        // copy the parameters
        addParameterMap(parametersMap);
    }

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Parameters in the original href are kept and not overridden.
     * @param parametersMap Map containing parameters
     */
    @Override
    public void addParameterMap(Map<String, String[]> parametersMap)
    {
        // handle nulls
        if (parametersMap == null)
        {
            return;
        }

        // copy value, escaping html
        Iterator<Entry<String, String[]>> mapIterator = parametersMap.entrySet().iterator();
        while (mapIterator.hasNext())
        {
            Entry<String, String[]> entry = mapIterator.next();
            String key = decodeParam(entry.getKey());

            // don't overwrite parameters
            if (!this.parameters.containsKey(key))
            {
                String[] value = entry.getValue();

                if (value != null)
                {
                    String[] values;
                    // check mantained for binary compatibility with displaytag 1.2
                    if (value.getClass().isArray())
                    {
                        values = value;
                        for (int i = 0; i < values.length; i++)
                        {
                            values[i] = decodeParam(values[i]);
                        }
                    }
                    else
                    {
                        values = new String[]{decodeParam(value)};
                    }

                    this.parameters.put(key, values);
                }
                else
                {
                    this.parameters.put(key, new String[0]);
                }
            }
        }
    }

    /**
     * Getter for the base url (without parameters).
     * @return String
     */
    @Override
    public String getBaseUrl()
    {
        return this.url;
    }

    /**
     * Returns the URI anchor.
     * @return anchor or <code>null</code> if no anchor has been set.
     */
    @Override
    public String getAnchor()
    {
        return this.anchor;
    }

    /**
     * Setter for the URI anchor.
     * @param name string to be used as anchor name (without #).
     */
    @Override
    public void setAnchor(String name)
    {
        this.anchor = name;
    }

    /**
     * toString: output the full url with parameters.
     * @return String
     */
    @Override
    public String toString()
    {
        StringBuffer buffer = new StringBuffer(30);

        buffer.append(this.url);

        if (this.parameters.size() > 0)
        {
            buffer.append('?');
            Set<Entry<String, String[]>> parameterSet = this.parameters.entrySet();

            Iterator<Entry<String, String[]>> iterator = parameterSet.iterator();

            while (iterator.hasNext())
            {
                Entry<String, String[]> entry = iterator.next();

                Object key = entry.getKey();
                Object value = entry.getValue();

                if (value == null)
                {
                    buffer.append(encodeParam(key)).append('='); // no value
                }
                else if (value.getClass().isArray())
                {
                    Object[] values = (Object[]) value;
                    if (values.length == 0)
                    {
                        buffer.append(encodeParam(key)).append('='); // no value
                    }
                    else
                    {
                        for (int i = 0; i < values.length; i++)
                        {
                            if (i > 0)
                            {
                                buffer.append(TagConstants.AMPERSAND);
                            }

                            buffer.append(encodeParam(key)).append('=').append(encodeParam(values[i]));
                        }
                    }
                }
                else
                {
                    buffer.append(encodeParam(key)).append('=').append(encodeParam(value));
                }

                if (iterator.hasNext())
                {
                    buffer.append(TagConstants.AMPERSAND);
                }
            }
        }

        if (this.anchor != null)
        {
            buffer.append('#');
            buffer.append(this.anchor);
        }

        return buffer.toString();
    }

    private String encodeParam(Object param)
    {
        if (param == null)
        {
            return StringUtils.EMPTY;
        }
        try
        {
            return URLEncoder.encode(param.toString(), "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            // should never happen
            throw new RuntimeException(e);
        }
    }

    private String decodeParam(Object param)
    {
        if (param == null)
        {
            return StringUtils.EMPTY;
        }
        try
        {
            return URLDecoder.decode(param.toString(), "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            // should never happen
            throw new RuntimeException(e);
        }
    }

    /**
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone()
    {
        final DefaultHref href;
        try
        {
            href = (DefaultHref) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e); // should never happen
        }

        href.parameters = new LinkedHashMap<String, String[]>(this.parameters);
        return href;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof DefaultHref))
        {
            return false;
        }
        DefaultHref rhs = (DefaultHref) object;

        // "parameters" can't be added directly, since equals on HashMap doesn't return true with equal key/values
        return new EqualsBuilder()
            .append(this.parameters.keySet(), rhs.parameters.keySet())
            .append(this.parameters.values().toArray(), rhs.parameters.values().toArray())
            .append(this.url, rhs.url)
            .append(this.anchor, rhs.anchor)
            .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(1313733113, -431360889)
            .append(this.parameters.keySet())
            .append(this.parameters.values().toArray())
            .append(this.url)
            .append(this.anchor)
            .toHashCode();
    }
}
