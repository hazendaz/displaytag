package org.displaytag.export.excel;

import org.displaytag.exception.BaseNestableJspTagException;
import org.displaytag.exception.SeverityEnum;
import org.displaytag.Messages;
import org.displaytag.properties.TableProperties;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.HashMap;

/**
 * Convenience methods for the excel export.    Contains code extracted from several existing classes.
 * @author andy
 * Date: Nov 13, 2010
 * Time: 10:16:33 AM
 */
public class ExcelUtils
{
    public final static String EXCEL_SHEET_NAME = "export.excel.sheetname";    //$NON-NLS-1$
    public final static String EXCEL_FORMAT_INTEGER = "export.excel.format.integer";    //$NON-NLS-1$
    public final static String EXCEL_FORMAT_DATE = "export.excel.format.date";    //$NON-NLS-1$
    public final static String EXCEL_FORMAT_NUMBER = "export.excel.format.number";    //$NON-NLS-1$
    public final static String EXCEL_WRAPAT = "export.excel.wraptextlength";    //$NON-NLS-1$

    /*
     * Available already configured cell styles, as HSSF JavaDoc claims there are limits to cell styles.
     */
    private Map<String, HSSFCellStyle> cellStyles = new HashMap<String, HSSFCellStyle>();

    /**
     * Max line length for wrapping.
     */
    private int wrapAt;

//    public static final Integer

    /**
     * Style constant for looking up cell styles.
     */
    public static final String STYLE_INTEGER = "integer";
    /**
     * Style constant for looking up cell styles.
     */
    public static final String STYLE_NUMBER = "number";
    /**
     * Style constant for looking up cell styles.
     */
    public static final String STYLE_DATE = "date";
    /**
     * Style constant for looking up cell styles.
     */
    public static final String STYLE_STRING = "string";
    /**
     * Style constant for looking up cell styles.
     */
    public static final String STYLE_LONGSTRING = "longstring";
    /**
     * Style constant for looking up cell styles.
     */
    public static final String STYLE_PCT = "pct";

    /**
     * Workbook
     */
    private HSSFWorkbook wb;

    public ExcelUtils(HSSFWorkbook book)
    {
        wb = book;
    }

    public HSSFWorkbook getWb()
    {
        return wb;
    }

    public void setWb(HSSFWorkbook wb)
    {
        this.wb = wb;
    }

    /**
     * We cache the styles; they are expensive to construct.
     * @param properties props for this run
     */
    public void initCellStyles(TableProperties properties)
    {
        // Integer
        HSSFCellStyle style = getNewCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat( properties.getProperty(ExcelUtils.EXCEL_FORMAT_INTEGER) ));
        cellStyles.put(STYLE_INTEGER, style);

        // NUMBER
        style = getNewCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat(properties.getProperty(ExcelUtils.EXCEL_FORMAT_NUMBER)));
        cellStyles.put(STYLE_NUMBER, style);


//        style = HSSFDataFormat.getBuiltinFormat("0.00%");

        // Date
        style = getNewCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat(properties.getProperty(ExcelUtils.EXCEL_FORMAT_DATE)));
        style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        cellStyles.put(STYLE_DATE, style);

        // Long text
        style = getNewCellStyle();        // http://jakarta.apache.org/poi/hssf/quick-guide.html#NewLinesInCells
        style.setWrapText(true);
        cellStyles.put(STYLE_LONGSTRING, style);

        // Regular text
        cellStyles.put(STYLE_STRING, getNewCellStyle());

        wrapAt = Integer.valueOf(properties.getProperty(ExcelUtils.EXCEL_WRAPAT));
    }

    /**
     * You can add styles too, but they won't be picked up unless you do so in a subclass.
     * @param key
     * @param st
     */
    public void addCellStyle(String key, HSSFCellStyle st)
    {
        cellStyles.put(key,st);
    }

    public HSSFCellStyle getNewCellStyle()
    {
        return getWb() == null ? null : getWb().createCellStyle();
    }


    public HSSFCellStyle getStyle(String clz)
    {
        return cellStyles.get(clz);
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
     * @version $Revision: 1163 $ ($Author: rapruitt $)
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

    /**
     * Set the cell to wrap if the text is this long.
     * @return the max length for not wrapping
     */
    public int getWrapAtLength()
    {
        return wrapAt;

    }

    // patch from Karsten Voges
    /**
     * Escape certain values that are not permitted in excel cells.
     * @param rawValue the object value
     * @return the escaped value
     */
    public static String escapeColumnValue(Object rawValue)
    {
        if (rawValue == null)
        {
            return null;
        }
//        str = Patterns.replaceAll(str, "(\\r\\n|\\r|\\n|\\n\\r)\\s*", "");
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


}
