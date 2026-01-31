/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
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
     */
    static class ItextGenerationException extends BaseNestableJspTagException {

        /**
         * Serial ID.
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
