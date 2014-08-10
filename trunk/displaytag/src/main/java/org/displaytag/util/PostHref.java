package org.displaytag.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


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
    @Override
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
    @Override
    public Href addParameter(String name, int value)
    {
        this.parent.addParameter(name, value);
        return this;
    }

    /**
     * @param parametersMap
     * @see org.displaytag.util.Href#addParameterMap(java.util.Map)
     */
    @Override
    public void addParameterMap(Map<String, Object> parametersMap)
    {
        this.parent.addParameterMap(parametersMap);
    }

    /**
     * @param object
     * @return
     * @see org.displaytag.util.Href#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object)
    {
        return this.parent.equals(object);
    }

    /**
     * @return
     * @see org.displaytag.util.Href#getAnchor()
     */
    @Override
    public String getAnchor()
    {
        return this.parent.getAnchor();
    }

    /**
     * @return
     * @see org.displaytag.util.Href#getBaseUrl()
     */
    @Override
    public String getBaseUrl()
    {
        return this.parent.getBaseUrl();
    }

    /**
     * @return
     * @see org.displaytag.util.Href#getParameterMap()
     */
    @Override
    public Map<String, Object> getParameterMap()
    {
        return this.parent.getParameterMap();
    }

    /**
     * @param name
     * @see org.displaytag.util.Href#removeParameter(java.lang.String)
     */
    @Override
    public void removeParameter(String name)
    {
        this.parent.removeParameter(name);
    }

    /**
     * @param name
     * @see org.displaytag.util.Href#setAnchor(java.lang.String)
     */
    @Override
    public void setAnchor(String name)
    {
        this.parent.setAnchor(name);
    }

    /**
     * @param url
     * @see org.displaytag.util.Href#setFullUrl(java.lang.String)
     */
    @Override
    public void setFullUrl(String url)
    {
        this.parent.setFullUrl(url);
    }

    /**
     * @param parametersMap
     * @see org.displaytag.util.Href#setParameterMap(java.util.Map)
     */
    @Override
    public void setParameterMap(Map<String, Object> parametersMap)
    {
        this.parent.setParameterMap(parametersMap);
    }

    /**
     * @return
     * @see org.displaytag.util.Href#toString()
     */
    @Override
    public String toString()
    {

        StringBuffer buffer = new StringBuffer(30);

        buffer.append("javascript:displaytagform('");
        buffer.append(this.form);
        buffer.append("',[");

        Map<String, Object> parameters = getParameterMap();

        Set parameterSet = parameters.entrySet();

        Iterator<Map.Entry<Object, Object>> iterator = parameterSet.iterator();

        while (iterator.hasNext())
        {
            // {f:'param1',v:'1'},
            Map.Entry<Object, Object> entry = iterator.next();

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
            String param = URLDecoder.decode(value != null ? value.toString() : StringUtils.EMPTY, "utf-8");
            param = StringUtils.replace(param, "'", "\\'");
            param = StringUtils.replace(param, "\"", "%22");
            return param;
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone()
    {
        final PostHref href;
        try
        {
            href = (PostHref) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        }
        href.parent = (Href) parent.clone();

        return href;
    }

}
