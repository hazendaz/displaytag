/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.displaytag.export.excel;

import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.displaytag.export.BinaryExportView;
import org.displaytag.model.Column;
import org.displaytag.model.ColumnIterator;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.RowIterator;
import org.displaytag.model.TableModel;


/**
 * Excel exporter using POI HSSF.
 * @author Fabrizio Giustina
 * @author rapruitt
 * @version $Revision$ ($Author$)
 */
public class ExcelHssfView implements BinaryExportView
{

    /**
     * TableModel to render.
     */
    private TableModel model;

    /** export full list?. */
    private boolean exportFull;

    /** include header in export?. */
    private boolean header;

    /** decorate export?. */
    private boolean decorated;

    /** Name of Excel Spreadsheet. */
    private String sheetName;

    /** Workbook. */
    private HSSFWorkbook wb;

    /** Worksheet. */
    private HSSFSheet sheet;

    /** utils. */
    ExcelUtils utils;

    /**
     * Instantiates a new excel hssf view.
     */
    public ExcelHssfView()
    {
    }

    /**
     * @see org.displaytag.export.ExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
    @Override
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader,
        boolean decorateValues)
    {
        this.model = tableModel;
        this.exportFull = exportFullList;
        this.header = includeHeader;
        this.decorated = decorateValues;
        this.utils = new ExcelUtils(new HSSFWorkbook());
        this.utils.initCellStyles(tableModel.getProperties());
    }

    /**
     * @return "application/vnd.ms-excel"
     * @see org.displaytag.export.BaseExportView#getMimeType()
     */
    @Override
    public String getMimeType()
    {
        return "application/vnd.ms-excel"; //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.export.BinaryExportView#doExport(OutputStream)
     */
    @Override
    public void doExport(OutputStream out) throws JspException
    {
        try
        {
            String inputSheetName = this.model.getProperties().getProperty(ExcelUtils.EXCEL_SHEET_NAME);
            setSheetName(inputSheetName);
            setSheet(getWb().createSheet(getSheetName()));

            int rowNum = 0;
            int colNum = 0;

            if (this.header)
            {
                // Create an header row
                HSSFRow xlsRow = this.sheet.createRow(rowNum++);

                Iterator<HeaderCell> iterator = this.model.getHeaderCellList().iterator();

                while (iterator.hasNext())
                {
                    HeaderCell headerCell = iterator.next();

                    HSSFCell cell = xlsRow.createCell(colNum++);
                    cell.setCellValue(new HSSFRichTextString(getHeaderCellValue(headerCell)));
                    cell.setCellStyle(createHeaderStyle(getWb(), headerCell));
                }
            }

            // get the correct iterator (full or partial list according to the exportFull field)
            RowIterator rowIterator = this.model.getRowIterator(this.exportFull);

            // iterator on rows
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                HSSFRow xlsRow = getSheet().createRow(rowNum++);
                colNum = 0;

                // iterator on columns
                ColumnIterator columnIterator = row.getColumnIterator(this.model.getHeaderCellList());

                while (columnIterator.hasNext())
                {
                    Column column = columnIterator.nextColumn();

                    // Get the value to be displayed for the column
                    Object value = column.getValue(this.decorated);

                    HSSFCell cell = xlsRow.createCell(colNum++);
                    writeCell(value, cell);
                }
            }

            createTotalsRow(getSheet(), rowNum, this.model);

            autosizeColumns();

            getWb().write(out);
        }
        catch (Exception e)
        {
            throw new ExcelUtils.ExcelGenerationException(e);
        }
    }

    /**
     * Uses POI Autosizing. WARNING. This has been known to cause performance problems and various exceptions. use at
     * your own risk! Overriding this method is suggested. From POI HSSF documentation for autoSizeColumn: "To calculate
     * column width HSSFSheet.autoSizeColumn uses Java2D classes that throw exception if graphical environment is not
     * available. In case if graphical environment is not available, you must tell Java that you are running in headless
     * mode and set the following system property: java.awt.headless=true."
     */
    protected void autosizeColumns()
    {
        for (int i = 0; i < getModel().getNumberOfColumns(); i++)
        {
            getSheet().autoSizeColumn((short) i);
            // since this usually creates column widths that are just too short, adjust here!
            // gives column width an extra character
            int width = getSheet().getColumnWidth(i);
            width += 256;
            getSheet().setColumnWidth(i, (short) width);
        }
    }

