package org.displaytag.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * <p>Object representing an URI (the href parameter of an &lt;a&gt; tag)</p>
 * <p>Provides methods to insert new parameters. It doesn't support multiple parameter values</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class Href
{

    /**
     * Field mBaseUrl
     */
    private String mBaseUrl;

    /**
     * Field mParameters
     */
    private HashMap mParameters;

    /**
     * Constructor for Href
     * @param pBaseUrl String
     */
    public Href(String pBaseUrl)
    {
        mParameters = new HashMap();

        if (pBaseUrl.indexOf("?") == -1)
        {
            // simple url, no parameters
            mBaseUrl = pBaseUrl;
        }
        else
        {
            // the Url already has parameters, put them in the parameter Map
            StringTokenizer lTokenizer = new StringTokenizer(pBaseUrl, "?");

            // base url (before "?")
            mBaseUrl = lTokenizer.nextToken();

            if (lTokenizer.hasMoreTokens())
            {

                StringTokenizer lParamTokenizer = new StringTokenizer(lTokenizer.nextToken(), "&");

                // split parameters (key=value)
                while (lParamTokenizer.hasMoreTokens())
                {

                    // split key and value ...
                    String[] lKeyValue = StringUtils.split(lParamTokenizer.nextToken(), "=");

                    // ... and add it to the map
                    // ... but remember to encode name/value to prevent css
                    mParameters.put(
                        StringEscapeUtils.escapeHtml(lKeyValue[0]),
                        StringEscapeUtils.escapeHtml(lKeyValue[1]));

                }
            }

        }
    }

    /**
     * Constructor for Href
     * @param pHref Href
     */
    public Href(Href pHref)
    {
        mBaseUrl = pHref.getBaseUrl();
        mParameters = pHref.getParameterMap();
    }

    /**
     * Method addParameter
     * @param pName String
     * @param pValue Object
     */
    public void addParameter(String pName, Object pValue)
    {
        mParameters.put(pName, pValue);
    }

    /**
     * Method addParameter
     * @param pName String
     * @param pValue int
     */
    public void addParameter(String pName, int pValue)
    {
        mParameters.put(pName, new Integer(pValue));
    }

    /**
     * Method getParameterMap
     * @return HashMap
     */
    public HashMap getParameterMap()
    {
        return (HashMap) mParameters.clone();
    }

    /**
     * Sets the parameters in the Href. The value in the given Map will be escaped before added
     * @param parametersMap Map containing parameters
     */
    public void setParameterMap(Map parametersMap)
    {
        // create a new HashMap
        mParameters = new HashMap(parametersMap.size());

        // copy value, escaping html
        Iterator mapIterator = parametersMap.entrySet().iterator();
        while (mapIterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) mapIterator.next();
            String key = StringEscapeUtils.escapeHtml((String) entry.getKey());
            String value = StringEscapeUtils.escapeHtml((String) entry.getValue());
            mParameters.put(key, value);
        }
    }

    /**
     * Method getBaseUrl
     * @return String
     */
    public String getBaseUrl()
    {
        return mBaseUrl;
    }

    /**
     * toString: output the full url with parameters
     * @return String
     */
    public String toString()
    {

        // no parameters? simply return the base Url
        if (mParameters.size() == 0)
        {
            return mBaseUrl;
        }

        StringBuffer lBuffer = new StringBuffer(30);
        lBuffer.append(mBaseUrl).append('?');
        Set lParameterSet = mParameters.entrySet();

        Iterator lIterator = lParameterSet.iterator();

        while (lIterator.hasNext())
        {
            Map.Entry lEntry = (Map.Entry) lIterator.next();
            lBuffer.append(lEntry.getKey()).append('=').append(lEntry.getValue());
            if (lIterator.hasNext())
            {
                lBuffer.append(TagConstants.AMPERSAND);
            }
        }

        return lBuffer.toString();
    }

}