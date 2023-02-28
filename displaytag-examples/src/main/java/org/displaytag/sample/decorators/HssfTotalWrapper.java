/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.sample.decorators;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.displaytag.decorator.hssf.DecoratesHssf;


/**
 * Same idea implemented in HssfTableWriter applied to decorators.
 *
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 * @see org.displaytag.render.HssfTableWriter
 */
public class HssfTotalWrapper extends TotalWrapperTemplate implements DecoratesHssf
{

    /** The sheet. */
    private HSSFSheet sheet;

    /** The current cell. */
    private HSSFCell currentCell;

    /** The current row. */
    private HSSFRow currentRow;

    /** The col num. */
    private int colNum;

    @Override
    protected void writeCityTotal(String city, double total)
    {
        this.writeTotal(city, total);
    }

    /**
     * Write total.
     *
     * @param value the value
     * @param total the total
     */
    private void writeTotal(String value, double total)
    {
        if (this.assertRequiredState())
        {
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
    private void prepareCell()
    {
        this.currentCell = this.currentRow.createCell(this.colNum++);
    }

    @Override
    protected void writeGrandTotal(double total)
    {
        this.writeTotal("Grand", total);
    }

    @Override
    public void setSheet(HSSFSheet sheet)
    {
        this.sheet = sheet;
    }

    /**
     * Asserts that the sheet property needed have been set by the client.
     * @return true if the required properties are not null; false otherwise.
     */
    private boolean assertRequiredState()
    {
        return this.sheet != null;
    }
}
