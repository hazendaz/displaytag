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

import java.io.OutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.rtf.RtfWriter2;


/**
 * RTF exporter using iText.
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 */
public class DefaultRtfExportView extends DefaultItextExportView
{

    /**
     * @see org.displaytag.export.BaseExportView#getMimeType()
     * @return "application/rtf"
     */
    public String getMimeType()
    {
        return "application/rtf"; //$NON-NLS-1$
    }

    /**
     * Initializes the RTF writer this export view uses to write the table document.
     * @param document The iText document to be written.
     * @param out The output stream to which the document is written.
     */
    protected void initItextWriter(Document document, OutputStream out)
    {
        RtfWriter2.getInstance(document, out);
    }
}
