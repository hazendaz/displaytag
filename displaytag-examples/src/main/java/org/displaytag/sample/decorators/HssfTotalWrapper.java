/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.sample.decorators;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.displaytag.decorator.hssf.DecoratesHssf;

/**
 * Same idea implemented in HssfTableWriter applied to decorators.
 *
 * @see org.displaytag.render.HssfTableWriter
 */
public class HssfTotalWrapper extends TotalWrapperTemplate implements DecoratesHssf {

    /** The sheet. */
    private HSSFSheet sheet;

    /** The current cell. */
    private HSSFCell currentCell;

    /** The current row. */
    private HSSFRow currentRow;

    /** The col num. */
    private int colNum;

    @Override
    protected void writeCityTotal(String city, double total) {
        this.writeTotal(city, total);
    }

    /**
     * Write total.
     *
     * @param value
     *            the value
     * @param total
     *            the total
     */
    private void writeTotal(String value, double total) {
        if (this.assertRequiredState()) {
            int rowNum = this.sheet.getLastRowNum();
            this.currentRow = this.sheet.createRow(++rowNum);
            this.colNum = 0;
            prepareCell();
            prepareCell();
            prepareCell();
            this.currentCell.setCellValue(new HSSFRichTextString("------------"));

            this.currentRow = this.sheet.createRow(++rowNum);
            this.colNum = 0;
            prepareCell();
            prepareCell();
            this.currentCell.setCellValue(new HSSFRichTextString(value + " Total:"));
            prepareCell();
            this.currentCell.setCellValue(total);
        }
    }

    /**
     * Prepare cell.
     */
    private void prepareCell() {
        this.currentCell = this.currentRow.createCell(this.colNum++);
    }

    @Override
    protected void writeGrandTotal(double total) {
        this.writeTotal("Grand", total);
    }

    @Override
    public void setSheet(HSSFSheet sheet) {
        this.sheet = sheet;
    }

    /**
     * Asserts that the sheet property needed have been set by the client.
     *
     * @return true if the required properties are not null; false otherwise.
     */
    private boolean assertRequiredState() {
        return this.sheet != null;
    }
}
