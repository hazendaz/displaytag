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

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.itextpdf.text.pdf.PdfReader;

/**
 * Created by IntelliJ IDEA.
 *
 * @author rapruitt Date: May 19, 2010 Time: 8:31:54 PM
 */
class XslTransformerTest {

    /**
     * Test main test.
     *
     * @throws Exception
     *             the exception
     */
    @Test
    void testMainTest() throws Exception {
        final File f = File.createTempFile("inline", "pdf");
        final String styleSheetPath = "/org/displaytag/export/asFo_us.xsl";
        FopExportView.transform(XslTransformerTest.XML, styleSheetPath, f);
        final PdfReader reader = new PdfReader(f.getAbsolutePath());
        // byte[] page = reader.getPageContent(1);
        Assertions.assertEquals(1, reader.getNumberOfPages(), "Expected a valid pdf file with a single page");

    }

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     *
     * @throws Exception
     *             the exception
     */
    public static void main(final String[] args) throws Exception {
        XslTransformerTest.script();
    }

    /**
     * Script.
     *
     * @throws Exception
     *             the exception
     */
    public static void script() throws Exception {
        final Source src = new StreamSource(new StringReader(XslTransformerTest.XML));
        final String styleSheetPath = "/org/displaytag/export/asFo_us.xsl";
        final InputStream styleSheetStream = FopExportView.class.getResourceAsStream(styleSheetPath);

        final Source xslt = new StreamSource(styleSheetStream);
        final TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = factory.newTransformer(xslt);
        } catch (final TransformerConfigurationException e) {
            throw new JspException("Cannot configure pdf export " + e.getMessage(), e); //$NON-NLS-1$
        }
        final StreamResult debugRes = new StreamResult(new StringWriter());
        try {
            transformer.transform(src, debugRes);
        } catch (final TransformerException e) {
            throw new JspException("error creating pdf output " + e.getMessage(), e); //$NON-NLS-1$
        }
        System.out.println(debugRes.getWriter());
        FopExportView.transform(XslTransformerTest.XML, styleSheetPath, new File("/Users/andy/test.pdf"));
    }

    /** The Constant XML. */
    public static final String XML = "<table>\n" + "    <header>\n" + "        <header-cell >AntColumn</header-cell>\n"
            + "        <header-cell >BeeColumn</header-cell>\n" + "        <header-cell >Column3</header-cell>\n"
            + "        <header-cell >BeeColumn</header-cell>\n" + "    </header>\n" + "    <data>\n"
            + "<subgroup grouped-by=\"0\">\n" + "    <subgroup grouped-by=\"1\">\n"
            + "        <subgroup grouped-by=\"2\">\n" + "            <row>\n"
            + "                <cell grouped=\"true\">Ant</cell>\n"
            + "                <cell grouped=\"true\">Bee</cell>\n"
            + "                <cell text-align=\"right\">2</cell>\n" + "                <cell>Bee</cell>\n"
            + "            </row>\n" + "            <row>\n" + "                <cell grouped=\"true\">Ant</cell>\n"
            + "                <cell grouped=\"true\">Bee</cell>\n"
            + "                <cell text-align=\"right\">3</cell>\n" + "                <cell>Bee</cell>\n"
            + "            </row>\n" + "            <subtotal>\n" + "                <subtotal-cell></subtotal-cell>\n"
            + "                <subtotal-cell></subtotal-cell>\n"
            + "                <subtotal-cell text-align=\"right\">5</subtotal-cell>\n"
            + "                <subtotal-cell></subtotal-cell>\n" + "            </subtotal>\n"
            + "        </subgroup>\n" + "        <subgroup grouped-by=\"2\">\n" + "            <row>\n"
            + "                <cell grouped=\"true\">Ant</cell>\n"
            + "                <cell grouped=\"true\">Beetle</cell>\n"
            + "                <cell text-align=\"right\">7</cell>\n" + "                <cell>Beetle</cell>\n"
            + "            </row>\n" + "            <subtotal>\n" + "                <subtotal-cell></subtotal-cell>\n"
            + "                <subtotal-cell></subtotal-cell>\n"
            + "                <subtotal-cell text-align=\"right\">7</subtotal-cell>\n"
            + "                <subtotal-cell></subtotal-cell>\n" + "            </subtotal>\n"
            + "        </subgroup>\n" + "        <subtotal>\n" + "            <subtotal-cell></subtotal-cell>\n"
            + "            <subtotal-cell></subtotal-cell>\n"
            + "            <subtotal-cell text-align=\"right\">12</subtotal-cell>\n"
            + "            <subtotal-cell></subtotal-cell>\n" + "        </subtotal>\n" + "    </subgroup>\n"
            + "        <subgroup grouped-by=\"1\">\n" + "            <subgroup grouped-by=\"2\">\n"
            + "                 <row>\n" + "                    <cell grouped=\"true\">Asp</cell>\n"
            + "                    <cell grouped=\"true\">Beetle</cell>\n"
            + "                    <cell text-align=\"right\">11</cell>\n" + "                    <cell>Beetle</cell>\n"
            + "                </row>\n" + "                <subtotal>\n"
            + "                    <subtotal-cell></subtotal-cell>\n"
            + "                    <subtotal-cell></subtotal-cell>\n"
            + "                    <subtotal-cell text-align=\"right\">11</subtotal-cell>\n"
            + "                    <subtotal-cell></subtotal-cell>\n" + "                </subtotal>\n"
            + "            </subgroup>\n" + "            <subtotal>\n"
            + "                <subtotal-cell></subtotal-cell>\n" + "                <subtotal-cell></subtotal-cell>\n"
            + "                <subtotal-cell text-align=\"right\">11</subtotal-cell>\n"
            + "                <subtotal-cell></subtotal-cell>\n" + "            </subtotal>\n"
            + "        </subgroup>\n" + "\n" + "        <subtotal>\n" + "            <subtotal-cell></subtotal-cell>\n"
            + "            <subtotal-cell></subtotal-cell>\n"
            + "            <subtotal-cell text-align=\"right\">23</subtotal-cell>\n"
            + "            <subtotal-cell></subtotal-cell>\n" + "        </subtotal>\n" + "\n" + "        </subgroup>\n"
            + "\n" + "    </data>\n" + "</table>";
}
