package org.displaytag.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;


/**
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class PostHref implements Href
{

    /**
     * D1597A17A6.
     */
    private static final long serialVersionUID = 899149338534L;

    private Href parent;

    private String form;

    public PostHref(Href parent, String form)
    {
        this.parent = parent;
        this.form = form;
    }

    /**
     * @param name
     * @param value
     * @return
     * @see org.displaytag.util.Href#addParameter(java.lang.String, java.lang.Object)
     */
    public Href addParameter(String name, Object value)
    {
        this.parent.addParameter(name, value);
        return this;
    }

    /**
     * @param name
     * @param value
     * @return
     * @see org.displaytag.util.Href#addParameter(java.lang.String, int)
     */
    public Href addParameter(String name, int value)
    {
        this.parent.addParameter(name, value);
        return this;
    }

    /**
     * @param parametersMap
     * @see org.displaytag.util.Href#addParameterMap(java.util.Map)
     */
    public void addParameterMap(Map parametersMap)
    {
        this.parent.addParameterMap(parametersMap);
    }

    /**
     * @param object
     * @return
     * @see org.displaytag.util.Href#equals(java.lang.Object)
     */
    public boolean equals(Object object)
    {
        return this.parent.equals(object);
    }

    /**
     * @return
     * @see org.displaytag.util.Href#getAnchor()
     */
    public String getAnchor()
    {
        return this.parent.getAnchor();
    }

    /**
     * @return
     * @see org.displaytag.util.Href#getBaseUrl()
     */
    public String getBaseUrl()
    {
        return this.parent.getBaseUrl();
    }

    /**
     * @return
     * @see org.displaytag.util.Href#getParameterMap()
     */
    public Map getParameterMap()
    {
        return this.parent.getParameterMap();
    }

    /**
     * @param name
     * @see org.displaytag.util.Href#removeParameter(java.lang.String)
     */
    public void removeParameter(String name)
    {
        this.parent.removeParameter(name);
    }

    /**
     * @param name
     * @see org.displaytag.util.Href#setAnchor(java.lang.String)
     */
    public void setAnchor(String name)
    {
        this.parent.setAnchor(name);
    }

    /**
     * @param url
     * @see org.displaytag.util.Href#setFullUrl(java.lang.String)
     */
    public void setFullUrl(String url)
    {
        this.parent.setFullUrl(url);
    }

    /**
     * @param parametersMap
     * @see org.displaytag.util.Href#setParameterMap(java.util.Map)
     */
    public void setParameterMap(Map parametersMap)
    {
        this.parent.setParameterMap(parametersMap);
    }

    /**
     * @return
     * @see org.displaytag.util.Href#toString()
     */
    public String toString()
    {

        StringBuffer buffer = new StringBuffer(30);

        buffer.append("javascript:displaytagform('");
        buffer.append(this.form);
        buffer.append("',[");

        Map parameters = getParameterMap();

        Set parameterSet = parameters.entrySet();

        Iterator iterator = parameterSet.iterator();

        while (iterator.hasNext())
        {
            // {f:'param1',v:'1'},
            Map.Entry entry = (Map.Entry) iterator.next();

            Object key = entry.getKey();
            Object value = entry.getValue();

            buffer.append("{f:'");
            buffer.append(esc(key));
            buffer.append("',v:");

            if (value != null && value.getClass().isArray())
            {
                Object[] values = (Object[]) value;

                buffer.append("[");
                for (int i = 0; i < values.length; i++)
                {
                    if (i > 0)
                    {
                        buffer.append(",");
                    }

                    buffer.append("'");
                    buffer.append(esc(values[i]));
                    buffer.append("'");
                }
                buffer.append("]");
            }
            else
            {
                buffer.append("'");
                buffer.append(esc(value));
                buffer.append("'");
            }

            buffer.append("}");

            if (iterator.hasNext())
            {
                buffer.append(",");
            }
        }

        buffer.append("])");
        return buffer.toString();
    }

    private String esc(Object value)
    {

        try
        {
            String param = URLDecoder.decode(ObjectUtils.toString(value), "utf-8");
            param = StringUtils.replace(param, "'", "\\'");
            param = StringUtils.replace(param, "\"", "%22");
            return param;
        }
        catch (UnsupportedEncodingException e)
        {
            throw new UnhandledException(e);
        }
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone()
    {
        final PostHref href;
        try
        {
            href = (PostHref) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new UnhandledException(e);
        }
        href.parent = (Href) parent.clone();

        return href;
    }

}
