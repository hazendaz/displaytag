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

import org.displaytag.tags.TableTag;


/**
 * BeanInfo descriptor for the <code>ELTableTag</code> class. Unevaluated EL expression has to be kept separately from
 * the evaluated value, since the JSP compiler can choose to reuse different tag instances if they received the same
 * original attribute values, and the JSP compiler can choose to not re-call the setter methods.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class ELTableTagBeanInfo extends SimpleBeanInfo
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
                ELTableTag.class, null, "setCellpadding")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("cellspacing", //$NON-NLS-1$
                ELTableTag.class, null, "setCellspacing")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("class", //$NON-NLS-1$
                ELTableTag.class, null, "setClass")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("decorator", //$NON-NLS-1$
                ELTableTag.class, null, "setDecorator")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("defaultorder", //$NON-NLS-1$
                ELTableTag.class, null, "setDefaultorder")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("defaultsort", //$NON-NLS-1$
                ELTableTag.class, null, "setDefaultsort")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("export", //$NON-NLS-1$
                ELTableTag.class, null, "setExport")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("frame", //$NON-NLS-1$
                ELTableTag.class, null, "setFrame")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("length", //$NON-NLS-1$
                ELTableTag.class, null, "setLength")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("name", //$NON-NLS-1$
                ELTableTag.class, null, "setName")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("offset", //$NON-NLS-1$
                ELTableTag.class, null, "setOffset")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("pagesize", //$NON-NLS-1$
                ELTableTag.class, null, "setPagesize")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("requestURI", //$NON-NLS-1$
                ELTableTag.class, null, "setRequestURI")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("requestURIcontext", //$NON-NLS-1$
                ELTableTag.class, null, "setRequestURIcontext")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("rules", //$NON-NLS-1$
                ELTableTag.class, null, "setRules")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("sort", //$NON-NLS-1$
                ELTableTag.class, null, "setSort")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("style", //$NON-NLS-1$
                ELTableTag.class, null, "setStyle")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("summary", //$NON-NLS-1$
                ELTableTag.class, null, "setSummary")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("excludedParams", //$NON-NLS-1$
                ELTableTag.class, null, "setExcludedParams")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("id", //$NON-NLS-1$ 
                ELTableTag.class, null, "setUid")); //$NON-NLS-1$

            proplist.add(new PropertyDescriptor("uid", //$NON-NLS-1$ 
                ELTableTag.class, null, "setUid")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("htmlId", //$NON-NLS-1$ 
                ELTableTag.class, null, "setHtmlId")); //$NON-NLS-1$ 

            // deprecated attributes (not supporting expressions)
            proplist.add(new PropertyDescriptor("width", //$NON-NLS-1$ 
                TableTag.class, null, "setWidth")); //$NON-NLS-1$ 
            proplist.add(new PropertyDescriptor("styleClass", //$NON-NLS-1$ 
                ELTableTag.class, null, "setClass")); //$NON-NLS-1$ 

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

        }
        catch (IntrospectionException ex)
        {
            // ignore, this should never happen
        }

        PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return ((PropertyDescriptor[]) proplist.toArray(result));
    }

}