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
     * @see org.displaytag.export.BaseExportView#BaseExportView(TableModel, boolean, boolean, boolean)
     */
    public XmlView(TableModel tableModel, boolean exportFullList, boolean includeHeader, boolean decorateValues)
    {
        super(tableModel, exportFullList, includeHeader, decorateValues);
    }

    /**
     * @see org.displaytag.export.BaseExportView#getRowStart()
     */
    protected String getRowStart()
    {
        return "<row>\n";
    }

    /**
     * @see org.displaytag.export.BaseExportView#getRowEnd()
     */
    protected String getRowEnd()
    {
        return "</row>\n";
    }

    /**
     * @see org.displaytag.export.BaseExportView#getCellStart()
     */
    protected String getCellStart()
    {
        return "<column>";
    }

    /**
     * @see org.displaytag.export.BaseExportView#getCellEnd()
     */
    protected String getCellEnd()
    {
        return "</column>\n";
    }

    /**
     * @see org.displaytag.export.BaseExportView#getDocumentStart()
     */
    protected String getDocumentStart()
    {
        return "<?xml version=\"1.0\"?>\n<table>\n";
    }

    /**
     * @see org.displaytag.export.BaseExportView#getDocumentEnd()
     */
    protected String getDocumentEnd()
    {
        return "</table>\n";
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
     * @see org.displaytag.export.BaseExportView#getMimeType()
     */
    public String getMimeType()
    {
        return "text/xml";
    }

    /**
     * @see org.displaytag.export.BaseExportView#escapeColumnValue(java.lang.Object)
     */
    protected Object escapeColumnValue(Object value)
    {
        if (value != null)
        {
            return StringEscapeUtils.escapeXml(value.toString());
        }
        return null;
    }

}