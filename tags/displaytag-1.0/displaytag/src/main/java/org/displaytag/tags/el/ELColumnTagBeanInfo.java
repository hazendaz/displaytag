/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */
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
            proplist.add(new PropertyDescriptor("autolink", //$NON-NLS-1$
                ELColumnTag.class, null, "setAutolink")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("class", //$NON-NLS-1$
                ELColumnTag.class, null, "setClass")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("decorator", //$NON-NLS-1$
                ELColumnTag.class, null, "setDecorator")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("group", //$NON-NLS-1$
                ELColumnTag.class, null, "setGroup")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("headerClass", //$NON-NLS-1$
                ELColumnTag.class, null, "setHeaderClass")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("href", //$NON-NLS-1$
                ELColumnTag.class, null, "setHref")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("maxLength", //$NON-NLS-1$
                ELColumnTag.class, null, "setMaxLength")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("maxWords", //$NON-NLS-1$
                ELColumnTag.class, null, "setMaxWords")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("media", //$NON-NLS-1$
                ELColumnTag.class, null, "setMedia")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("nulls", //$NON-NLS-1$
                ELColumnTag.class, null, "setNulls")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("paramId", //$NON-NLS-1$
                ELColumnTag.class, null, "setParamId")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("paramName", //$NON-NLS-1$
                ELColumnTag.class, null, "setParamName")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("paramProperty", //$NON-NLS-1$
                ELColumnTag.class, null, "setParamProperty")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("paramScope", //$NON-NLS-1$
                ELColumnTag.class, null, "setParamScope")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("property", //$NON-NLS-1$
                ELColumnTag.class, null, "setProperty")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("sortable", //$NON-NLS-1$
                ELColumnTag.class, null, "setSortable")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("style", //$NON-NLS-1$
                ELColumnTag.class, null, "setStyle")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("title", //$NON-NLS-1$
                ELColumnTag.class, null, "setTitle")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("titleKey", //$NON-NLS-1$
                ELColumnTag.class, null, "setTitleKey")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("url", //$NON-NLS-1$
                ELColumnTag.class, null, "setUrl")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("sortProperty", //$NON-NLS-1$
                ELColumnTag.class, null, "setSortProperty")); //$NON-NLS-1$ 

            // deprecated attribute
            proplist.add(new PropertyDescriptor("sort", //$NON-NLS-1$
                ELColumnTag.class, null, "setSortable")); // map //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("styleClass", //$NON-NLS-1$
                ELColumnTag.class, null, "setStyle")); // map //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("headerStyleClass", //$NON-NLS-1$
                ELColumnTag.class, null, "setHeaderClass")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("width", //$NON-NLS-1$
                ColumnTag.class, null, "setWidth")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("align", //$NON-NLS-1$
                ColumnTag.class, null, "setAlign")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("background", //$NON-NLS-1$
                ColumnTag.class, null, "setBackground")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("bgcolor", //$NON-NLS-1$
                ColumnTag.class, null, "setBgcolor")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("height", //$NON-NLS-1$
                ColumnTag.class, null, "setHeight")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("nowrap", //$NON-NLS-1$
                ColumnTag.class, null, "setNowrap")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("valign", //$NON-NLS-1$
                ColumnTag.class, null, "setValign")); //$NON-NLS-1$ 

        }
        catch (IntrospectionException ex)
        {
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }

}