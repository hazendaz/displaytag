package org.displaytag.export.excel;

import java.io.OutputStream;
import java.util.*;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.displaytag.Messages;
import org.displaytag.properties.TableProperties;
import org.displaytag.exception.BaseNestableJspTagException;
import org.displaytag.exception.SeverityEnum;
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
    public final static String EXCEL_SHEET_NAME = "export.excel.sheetname";    //$NON-NLS-1$
    public final static String EXCEL_FORMAT_INTEGER = "export.excel.format.integer";    //$NON-NLS-1$
    public final static String EXCEL_FORMAT_DATE = "export.excel.format.date";    //$NON-NLS-1$
    public final static String EXCEL_FORMAT_NUMBER = "export.excel.format.number";    //$NON-NLS-1$

    /**
     * TableModel to render.
     */
    private TableModel model;

    /**
     * export full list?
     */
    private boolean exportFull;

    /**
     * include header in export?
     */
    private boolean header;

    /**
     * decorate export?
     */
    private boolean decorated;

    /**
     * Name of Excel Spreadsheet
     */
    private String sheetName;

    /**
     * Workbook
     */
    private HSSFWorkbook wb;

    /**
     * Worksheet
     */
    private HSSFSheet sheet;

    /*
     * Available already configured cell styles, as HSSF JavaDoc claims there are limits to cell styles.
     */
    private Map<CellFormatTypes, HSSFCellStyle> cellStyles = new HashMap<CellFormatTypes, HSSFCellStyle>();

    public ExcelHssfView()
    {
    }

    void initCellStyles()
    {
        setWb(new HSSFWorkbook());

        TableProperties properties = getTableModel().getProperties();
        
        // Integer
        HSSFCellStyle style = getNewCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat( properties.getProperty(EXCEL_FORMAT_INTEGER) ));
        cellStyles.put(CellFormatTypes.INTEGER, style);

        // NUMBER
        style = getNewCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat(properties.getProperty(EXCEL_FORMAT_NUMBER)));
        cellStyles.put(CellFormatTypes.NUMBER, style);

        // Date
        style = getNewCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat(properties.getProperty(EXCEL_FORMAT_DATE)));
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyles.put(CellFormatTypes.DATE, style);
    }

    /**
     * @see org.displaytag.export.ExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader,
        boolean decorateValues)
    {
        this.model = tableModel;
        this.exportFull = exportFullList;
        this.header = includeHeader;
        this.decorated = decorateValues;
        initCellStyles();
    }

    /**
     * @return "application/vnd.ms-excel"
     * @see org.displaytag.export.BaseExportView#getMimeType()
     */
    public String getMimeType()
    {
        return "application/vnd.ms-excel"; //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.export.BinaryExportView#doExport(OutputStream)
     */
    public void doExport(OutputStream out) throws JspException
    {
        try
        {
            String inputSheetName = this.model.getProperties().getProperty(EXCEL_SHEET_NAME);
            setSheetName(inputSheetName);
            setSheet(getWb().createSheet(getSheetName()));

            int rowNum = 0;
            int colNum = 0;

            if (this.header)
            {
                // Create an header row
                HSSFRow xlsRow = sheet.createRow(rowNum++);

                Iterator iterator = this.model.getHeaderCellList().iterator();

                while (iterator.hasNext())
                {
                    HeaderCell headerCell = (HeaderCell) iterator.next();

                    HSSFCell cell = xlsRow.createCell( colNum++);
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
            throw new ExcelGenerationException(e);
        }
    }

    /**
     * Uses POI Autosizing.
     *
     * WARNING.  This has been known to cause performance problems and various exceptions.  use at your own risk!  Overriding this method is suggested.
     *
     * From POI HSSF documentation for autoSizeColumn:
     *  "To calculate column width HSSFSheet.autoSizeColumn uses Java2D classes that throw exception if graphical environment is not available.
     *  In case if graphical environment is not available, you must tell Java that you are running in headless mode and set the following system property:  java.awt.headless=true."
     */
    protected void autosizeColumns() {
        for (int i=0; i < getModel().getNumberOfColumns(); i++)
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
     * Write the value to the cell.  Override this method if you have complex data types that may need to be exported.
     * @param value the value of the cell
     * @param cell the cell to write it to
     */
    protected void writeCell(Object value, HSSFCell cell)
    {
        if (value == null) {
            cell.setCellValue(new HSSFRichTextString(""));
        }
        else if (value instanceof Integer)
        {
            Integer integer = (Integer) value;
            // due to a weird bug in HSSF where it uses shorts, we need to input this as a double value :(
            cell.setCellValue(integer.doubleValue());
            cell.setCellStyle(cellStyles.get(CellFormatTypes.INTEGER));
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
            cell.setCellStyle(cellStyles.get(CellFormatTypes.NUMBER));
        }
        else if (value instanceof Date)
        {
            cell.setCellValue((Date) value);
            cell.setCellStyle(cellStyles.get(CellFormatTypes.DATE));
        }
        else if (value instanceof Calendar)
        {
            cell.setCellValue((Calendar) value);
            cell.setCellStyle(cellStyles.get(CellFormatTypes.DATE));
        }
        else
        {
            cell.setCellValue(new HSSFRichTextString(escapeColumnValue(value)));
        }
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
     * Templated method that is called for all non-header & non-total cells.
     * @param wb
     * @param rowCtr
     * @param column
     * @return
     */
    public HSSFCellStyle createRowStyle(HSSFWorkbook wb, int rowCtr, Column column)
    {
        return wb.createCellStyle();
    }

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
     * @param wb
     * @param headerCell
     * @return
     */
    public HSSFCellStyle createHeaderStyle(HSSFWorkbook wb, HeaderCell headerCell)
    {
        HSSFCellStyle headerStyle = getNewCellStyle();

        headerStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
        headerStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
        HSSFFont bold = wb.createFont();
        bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        bold.setColor(HSSFColor.WHITE.index);
        headerStyle.setFont(bold);

        return headerStyle;
    }

    /**
     * Templated method that is used if a totals row is desired.
     * @param sheet
     * @param rowNum
     * @param tableModel
     */
    public void createTotalsRow(HSSFSheet sheet, int rowNum, TableModel tableModel)
    {
    }

    public TableModel getTableModel()
    {
        return model;
    }

    public boolean isExportFull()
    {
        return exportFull;
    }

    public boolean isIncludeHeaderInExport()
    {
        return header;
    }

    public boolean isDecorateExport()
    {
        return decorated;
    }

    public String getSheetName()
    {
        return sheetName;
    }

    public void setSheetName(String sheetName) throws JspException
    {
        // this is due to either the POI limitations or excel (I'm not sure). you get the following error if you don't do this:
        // Exception: [.ExcelHssfView] !ExcelView.errorexporting! Cause: Sheet name cannot be blank, greater than 31 chars, or contain any of /\*?[]
        if (StringUtils.isBlank(sheetName))
        {
            throw new JspException("The sheet name property " + EXCEL_SHEET_NAME + " must not be blank.");
        }
        sheetName =  sheetName.replaceAll("/|\\\\|\\*|\\?|\\[|\\]","");
        this.sheetName = sheetName.length() <= 31 ? sheetName : sheetName.substring(0,31-3) + "...";
    }

    public HSSFCellStyle getNewCellStyle()
    {
        return getWb() == null ? null : getWb().createCellStyle();
    }

    public HSSFWorkbook getWb()
    {
        return wb;
    }

    public void setWb(HSSFWorkbook wb)
    {
        this.wb = wb;
    }

    public HSSFSheet getSheet()
    {
        return sheet;
    }

    public void setSheet(HSSFSheet sheet)
    {
        this.sheet = sheet;
    }

    public TableModel getModel()
    {
        return model;
    }

    public void setModel(TableModel model)
    {
        this.model = model;
    }

    public enum CellFormatTypes
    {
        INTEGER,
        NUMBER,
        DATE
    }

    /**
     * Wraps IText-generated exceptions.
     * @author Fabrizio Giustina
     * @version $Revision$ ($Author$)
     */
    static class ExcelGenerationException extends BaseNestableJspTagException
    {

        /**
         * D1597A17A6.
         */
        private static final long serialVersionUID = 899149338534L;

        /**
         * Instantiate a new PdfGenerationException with a fixed message and the given cause.
         * @param cause Previous exception
         */
        public ExcelGenerationException(Throwable cause)
        {
            super(ExcelHssfView.class, Messages.getString("ExcelView.errorexporting"), cause); //$NON-NLS-1$
        }

        /**
         * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
         */
        public SeverityEnum getSeverity()
        {
            return SeverityEnum.ERROR;
        }
    }

}
