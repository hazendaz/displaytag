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
package org.displaytag.render;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.decorator.hssf.DecoratesHssf;
import org.displaytag.model.Column;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;


/**
 * A table writer that formats a table in Excel's spreadsheet format, and writes it to an HSSF workbook.
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 * @see org.displaytag.render.TableWriterTemplate
 */
public class HssfTableWriter extends TableWriterAdapter
{

    /**
     * The workbook to which the table is written.
     */
    private HSSFWorkbook wb;

    /**
     * Generated sheet.
     */
    private HSSFSheet sheet;

    /**
     * Current row number.
     */
    private int rowNum;

    /**
     * Current row.
     */
    private HSSFRow currentRow;

    /**
     * Current column number.
     */
    private int colNum;

    /**
     * Current cell.
     */
    private HSSFCell currentCell;

    /**
     * Percent Excel format.
     */
    private short pctFormat = HSSFDataFormat.getBuiltinFormat("0.00%");

    /**
     * This table writer uses an HSSF workbook to write the table.
     * @param wb The HSSF workbook to write the table.
     */
    public HssfTableWriter(HSSFWorkbook wb)
    {
        this.wb = wb;
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableOpener(org.displaytag.model.TableModel)
     */
    protected void writeTableOpener(TableModel model) throws Exception
    {
        this.sheet = wb.createSheet("-");
        this.rowNum = 0;
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeCaption(org.displaytag.model.TableModel)
     */
    protected void writeCaption(TableModel model) throws Exception
    {
        HSSFCellStyle style = this.wb.createCellStyle();
        HSSFFont bold = this.wb.createFont();
        bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        bold.setFontHeightInPoints((short) 14);
        style.setFont(bold);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        this.colNum = 0;
        this.currentRow = this.sheet.createRow(this.rowNum++);
        this.currentCell = this.currentRow.createCell(this.colNum);
        this.currentCell.setCellStyle(style);
        String caption = model.getCaption();
        this.currentCell.setCellValue(new HSSFRichTextString(caption));
        this.rowSpanTable(model);
    }

    /**
     * Obtain the region over which to merge a cell.
     * @param first Column number of first cell from which to merge.
     * @param last Column number of last cell over which to merge.
     * @return The region over which to merge a cell.
     */
    private CellRangeAddress getMergeCellsRegion(int first, int last)
    {
        return new CellRangeAddress(this.currentRow.getRowNum(), first, this.currentRow.getRowNum(), last);
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableHeader(org.displaytag.model.TableModel)
     */
    protected void writeTableHeader(TableModel model) throws Exception
    {
        this.currentRow = this.sheet.createRow(this.rowNum++);
        this.colNum = 0;
        HSSFCellStyle headerStyle = this.getHeaderFooterStyle();
        for (Iterator iterator = model.getHeaderCellList().iterator(); iterator.hasNext();)
        {
            HeaderCell headerCell = (HeaderCell) iterator.next();
            String columnHeader = headerCell.getTitle();
            if (columnHeader == null)
            {
                columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
            }

            this.writeHeaderFooter(columnHeader, this.currentRow, headerStyle);
        }
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowStart(TableModel model)
    {
        model.getTableDecorator().startRow();
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeRowOpener(org.displaytag.model.TableModel)
     */
    protected void writeRowOpener(Row row) throws Exception
    {
        this.currentRow = this.sheet.createRow(rowNum++);
        this.colNum = 0;
    }

    /**
     * Write a column's opening structure to a HSSF document.
     * @see org.displaytag.render.TableWriterTemplate#writeColumnOpener(org.displaytag.model.Column)
     */
    protected void writeColumnOpener(Column column) throws Exception
    {
        column.getOpenTag(); // has side effect, setting its stringValue, which affects grouping logic.
        this.currentCell = this.currentRow.createCell(this.colNum++);
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeColumnValue(Object,org.displaytag.model.Column)
     */
    protected void writeColumnValue(Object value, Column column) throws Exception
    {
        if (value instanceof Number)
        {
            Number num = (Number) value;
            // Percentage
            if (value.toString().indexOf("%") > -1)
            {
                this.currentCell.setCellValue(num.doubleValue() / 100);
                HSSFCellStyle cellStyle = this.wb.createCellStyle();
                cellStyle.setDataFormat(this.pctFormat);
                this.currentCell.setCellStyle(cellStyle);
            }
            else
            {
                this.currentCell.setCellValue(num.doubleValue());
            }
        }
        else if (value instanceof Date)
        {
            this.currentCell.setCellValue((Date) value);
        }
        else if (value instanceof Calendar)
        {
            this.currentCell.setCellValue((Calendar) value);
        }
        else
        {
            this.currentCell.setCellValue(new HSSFRichTextString(this.escapeColumnValue(value)));
        }

    }

    /**
     * Decorators that help render the table to an HSSF table must implement DecoratesHssf.
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowFinish(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowFinish(TableModel model) throws Exception
    {
        TableDecorator decorator = model.getTableDecorator();
        if (decorator instanceof DecoratesHssf)
        {
            DecoratesHssf hdecorator = (DecoratesHssf) decorator;
            hdecorator.setSheet(this.sheet);
        }
        decorator.finishRow();
        this.rowNum = this.sheet.getLastRowNum();
        this.rowNum++;
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writePostBodyFooter(org.displaytag.model.TableModel)
     */
    protected void writePostBodyFooter(TableModel model) throws Exception
    {
        this.colNum = 0;
        this.currentRow = this.sheet.createRow(this.rowNum++);
        this.writeHeaderFooter(model.getFooter(), this.currentRow, this.getHeaderFooterStyle());
        this.rowSpanTable(model);
    }

    /**
     * Make a row span the width of the table.
     * @param model The table model representing the rendered table.
     */
    private void rowSpanTable(TableModel model)
    {
        this.sheet.addMergedRegion(this.getMergeCellsRegion(this.currentCell.getColumnIndex(), (model
            .getNumberOfColumns() - 1)));
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedTableFinish(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedTableFinish(TableModel model)
    {
        model.getTableDecorator().finish();
    }

    // patch from Karsten Voges
    /**
     * Escape certain values that are not permitted in excel cells.
     * @param rawValue the object value
     * @return the escaped value
     */
    protected String escapeColumnValue(Object rawValue)
    {
        if (rawValue == null)
        {
            return null;
        }
        String returnString = ObjectUtils.toString(rawValue);
        // escape the String to get the tabs, returns, newline explicit as \t \r \n
        returnString = StringEscapeUtils.escapeJava(StringUtils.trimToEmpty(returnString));
        // remove tabs, insert four whitespaces instead
        returnString = StringUtils.replace(StringUtils.trim(returnString), "\\t", "    ");
        // remove the return, only newline valid in excel
        returnString = StringUtils.replace(StringUtils.trim(returnString), "\\r", " ");
        // unescape so that \n gets back to newline
        returnString = StringEscapeUtils.unescapeJava(returnString);
        return returnString;
    }

    /**
     * Is this value numeric? You should probably override this method to handle your locale.
     * @param rawValue the object value
     * @return true if numeric
     */
    protected boolean isNumber(String rawValue)
    {
        if (rawValue == null)
        {
            return false;
        }
        String rawV = rawValue;
        if (rawV.indexOf('%') > -1)
        {
            rawV = rawV.replace('%', ' ').trim();
        }
        if (rawV.indexOf('$') > -1)
        {
            rawV = rawV.replace('$', ' ').trim();
        }
        if (rawV.indexOf(',') > -1)
        {
            rawV = StringUtils.replace(rawV, ",", "");
        }
        return NumberUtils.isNumber(rawV.trim());
    }

    /**
     * Writes a table header or a footer.
     * @param value Header or footer value to be rendered.
     * @param row The row in which to write the header or footer.
     * @param style Style used to render the header or footer.
     */
    private void writeHeaderFooter(String value, HSSFRow row, HSSFCellStyle style)
    {
        this.currentCell = row.createCell(this.colNum++);
        this.currentCell.setCellValue(new HSSFRichTextString(value));
        this.currentCell.setCellStyle(style);
    }

    /**
     * Obtain the style used to render a header or footer.
     * @return The style used to render a header or footer.
     */
    private HSSFCellStyle getHeaderFooterStyle()
    {
        HSSFCellStyle style = this.wb.createCellStyle();
        style.setFillPattern(HSSFCellStyle.FINE_DOTS);
        style.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
        HSSFFont bold = this.wb.createFont();
        bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        bold.setColor(HSSFColor.WHITE.index);
        style.setFont(bold);
        return style;
    }

    /**
     * @see org.displaytag.render.TableWriterAdapter#writeBottomBanner(org.displaytag.model.TableModel)
     */
    protected void writeBottomBanner(TableModel model) throws Exception
    {
        // adjust the column widths
        int colCount = 0;
        while (colCount <= colNum)
        {
            sheet.autoSizeColumn((short) colCount++);
        }
    }
}
