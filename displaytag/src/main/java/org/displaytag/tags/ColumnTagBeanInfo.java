/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
    @Override
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        List<PropertyDescriptor> proplist = new ArrayList<PropertyDescriptor>();

        try
        {
            proplist.add(new PropertyDescriptor("autolink", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setAutolink")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("escapeXml", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setEscapeXml")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("class", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setClass")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("decorator", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setDecorator")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("group", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setGroup")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("headerClass", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setHeaderClass")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("href", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setHref")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("maxLength", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setMaxLength")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("maxWords", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setMaxWords")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("media", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setMedia")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("nulls", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setNulls")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("paramId", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setParamId")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("paramName", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setParamName")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("paramProperty", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setParamProperty")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("property", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setProperty")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("sortable", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setSortable")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("sortName", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setSortName")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("style", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setStyle")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("title", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setTitle")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("titleKey", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setTitleKey")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("url", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setUrl")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("sortProperty", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setSortProperty")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("total", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setTotal")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("comparator", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setComparator")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("defaultorder", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setDefaultorder")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("headerScope", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setHeaderScope")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("scope", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setScope")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("format", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setFormat")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("value", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setValue")); //$NON-NLS-1$

            // make ATG Dynamo happy:
            proplist.add(new PropertyDescriptor("className", //$NON-NLS-1$
                ColumnTag.class,
                null,
                "setClass")); // map //$NON-NLS-1$

        }
        catch (IntrospectionException ex)
        {
            throw new RuntimeException("You got an introspection exception - maybe defining a property that is not"
                + " defined in the ColumnTag?: "
                + ex.getMessage(), ex);
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return proplist.toArray(result);
    }

}