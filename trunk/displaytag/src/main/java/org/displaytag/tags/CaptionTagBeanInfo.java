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
            proplist.add(new PropertyDescriptor("class", //$NON-NLS-1$
                CaptionTag.class, null, "setClass")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("dir", //$NON-NLS-1$
                CaptionTag.class, null, "setDir")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("id", //$NON-NLS-1$
                CaptionTag.class, null, "setId")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("lang", //$NON-NLS-1$
                CaptionTag.class, null, "setLang")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("style", //$NON-NLS-1$
                CaptionTag.class, null, "setStyle")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("title", //$NON-NLS-1$
                CaptionTag.class, null, "setTitle")); //$NON-NLS-1$ 

            // make ATG Dynamo happy:
            // Attribute "className" of tag "caption" in taglib descriptor file displaytag-11.tld" must have a
            // corresponding property in class "org.displaytag.tags.CaptionTag" with a public setter method
            proplist.add(new PropertyDescriptor("className", //$NON-NLS-1$
                CaptionTag.class, null, "setClass")); //$NON-NLS-1$ 
        }
        catch (IntrospectionException ex)
        {
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }

}