package org.displaytag.tags.el;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;


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
        ArrayList proplist = new ArrayList();

        try
        {
            // @todo check attributes in ELColumnTag and fix others
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
            proplist.add(new PropertyDescriptor("property", ELColumnTag.class, null, "setProperty"));
            proplist.add(new PropertyDescriptor("sortable", ELColumnTag.class, null, "setSortable"));
            proplist.add(new PropertyDescriptor("style", ELColumnTag.class, null, "setStyle"));
            proplist.add(new PropertyDescriptor("title", ELColumnTag.class, null, "setTitle"));
        }
        catch (IntrospectionException ex)
        {
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }

}