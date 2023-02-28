/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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
 * Needed to make the "class" tag attribute working.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public class CaptionTagBeanInfo extends SimpleBeanInfo {

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
            proplist.add(new PropertyDescriptor("class", //$NON-NLS-1$
                    CaptionTag.class, null, "setClass")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("dir", //$NON-NLS-1$
                    CaptionTag.class, null, "setDir")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("id", //$NON-NLS-1$
                    CaptionTag.class, null, "setId")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("lang", //$NON-NLS-1$
                    CaptionTag.class, null, "setLang")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("media", //$NON-NLS-1$
                    CaptionTag.class, null, "setMedia")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("style", //$NON-NLS-1$
                    CaptionTag.class, null, "setStyle")); //$NON-NLS-1$
            proplist.add(new PropertyDescriptor("title", //$NON-NLS-1$
                    CaptionTag.class, null, "setTitle")); //$NON-NLS-1$

            // make ATG Dynamo happy:
            // Attribute "className" of tag "caption" in taglib descriptor file displaytag-11.tld" must have a
            // corresponding property in class "org.displaytag.tags.CaptionTag" with a public setter method
            proplist.add(new PropertyDescriptor("className", //$NON-NLS-1$
                    CaptionTag.class, null, "setClass")); //$NON-NLS-1$
        } catch (final IntrospectionException ex) {
            throw new RuntimeException("You got an introspection exception - maybe defining a property that is not"
                    + " defined in the CaptionTag?: " + ex.getMessage(), ex);
        }

        final PropertyDescriptor[] result = new PropertyDescriptor[proplist.size()];
        return proplist.toArray(result);
    }

}
