/*
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
package org.displaytag.localization;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * Interface for resource providers. Given a <code>resourceKey</code> and a <code>defaultValue</code>, a resource
 * provider returns the String which should be displayed. For column headers <code>resourceKey</code> will be the value
 * of the <code>titleKey</code> attribute, <code>defaultValue</code> will be the value of the <code>property</code>
 * attribute. Different resource providers can be plugged using the displaytag.properties file.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
 */
public interface I18nResourceProvider {

    /**
     * Returns a localized String. A resource provider is free to use both <code>resourceKey</code> or
     * <code>defaultValue</code> for the lookup. For example in column titles <code>defaultValue</code> is the value of
     * the <code>property</code> attribute and can be used as a default if <code>titleKey</code> is not specified.
     *
     * @param resourceKey
     *            used-specified resource key
     * @param defaultValue
     *            default or fallback value
     * @param tag
     *            calling tag (TableTag), which can be used to find needed ancestor tags
     * @param context
     *            jsp page context
     *
     * @return localized String.
     */
    String getResource(String resourceKey, String defaultValue, Tag tag, PageContext context);

}
