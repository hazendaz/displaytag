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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.properties.TableProperties;
import org.displaytag.render.TableTotaler;
import org.displaytag.test.KnownValue;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * The Class SubtotaledExcelTest.
 */
class SubtotaledExcelTest {

    /**
     * Gets the model.
     *
     * @return the model
     */
    TableModel getModel() {
        final TableProperties props = TableProperties.getInstance(null);
        final TableModel model = new TableModel(props, "", null);
        model.setRowListPage(model.getRowListFull());
        {
            final HeaderCell ha = new HeaderCell();
            ha.setTitle("ColumnAnt");
            ha.setBeanPropertyName("ant");
            ha.setHtmlAttributes(new HtmlAttributeMap());
            ha.setGroup(1);
            model.addColumnHeader(ha);
        }
        {
            final HeaderCell hb = new HeaderCell();
            hb.setTitle("Column2");
            hb.setHtmlAttributes(new HtmlAttributeMap());
            hb.setBeanPropertyName("bee");
            hb.setGroup(2);
            model.addColumnHeader(hb);
        }
        {
            final HeaderCell hb = new HeaderCell();
            hb.setTitle("long");
            hb.setBeanPropertyName("camel");
            hb.setTotaled(false);
            final HtmlAttributeMap mm = new HtmlAttributeMap();
            mm.put(TagConstants.ATTRIBUTE_STYLE, "font-weight: bold; text-align: right");
            mm.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute("right rowish"));
            hb.setHtmlAttributes(mm);
            model.addColumnHeader(hb);
        }
        {
            final HeaderCell hb = new HeaderCell();
            hb.setTitle("Column3");
            hb.setHtmlAttributes(new HtmlAttributeMap());
            hb.setBeanPropertyName("two");
            hb.setTotaled(true);
            model.addColumnHeader(hb);
        }
        {
            final HeaderCell hb = new HeaderCell();
            hb.setTitle("DateColumn");
            hb.setHtmlAttributes(new HtmlAttributeMap());
            hb.setBeanPropertyName("date");
            model.addColumnHeader(hb);
        }
        model.addRow(new Row(new KnownValue(), 0));
        model.addRow(new Row(new KnownValue(), 0));
        model.addRow(new Row(new KnownValue(), 1));
        final KnownValue third = new KnownValue();
        third.beeValue = "BeeAnt";
        third.twoValue = 3;
        third.camelValue = "arealllylongtextstringthatshouldforceafailuretowrapontheoutputlasdfasdfddine";
        // third.camelValue = "a reallly long text string that should force a failure to wrap on the output line";
        model.addRow(new Row(third, 2));
        final KnownValue antv = new KnownValue();
        antv.antValue = "anteater";
        antv.twoValue = 4;
        model.addRow(new Row(antv, 3));
        return model;
    }

    /**
     * Test no groups.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testNoGroups() throws Exception {
        final TableModel m = this.getModel();
        for (final HeaderCell cell : m.getHeaderCellList()) {
            cell.setGroup(0);
        }
        final TableTotaler tt = new TableTotaler();
        m.setTotaler(tt);
        final HssfDoubleExportView view = new HssfDoubleExportView();
        tt.init(m);
        view.setParameters(m, true, true, true);

        final File f = File.createTempFile("nogroups", null);
        final FileOutputStream str = new FileOutputStream(f);
        view.doExport(str);
        str.flush();
        str.close();

        final FileInputStream istr = new FileInputStream(f);
        final Workbook wb = new HSSFWorkbook(istr);

        final Sheet sh = wb.getSheetAt(0);

        final Cell a2 = sh.getRow(1).getCell(0);
        final Cell b2 = sh.getRow(1).getCell(1);
        final Cell d2 = sh.getRow(1).getCell(3);
        final Cell e2 = sh.getRow(1).getCell(4);
        Assertions.assertEquals("ant", a2.getStringCellValue());
        Assertions.assertEquals("bee", b2.getStringCellValue());
        Assertions.assertEquals(KnownValue.MAY, e2.getDateCellValue());
        Assertions.assertEquals(2, (int) d2.getNumericCellValue());
        wb.close();
    }

    /**
     * Test simple totals correct.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testSimpleTotalsCorrect() throws Exception {
        final TableModel m = this.getModel();
        final TableTotaler tt = new TableTotaler();
        m.setTotaler(tt);
        final HssfDoubleExportView view = new HssfDoubleExportView();
        tt.init(m);
        view.setParameters(m, true, true, true);

        final File f = File.createTempFile("displaytag", null);
        final FileOutputStream str = new FileOutputStream(f);
        view.doExport(str);
        str.flush();
        str.close();

        final FileInputStream istr = new FileInputStream(f);
        final Workbook wb = new HSSFWorkbook(istr);

        final Sheet sh = wb.getSheetAt(0);

        final Cell a2 = sh.getRow(1).getCell(0);
        final Cell b2 = sh.getRow(1).getCell(1);
        Assertions.assertEquals("ant", a2.getStringCellValue());
        Assertions.assertEquals("", b2.getStringCellValue());

        final Cell a3 = sh.getRow(2).getCell(0);
        final Cell b3 = sh.getRow(2).getCell(1);
        final Cell c3 = sh.getRow(2).getCell(2);
        Assertions.assertEquals("", a3.getStringCellValue());
        Assertions.assertEquals("", c3.getStringCellValue());
        Assertions.assertEquals("bee", b3.getStringCellValue());

        final Cell a4 = sh.getRow(3).getCell(0);
        final Cell b4 = sh.getRow(3).getCell(1);
        final Cell c4 = sh.getRow(3).getCell(2);
        Assertions.assertEquals("", a4.getStringCellValue());
        Assertions.assertEquals("", b4.getStringCellValue());
        Assertions.assertEquals("camel", c4.getStringCellValue());

        final Cell d7 = sh.getRow(6).getCell(3);
        final Cell b7 = sh.getRow(6).getCell(1);
        Assertions.assertEquals(6, (int) d7.getNumericCellValue());
        Assertions.assertEquals("bee Total", b7.getStringCellValue());

        final Cell d10 = sh.getRow(9).getCell(3);
        final Cell b10 = sh.getRow(9).getCell(1);
        Assertions.assertEquals(3, (int) d10.getNumericCellValue());
        Assertions.assertEquals("BeeAnt Total", b10.getStringCellValue());

        // verify that the total for the entire table is correct
        // We want an overlay that gives us a model of the grouping, so
        // Ant | Bee | Value grouping
        // --------- --
        // A | B | 2 0, 1 2
        // A | B | 2
        // A | BA | 2 2, 2
        // B | B | 2
        // reduces to
        // A:B=2
        // A:BB=3
        // A=5

        // so, GroupTotal[] = getGroups(colNumber) for colNumber = 1 gives GroupTotal[a],GroupTotal[b]
        // for colNumber = 2 gives GroupTotal[a:b],GroupTotal[a:ba],GroupTotal[b:b]
        wb.close();
    }

}
