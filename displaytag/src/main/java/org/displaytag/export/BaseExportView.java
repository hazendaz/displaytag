package org.displaytag.export;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;

/**
 * <p>Base abstract class for simple export views</p>
 * <p>A class wich extends BaseExportView simple need to provide delimiters for rows
 * and columns</p>
 * @author fgiust
 * @version $Revision$ ($Author$)
 */
public abstract class BaseExportView
{

	/**
	 * logger
	 */
	private static Log mLog = LogFactory.getLog(BaseExportView.class);

	/**
	 * TableModel to render
	 */
	private TableModel mTableModel;

	/**
	 * export full list?
	 */
	private boolean mExportFullList;

	/**
	 * Constructor for BaseExportView
	 * @param pTableModel TableModel to render
	 * @param pExportFullList boolean export full list?
	 */
	public BaseExportView(TableModel pTableModel, boolean pExportFullList)
	{
		mTableModel = pTableModel;
		mExportFullList = pExportFullList;
	}

	/**
	 * Returns the TableModel.
	 * @return TableModel
	 */
	public TableModel getTableModel()
	{
		return mTableModel;
	}

	/**
	 * String to add before a row
	 * @return String
	 */
	protected abstract String getRowStart();

	/**
	 * String to add after a row
	 * @return String
	 */
	protected abstract String getRowEnd();

	/**
	 * String to add before a cell
	 * @return String
	 */
	protected abstract String getCellStart();

	/**
	 * String to add after a cell
	 * @return String
	 */
	protected abstract String getCellEnd();

	/**
	 * String to add to the top of document
	 * @return String
	 */
	protected abstract String getDocumentStart();

	/**
	 * String to add to the end of document
	 * @return String
	 */
	protected abstract String getDocumentEnd();

	/**
	 * always append cell end string?
	 * @return boolean
	 */
	protected abstract boolean getAlwaysAppendCellEnd();

	/**
	 * always append row end string?
	 * @return boolean
	 */
	protected abstract boolean getAlwaysAppendRowEnd();

	/**
	 * MimeType to return
	 * @return String myme type
	 */
	public abstract String getMimeType();

	/**
	 * Write table header
	 * @return String rendered header
	 */
	protected String doHeaders()
	{

		final String ROW_START = getRowStart();
		final String ROW_END = getRowEnd();
		final String CELL_START = getCellStart();
		final String CELL_END = getCellEnd();
		final boolean ALWAYS_APPEND_CELL_END = getAlwaysAppendCellEnd();

		StringBuffer lBuffer = new StringBuffer(1000);
		TableModel lTableModel = getTableModel();

		Iterator lIterator = lTableModel.getHeaderCellList().iterator();

		// start row
		lBuffer.append(ROW_START);

		while (lIterator.hasNext())
		{
			mLog.debug("lIterator.hasNext()");
			HeaderCell lHeaderCell = (HeaderCell) lIterator.next();

			String lHeader = lHeaderCell.getTitle();

			if (lHeader == null)
			{
				lHeader = StringUtils.capitalise(lHeaderCell.getBeanPropertyName());
			}

			lBuffer.append(CELL_START);

			lBuffer.append(lHeader);

			if (ALWAYS_APPEND_CELL_END || lIterator.hasNext())
			{
				lBuffer.append(CELL_END);
			}
		}

		// end row
		lBuffer.append(ROW_END);

		return lBuffer.toString();

	}

	/**
	 * Write the rendered table
	 * @return String rendered table body
	 * @throws JspException for errors during cell value lookup
	 */
	public String doExport() throws JspException
	{

		StringBuffer lBuffer = new StringBuffer(8000);

		TableModel lTableModel = getTableModel();

		final String DOCUMENT_START = getDocumentStart();
		final String DOCUMENT_END = getDocumentEnd();
		final String ROW_START = getRowStart();
		final String ROW_END = getRowEnd();
		final String CELL_START = getCellStart();
		final String CELL_END = getCellEnd();
		final boolean ALWAYS_APPEND_CELL_END = getAlwaysAppendCellEnd();
		final boolean ALWAYS_APPEND_ROW_END = getAlwaysAppendRowEnd();

		// document start
		lBuffer.append(DOCUMENT_START);

		//get the correct iterator (full or partial list according to the mExportFullList field)
		RowIterator lRowIterator =
			mExportFullList ? lTableModel.getFullListRowIterator() : lTableModel.getRowIterator();

		// iterator on rows
		while (lRowIterator.hasNext())
		{
			mLog.debug("lRowIterator.hasNext()");
			Row lRow = lRowIterator.next();
			mLog.debug("lRow=" + lRow);

			if (lTableModel.getTableDecorator() != null)
			{

				String lStringStartRow = lTableModel.getTableDecorator().startRow();
				if (lStringStartRow != null)
				{
					lBuffer.append(lStringStartRow);
				}

			}

			// iterator on columns
			ColumnIterator lColumnIterator = lRow.getColumnIterator(lTableModel.getHeaderCellList());

			lBuffer.append(ROW_START);

			while (lColumnIterator.hasNext())
			{
				Column lColumn = lColumnIterator.nextColumn();

				Object lValue;

				// Get the value to be displayed for the column
				try
				{
					lValue = lColumn.getValue(true);
				}
				catch (Exception ex)
				{
					mLog.error(ex.getMessage(), ex);
					throw new JspException(ex.getMessage());
				}

				lBuffer.append(CELL_START);
				lBuffer.append(lValue);

				if (ALWAYS_APPEND_CELL_END || lColumnIterator.hasNext())
				{
					lBuffer.append(CELL_END);
				}

			}
			if (ALWAYS_APPEND_ROW_END || lRowIterator.hasNext())
			{
				lBuffer.append(ROW_END);
			}
		}

		// document start
		lBuffer.append(DOCUMENT_END);

		return lBuffer.toString();

	}

}