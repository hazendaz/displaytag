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
package org.displaytag.localization;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;


/**
 * Interface for resource providers. Given a <code>resourceKey</code> and a <code>defaultValue</code>, a resource
 * provider returns the String which should be displayed. For column headers <code>resourceKey</code> will be the
 * value of the <code>titleKey</code> attribute, <code>defaultValue</code> will be the value of the
 * <code>property</code> attribute. Different resource providers can be plugged using the displaytag.properties file.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public interface I18nResourceProvider
{

    /**
     * Returns a localized String. A resource provider is free to use both <code>resourceKey</code> or
     * <code>defaultValue</code> for the lookup. For example in column titles <code>defaultValue</code> is the value
     * of the <code>property</code> attribute and can be used as a default if <code>titleKey</code> is not
     * specified.
     * @param resourceKey used-specified resource key
     * @param defaultValue default or fallback value
     * @param tag calling tag (TableTag), which can be used to find needed ancestor tags
     * @param context jsp page context
     * @return localized String.
     */
    String getResource(String resourceKey, String defaultValue, Tag tag, PageContext context);

}
