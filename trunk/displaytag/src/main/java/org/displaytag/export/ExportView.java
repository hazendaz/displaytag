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
package org.displaytag.export;

import org.displaytag.model.TableModel;


/**
 * Interface for export classes. ExportViewFactory is responsible for registering and initialization of export views. A
 * default, no parameters constructor is required. The <code>setParameters()</code> method is guarantee to be called
 * before any other operation.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public interface ExportView
{

    /**
     * initialize the parameters needed for export. The method is guarantee be called before <code>doExport()</code>
     * and <code>getMimeType()</code>. Classes implementing ExportView should reset any instance field previously set
     * when this method is called, in order to support instance reusing.
     * @param tableModel TableModel to render
     * @param exportFullList boolean export full list?
     * @param includeHeader should header be included in export?
     * @param decorateValues should output be decorated?
     */
    void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader, boolean decorateValues);

    /**
     * MimeType to return.
     * @return String mime type
     */
    String getMimeType();

}
