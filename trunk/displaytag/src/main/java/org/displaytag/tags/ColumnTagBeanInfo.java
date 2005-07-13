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
package org.displaytag.tags;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;


/**
 * BeanInfo descriptor for the <code>ColumnTag</code> class..
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ColumnTagBeanInfo extends SimpleBeanInfo
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
                ColumnTag.class, null, "setAutolink")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("class", //$NON-NLS-1$
                ColumnTag.class, null, "setClass")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("decorator", //$NON-NLS-1$
                ColumnTag.class, null, "setDecorator")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("group", //$NON-NLS-1$
                ColumnTag.class, null, "setGroup")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("headerClass", //$NON-NLS-1$
                ColumnTag.class, null, "setHeaderClass")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("href", //$NON-NLS-1$
                ColumnTag.class, null, "setHref")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("maxLength", //$NON-NLS-1$
                ColumnTag.class, null, "setMaxLength")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("maxWords", //$NON-NLS-1$
                ColumnTag.class, null, "setMaxWords")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("media", //$NON-NLS-1$
                ColumnTag.class, null, "setMedia")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("nulls", //$NON-NLS-1$
                ColumnTag.class, null, "setNulls")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("paramId", //$NON-NLS-1$
                ColumnTag.class, null, "setParamId")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("paramName", //$NON-NLS-1$
                ColumnTag.class, null, "setParamName")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("paramProperty", //$NON-NLS-1$
                ColumnTag.class, null, "setParamProperty")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("paramScope", //$NON-NLS-1$
                ColumnTag.class, null, "setParamScope")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("property", //$NON-NLS-1$
                ColumnTag.class, null, "setProperty")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("sortable", //$NON-NLS-1$
                ColumnTag.class, null, "setSortable")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("sortName", //$NON-NLS-1$
                ColumnTag.class, null, "setSortName")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("style", //$NON-NLS-1$
                ColumnTag.class, null, "setStyle")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("title", //$NON-NLS-1$
                ColumnTag.class, null, "setTitle")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("titleKey", //$NON-NLS-1$
                ColumnTag.class, null, "setTitleKey")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("url", //$NON-NLS-1$
                ColumnTag.class, null, "setUrl")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("sortProperty", //$NON-NLS-1$
                ColumnTag.class, null, "setSortProperty")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("total", //$NON-NLS-1$
                ColumnTag.class, null, "setTotal")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("width", //$NON-NLS-1$
                ColumnTag.class, null, "setWidth")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("width", //$NON-NLS-1$
                ColumnTag.class, null, "setWidth")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("align", //$NON-NLS-1$
                ColumnTag.class, null, "setAlign")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("comparator", //$NON-NLS-1$
                ColumnTag.class, null, "setComparator")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("valueClass", //$NON-NLS-1$
                ColumnTag.class, null, "setValueClass")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("defaultorder", //$NON-NLS-1$
                ColumnTag.class, null, "setDefaultorder")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("headerScope", //$NON-NLS-1$
                ColumnTag.class, null, "setHeaderScope")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("scope", //$NON-NLS-1$
                ColumnTag.class, null, "setScope")); //$NON-NLS-1$

            // deprecated attribute
            proplist.add(new PropertyDescriptor("sort", //$NON-NLS-1$
                ColumnTag.class, null, "setSortable")); // map //$NON-NLS-1$

            // make ATG Dynamo happy:
            proplist.add(new PropertyDescriptor("className", //$NON-NLS-1$
                ColumnTag.class, null, "setClass")); // map //$NON-NLS-1$

        }
        catch (IntrospectionException ex)
        {
            throw new RuntimeException("You got an introspection exception - maybe defining a property that is not"
                + " defined in the ColumnTag?: "
                + ex.getMessage(), ex);
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }

}