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

import org.apache.commons.lang.UnhandledException;


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
                ELColumnTag.class,
                null,
                "setAutolink")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("escapeXml", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setEscapeXml")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("class", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setClass")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("decorator", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setDecorator")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("group", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setGroup")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("headerClass", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setHeaderClass")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("href", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setHref")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("maxLength", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setMaxLength")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("maxWords", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setMaxWords")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("media", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setMedia")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("nulls", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setNulls")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("paramId", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setParamId")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("paramName", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setParamName")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("paramProperty", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setParamProperty")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("paramScope", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setParamScope")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("property", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setProperty")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("sortable", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setSortable")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("sortName", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setSortName")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("style", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setStyle")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("total", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setTotal")); // map //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("title", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setTitle")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("titleKey", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setTitleKey")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("url", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setUrl")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("sortProperty", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setSortProperty")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("comparator", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setComparator")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("defaultorder", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setDefaultorder")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("headerScope", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setHeaderScope")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("scope", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setScope")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("format", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setFormat")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("value", //$NON-NLS-1$
                ELColumnTag.class,
                null,
                "setValue")); //$NON-NLS-1$

        }
        catch (IntrospectionException ex)
        {
            throw new UnhandledException("You got an introspection exception - maybe defining a property that is not"
                + " defined in the bean?: "
                + ex.getMessage(), ex);
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }

}