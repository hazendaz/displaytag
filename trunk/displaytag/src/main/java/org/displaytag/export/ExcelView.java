package org.displaytag.export;

import org.displaytag.model.TableModel;

/**
 * <p>Export view for excel exporting</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class ExcelView extends BaseExportView
{

    /**
     * @see org.displaytag.export.BaseExportView#BaseExportView(TableModel, boolean)
     */
    public ExcelView(TableModel pTableModel, boolean pExportFullList)
    {
        super(pTableModel, pExportFullList);
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

}
