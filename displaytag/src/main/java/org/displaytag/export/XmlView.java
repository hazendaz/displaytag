package org.displaytag.export;

import org.displaytag.model.TableModel;

/**
 * <p>Export view for xml exporting</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public class XmlView extends BaseExportView
{

	/**
	 * @see org.displaytag.export.BaseExportView#BaseExportView(TableModel, boolean)
	 */
	public XmlView(TableModel pTableModel, boolean pExportFullList)
	{
		super(pTableModel, pExportFullList);
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

}
