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
 * Beaninfo class for tableTag. Needed to make the "class" tag attribute working and to handle the swith between
 * setName() and setNameString() setters for the name attribute.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class TableTagBeanInfo extends SimpleBeanInfo
{

    /**
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    public PropertyDescriptor[] getPropertyDescriptors()
    {
        List proplist = new ArrayList();

        try
        {
            proplist.add(new PropertyDescriptor("cellpadding", //$NON-NLS-1$
                TableTag.class, null, "setCellpadding")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("cellspacing", //$NON-NLS-1$
                TableTag.class, null, "setCellspacing")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("class", //$NON-NLS-1$
                TableTag.class, null, "setClass")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("decorator", //$NON-NLS-1$
                TableTag.class, null, "setDecorator")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("defaultorder", //$NON-NLS-1$
                TableTag.class, null, "setDefaultorder")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("defaultsort", //$NON-NLS-1$
                TableTag.class, null, "setDefaultsort")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("export", //$NON-NLS-1$
                TableTag.class, null, "setExport")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("frame", //$NON-NLS-1$
                TableTag.class, null, "setFrame")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("length", //$NON-NLS-1$
                TableTag.class, null, "setLength")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("offset", //$NON-NLS-1$
                TableTag.class, null, "setOffset")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("pagesize", //$NON-NLS-1$
                TableTag.class, null, "setPagesize")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("requestURI", //$NON-NLS-1$
                TableTag.class, null, "setRequestURI")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("requestURIcontext", //$NON-NLS-1$
                TableTag.class, null, "setRequestURIcontext")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("rules", //$NON-NLS-1$
                TableTag.class, null, "setRules")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("sort", //$NON-NLS-1$
                TableTag.class, null, "setSort")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("style", //$NON-NLS-1$
                TableTag.class, null, "setStyle")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("summary", //$NON-NLS-1$
                TableTag.class, null, "setSummary")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("excludedParams", //$NON-NLS-1$
                TableTag.class, null, "setExcludedParams")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("id", //$NON-NLS-1$
                TableTag.class, null, "setUid")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("uid", //$NON-NLS-1$
                TableTag.class, null, "setUid")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("htmlId", //$NON-NLS-1$
                TableTag.class, null, "setHtmlId")); //$NON-NLS-1$ 

            // deprecated attributes
            proplist.add(new PropertyDescriptor("list", //$NON-NLS-1$
                TableTag.class, null, "setList")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("property", //$NON-NLS-1$
                TableTag.class, null, "setProperty")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("scope", //$NON-NLS-1$
                TableTag.class, null, "setScope")); //$NON-NLS-1$ 

            proplist.add(new PropertyDescriptor("width", //$NON-NLS-1$
                TableTag.class, null, "setWidth")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("styleClass", //$NON-NLS-1$
                TableTag.class, null, "setClass")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("border", //$NON-NLS-1$
                TableTag.class, null, "setBorder")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("align", //$NON-NLS-1$
                TableTag.class, null, "setAlign")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("background", //$NON-NLS-1$
                TableTag.class, null, "setBackground")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("bgcolor", //$NON-NLS-1$
                TableTag.class, null, "setBgcolor")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("height", //$NON-NLS-1$
                TableTag.class, null, "setHeight")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("hspace", //$NON-NLS-1$
                TableTag.class, null, "setHspace")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("vspace", //$NON-NLS-1$
                TableTag.class, null, "setVspace")); //$NON-NLS-1$ 

            // make ATG Dynamo happy:
            proplist.add(new PropertyDescriptor("className", //$NON-NLS-1$
                TableTag.class, null, "setClass")); //$NON-NLS-1$ 

            try
            {
                Class.forName("javax.servlet.jsp.tagext.IterationTag"); //$NON-NLS-1$
                // jsp >= 1.2
                proplist.add(new PropertyDescriptor("name", //$NON-NLS-1$
                    TableTag.class, null, "setName")); //$NON-NLS-1$ 
            }
            catch (Throwable e)
            {
                // jsp 1.1, can't use a setter with an Object parameter
                proplist.add(new PropertyDescriptor("name", //$NON-NLS-1$
                    TableTag.class, null, "setNameString")); //$NON-NLS-1$ 
            }

        }
        catch (IntrospectionException ex)
        {
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }
}