    /**
     * Write the value to the cell. Override this method if you have complex data types that may need to be exported.
     * @param value the value of the cell
     * @param cell the cell to write it to
     */
    protected void writeCell(Object value, HSSFCell cell)
    {
        if (value == null)
        {
            cell.setCellValue(new HSSFRichTextString(""));
        }
        else if (value instanceof Integer)
        {
            Integer integer = (Integer) value;
            // due to a weird bug in HSSF where it uses shorts, we need to input this as a double value :(
            cell.setCellValue(integer.doubleValue());
            cell.setCellStyle(this.utils.getStyle(ExcelUtils.STYLE_INTEGER));
        }
        else if (value instanceof Number)
        {
            Number num = (Number) value;
            if (num.equals(Double.NaN))
            {
                cell.setCellValue(new HSSFRichTextString(""));
            }
            else
            {
                cell.setCellValue(num.doubleValue());
            }
            cell.setCellStyle(this.utils.getStyle(ExcelUtils.STYLE_NUMBER));
        }
        else if (value instanceof Date)
        {
            cell.setCellValue((Date) value);
            cell.setCellStyle(this.utils.getStyle(ExcelUtils.STYLE_DATE));
        }
        else if (value instanceof Calendar)
        {
            cell.setCellValue((Calendar) value);
            cell.setCellStyle(this.utils.getStyle(ExcelUtils.STYLE_DATE));
        }
        else
        {
            cell.setCellValue(new HSSFRichTextString(ExcelUtils.escapeColumnValue(value)));
        }
    }

    /**
     * Templated method that is called for all non-header and non-total cells.
     *
     * @param wb the wb
     * @param rowCtr the row ctr
     * @param column the column
     * @return the HSSF cell style
     */
    public HSSFCellStyle createRowStyle(HSSFWorkbook wb, int rowCtr, Column column)
    {
        return wb.createCellStyle();
    }

    /**
     * Gets the header cell value.
     *
     * @param headerCell the header cell
     * @return the header cell value
     */
    public String getHeaderCellValue(HeaderCell headerCell)
    {
        String columnHeader = headerCell.getTitle();

        if (columnHeader == null)
        {
            columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
        }

        return columnHeader;
    }

    /**
     * Templated method that is called for all header cells.
     *
     * @param wb the wb
     * @param headerCell the header cell
     * @return the HSSF cell style
     */
    public HSSFCellStyle createHeaderStyle(HSSFWorkbook wb, HeaderCell headerCell)
    {
        HSSFCellStyle headerStyle = getNewCellStyle();

        headerStyle.setFillPattern(CellStyle.FINE_DOTS);
        headerStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
        HSSFFont bold = wb.createFont();
        bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
        bold.setColor(HSSFColor.WHITE.index);
        headerStyle.setFont(bold);

        return headerStyle;
    }

    /**
     * Templated method that is used if a totals row is desired.
     *
     * @param sheet the sheet
     * @param rowNum the row num
     * @param tableModel the table model
     */
    public void createTotalsRow(HSSFSheet sheet, int rowNum, TableModel tableModel)
    {
    }

    /**
     * Gets the table model.
     *
     * @return the table model
     */
    public TableModel getTableModel()
    {
        return this.model;
    }

    /**
     * Checks if is export full.
     *
     * @return true, if is export full
     */
    public boolean isExportFull()
    {
        return this.exportFull;
    }

    /**
     * Checks if is include header in export.
     *
     * @return true, if is include header in export
     */
    public boolean isIncludeHeaderInExport()
    {
        return this.header;
    }

    /**
     * Checks if is decorate export.
     *
     * @return true, if is decorate export
     */
    public boolean isDecorateExport()
    {
        return this.decorated;
    }

    /**
     * Gets the sheet name.
     *
     * @return the sheet name
     */
    public String getSheetName()
    {
        return this.sheetName;
    }

    /**
     * Sets the sheet name.
     *
     * @param sheetName the new sheet name
     * @throws JspException the jsp exception
     */
    public void setSheetName(String sheetName) throws JspException
    {
        // this is due to either the POI limitations or excel (I'm not sure). you get the following error if you don't
        // do this:
        // Exception: [.ExcelHssfView] !ExcelView.errorexporting! Cause: Sheet name cannot be blank, greater than 31
        // chars, or contain any of /\*?[]
        if (StringUtils.isBlank(sheetName))
        {
            throw new JspException("The sheet name property " + ExcelUtils.EXCEL_SHEET_NAME + " must not be blank.");
        }
        sheetName = sheetName.replaceAll("/|\\\\|\\*|\\?|\\[|\\]", "");
        this.sheetName = sheetName.length() <= 31 ? sheetName : sheetName.substring(0, 31 - 3) + "...";
    }

    /**
     * Gets the new cell style.
     *
     * @return the new cell style
     */
    public HSSFCellStyle getNewCellStyle()
    {
        return getWb() == null ? null : getWb().createCellStyle();
    }

    /**
     * Gets the wb.
     *
     * @return the wb
     */
    public HSSFWorkbook getWb()
    {
        return this.wb;
    }

    /**
     * Sets the wb.
     *
     * @param wb the new wb
     */
    public void setWb(HSSFWorkbook wb)
    {
        this.wb = wb;
    }

    /**
     * Gets the sheet.
     *
     * @return the sheet
     */
    public HSSFSheet getSheet()
    {
        return this.sheet;
    }

    /**
     * Sets the sheet.
     *
     * @param sheet the new sheet
     */
    public void setSheet(HSSFSheet sheet)
    {
        this.sheet = sheet;
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public TableModel getModel()
    {
        return this.model;
    }

    /**
     * Sets the model.
     *
     * @param model the new model
     */
    public void setModel(TableModel model)
    {
        this.model = model;
    }

}
