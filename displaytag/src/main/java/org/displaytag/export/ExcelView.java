package org.displaytag.export;

import org.apache.commons.lang.StringUtils;
import org.displaytag.model.TableModel;

/**
 * <p>Export view for excel exporting</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class ExcelView extends BaseExportView
{

    /**
     * @see org.displaytag.export.BaseExportView#BaseExportView(TableModel, boolean, boolean)
     */
    public ExcelView(TableModel tableModel, boolean exportFullList, boolean includeHeader)
    {
        super(tableModel, exportFullList, includeHeader);
    }

    /**
     * Method getMimeType
     * @return String
     */
    public String getMimeType()
    {
        return "application/vnd.ms-excel";
    }

    /**
     * Method getRowStart
     * @return String
     */
    protected String getRowStart()
    {
        return "";
    }

    /**
     * Method getRowEnd
     * @return String
     */
    protected String getRowEnd()
    {
        return "\n";
    }

    /**
     * Method getCellStart
     * @return String
     */
    protected String getCellStart()
    {
        return "";
    }

    /**
     * Method getCellEnd
     * @return String
     */
    protected String getCellEnd()
    {
        return "\t";
    }

    /**
     * Method getDocumentStart
     * @return String
     */
    protected String getDocumentStart()
    {
        return "";
    }

    /**
     * Method getDocumentEnd
     * @return String
     */
    protected String getDocumentEnd()
    {
        return "";
    }

    /**
     * Method getAlwaysAppendCellEnd
     * @return boolean
     */
    protected boolean getAlwaysAppendCellEnd()
    {
        return false;
    }

    /**
     * Method getAlwaysAppendRowEnd
     * @return boolean
     */
    protected boolean getAlwaysAppendRowEnd()
    {
        return false;
    }

    /**
     * Escaping for excel format:
     * <ul>
     * <li>Quotes inside quoted strings are escaped with a double quote.</li>
     * <li>Fields are surrounded by "" (should be optional, but sometimes you get a "Sylk error" without those)</li>
     * </ul>
     * @see org.displaytag.export.BaseExportView#escapeColumnValue(java.lang.Object)
     */
    protected Object escapeColumnValue(Object value)
    {
        if (value != null)
        {
            // quotes around fields are needed to avoid occasional "Sylk format invalid" messages from excel
            return "\"" + StringUtils.replace(StringUtils.trim(value.toString()), "\"", "\"\"") + "\"";
        }

        return null;
    }

}
