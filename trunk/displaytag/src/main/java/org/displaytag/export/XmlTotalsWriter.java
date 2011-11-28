package org.displaytag.export;

import org.displaytag.model.*;
import org.displaytag.render.TableWriterAdapter;
import org.displaytag.render.TableTotaler;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.TagConstants;
import org.displaytag.util.MultipleHtmlAttribute;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Writes the table as an XML file, including any totals and grouping information. Used by the FOP export.
 * @author  rapruitt
 * Date: Mar 10, 2010
 * Time: 4:21:28 PM
 * @see FopExportView
 */

/*
Sample fragment
<table>
    <header>
        <header-cell >AntColumn</header-cell>
    </header>
    <data>
    <subgroup grouped-by="0">
             <row>
                <cell grouped="true">Ant</cell>
              </row>
              <subtotal


* */
public class XmlTotalsWriter extends TableWriterAdapter
{
    Pattern stylePat = Pattern.compile("\\s*?([\\w\\-]+?)\\s*?:\\s*?([\\w\\-]+?)(?:;|$)");
    protected StringBuffer xml = new StringBuffer();
    Map<Integer,String> currentGroupingValueByGroup = new HashMap<Integer,String>();
    Integer groupId;
    int currentGroupingLevel = 0;
    int maxWordLength = 15;
    public static final TableDecorator NOOP = new TableDecorator()
        {
            @Override
            public String displayGroupedValue(String cellValue, short groupingStatus, int columnNumber)
            {
                return cellValue;
            }
        };

    public XmlTotalsWriter(TableModel m)
    {
        setModel(m);
    }


    public void setModel(TableModel m)
    {
        m.setTableDecorator(NOOP);
        if (m.getTotaler() == null || m.getTotaler() == TableTotaler.NULL)
        {
            TableTotaler tt = new TableTotaler();
            tt.init(m);
            m.setTotaler(tt);
        }
    }


    /**
     * <code>TableModel</code>
     */

    @Override
    protected void writeTableOpener(TableModel model) throws Exception
    {
        xml.append( "<?xml version=\"1.0\"?>\n<table>\n"); //$NON-NLS-1$
    }

    @Override
    protected void writeSubgroupStart(TableModel model)
    {
        TableTotaler tt = model.getTotaler();

        // for each newly opened subgroup we need to output the opener, in order;
        //   so we need to know somehow which groups are new since we last wrote out openers; how about we track a list of the
        //    already opened groups, and ask the tt for a list of all known groups?
        for (int i : tt.getOpenedColumns())
        {
            xml.append("<subgroup grouped-by=\"").append(i).append("\">");

        }
    }

    @Override
    protected void writeSubgroupStop(TableModel model)
    {
        List<Integer> closed = model.getTotaler().getClosedColumns();
        if (! closed.isEmpty())
        {
            // write subtotals
            writeSubtotals(model, closed);
         }
    }

    protected void writeSubtotals(TableModel model, List<Integer> closedColumns)
    {
        TableTotaler tt = model.getTotaler();
        Collections.reverse(closedColumns);
        for (int i : closedColumns)
        {
            xml.append("<subtotal>\n");
            for (HeaderCell cell : model.getHeaderCellList())
            {
                if (cell.isTotaled())
                {
                    xml.append("\t<subtotal-cell ");
                    HtmlAttributeMap atts = cell.getHtmlAttributes();
                    writeAttributes(atts);
                    xml.append('>');
                    cdata( tt.formatTotal(cell, tt.getTotalForColumn(cell.getColumnNumber(), tt.asGroup(i))));
                    xml.append("</subtotal-cell>");
                }
                else
                {
                    xml.append("\t<subtotal-cell/>");
                }
            }
            xml.append("\n</subtotal>\n");
            writeExtraGroupInfo(model, i);
            xml.append("</subgroup>\n");
        }
    }

    protected void writeExtraGroupInfo(TableModel model, int groupColumn){

    }

