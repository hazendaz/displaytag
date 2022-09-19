/*
 * Copyright (C) 2002-2022 Fabrizio Giustina, the Displaytag team
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
 * Beaninfo class for tableTag. Needed to make the "class" tag attribute working and to handle the swith between
 * setName() and setNameString() setters for the name attribute.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class TableTagBeanInfo extends SimpleBeanInfo {

    /**
     * Gets the property descriptors.
     *
     * @return the property descriptors
     *
     * @see java.beans.BeanInfo#getPropertyDescriptors()
     */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        final List<PropertyDescriptor> proplist = new ArrayList<>();

        try {
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
            proplist.add(new PropertyDescriptor("partialList", //$NON-NLS-1$
                    TableTag.class, null, "setPartialList")); //$NON-NLS-1$
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
            proplist.add(new PropertyDescriptor("varTotals", //$NON-NLS-1$
                    TableTag.class, null, "setVarTotals")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("keepStatus", //$NON-NLS-1$
                    TableTag.class, null, "setKeepStatus")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("clearStatus", //$NON-NLS-1$
                    TableTag.class, null, "setClearStatus")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("form", //$NON-NLS-1$
                    TableTag.class, null, "setForm")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("items", //$NON-NLS-1$
                    TableTag.class, null, "setItems")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("size", //$NON-NLS-1$
                    TableTag.class, null, "setSize")); //$NON-NLS-1$

            // deprecated attributes
            proplist.add(new PropertyDescriptor("list", //$NON-NLS-1$
                    TableTag.class, null, "setList")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("name", //$NON-NLS-1$
                    TableTag.class, null, "setName")); //$NON-NLS-1$

            // make ATG Dynamo happy:
            proplist.add(new PropertyDescriptor("className", //$NON-NLS-1$
                    TableTag.class, null, "setClass")); //$NON-NLS-1$

        } catch (final IntrospectionException ex) {
            throw new RuntimeException("You got an introspection exception - maybe defining a property that is not"
                    + " defined in the TableTag?: " + ex.getMessage(), ex);
        }

        final PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return proplist.toArray(result);
    }
}