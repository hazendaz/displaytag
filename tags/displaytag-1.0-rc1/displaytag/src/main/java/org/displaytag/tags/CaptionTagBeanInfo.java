package org.displaytag.tags;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;


/**
 * Needed to make the "class" tag attribute working.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 * @see org.displaytag.util.BeanInfoUtil
 */
public class CaptionTagBeanInfo extends SimpleBeanInfo
{

    /**
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        List proplist = new ArrayList();

        try
        {
            proplist.add(new PropertyDescriptor("class", CaptionTag.class, null, "setClass"));
            proplist.add(new PropertyDescriptor("dir", CaptionTag.class, null, "setDir"));
            proplist.add(new PropertyDescriptor("id", CaptionTag.class, null, "setId"));
            proplist.add(new PropertyDescriptor("lang", CaptionTag.class, null, "setLang"));
            proplist.add(new PropertyDescriptor("style", CaptionTag.class, null, "setStyle"));
            proplist.add(new PropertyDescriptor("title", CaptionTag.class, null, "setTitle"));
        }
        catch (IntrospectionException ex)
        {
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }

}