    protected void writeAttributes(HtmlAttributeMap atts)
    {
        if (atts != null)
        {
            String style = (String)atts.get(TagConstants.ATTRIBUTE_STYLE);
            if (StringUtils.isNotBlank(style))
            {
                Matcher m  = stylePat.matcher(style);
                while (m.find())
                {
                    xml.append(m.group(1));
                    xml.append("=\"");
                    xml.append(m.group(2));
                    xml.append("\" ");
                }
            }
            MultipleHtmlAttribute cssClass = (MultipleHtmlAttribute)atts.get(TagConstants.ATTRIBUTE_CLASS);
            if (cssClass != null && !cssClass.isEmpty())
            {
                xml.append(" class");
                xml.append("=\"");
                xml.append(cssClass.toString());
                xml.append("\"");
            }
        }

    }

    @Override
    protected void writeColumnOpener(Column column) throws Exception
    {
        boolean grouped = column.getHeaderCell().getGroup() >= currentGroupingLevel ;
        String attr = "";
        if (grouped)
        {
            attr = " grouped=\"true\" ";
        }
        xml.append( "\t<cell " );
        xml.append( attr );
        HtmlAttributeMap atts = column.getHeaderCell().getHtmlAttributes();
        writeAttributes(atts);
        xml.append( ">" );
    }

    @Override
    protected void writeColumnCloser(Column column) throws Exception
    {
        xml.append( "</cell>\n" );
    }

    @Override
    protected void writeTableHeader(TableModel model) throws Exception
    {
        Iterator<HeaderCell> iterator = model.getHeaderCellList().iterator();

        xml.append("<header>\n");
        xml.append("\n");
        while (iterator.hasNext())
        {
            // get the header cell
            HeaderCell headerCell = iterator.next();
            xml.append("<header-cell>");
            cdata(headerCell.getTitle());
            xml.append("</header-cell>\n");
        }
        xml.append("</header>\n");
        xml.append("<data>");
        xml.append("<subgroup grouped-by=\""+TableTotaler.WHOLE_TABLE+"\">");
    }


    // just use the hyphenate support from fop -- http://xmlgraphics.apache.org/fop/1.0/hyphenation.html

    protected void cdata(Object str)
    {
        xml.append("<![CDATA[");
        String defStr = StringUtils.defaultString(""+str);
        xml.append(defStr);
        xml.append("]]>");
    }

    @Override
    protected void writeDecoratedTableFinish(TableModel model)
    {
        model.getTableDecorator().finish();
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowStart(TableModel model)
    {
        xml.append(StringUtils.defaultString(model.getTableDecorator().startRow()));
    }

    @Override
    protected void writeTableCloser(TableModel model) throws Exception
    {
        xml.append("</table>");                            //$NON-NLS-1$
    }

    protected void writeDecoratedRowFinish(TableModel model)
    {
        xml.append(StringUtils.defaultString(model.getTableDecorator().finishRow()));
    }

    protected void writeRowOpener(Row row) throws Exception
    {
        xml.append("\n<row>\n");                                          //$NON-NLS-1$
    }

    protected void writeColumnValue(Object value, Column column) throws Exception
    {
        Object rawValue = column.getValue(true);
        cdata(rawValue);
    }

    protected void writeRowWithNoColumns(String value) throws Exception
    {
        xml.append("<row/>\n");  //$NON-NLS-1$
    }

    protected void writeRowCloser(Row row) throws Exception
    {
        xml.append("</row>\n");               //$NON-NLS-1$
    }

    protected void writeTableBodyCloser(TableModel model)
    {
        xml.append("\n<!-- grand totals -->\n");
        writeSubtotals(model, Arrays.asList(TableTotaler.WHOLE_TABLE) );
        xml.append("</data>");     //$NON-NLS-1$
    }

    public String getXml()
    {

        return xml.toString();
    }
}
