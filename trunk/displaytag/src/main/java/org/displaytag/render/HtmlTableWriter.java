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
package org.displaytag.render;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;

import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.model.*;
import org.displaytag.tags.CaptionTag;
import org.displaytag.tags.TableTag;
import org.displaytag.util.TagConstants;


/**
 * A table writer that formats a table in HTML and writes it to a JSP page. (Background: this code was factored from
 * TableTag.writeHTMLData)
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 * @see org.displaytag.render.TableWriterTemplate
 */
public class HtmlTableWriter extends TableWriterAdapter
{

    /**
     * <code>TableTag</code> callback instance.
     */
    private TableTag tableTag;

    /**
     * Output destination.
     */
    private JspWriter out;

    /**
     * This table writer uses a <code>TableTag</code> and a <code>JspWriter</code> to do its work.
     * @param tableTag <code>TableTag</code> instance called back by this writer.
     * @param out The output destination.
     */
    public HtmlTableWriter(TableTag tableTag, JspWriter out)
    {
        this.tableTag = tableTag;
        this.out = out;
    }

    /**
     * Writes a banner containing search result and paging navigation above an HTML table to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTopBanner(org.displaytag.model.TableModel)
     */
    protected void writeTopBanner(TableModel model)
    {
        this.tableTag.writeSearchResultAndNavigation();
    }

    /**
     * Writes an HTML table's opening tags to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTableOpener(org.displaytag.model.TableModel)
     */
    protected void writeTableOpener(TableModel model)
    {
        this.write(this.tableTag.getOpenTag(), this.out);
    }

    /**
     * Writes an HTML table's caption to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeCaption(org.displaytag.model.TableModel)
     */
    protected void writeCaption(TableModel model)
    {
        CaptionTag captionTag = this.tableTag.getCaptionTag();
        this.write(captionTag.getOpenTag() + model.getCaption() + captionTag.getCloseTag(), this.out);
    }

    /**
     * Writes an HTML table's column header to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTableHeader(org.displaytag.model.TableModel)
     */
    protected void writeTableHeader(TableModel model)
    {
        // thead
        this.tableTag.writeTableHeader();
    }

    /**
     * Writes an HTML table's footer to a JSP page; HTML requires tfoot to appear before tbody.
     * @see org.displaytag.render.TableWriterTemplate#writeFooter(org.displaytag.model.TableModel)
     */
    protected void writePreBodyFooter(TableModel model)
    {
        this.write(TagConstants.TAG_TFOOTER_OPEN, this.out);
        this.write(model.getFooter(), this.out);
        this.write(TagConstants.TAG_TFOOTER_CLOSE, this.out);
        // reset footer
        this.tableTag.setFooter(null);
    }

    /**
     * Writes the start of an HTML table's body to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyOpener(org.displaytag.model.TableModel)
     */
    protected void writeTableBodyOpener(TableModel model)
    {
        this.write(TagConstants.TAG_TBODY_OPEN, this.out);

    }

    /**
     * Writes the end of an HTML table's body to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyCloser(org.displaytag.model.TableModel)
     */
    protected void writeTableBodyCloser(TableModel model)
    {
        this.write(TagConstants.TAG_TBODY_CLOSE, this.out);
    }

    /**
     * Writes the closing structure of an HTML table to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTableCloser(org.displaytag.model.TableModel)
     */
    protected void writeTableCloser(TableModel model)
    {
        this.write(this.tableTag.getCloseTag(), this.out);
    }

    /**
     * Writes a banner containing search result, paging navigation, and export links below an HTML table to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeBottomBanner(org.displaytag.model.TableModel)
     */
    protected void writeBottomBanner(TableModel model)
    {
        this.tableTag.writeNavigationAndExportLinks();
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedTableFinish(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedTableFinish(TableModel model)
    {
        model.getTableDecorator().finish();
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowStart(TableModel model)
    {
        String stringStartRow = model.getTableDecorator().startRow();
        if (stringStartRow != null)
        {
            this.write(stringStartRow, this.out);
        }
    }

    /**
     * Writes an HTML table's row-opening tag to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeRowOpener(org.displaytag.model.Row)
     */
    protected void writeRowOpener(Row row)
    {
        this.write(row.getOpenTag(), this.out);
    }

    /**
     * Writes an HTML table's column-opening tag to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeColumnOpener(org.displaytag.model.Column)
     */
    protected void writeColumnOpener(Column column) throws ObjectLookupException, DecoratorException
    {
        this.write(column.getOpenTag(), this.out);
    }

    /**
     * Writes an HTML table's column-closing tag to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeColumnCloser(org.displaytag.model.Column)
     */
    protected void writeColumnCloser(Column column)
    {
        this.write(column.getCloseTag(), this.out);
    }

    /**
     * Writes to a JSP page an HTML table row that has no columns.
     * @see org.displaytag.render.TableWriterTemplate#writeRowWithNoColumns(java.lang.String)
     */
    protected void writeRowWithNoColumns(String rowValue)
    {
        this.write(TagConstants.TAG_TD_OPEN, this.out);
        this.write(rowValue, this.out);
        this.write(TagConstants.TAG_TD_CLOSE, this.out);
    }

    /**
     * Writes an HTML table's row-closing tag to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeRowCloser(org.displaytag.model.Row)
     */
    protected void writeRowCloser(Row row)
    {
        this.write(row.getCloseTag(), this.out);
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowFinish(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowFinish(TableModel model)
    {
        String endRow = model.getTableDecorator().finishRow();
        if (endRow != null)
        {
            this.write(endRow, this.out);
        }
    }

    /**
     * Writes an HTML message to a JSP page explaining that the table model contains no data.
     * @see org.displaytag.render.TableWriterTemplate#writeEmptyListMessage(java.lang.String)
     */
    protected void writeEmptyListMessage(String emptyListMessage)
    {
        this.write(emptyListMessage, out);
    }

    /**
     * Writes a HTML table column value to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeColumnValue(java.lang.String,org.displaytag.model.Column)
     */
    protected void writeColumnValue(String value, Column column)
    {
        this.write(value, out);
    }

    /**
     * Writes an HTML message to a JSP page explaining that the row contains no data.
     * @see org.displaytag.render.TableWriterTemplate#writeEmptyListRowMessage(java.lang.String)
     */
    protected void writeEmptyListRowMessage(String message)
    {
        this.write(message, out);
    }

    /**
     * Utility method. Write a string to the given JspWriter
     * @param string String
     * @param out JspWriter
     */
    private void write(String string, JspWriter out)
    {
        this.tableTag.write(string, out);
    }

    public void writeTable(TableModel model, String id) throws JspException {
        super.writeTable(model, id);
        if (tableTag.getVarTotals() != null){
            tableTag.getPageContext().setAttribute(tableTag.getVarTotals(), tableTag.getTotals());
        }                                                                                                 
    }


}
