package org.displaytag.render;

import junit.framework.TestCase;
import org.displaytag.model.TableModel;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.properties.TableProperties;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.TagConstants;
import org.displaytag.util.MultipleHtmlAttribute;
import org.displaytag.test.KnownValue;
import org.displaytag.export.XmlTotalsWriter;
import org.displaytag.export.XslTransformerTest;
import org.displaytag.export.FopExportView;
import org.custommonkey.xmlunit.XMLTestCase;

import java.io.File;


/**
 * User: rapruitt
 * Date: May 31, 2010
 * Time: 1:08:02 PM
 */
public class TableTotalerTest  extends XMLTestCase
{

    TableModel getModel(){
        TableProperties props =  TableProperties.getInstance(null);
        TableModel model = new TableModel(props, "", null);
        model.setRowListPage(model.getRowListFull());
        {
            HeaderCell ha = new HeaderCell();
            ha.setTitle("ColumnAnt");
            ha.setBeanPropertyName("ant");
            ha.setHtmlAttributes( new HtmlAttributeMap());
            ha.setGroup(1);
            model.addColumnHeader(ha);
        }
        {
            HeaderCell hb = new HeaderCell();
            hb.setTitle("Column2");
            hb.setHtmlAttributes( new HtmlAttributeMap());
            hb.setBeanPropertyName("bee");
            hb.setGroup(2);
            model.addColumnHeader(hb);
        }
        {
            HeaderCell hb = new HeaderCell();
            hb.setTitle("long");
            hb.setBeanPropertyName("camel");
            hb.setTotaled(false);
            HtmlAttributeMap mm = new HtmlAttributeMap();
            mm.put(TagConstants.ATTRIBUTE_STYLE, "font-weight: bold; text-align: right");
            mm.put(TagConstants.ATTRIBUTE_CLASS, new MultipleHtmlAttribute("right rowish"));
            hb.setHtmlAttributes(mm);
            model.addColumnHeader(hb);
        }
        {
            HeaderCell hb = new HeaderCell();
            hb.setTitle("Column3");
            hb.setHtmlAttributes( new HtmlAttributeMap());
            hb.setBeanPropertyName("two");
            hb.setTotaled(true);
            model.addColumnHeader(hb);
        }
        model.addRow(new Row(new KnownValue(), 0));
        model.addRow(new Row(new KnownValue(), 1));
        KnownValue third = new KnownValue();
        third.beeValue = "BeeAnt";
        third.twoValue = 3;
        third.camelValue = "arealllylongtextstringthatshouldforceafailuretowrapontheoutputline";
//        third.camelValue = "a reallly long text string that should force a failure to wrap on the output line";
        model.addRow(new Row(third, 2));
        KnownValue antv = new KnownValue();
        antv.antValue = "bee";
        antv.twoValue = 4;
        model.addRow( new Row(antv, 3));
        return model;
    }






    public void testSimpleTotalsCorrect() throws Exception
    {
        TableModel m = getModel();
        TableTotaler tt = new TableTotaler();
        m.setTotaler(tt);
        tt.init(m);
        XmlTotalsWriter tw = new XmlTotalsWriter(m);
        tw.writeTable(m, "safd");
        System.out.println(""+tw.getXml());
        String xml = tw.getXml();
        assertXpathEvaluatesTo("11.0", "//subgroup[@grouped-by=0]/subtotal/subtotal-cell[4]", xml);
        assertXpathEvaluatesTo("7.0", "//subgroup[@grouped-by=1]/subtotal/subtotal-cell[4]", xml);
        assertXpathEvaluatesTo("4.0", "//subgroup[@grouped-by=2]/subtotal/subtotal-cell[4]", xml);
        assertXpathExists( "//cell[@text-align='right']", xml);


        File f = File.createTempFile("displaytag","pdf");

        FopExportView.transform(tw.getXml(), "/org/displaytag/export/asFo_us.xsl",f );

        // verify that the total for the entire table is correct
        // We want an overlay that gives us a model of the grouping, so
        // Ant | Bee | Value                     grouping
        // ---------                                 --
        // A   | B   | 2                             0, 1 2
        // A   | B   | 2
        // A   | BA  | 2                             2, 2
        // B   | B   | 2
        // reduces to
        // A:B=2
        // A:BB=3
        // A=5

        // so, GroupTotal[] = getGroups(colNumber)  for colNumber = 1 gives GroupTotal[a],GroupTotal[b]
        //                                     for colNumber = 2 gives GroupTotal[a:b],GroupTotal[a:ba],GroupTotal[b:b]



    }
}
