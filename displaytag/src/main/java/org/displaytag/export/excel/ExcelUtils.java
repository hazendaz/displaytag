/*
 * Copyright (C) 2002-2024 Fabrizio Giustina, the Displaytag team
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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.displaytag.Messages;
import org.displaytag.exception.BaseNestableJspTagException;
import org.displaytag.exception.SeverityEnum;
import org.displaytag.properties.TableProperties;

/**
 * Convenience methods for the excel export. Contains code extracted from several existing classes.
 */
public class ExcelUtils {

    /** The Constant EXCEL_SHEET_NAME. */
    public static final String EXCEL_SHEET_NAME = "export.excel.sheetname"; //$NON-NLS-1$

    /** The Constant EXCEL_FORMAT_INTEGER. */
    public static final String EXCEL_FORMAT_INTEGER = "export.excel.format.integer"; //$NON-NLS-1$

    /** The Constant EXCEL_FORMAT_DATE. */
    public static final String EXCEL_FORMAT_DATE = "export.excel.format.date"; //$NON-NLS-1$

    /** The Constant EXCEL_FORMAT_NUMBER. */
    public static final String EXCEL_FORMAT_NUMBER = "export.excel.format.number"; //$NON-NLS-1$

    /** The Constant EXCEL_WRAPAT. */
    public static final String EXCEL_WRAPAT = "export.excel.wraptextlength"; //$NON-NLS-1$

    /** The cell styles. */
    /*
     * Available already configured cell styles, as HSSF JavaDoc claims there are limits to cell styles.
     */
    private final Map<String, HSSFCellStyle> cellStyles = new HashMap<>();

    /**
     * Max line length for wrapping.
     */
    private int wrapAt;

    // public static final Integer

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

    /** Workbook. */
    private HSSFWorkbook wb;

    /**
     * Instantiates a new excel utils.
     *
     * @param book
     *            the book
     */
    public ExcelUtils(final HSSFWorkbook book) {
        this.wb = book;
    }

    /**
     * Gets the wb.
     *
     * @return the wb
     */
    public HSSFWorkbook getWb() {
        return this.wb;
    }

    /**
     * Sets the wb.
     *
     * @param wb
     *            the new wb
     */
    public void setWb(final HSSFWorkbook wb) {
        this.wb = wb;
    }

    /**
     * We cache the styles; they are expensive to construct.
     *
     * @param properties
     *            props for this run
     */
    public void initCellStyles(final TableProperties properties) {
        // Integer
        HSSFCellStyle style = this.getNewCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat(properties.getProperty(ExcelUtils.EXCEL_FORMAT_INTEGER)));
        this.cellStyles.put(ExcelUtils.STYLE_INTEGER, style);

        // NUMBER
        style = this.getNewCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat(properties.getProperty(ExcelUtils.EXCEL_FORMAT_NUMBER)));
        this.cellStyles.put(ExcelUtils.STYLE_NUMBER, style);

        // Date
        style = this.getNewCellStyle();
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat(properties.getProperty(ExcelUtils.EXCEL_FORMAT_DATE)));
        style.setAlignment(HorizontalAlignment.RIGHT);
        this.cellStyles.put(ExcelUtils.STYLE_DATE, style);

        // Long text
        style = this.getNewCellStyle(); // http://jakarta.apache.org/poi/hssf/quick-guide.html#NewLinesInCells
        style.setWrapText(true);
        this.cellStyles.put(ExcelUtils.STYLE_LONGSTRING, style);

        // Regular text
        this.cellStyles.put(ExcelUtils.STYLE_STRING, this.getNewCellStyle());

        this.wrapAt = Integer.parseInt(properties.getProperty(ExcelUtils.EXCEL_WRAPAT));
    }

    /**
     * You can add styles too, but they won't be picked up unless you do so in a subclass.
     *
     * @param key
     *            the key
     * @param st
     *            the st
     */
    public void addCellStyle(final String key, final HSSFCellStyle st) {
        this.cellStyles.put(key, st);
    }

    /**
     * Gets the new cell style.
     *
     * @return the new cell style
     */
    public HSSFCellStyle getNewCellStyle() {
        return this.getWb() == null ? null : this.getWb().createCellStyle();
    }

    /**
     * Gets the style.
     *
     * @param clz
     *            the clz
     *
     * @return the style
     */
    public HSSFCellStyle getStyle(final String clz) {
        return this.cellStyles.get(clz);
    }

    /**
     * The Enum CellFormatTypes.
     */
    public enum CellFormatTypes {

        /** The integer. */
        INTEGER,

        /** The number. */
        NUMBER,

        /** The date. */
        DATE
    }

    /**
     * Wraps IText-generated exceptions.
     */
    static class ExcelGenerationException extends BaseNestableJspTagException {

        /**
         * Serial ID.
         */
        private static final long serialVersionUID = 899149338534L;

        /**
         * Instantiate a new PdfGenerationException with a fixed message and the given cause.
         *
         * @param cause
         *            Previous exception
         */
        public ExcelGenerationException(final Throwable cause) {
            super(ExcelHssfView.class, Messages.getString("ExcelView.errorexporting"), cause); //$NON-NLS-1$
        }

        /**
         * Gets the severity.
         *
         * @return the severity
         *
         * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
         */
        @Override
        public SeverityEnum getSeverity() {
            return SeverityEnum.ERROR;
        }
    }

    /**
     * Set the cell to wrap if the text is this long.
     *
     * @return the max length for not wrapping
     */
    public int getWrapAtLength() {
        return this.wrapAt;

    }

    // patch from Karsten Voges
    /**
     * Escape certain values that are not permitted in excel cells.
     *
     * @param rawValue
     *            the object value
     *
     * @return the escaped value
     */
    public static String escapeColumnValue(final Object rawValue) {
        if (rawValue == null) {
            return null;
        }
        String returnString = rawValue.toString();
        // escape the String to get the tabs, returns, newline explicit as \t \r \n
        returnString = StringEscapeUtils.escapeJava(StringUtils.trimToEmpty(returnString));
        // remove tabs, insert four whitespaces instead
        returnString = StringUtils.replace(StringUtils.trim(returnString), "\\t", "    ");
        // remove the return, only newline valid in excel
        returnString = StringUtils.replace(StringUtils.trim(returnString), "\\r", " ");
        return StringEscapeUtils.unescapeJava(returnString);
    }

}
