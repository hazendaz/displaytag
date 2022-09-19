/*
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
package org.displaytag.export;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.model.Column;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.render.TableTotaler;
import org.displaytag.render.TableWriterAdapter;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;

/**
 * Writes the table as an XML file, including any totals and grouping information. Used by the FOP export.
 *
 * @author rapruitt Date: Mar 10, 2010 Time: 4:21:28 PM
 *
 * @see FopExportView
 */

/*
 * Sample fragment <table> <header> <header-cell >AntColumn</header-cell> </header> <data> <subgroup grouped-by="0">
 * <row> <cell grouped="true">Ant</cell> </row> <subtotal
 */
public class XmlTotalsWriter extends TableWriterAdapter {

    /** The style pat. */
    Pattern stylePat = Pattern.compile("\\s*?([\\w\\-]+?)\\s*?:\\s*?([\\w\\-]+?)(?:;|$)");

    /** The xml. */
    protected StringBuilder xml = new StringBuilder();

    /** The current grouping value by group. */
    Map<Integer, String> currentGroupingValueByGroup = new HashMap<>();

    /** The group id. */
    Integer groupId;

    /** The current grouping level. */
    int currentGroupingLevel = 0;

    /** The max word length. */
    int maxWordLength = 15;

    /** The Constant NOOP. */
    public static final TableDecorator NOOP = new TableDecorator() {
        @Override
        public String displayGroupedValue(final String cellValue, final short groupingStatus, final int columnNumber) {
            return cellValue;
        }
    };

    /**
     * Instantiates a new xml totals writer.
     *
     * @param m
     *            the m
     */
    public XmlTotalsWriter(final TableModel m) {
        this.setModel(m);
    }

    /**
     * Sets the model.
     *
     * @param m
     *            the new model
     */
    public void setModel(final TableModel m) {
        m.setTableDecorator(XmlTotalsWriter.NOOP);
        if (m.getTotaler() == null || m.getTotaler() == TableTotaler.NULL) {
            final TableTotaler tt = new TableTotaler();
            tt.init(m);
            m.setTotaler(tt);
        }
    }

    /**
     * <code>TableModel</code>.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     */

    @Override
    protected void writeTableOpener(final TableModel model) throws Exception {
        this.xml.append("<?xml version=\"1.0\"?>\n<table>\n"); //$NON-NLS-1$
    }

    /**
     * Write subgroup start.
     *
     * @param model
     *            the model
     */
    @Override
    protected void writeSubgroupStart(final TableModel model) {
        final TableTotaler tt = model.getTotaler();

        // for each newly opened subgroup we need to output the opener, in order;
        // so we need to know somehow which groups are new since we last wrote out openers; how about we track a list of
        // the
        // already opened groups, and ask the tt for a list of all known groups?
        for (final int i : tt.getOpenedColumns()) {
            this.xml.append("<subgroup grouped-by=\"").append(i).append("\">");

        }
    }

    /**
     * Write subgroup stop.
     *
     * @param model
     *            the model
     */
    @Override
    protected void writeSubgroupStop(final TableModel model) {
        final List<Integer> closed = model.getTotaler().getClosedColumns();
        if (!closed.isEmpty()) {
            // write subtotals
            this.writeSubtotals(model, closed);
        }
    }

    /**
     * Write subtotals.
     *
     * @param model
     *            the model
     * @param closedColumns
     *            the closed columns
     */
    protected void writeSubtotals(final TableModel model, final List<Integer> closedColumns) {
        final TableTotaler tt = model.getTotaler();
        Collections.reverse(closedColumns);
        for (final int i : closedColumns) {
            this.xml.append("<subtotal>\n");
            for (final HeaderCell cell : model.getHeaderCellList()) {
                if (cell.isTotaled()) {
                    this.xml.append("\t<subtotal-cell ");
                    final HtmlAttributeMap atts = cell.getHtmlAttributes();
                    this.writeAttributes(atts);
                    this.xml.append('>');
                    this.cdata(tt.formatTotal(cell, tt.getTotalForColumn(cell.getColumnNumber(), tt.asGroup(i))));
                    this.xml.append("</subtotal-cell>");
                } else {
                    this.xml.append("\t<subtotal-cell/>");
                }
            }
            this.xml.append("\n</subtotal>\n");
            this.writeExtraGroupInfo(model, i);
            this.xml.append("</subgroup>\n");
        }
    }

    /**
     * Write extra group info.
     *
     * @param model
     *            the model
     * @param groupColumn
     *            the group column
     */
    protected void writeExtraGroupInfo(final TableModel model, final int groupColumn) {
        // Not Implemented
    }

