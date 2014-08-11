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
 * @see org.displaytag.render.ItextTableWriter
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 */
public class ItextTotalWrapper extends TotalWrapperTemplate
    implements
    org.displaytag.render.ItextTableWriter.ItextDecorator
{

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
     * @param table The table required to render the totals line.
     * @see org.displaytag.decorator.itext.DecoratesItext#setTable(com.lowagie.text.Table)
     */
    @Override
    public void setTable(PdfPTable table)
    {
        this.table = table;
    }

    /**
     * Set the font required to render the totals line.
     * @param font The font required to render the totals line.
     * @see org.displaytag.decorator.itext.DecoratesItext#setFont(com.lowagie.text.Font)
     */
    @Override
    public void setFont(Font font)
    {
        this.font = font;
    }

    /**
     * Writes cell border at bottom of cell.
     */
    @Override
    public String startRow()
    {
        this.table.getDefaultCell().setBorder(Rectangle.BOTTOM);
        return null;
    }

    /**
     * Writes the city total line.
     * @param city City name.
     * @param total City total.
     */
    @Override
    protected void writeCityTotal(String city, double total)
    {
        this.writeTotal(city, total);
    }

    /**
     * Writes the table grand total
     * @param total Table grand total
     */
    @Override
    protected void writeGrandTotal(double total)
    {
        this.writeTotal("Grand", total);
    }

    /**
     * Writes a total line.
     * @param value Total message.
     * @param total Total number.
     */
    private void writeTotal(String value, double total)
    {
        if (assertRequiredState())
        {
            this.font = FontFactory.getFont(
                this.font.getFamilyname(),
                this.font.getSize(),
                Font.BOLD,
                this.font.getColor());
            table.addCell(this.getCell(""));
            table.addCell(this.getCell(""));
            table.addCell(this.getCell("-------------"));
            table.addCell(this.getCell(""));
            // new row
            table.addCell(this.getCell(""));
            table.addCell(this.getCell(value + " Total:"));
            table.addCell(this.getCell(total + ""));
            table.addCell(this.getCell(""));
        }
    }

    /**
     * Obtain a cell with the given value.
     * @param value Value to display in the cell.
     * @return A cell with the given value.
     */
    private PdfPCell getCell(String value)
    {
        PdfPCell cell = new PdfPCell(new Phrase(new Chunk(value, this.font)));
        cell.setLeading(8, 0);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }

    /**
     * Asserts that the table and font properties needed have been set by the client.
     * @return true if the required properties are not null; false otherwise.
     */
    private boolean assertRequiredState()
    {
        return this.table != null && this.font != null;
    }
}
