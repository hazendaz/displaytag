package org.displaytag.export;

import org.apache.commons.lang.StringUtils;
import org.displaytag.model.TableModel;

/**
 * <p>Export view for comma separated value exporting</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class CsvView extends BaseExportView
{

    /**
     * @see org.displaytag.export.BaseExportView#BaseExportView(TableModel, boolean, boolean)
     */
    public CsvView(TableModel tableModel, boolean exportFullList, boolean includeHeader)
    {
        super(tableModel, exportFullList, includeHeader);
    }

    /**
     * @see org.displaytag.export.BaseExportView#getRowStart()
     */
    protected String getRowStart()
    {
        return "";
    }

    /**
     * @see org.displaytag.export.BaseExportView#getRowEnd()
     */
    protected String getRowEnd()
    {
        return "\n";
    }

    /**
     * @see org.displaytag.export.BaseExportView#getCellStart()
     */
    protected String getCellStart()
    {
        return "";
    }

    /**
     * @see org.displaytag.export.BaseExportView#getCellEnd()
     */
    protected String getCellEnd()
    {
        return ",";
    }

    /**
     * @see org.displaytag.export.BaseExportView#getDocumentStart()
     */
    protected String getDocumentStart()
    {
        return "";
    }

    /**
     * @see org.displaytag.export.BaseExportView#getDocumentEnd()
     */
    protected String getDocumentEnd()
    {
        return "";
    }

    /**
     * @see org.displaytag.export.BaseExportView#getAlwaysAppendCellEnd()
     */
    protected boolean getAlwaysAppendCellEnd()
    {
        return false;
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
        return "text/csv";
    }

    /**
     * @see org.displaytag.export.BaseExportView#escapeColumnValue(java.lang.Object)
     * @todo how to escape commas?
     */
    protected Object escapeColumnValue(Object value)
    {
        if (value != null)
        {
            return StringUtils.replace(value.toString(), ",", ";");
        }
        return null;
    }

}
