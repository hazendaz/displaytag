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

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * Same idea implemented in ItextTableWriter applied to decorators.
 *
 * @author Jorge L. Barroso
 *
 * @version $Revision$ ($Author$)
 *
 * @see org.displaytag.render.ItextTableWriter
 */
public class ItextTotalWrapper extends TotalWrapperTemplate
        implements org.displaytag.render.ItextTableWriter.ItextDecorator {

    /**
     * The iText table in which the totals are rendered.
     */
    private PdfPTable table;

    /**
     * The iText font used to render the totals.
     */
    private Font font;

    /**
     * Set the table required to render the totals line.
     *
     * @param table
     *            The table required to render the totals line.
     *
     * @see org.displaytag.render.ItextTableWriter.ItextDecorator#setTable(com.itextpdf.text.pdf.PdfPTable)
     */
    @Override
    public void setTable(PdfPTable table) {
        this.table = table;
    }

    /**
     * Set the font required to render the totals line.
     *
     * @param font
     *            The font required to render the totals line.
     *
     * @see org.displaytag.render.ItextTableWriter.ItextDecorator#setFont(com.itextpdf.text.Font)
     */
    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Writes cell border at bottom of cell.
     */
    @Override
    public String startRow() {
        this.table.getDefaultCell().setBorder(Rectangle.BOTTOM);
        return null;
    }

    /**
     * Writes the city total line.
     *
     * @param city
     *            City name.
     * @param total
     *            City total.
     */
    @Override
    protected void writeCityTotal(String city, double total) {
        this.writeTotal(city, total);
    }

    /**
     * Writes the table grand total
     *
     * @param total
     *            Table grand total
     */
    @Override
    protected void writeGrandTotal(double total) {
        this.writeTotal("Grand", total);
    }

    /**
     * Writes a total line.
     *
     * @param value
     *            Total message.
     * @param total
     *            Total number.
     */
    private void writeTotal(String value, double total) {
        if (assertRequiredState()) {
            this.font = FontFactory.getFont(this.font.getFamilyname(), this.font.getSize(), Font.BOLD,
                    this.font.getColor());
            this.table.addCell(this.getCell(""));
            this.table.addCell(this.getCell(""));
            this.table.addCell(this.getCell("-------------"));
            this.table.addCell(this.getCell(""));
            // new row
            this.table.addCell(this.getCell(""));
            this.table.addCell(this.getCell(value + " Total:"));
            this.table.addCell(this.getCell(total + ""));
            this.table.addCell(this.getCell(""));
        }
    }

    /**
     * Obtain a cell with the given value.
     *
     * @param value
     *            Value to display in the cell.
     *
     * @return A cell with the given value.
     */
    private PdfPCell getCell(String value) {
        PdfPCell cell = new PdfPCell(new Phrase(new Chunk(value, this.font)));
        cell.setLeading(8, 0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }

    /**
     * Asserts that the table and font properties needed have been set by the client.
     *
     * @return true if the required properties are not null; false otherwise.
     */
    private boolean assertRequiredState() {
        return this.table != null && this.font != null;
    }
}
