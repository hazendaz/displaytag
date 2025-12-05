/*
 * Copyright (C) 2002-2025 Fabrizio Giustina, the Displaytag team
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

import jakarta.servlet.jsp.JspException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.fo.ValidationException;
import org.displaytag.model.TableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exports the data to a totaled xml format, and then transforms that data using XSL-FO to a pdf. The stylesheet can be
 * fed in as a string from the property export.pdf.fo.stylesheetbody, or you can use a default stylesheet named by the
 * property export.pdf.fo.stylesheet. When you are developing a stylesheet, this class will output the raw FO if you set
 * your log level to debug, which is very handy if you are getting errors or unexpected pdf output. See asFo_us.xsl for
 * a sample XSL-FO stylesheet. The basic structure of the intermediate XML is
 *
 * <pre>
 * &lt;table&gt;
 *   &lt;header&gt;
 *     &lt;header-cell&gt;AntColumn&lt;/header-cell&gt;
 *   &lt;/header&gt;
 *   &lt;data&gt;
 *     &lt;subgroup grouped-by="0"&gt;
 *       &lt;row&gt;
 *         &lt;cell grouped="true"&gt;Ant&lt;/cell&gt;
 *       &lt;/row&gt;
 *       &lt;subtotal&gt;
 *         &lt;subtotal-cell&gt; &lt;/subtotal-cell&gt;
 *       &lt;/subtotal&gt;
 *     &lt;/subgroup&gt;
 *   &lt;/data&gt;
 * &lt;/table&gt;
 * </pre>
 *
 * @see FopExportView#SPECIFIC_STYLESHEET the property that contains the text of a stylesheet
 * @see FopExportView#DEFAULT_STYLESHEET the default stylesheet location
 * @see XmlTotalsWriter
 */
public class FopExportView implements BinaryExportView {

    /** The log. */
    private static Logger log = LoggerFactory.getLogger(FopExportView.class);

    /**
     * Default stylesheet.
     */
    public static final String DEFAULT_STYLESHEET = "export.pdf.fo.stylesheet"; //$NON-NLS-1$

    /**
     * A stylesheet as a string on a property.
     */
    public static final String SPECIFIC_STYLESHEET = "export.pdf.fo.stylesheetbody"; //$NON-NLS-1$

    /**
     * TableModel to render.
     */
    protected TableModel model;

    /**
     * Sets the parameters.
     *
     * @param tableModel
     *            the table model
     * @param exportFullList
     *            the export full list
     * @param includeHeader
     *            the include header
     * @param decorateValues
     *            the decorate values
     *
     * @see org.displaytag.export.ExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
    @Override
    public void setParameters(final TableModel tableModel, final boolean exportFullList, final boolean includeHeader,
            final boolean decorateValues) {
        this.model = tableModel;
    }

    /**
     * Gets the mime type.
     *
     * @return "application/pdf"
     *
     * @see org.displaytag.export.BaseExportView#getMimeType()
     */
    @Override
    public String getMimeType() {
        return "application/pdf"; //$NON-NLS-1$
    }

    /**
     * Load the stylesheet.
     *
     * @return the stylesheet
     *
     * @throws IOException
     *             if we cannot locate it
     */
    public InputStream getStyleSheet() throws IOException {

        InputStream styleSheetStream;
        final String styleSheetString = this.model.getProperties().getProperty(FopExportView.SPECIFIC_STYLESHEET);
        if (StringUtils.isNotEmpty(styleSheetString)) {
            styleSheetStream = new ByteArrayInputStream(styleSheetString.getBytes(StandardCharsets.UTF_8));
        } else {
            final String styleSheetPath = this.model.getProperties().getProperty(FopExportView.DEFAULT_STYLESHEET);
            styleSheetStream = this.getClass().getResourceAsStream(styleSheetPath);
            if (styleSheetStream == null) {
                throw new IOException("Cannot locate stylesheet " + styleSheetPath); //$NON-NLS-1$
            }
        }
        return styleSheetStream;
    }

    /**
     * Don't forget to enable debug if you want to see the raw FO.
     *
     * @param out
     *            output writer
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws JspException
     *             the jsp exception
     */
    @Override
    public void doExport(final OutputStream out) throws IOException, JspException {
        final String xmlResults = this.getXml();

        final FopFactory fopFactory = FopFactory.newInstance(Path.of(".").toUri());
        final Source xslt = new StreamSource(this.getStyleSheet());
        final TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = factory.newTransformer(xslt);
        } catch (final TransformerConfigurationException e) {
            throw new JspException("Cannot configure pdf export " + e.getMessage(), e); //$NON-NLS-1$
        }

