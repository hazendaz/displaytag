package org.displaytag.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Object representing an URI (the href parameter of an &lt;a&gt; tag)
 * Provides methods to insert new parameters. It doesn't support multiple parameter values
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class Href
{

    /**
     * Field mBaseUrl
     */
    private String url;

    /**
     * Field mParameters
     */
    private HashMap parameters;

    /**
     * Constructor for Href
     * @param baseUrl String
     */
    public Href(String baseUrl)
    {
        parameters = new HashMap();

        if (baseUrl.indexOf("?") == -1)
        {
            // simple url, no parameters
            url = baseUrl;
        }
        else
        {
            // the Url already has parameters, put them in the parameter Map
            StringTokenizer tokenizer = new StringTokenizer(baseUrl, "?");

            // base url (before "?")
            url = tokenizer.nextToken();

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
                    parameters.put(
                        StringEscapeUtils.escapeHtml(keyValue[0]),
                        StringEscapeUtils.escapeHtml(keyValue[1]));

                }
            }

        }
    }

    /**
     * Constructor for Href
     * @param href Href
     */
    public Href(Href href)
    {
        url = href.getBaseUrl();
        parameters = href.getParameterMap();
    }

    /**
     * Method addParameter
     * @param name String
     * @param value Object
     */
    public void addParameter(String name, Object value)
    {
        parameters.put(name, value);
    }

    /**
     * Method addParameter
     * @param name String
     * @param value int
     */
    public void addParameter(String name, int value)
    {
        parameters.put(name, new Integer(value));
    }

    /**
     * Method getParameterMap
     * @return HashMap
     */
    public HashMap getParameterMap()
    {
        return (HashMap) parameters.clone();
    }

    /**
     * Sets the parameters in the Href. The value in the given Map will be escaped before added.
     * Any parameter already in the map is removed.
     * @param parametersMap Map containing parameters
     */
    public void setParameterMap(Map parametersMap)
    {
        // create a new HashMap
        parameters = new HashMap(parametersMap.size());

        // copy the parameters
        addParameterMap(parametersMap);
    }

    /**
    * Sets the parameters in the Href. The value in the given Map will be escaped before added.
    * Parameters in the original href are keeped.
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
            String value = StringEscapeUtils.escapeHtml((String) entry.getValue());
            parameters.put(key, value);
        }
    }

    /**
     * Method getBaseUrl
     * @return String
     */
    public String getBaseUrl()
    {
        return url;
    }

    /**
     * toString: output the full url with parameters
     * @return String
     */
    public String toString()
    {

        // no parameters? simply return the base Url
        if (parameters.size() == 0)
        {
            return url;
        }

        StringBuffer buffer = new StringBuffer(30);
        buffer.append(url).append('?');
        Set parameterSet = parameters.entrySet();

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