/*
 * Copyright (C) 2002-2023 Fabrizio Giustina, the Displaytag team
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

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.OutputStream;

import javax.servlet.jsp.JspException;

import org.displaytag.Messages;
import org.displaytag.exception.BaseNestableJspTagException;
import org.displaytag.exception.SeverityEnum;
import org.displaytag.model.TableModel;
import org.displaytag.render.ItextTableWriter;

/**
 * Exporter using iText: subclasses export to any of the iText document types, such as PDF and RTF.
 *
 * @author Jorge L. Barroso
 *
 * @version $Revision$ ($Author$)
 */
public abstract class DefaultItextExportView implements BinaryExportView {

    /**
     * TableModel to render.
     */
    private TableModel model;

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
     * @return null
     *
     * @see org.displaytag.export.BaseExportView#getMimeType() Meant to be overwritten by subclasses.
     */
    @Override
    public String getMimeType() {
        return null;
    }

    /**
     * Do export.
     *
     * @param out
     *            the out
     *
     * @throws JspException
     *             the jsp exception
     *
     * @see org.displaytag.export.BinaryExportView#doExport(OutputStream)
     */
    @Override
    public void doExport(final OutputStream out) throws JspException {
        try {
            final Document document = new Document(PageSize.A4.rotate(), 60, 60, 40, 40);
            this.initItextWriter(document, out);
            document.open();
            final PdfPTable table = new PdfPTable(this.model.getNumberOfColumns());
            final ItextTableWriter writer = new ItextTableWriter(table, document);
            writer.writeTable(this.model, "-1");
            document.add(table);
            document.close();
        } catch (final Exception e) {
            throw new ItextGenerationException(e);
        }
    }

    /**
     * Initializes the iText writer used by export view to write iText document, such as PDF or RTF iText writer.
     *
     * @param document
     *            The iText document to be written.
     * @param out
     *            The output stream to which the document is written.
     *
     * @throws DocumentException
     *             If something goes wrong during initialization.
     */
    protected abstract void initItextWriter(Document document, OutputStream out) throws DocumentException;

    /**
     * Wraps iText-generated exceptions.
     *
     * @author Fabrizio Giustina
     *
     * @version $Revision$ ($Author$)
     */
    static class ItextGenerationException extends BaseNestableJspTagException {

        /**
         * D1597A17A6.
         */
        private static final long serialVersionUID = 899149338534L;

        /**
         * Instantiate a new PdfGenerationException with a fixed message and the given cause.
         *
         * @param cause
         *            Previous exception
         */
        public ItextGenerationException(final Throwable cause) {
            super(DefaultItextExportView.class, Messages.getString("DefaultItextExportView.errorexporting"), cause); //$NON-NLS-1$
            this.initCause(cause);
        }

        /**
         * Gets the severity.
         *
         * @return the severity
         *
         * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
         */
        @Override
        public SeverityEnum getSeverity() {
            return SeverityEnum.ERROR;
        }
    }
}