    /**
     * Write attributes.
     *
     * @param atts
     *            the atts
     */
    protected void writeAttributes(final HtmlAttributeMap atts) {
        if (atts != null) {
            final String style = (String) atts.get(TagConstants.ATTRIBUTE_STYLE);
            if (StringUtils.isNotBlank(style)) {
                final Matcher m = this.stylePat.matcher(style);
                while (m.find()) {
                    this.xml.append(m.group(1));
                    this.xml.append("=\"");
                    this.xml.append(m.group(2));
                    this.xml.append("\" ");
                }
            }
            final MultipleHtmlAttribute cssClass = (MultipleHtmlAttribute) atts.get(TagConstants.ATTRIBUTE_CLASS);
            if (cssClass != null && !cssClass.isEmpty()) {
                this.xml.append(" class");
                this.xml.append("=\"");
                this.xml.append(cssClass.toString());
                this.xml.append("\"");
            }
        }

    }

    /**
     * Write column opener.
     *
     * @param column
     *            the column
     *
     * @throws Exception
     *             the exception
     */
    @Override
    protected void writeColumnOpener(final Column column) throws Exception {
        final boolean grouped = column.getHeaderCell().getGroup() >= this.currentGroupingLevel;
        String attr = "";
        if (grouped) {
            attr = " grouped=\"true\" ";
        }
        this.xml.append("\t<cell ");
        this.xml.append(attr);
        final HtmlAttributeMap atts = column.getHeaderCell().getHtmlAttributes();
        this.writeAttributes(atts);
        this.xml.append(">");
    }

    /**
     * Write column closer.
     *
     * @param column
     *            the column
     *
     * @throws Exception
     *             the exception
     */
    @Override
    protected void writeColumnCloser(final Column column) throws Exception {
        this.xml.append("</cell>\n");
    }

    /**
     * Write table header.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     */
    @Override
    protected void writeTableHeader(final TableModel model) throws Exception {
        final Iterator<HeaderCell> iterator = model.getHeaderCellList().iterator();

        this.xml.append("<header>\n");
        this.xml.append("\n");
        while (iterator.hasNext()) {
            // get the header cell
            final HeaderCell headerCell = iterator.next();
            this.xml.append("<header-cell>");
            this.cdata(headerCell.getTitle());
            this.xml.append("</header-cell>\n");
        }
        this.xml.append("</header>\n");
        this.xml.append("<data>");
        this.xml.append("<subgroup grouped-by=\"" + TableTotaler.WHOLE_TABLE + "\">");
    }

    // just use the hyphenate support from fop -- http://xmlgraphics.apache.org/fop/1.0/hyphenation.html

    /**
     * Cdata.
     *
     * @param str
     *            the str
     */
    protected void cdata(final Object str) {
        this.xml.append("<![CDATA[");
        final String defStr = StringUtils.defaultString("" + str);
        this.xml.append(defStr);
        this.xml.append("]]>");
    }

    /**
     * Write decorated table finish.
     *
     * @param model
     *            the model
     */
    @Override
    protected void writeDecoratedTableFinish(final TableModel model) {
        model.getTableDecorator().finish();
    }

    /**
     * Write decorated row start.
     *
     * @param model
     *            the model
     *
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowStart(final TableModel model) {
        this.xml.append(StringUtils.defaultString(model.getTableDecorator().startRow()));
    }

    /**
     * Write table closer.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     */
    @Override
    protected void writeTableCloser(final TableModel model) throws Exception {
        this.xml.append("</table>"); //$NON-NLS-1$
    }

    /**
     * Write decorated row finish.
     *
     * @param model
     *            the model
     */
    @Override
    protected void writeDecoratedRowFinish(final TableModel model) {
        this.xml.append(StringUtils.defaultString(model.getTableDecorator().finishRow()));
    }

    /**
     * Write row opener.
     *
     * @param row
     *            the row
     *
     * @throws Exception
     *             the exception
     */
    @Override
    protected void writeRowOpener(final Row row) throws Exception {
        this.xml.append("\n<row>\n"); //$NON-NLS-1$
    }

    /**
     * Write column value.
     *
     * @param value
     *            the value
     * @param column
     *            the column
     *
     * @throws Exception
     *             the exception
     */
    @Override
    protected void writeColumnValue(final Object value, final Column column) throws Exception {
        final Object rawValue = column.getValue(true);
        this.cdata(rawValue);
    }

    /**
     * Write row with no columns.
     *
     * @param value
     *            the value
     *
     * @throws Exception
     *             the exception
     */
    @Override
    protected void writeRowWithNoColumns(final String value) throws Exception {
        this.xml.append("<row/>\n"); //$NON-NLS-1$
    }

    /**
     * Write row closer.
     *
     * @param row
     *            the row
     *
     * @throws Exception
     *             the exception
     */
    @Override
    protected void writeRowCloser(final Row row) throws Exception {
        this.xml.append("</row>\n"); //$NON-NLS-1$
    }

    /**
     * Write table body closer.
     *
     * @param model
     *            the model
     */
    @Override
    protected void writeTableBodyCloser(final TableModel model) {
        this.xml.append("\n<!-- grand totals -->\n");
        this.writeSubtotals(model, Arrays.asList(TableTotaler.WHOLE_TABLE));
        this.xml.append("</data>"); //$NON-NLS-1$
    }

    /**
     * Gets the xml.
     *
     * @return the xml
     */
    public String getXml() {

        return this.xml.toString();
    }
}
