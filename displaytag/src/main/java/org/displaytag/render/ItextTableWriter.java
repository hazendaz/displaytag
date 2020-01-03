/**
 * Copyright (C) 2002-2014 Fabrizio Giustina, the Displaytag team
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
package org.displaytag.render;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.model.Column;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.TableModel;


/**
 * A table writer that formats table as and writes it to an iText document.
 * @author Jorge L. Barroso
 * @version $Id$
 * @see org.displaytag.render.TableWriterTemplate
 */
public class ItextTableWriter extends TableWriterAdapter
{

    /**
     * iText representation of the table.
     */
    private PdfPTable table;

    /**
     * iText document to which the table is written.
     */
    private Document document;

    /**
     * The default font used in the document.
     */
    private Font defaultFont;

    /**
     * This table writer uses an iText table and document to do its work.
     * @param table iText representation of the table.
     * @param document iText document to which the table is written.
     */
    public ItextTableWriter(PdfPTable table, Document document)
    {
        this.table = table;
        this.document = document;
    }

    /**
     * Initialize the main info holder table, like the appropriate number of columns.
     * @param model The table being represented as iText.
     * @see org.displaytag.render.TableWriterTemplate#writeTableOpener(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableOpener(TableModel model)
    {
        this.table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        this.table.setWidthPercentage(100);
        this.defaultFont = this.getTableFont();
    }

    /**
     * Obtain the font used to render text in the table; Meant to be overriden if a different font is desired.
     * @return The font used to render text in the table.
     */
    protected Font getTableFont()
    {
        return FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, new BaseColor(0x00, 0x00, 0x00));
    }

    /**
     * Write the table's caption to a iText document.
     * @see org.displaytag.render.TableWriterTemplate#writeCaption(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeCaption(TableModel model) throws Exception
    {
        this.decorateCaption(model);
    }

    /**
     * Writes the table caption according to a set style.
     * @param model The table model containing the caption.
     * @throws DocumentException If an error occurrs while decorating the caption.
     */
    private void decorateCaption(TableModel model) throws DocumentException
    {
        Paragraph caption = new Paragraph(new Chunk(model.getCaption(), this.getCaptionFont()));
        caption.setAlignment(this.getCaptionHorizontalAlignment());
        this.document.add(caption);
    }

    /**
     * Obtain the caption font; Meant to be overriden if a different style is desired.
     * @return The caption font.
     */
    protected Font getCaptionFont()
    {
        return FontFactory.getFont(FontFactory.HELVETICA, 17, Font.BOLD, new BaseColor(0x00, 0x00, 0x00));
    }

    /**
     * Obtain the caption horizontal alignment; Meant to be overriden if a different style is desired.
     * @return The caption horizontal alignment.
     */
    protected int getCaptionHorizontalAlignment()
    {
        return Element.ALIGN_CENTER;
    }

    /**
     * Write the table's header columns to an iText document.
     * @see org.displaytag.render.TableWriterTemplate#writeTableHeader(org.displaytag.model.TableModel)
     * @throws DocumentException if an error occurs while writing header.
     */
    @Override
    protected void writeTableHeader(TableModel model) throws DocumentException
    {
        Iterator<HeaderCell> iterator = model.getHeaderCellList().iterator();

        float[] widths = new float[model.getNumberOfColumns()];
        for (int i = 0; iterator.hasNext(); i++)
        {
            HeaderCell headerCell = iterator.next();
            widths[i] = this.getCellWidth(headerCell);

            String columnHeader = headerCell.getTitle();

            if (columnHeader == null)
            {
                columnHeader = StringUtils.capitalize(headerCell.getBeanPropertyName());
            }

            PdfPCell hdrCell = this.getHeaderCell(columnHeader);
            this.table.addCell(hdrCell);
        }
        this.table.setHeaderRows(1);
        this.table.setWidths(widths);
    }

    /**
     * Returns the maximum size of all values in this column.
     * @param headerCell Header cell for this column.
     * @return The maximum size of all values in this column.
     */
    private float getCellWidth(HeaderCell headerCell)
    {
        int maxWidth = headerCell.getMaxLength();
        return maxWidth > 0 ? maxWidth : headerCell.getTitle().length();
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writePostBodyFooter(org.displaytag.model.TableModel)
     * @throws DocumentException if an error occurs while writing post-body footer.
     */
    @Override
    protected void writePostBodyFooter(TableModel model) throws DocumentException
    {
        Chunk cellContent = new Chunk(model.getFooter(), this.getFooterFont());
        this.setFooterFontStyle(cellContent);
        PdfPCell cell = new PdfPCell(new Phrase(cellContent));
        cell.setLeading(8, 0);
        cell.setBackgroundColor(this.getFooterBackgroundColor());
        cell.setHorizontalAlignment(this.getFooterHorizontalAlignment());
        cell.setColspan(model.getNumberOfColumns());
        this.table.addCell(cell);
    }

    /**
     * Obtain the footer background color; Meant to be overriden if a different style is desired.
     * @return The footer background color.
     */
    protected BaseColor getFooterBackgroundColor()
    {
        return new BaseColor(0xce, 0xcf, 0xce);
    }

    /**
     * Obtain the footer horizontal alignment; Meant to be overriden if a different style is desired.
     * @return The footer horizontal alignment.
     */
    protected int getFooterHorizontalAlignment()
    {
        return Element.ALIGN_LEFT;
    }

    /**
     * Set the font style used to render the header text; Meant to be overridden if a different header style is desired.
     * @param cellContent The header content whose font will be modified.
     */
    protected void setFooterFontStyle(Chunk cellContent)
    {
        this.setBoldStyle(cellContent, this.getFooterFontColor());
    }

    /**
     * Obtain the footer font color; Meant to be overriden if a different style is desired.
     * @return The footer font color.
     */
    protected BaseColor getFooterFontColor()
    {
        return new BaseColor(0x00, 0x00, 0x00);
    }

    /**
     * Obtain the footer font; Meant to be overriden if a different style is desired.
     * @return The footer font.
     */
    protected Font getFooterFont()
    {
        return FontFactory.getFont(FontFactory.HELVETICA, 10);
    }

    /**
     * Decorators that help render the table to an iText document must implement ItextDecorator.
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowStart(TableModel model)
    {
        TableDecorator decorator = model.getTableDecorator();
        if (decorator instanceof ItextDecorator)
        {
            ItextDecorator idecorator = (ItextDecorator) decorator;
            idecorator.setTable(this.table);
            idecorator.setFont(this.defaultFont);
        }
        decorator.startRow();
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowFinish(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowFinish(TableModel model) throws Exception
    {
        model.getTableDecorator().finishRow();
    }

    /**
     * Write a column's opening structure to an iText document.
     * @see org.displaytag.render.TableWriterTemplate#writeColumnOpener(org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnOpener(Column column) throws ObjectLookupException, DecoratorException
    {
        column.initialize(); // has side effect, setting its stringValue, which affects grouping logic.
    }

    /**
     * Write a column's value to a iText document.
     * @see org.displaytag.render.TableWriterTemplate#writeColumnValue(Object,org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnValue(Object value, Column column) throws BadElementException
    {
        this.table.addCell(getCell(value));
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedTableFinish(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedTableFinish(TableModel model)
    {
        model.getTableDecorator().finish();
    }

    /**
     * Returns a formatted cell for the given value.
     *
     * @param value cell value
     * @return Cell
     */
    private PdfPCell getCell(Object value)
    {
        PdfPCell cell = new PdfPCell(new Phrase(new Chunk(StringUtils.trimToEmpty(value != null
            ? value.toString()
            : StringUtils.EMPTY), this.defaultFont)));
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setLeading(8, 0);
        return cell;
    }

    /**
     * Obtain a header cell.
     * @param value Cell content.
     * @return A header cell with the given content.
     */
    private PdfPCell getHeaderCell(String value)
    {
        Chunk cellContent = new Chunk(value, this.getHeaderFont());
        setHeaderFontStyle(cellContent);
        PdfPCell cell = new PdfPCell(new Phrase(cellContent));
        cell.setLeading(8, 0);
        cell.setHorizontalAlignment(this.getHeaderHorizontalAlignment());
        cell.setBackgroundColor(this.getHeaderBackgroundColor());
        return cell;
    }

    /**
     * Obtain the font used to render the header text; Meant to be overridden if a different header font is desired.
     * @return The font used to render the header text.
     */
    protected Font getHeaderFont()
    {
        return this.defaultFont;
    }

    /**
     * Obtain the background color used to render the header; Meant to be overridden if a different header background
     * color is desired.
     * @return The backgrounc color used to render the header.
     */
    protected BaseColor getHeaderBackgroundColor()
    {
        return new BaseColor(0xee, 0xee, 0xee);
    }

    /**
     * Set the font style used to render the header text; Meant to be overridden if a different header style is desired.
     * @param cellContent The header content whose font will be modified.
     */
    protected void setHeaderFontStyle(Chunk cellContent)
    {
        setBoldStyle(cellContent, this.getHeaderFontColor());
    }

    /**
     * Set the font color used to render the header text; Meant to be overridden if a different header style is desired.
     * @return The font color used to render the header text.
     */
    protected BaseColor getHeaderFontColor()
    {
        return new BaseColor(0x00, 0x00, 0x00);
    }

    /**
     * Obtain the horizontal alignment used to render header text; Meant to be overridden if a different alignment is
     * desired.
     * @return The horizontal alignment used to render header text;
     */
    protected int getHeaderHorizontalAlignment()
    {
        return Element.ALIGN_CENTER;
    }

    /**
     * Makes chunk content bold.
     * @param chunk The chunk whose content is to be rendered bold.
     * @param color The font color desired.
     */
    private void setBoldStyle(Chunk chunk, BaseColor color)
    {
        Font font = chunk.getFont();
        chunk.setFont(FontFactory.getFont(font.getFamilyname(), font.getSize(), Font.BOLD, color));
    }

    /**
     * An implementor of this interface decorates tables and columns appearing in iText documents.
     * @author Jorge L. Barroso
     * @version $Revision$ ($Author$)
     */
    public interface ItextDecorator
    {

        /**
         * Set the iText table used to render a table model.
         * @param table The iText table used to render a table model.
         */
        void setTable(PdfPTable table);

        /**
         * Set the font used to render a table's content.
         * @param font The font used to render a table's content.
         */
        void setFont(Font font);
    }

}
