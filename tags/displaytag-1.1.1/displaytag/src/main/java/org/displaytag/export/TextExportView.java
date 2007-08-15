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

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;


/**
 * Main interface for exportViews which need to output character data.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public interface TextExportView extends ExportView
{

    /**
     * Returns the exported content as a String.
     * @param out output writer
     * @throws IOException for exceptions in accessing the output stream
     * @throws JspException for other exceptions during export
     */
    void doExport(Writer out) throws IOException, JspException;

    /**
     * If <code>true</code> exported data will be included in the html page. <strong>actually not evaluated. Included
     * for future enhancements </strong>
     * @return <code>true</code> if exported data should be included in the html page
     */
    boolean outputPage();
}
