package org.displaytag.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Object representing an URI (the href parameter of an &lt;a> tag). Provides methods to insert new parameters. It
 * doesn't support multiple parameter values
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class Href
{

    /**
     * Base url for the href.
     */
    private String url;

    /**
     * Url parameters.
     */
    private Map parameters;

    /**
     * Construct a new Href parsing a URL. Parameters are stripped from the base url and saved in the parameters map.
     * @param baseUrl String
     */
    public Href(String baseUrl)
    {
        this.parameters = new HashMap();

        if (baseUrl.indexOf("?") == -1)
        {
            // simple url, no parameters
            this.url = baseUrl;
        }
        else
        {
            // the Url already has parameters, put them in the parameter Map
            StringTokenizer tokenizer = new StringTokenizer(baseUrl, "?");

            // base url (before "?")
            this.url = tokenizer.nextToken();

            if (tokenizer.hasMoreTokens())
            {

                StringTokenizer paramTokenizer = new StringTokenizer(tokenizer.nextToken(), "&");

                // split parameters (key=value)
                while (paramTokenizer.hasMoreTokens())
                {

                    // split key and value ...
                    String[] keyValue = StringUtils.split(paramTokenizer.nextToken(), "=");

                    // ... and add it to the map
                    // ... but remember to encode name/value to prevent css
                    this.parameters.put(
                        StringEscapeUtils.escapeHtml(keyValue[0]),
                        StringEscapeUtils.escapeHtml(keyValue[1]));

                }
            }

        }
    }

    /**
     * Constructor for Href.
     * @param href Href
     */
    public Href(Href href)
    {
        this.url = href.getBaseUrl();
        this.parameters = href.getParameterMap();
    }

    /**
     * Adds a parameter to the href.
     * @param name String
     * @param value Object
     */
    public void addParameter(String name, Object value)
    {
        this.parameters.put(name, value);
    }

    /**
     * Adds an int parameter to the href.
     * @param name String
     * @param value int
     */
    public void addParameter(String name, int value)
    {
        this.parameters.put(name, new Integer(value));
    }

    /**
     * Getter for the map containing link parameters. The returned map is always a copy and not the original instance.
     * @return parameter Map (copy)
     */
    public Map getParameterMap()
    {
        Map copyMap = new HashMap(this.parameters.size());
        copyMap.putAll(this.parameters);
        return copyMap;
    }

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Any parameter already present in the href object is removed.
     * @param parametersMap Map containing parameters
     */
    public void setParameterMap(Map parametersMap)
    {
        // create a new HashMap
        this.parameters = new HashMap(parametersMap.size());

        // copy the parameters
        addParameterMap(parametersMap);
    }

    /**
     * Adds all the parameters contained in the map to the Href. The value in the given Map will be escaped before
     * added. Parameters in the original href are kept and not overridden.
     * @param parametersMap Map containing parameters
     */
    public void addParameterMap(Map parametersMap)
    {
        // copy value, escaping html
        Iterator mapIterator = parametersMap.entrySet().iterator();
        while (mapIterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) mapIterator.next();
            String key = StringEscapeUtils.escapeHtml((String) entry.getKey());

            // don't overwrite parameters
            if (!this.parameters.containsKey(key))
            {
                String value = StringEscapeUtils.escapeHtml((String) entry.getValue());
                this.parameters.put(key, value);
            }
        }
    }

    /**
     * Getter for the base url (without parameters).
     * @return String
     */
    public String getBaseUrl()
    {
        return this.url;
    }

    /**
     * toString: output the full url with parameters.
     * @return String
     */
    public String toString()
    {

        // no parameters? simply return the base Url
        if (this.parameters.size() == 0)
        {
            return this.url;
        }

        StringBuffer buffer = new StringBuffer(30);
        buffer.append(this.url).append('?');
        Set parameterSet = this.parameters.entrySet();

        Iterator iterator = parameterSet.iterator();

        while (iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            buffer.append(entry.getKey()).append('=').append(entry.getValue());
            if (iterator.hasNext())
            {
                buffer.append(TagConstants.AMPERSAND);
            }
        }

        return buffer.toString();
    }

}