        final boolean outputForDebug = FopExportView.log.isDebugEnabled();
        if (outputForDebug) {
            this.logXsl(xmlResults, transformer, null);
        }

        Fop fop;
        try {
            fop = fopFactory.newFop(org.apache.xmlgraphics.util.MimeConstants.MIME_PDF, out);
        } catch (final FOPException e) {
            throw new JspException("Cannot configure pdf export " + e.getMessage(), e); //$NON-NLS-1$
        }

        final StreamSource src = new StreamSource(new StringReader(xmlResults));
        Result res;
        try {
            res = new SAXResult(fop.getDefaultHandler());
        } catch (final FOPException e) {
            throw new JspException("error setting up transform ", e); //$NON-NLS-1$
        }
        try {
            transformer.transform(src, res);
        } catch (final TransformerException e) {
            if (!(e.getCause() instanceof ValidationException)) {
                throw new JspException("error creating pdf output", e); //$NON-NLS-1$
            }
            // recreate the errant fo
            final ValidationException ve = (ValidationException) e.getCause();
            this.logXsl(xmlResults, transformer, ve);

        }
    }

    /**
     * Gets the xml.
     *
     * @return the xml
     *
     * @throws JspException
     *             the jsp exception
     */
    protected String getXml() throws JspException {
        final XmlTotalsWriter totals = new XmlTotalsWriter(this.model);
        totals.writeTable(this.model, "-1");
        return totals.getXml();
    }

    /**
     * log it.
     *
     * @param xmlResults
     *            raw
     * @param transformer
     *            the transformer
     * @param e
     *            the optional exception
     *
     * @throws JspException
     *             wrapping an existing error
     */
    protected void logXsl(final String xmlResults, final Transformer transformer, final Exception e)
            throws JspException {
        final StreamResult debugRes = new StreamResult(new StringWriter());
        final StreamSource src = new StreamSource(new StringReader(xmlResults));
        try {
            transformer.transform(src, debugRes);
            if (e != null) {
                FopExportView.log.error("xslt-fo error {}", e.getMessage(), e); //$NON-NLS-1$
                FopExportView.log.error("xslt-fo result of {}", debugRes.getWriter()); //$NON-NLS-1$
                throw new JspException("Stylesheet produced invalid xsl-fo result", e); //$NON-NLS-1$
            }
            FopExportView.log.info("xslt-fo result of {}", debugRes.getWriter()); //$NON-NLS-1$
        } catch (final TransformerException ee) {
            throw new JspException("error creating pdf output " + ee.getMessage(), ee); //$NON-NLS-1$
        }
    }

    /**
     * If you are authoring a stylesheet locally, this is highly recommended as a way to test your stylesheet against
     * dummy data.
     *
     * @param xmlSrc
     *            xml as string
     * @param styleSheetPath
     *            the path to the stylesheet
     * @param f
     *            the f
     *
     * @throws Exception
     *             if trouble
     */
    public static void transform(final String xmlSrc, final String styleSheetPath, final File f) throws Exception {

        final FopFactory fopFactory = FopFactory.newInstance(Path.of(".").toUri());
        final InputStream styleSheetStream = FopExportView.class.getResourceAsStream(styleSheetPath);

        final Source xslt = new StreamSource(styleSheetStream);
        final TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = factory.newTransformer(xslt);
        } catch (final TransformerConfigurationException e) {
            throw new JspException("Cannot configure pdf export " + e.getMessage(), e); //$NON-NLS-1$
        }
        Fop fop;
        try {
            final OutputStream fw = Files.newOutputStream(f.toPath());
            fop = fopFactory.newFop(org.apache.xmlgraphics.util.MimeConstants.MIME_PDF, fw);
        } catch (final FOPException e) {
            throw new JspException("Cannot configure pdf export " + e.getMessage(), e); //$NON-NLS-1$
        }

        final Source src = new StreamSource(new StringReader(xmlSrc));
        Result res;
        try {
            res = new SAXResult(fop.getDefaultHandler());
        } catch (final FOPException e) {
            throw new JspException("error setting up transform ", e); //$NON-NLS-1$
        }
        try {
            transformer.transform(src, res);
        } catch (final TransformerException e) {
            throw new JspException("error creating pdf output " + e.getMessage(), e); //$NON-NLS-1$
        }
    }
}
