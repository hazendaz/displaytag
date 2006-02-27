package org.displaytag.tags.el;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;


/**
 * BeanInfo descriptor for the <code>ELCaptionTag</code> class. Unevaluated EL expression has to be kept separately
 * from the evaluated value, since the JSP compiler can choose to reuse different tag instances if they received the
 * same original attribute values, and the JSP compiler can choose to not re-call the setter methods.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELCaptionTagBeanInfo extends SimpleBeanInfo
{

    /**
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        List proplist = new ArrayList();

        try
        {
            proplist.add(new PropertyDescriptor("class", ELCaptionTag.class, null, "setClass"));
            proplist.add(new PropertyDescriptor("dir", ELCaptionTag.class, null, "setDir"));
            proplist.add(new PropertyDescriptor("id", ELCaptionTag.class, null, "setId"));
            proplist.add(new PropertyDescriptor("lang", ELCaptionTag.class, null, "setLang"));
            proplist.add(new PropertyDescriptor("style", ELCaptionTag.class, null, "setStyle"));
            proplist.add(new PropertyDescriptor("title", ELCaptionTag.class, null, "setTitle"));
        }
        catch (IntrospectionException ex)
        {
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }

}