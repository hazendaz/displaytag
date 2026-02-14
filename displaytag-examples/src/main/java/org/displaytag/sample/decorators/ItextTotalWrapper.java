/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.sample.decorators;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 * Same idea implemented in ItextTableWriter applied to decorators.
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
     * @see org.displaytag.render.ItextTableWriter.ItextDecorator#setTable(com.lowagie.text.pdf.PdfPTable)
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
     * @see org.displaytag.render.ItextTableWriter.ItextDecorator#setFont(com.lowagie.text.Font)
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
