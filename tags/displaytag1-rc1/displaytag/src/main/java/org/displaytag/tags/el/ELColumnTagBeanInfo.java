package org.displaytag.tags.el;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

import org.displaytag.tags.ColumnTag;


/**
 * BeanInfo descriptor for the <code>ELColumnTag</code> class. Unevaluated EL expression has to be kept separately
 * from the evaluated value, since the JSP compiler can choose to reuse different tag instances if they received the
 * same original attribute values, and the JSP compiler can choose to not re-call the setter methods.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELColumnTagBeanInfo extends SimpleBeanInfo
{

    /**
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        List proplist = new ArrayList();

        try
        {
            proplist.add(new PropertyDescriptor("autolink", ELColumnTag.class, null, "setAutolink"));
            proplist.add(new PropertyDescriptor("class", ELColumnTag.class, null, "setClass"));
            proplist.add(new PropertyDescriptor("decorator", ELColumnTag.class, null, "setDecorator"));
            proplist.add(new PropertyDescriptor("group", ELColumnTag.class, null, "setGroup"));
            proplist.add(new PropertyDescriptor("headerClass", ELColumnTag.class, null, "setHeaderClass"));
            proplist.add(new PropertyDescriptor("href", ELColumnTag.class, null, "setHref"));
            proplist.add(new PropertyDescriptor("maxLength", ELColumnTag.class, null, "setMaxLength"));
            proplist.add(new PropertyDescriptor("maxWords", ELColumnTag.class, null, "setMaxWords"));
            proplist.add(new PropertyDescriptor("media", ELColumnTag.class, null, "setMedia"));
            proplist.add(new PropertyDescriptor("nulls", ELColumnTag.class, null, "setNulls"));
            proplist.add(new PropertyDescriptor("paramId", ELColumnTag.class, null, "setParamId"));
            proplist.add(new PropertyDescriptor("paramName", ELColumnTag.class, null, "setParamName"));
            proplist.add(new PropertyDescriptor("paramProperty", ELColumnTag.class, null, "setParamProperty"));
            proplist.add(new PropertyDescriptor("paramScope", ELColumnTag.class, null, "setParamScope"));
            proplist.add(new PropertyDescriptor("property", ELColumnTag.class, null, "setProperty"));
            proplist.add(new PropertyDescriptor("sortable", ELColumnTag.class, null, "setSortable"));
            proplist.add(new PropertyDescriptor("style", ELColumnTag.class, null, "setStyle"));
            proplist.add(new PropertyDescriptor("title", ELColumnTag.class, null, "setTitle"));

            // EL only
            proplist.add(new PropertyDescriptor("titleKey", ELColumnTag.class, null, "setTitleKey"));
            proplist.add(new PropertyDescriptor("url", ELColumnTag.class, null, "setUrl"));

            // deprecated attribute
            proplist.add(new PropertyDescriptor("sort", ELColumnTag.class, null, "setSortable")); // map
            proplist.add(new PropertyDescriptor("styleClass", ELColumnTag.class, null, "setStyle")); // map
            proplist.add(new PropertyDescriptor("headerStyleClass", ELColumnTag.class, null, "setHeaderClass")); // map
            proplist.add(new PropertyDescriptor("width", ColumnTag.class, null, "setWidth"));
            proplist.add(new PropertyDescriptor("align", ColumnTag.class, null, "setAlign"));
            proplist.add(new PropertyDescriptor("background", ColumnTag.class, null, "setBackground"));
            proplist.add(new PropertyDescriptor("bgcolor", ColumnTag.class, null, "setBgcolor"));
            proplist.add(new PropertyDescriptor("height", ColumnTag.class, null, "setHeight"));
            proplist.add(new PropertyDescriptor("nowrap", ColumnTag.class, null, "setNowrap"));
            proplist.add(new PropertyDescriptor("valign", ColumnTag.class, null, "setValign"));

        }
        catch (IntrospectionException ex)
        {
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }

}