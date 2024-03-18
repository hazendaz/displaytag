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
package org.displaytag.render;

import java.io.File;

import org.displaytag.export.FopExportView;
import org.displaytag.export.XmlTotalsWriter;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.properties.TableProperties;
import org.displaytag.test.KnownValue;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.util.TagConstants;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj3.XmlAssert;

/**
 * User: rapruitt Date: May 31, 2010 Time: 1:08:02 PM.
 */
public class TableTotalerTest {

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
        model.addRow(new Row(new KnownValue(), 0));
        model.addRow(new Row(new KnownValue(), 1));
        final KnownValue third = new KnownValue();
        third.beeValue = "BeeAnt";
        third.twoValue = 3;
        third.camelValue = "arealllylongtextstringthatshouldforceafailuretowrapontheoutputline";
        // third.camelValue = "a reallly long text string that should force a failure to wrap on the output line";
        model.addRow(new Row(third, 2));
        final KnownValue antv = new KnownValue();
        antv.antValue = "bee";
        antv.twoValue = 4;
        model.addRow(new Row(antv, 3));
        return model;
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
        tt.init(m);
        final XmlTotalsWriter tw = new XmlTotalsWriter(m);
        tw.writeTable(m, "safd");
        final String xml = tw.getXml();
        XmlAssert xmlAssert = XmlAssert.assertThat(xml);
        xmlAssert.valueByXPath("//subgroup[@grouped-by=0]/subtotal/subtotal-cell[4]").isEqualTo("11.0");
        xmlAssert.valueByXPath("//subgroup[@grouped-by=1]/subtotal/subtotal-cell[4]").isEqualTo("7.0");
        xmlAssert.valueByXPath("//subgroup[@grouped-by=2]/subtotal/subtotal-cell[4]").isEqualTo("4.0");
        xmlAssert.hasXPath("//cell[@text-align='right']");

        final File f = File.createTempFile("displaytag", "pdf");

        FopExportView.transform(tw.getXml(), "/org/displaytag/export/asFo_us.xsl", f);

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

    }
}
