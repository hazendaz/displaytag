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

import org.apache.commons.lang.StringEscapeUtils;
import org.displaytag.model.TableModel;


/**
 * Export view for xml exporting.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class XmlView extends BaseExportView
{

    /**
     * @see org.displaytag.export.BaseExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader,
        boolean decorateValues)
    {
        super.setParameters(tableModel, exportFullList, includeHeader, decorateValues);
    }

    /**
     * @see org.displaytag.export.BaseExportView#getRowStart()
     */
    protected String getRowStart()
    {
        return "<row>\n"; //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.export.BaseExportView#getRowEnd()
     */
    protected String getRowEnd()
    {
        return "</row>\n"; //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.export.BaseExportView#getCellStart()
     */
    protected String getCellStart()
    {
        return "<column>"; //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.export.BaseExportView#getCellEnd()
     */
    protected String getCellEnd()
    {
        return "</column>\n"; //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.export.BaseExportView#getDocumentStart()
     */
    protected String getDocumentStart()
    {
        return "<?xml version=\"1.0\"?>\n<table>\n"; //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.export.BaseExportView#getDocumentEnd()
     */
    protected String getDocumentEnd()
    {
        return "</table>\n"; //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.export.BaseExportView#getAlwaysAppendCellEnd()
     */
    protected boolean getAlwaysAppendCellEnd()
    {
        return true;
    }

    /**
     * @see org.displaytag.export.BaseExportView#getAlwaysAppendRowEnd()
     */
    protected boolean getAlwaysAppendRowEnd()
    {
        return true;
    }

    /**
     * @see org.displaytag.export.ExportView#getMimeType()
     */
    public String getMimeType()
    {
        return "text/xml"; //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.export.BaseExportView#escapeColumnValue(java.lang.Object)
     */
    protected String escapeColumnValue(Object value)
    {
        if (value != null)
        {
            return StringEscapeUtils.escapeXml(value.toString());
        }
        return null;
    }

}