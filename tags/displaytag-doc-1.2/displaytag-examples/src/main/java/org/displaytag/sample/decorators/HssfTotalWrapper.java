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

package org.displaytag.sample.decorators;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.displaytag.decorator.hssf.DecoratesHssf;


/**
 * Same idea implemented in HssfTableWriter applied to decorators.
 * @see org.displaytag.render.HssfTableWriter
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 */
public class HssfTotalWrapper extends TotalWrapperTemplate implements DecoratesHssf
{

    private HSSFSheet sheet;

    private HSSFCell currentCell;

    private HSSFRow currentRow;

    private int colNum;

    protected void writeCityTotal(String city, double total)
    {
        this.writeTotal(city, total);
    }

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

    private void prepareCell()
    {
        this.currentCell = this.currentRow.createCell(this.colNum++);
    }

    protected void writeGrandTotal(double total)
    {
        this.writeTotal("Grand", total);
    }

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
