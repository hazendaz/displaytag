package org.displaytag.export;

 import org.junit.Assert;
import org.junit.Test;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.servlet.jsp.JspException;
import java.io.*;

import com.lowagie.text.pdf.PdfReader;
import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * @author rapruitt
 * Date: May 19, 2010
 * Time: 8:31:54 PM
 */
public class XslTransformerTest extends TestCase
{


    @Test
    public void testMainTest() throws Exception
    {
        File f =  File.createTempFile("inline","pdf");
        String styleSheetPath = "/org/displaytag/export/asFo_us.xsl";
        FopExportView.transform(XML, styleSheetPath, f);
        PdfReader reader = new PdfReader(f.getAbsolutePath());
//        byte[] page = reader.getPageContent(1);
        Assert.assertEquals("Expected a valid pdf file with a single page", 1, reader.getNumberOfPages());

    }

    public static void main(String[] args) throws Exception
    {
         script();
    }



    public static void script() throws Exception
    {
        Source src = new StreamSource( new StringReader(XML));
        String styleSheetPath = "/org/displaytag/export/asFo_us.xsl";
        InputStream styleSheetStream = FopExportView.class.getResourceAsStream(styleSheetPath);

        Source xslt = new StreamSource(styleSheetStream );
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try
        {
            transformer = factory.newTransformer(xslt);
        }
        catch (TransformerConfigurationException e)
        {
            throw new JspException("Cannot configure pdf export "+e.getMessage(),e);             //$NON-NLS-1$
        }
        StreamResult debugRes = new StreamResult(new StringWriter());
        try
        {
            transformer.transform(src, debugRes);
        }
        catch (TransformerException e)
        {
            throw new JspException("error creating pdf output " + e.getMessage(),e);                         //$NON-NLS-1$
        }
        System.out.println( debugRes.getWriter());
        FopExportView.transform(XML, styleSheetPath,  new File("/Users/andy/test.pdf"));
    }

    public static final String XML = "<table>\n" +
            "    <header>\n" +
            "        <header-cell >AntColumn</header-cell>\n" +
            "        <header-cell >BeeColumn</header-cell>\n" +
            "        <header-cell >Column3</header-cell>\n" +
            "        <header-cell >BeeColumn</header-cell>\n" +
            "    </header>\n" +
            "    <data>\n" +
            "<subgroup grouped-by=\"0\">\n" +
            "    <subgroup grouped-by=\"1\">\n" +
            "        <subgroup grouped-by=\"2\">\n" +
            "            <row>\n" +
            "                <cell grouped=\"true\">Ant</cell>\n" +
            "                <cell grouped=\"true\">Bee</cell>\n" +
            "                <cell text-align=\"right\">2</cell>\n" +
            "                <cell>Bee</cell>\n" +
            "            </row>\n" +
            "            <row>\n" +
            "                <cell grouped=\"true\">Ant</cell>\n" +
            "                <cell grouped=\"true\">Bee</cell>\n" +
            "                <cell text-align=\"right\">3</cell>\n" +
            "                <cell>Bee</cell>\n" +
            "            </row>\n" +
            "            <subtotal>\n" +
            "                <subtotal-cell></subtotal-cell>\n" +
            "                <subtotal-cell></subtotal-cell>\n" +
            "                <subtotal-cell text-align=\"right\">5</subtotal-cell>\n" +
            "                <subtotal-cell></subtotal-cell>\n" +
            "            </subtotal>\n" +
            "        </subgroup>\n" +
            "        <subgroup grouped-by=\"2\">\n" +
            "            <row>\n" +
            "                <cell grouped=\"true\">Ant</cell>\n" +
            "                <cell grouped=\"true\">Beetle</cell>\n" +
            "                <cell text-align=\"right\">7</cell>\n" +
            "                <cell>Beetle</cell>\n" +
            "            </row>\n" +
            "            <subtotal>\n" +
            "                <subtotal-cell></subtotal-cell>\n" +
            "                <subtotal-cell></subtotal-cell>\n" +
            "                <subtotal-cell text-align=\"right\">7</subtotal-cell>\n" +
            "                <subtotal-cell></subtotal-cell>\n" +
            "            </subtotal>\n" +
            "        </subgroup>\n" +
            "        <subtotal>\n" +
            "            <subtotal-cell></subtotal-cell>\n" +
            "            <subtotal-cell></subtotal-cell>\n" +
            "            <subtotal-cell text-align=\"right\">12</subtotal-cell>\n" +
            "            <subtotal-cell></subtotal-cell>\n" +
            "        </subtotal>\n" +
            "    </subgroup>\n" +
            "        <subgroup grouped-by=\"1\">\n" +
            "            <subgroup grouped-by=\"2\">\n" +
            "                 <row>\n" +
            "                    <cell grouped=\"true\">Asp</cell>\n" +
            "                    <cell grouped=\"true\">Beetle</cell>\n" +
            "                    <cell text-align=\"right\">11</cell>\n" +
            "                    <cell>Beetle</cell>\n" +
            "                </row>\n" +
            "                <subtotal>\n" +
            "                    <subtotal-cell></subtotal-cell>\n" +
            "                    <subtotal-cell></subtotal-cell>\n" +
            "                    <subtotal-cell text-align=\"right\">11</subtotal-cell>\n" +
            "                    <subtotal-cell></subtotal-cell>\n" +
            "                </subtotal>\n" +
            "            </subgroup>\n" +
            "            <subtotal>\n" +
            "                <subtotal-cell></subtotal-cell>\n" +
            "                <subtotal-cell></subtotal-cell>\n" +
            "                <subtotal-cell text-align=\"right\">11</subtotal-cell>\n" +
            "                <subtotal-cell></subtotal-cell>\n" +
            "            </subtotal>\n" +
            "        </subgroup>\n" +
            "\n" +
            "        <subtotal>\n" +
            "            <subtotal-cell></subtotal-cell>\n" +
            "            <subtotal-cell></subtotal-cell>\n" +
            "            <subtotal-cell text-align=\"right\">23</subtotal-cell>\n" +
            "            <subtotal-cell></subtotal-cell>\n" +
            "        </subtotal>\n" +
            "\n" +
            "        </subgroup>\n" +
            "\n" +
            "    </data>\n" +
            "</table>" ;
}
