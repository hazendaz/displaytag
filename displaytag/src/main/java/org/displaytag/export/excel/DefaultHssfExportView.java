/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.export.excel;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.displaytag.Messages;
import org.displaytag.exception.BaseNestableJspTagException;
import org.displaytag.exception.SeverityEnum;
import org.displaytag.export.BinaryExportView;
import org.displaytag.model.TableModel;
import org.displaytag.render.HssfTableWriter;

/**
 * Excel exporter using POI.
 */
public class DefaultHssfExportView implements BinaryExportView {
    /**
     * TableModel to render.
     */
    protected TableModel model;

    /**
     * Do export.
     *
     * @param out
     *            the out
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws JspException
     *             the jsp exception
     *
     * @see org.displaytag.export.BinaryExportView#doExport(java.io.OutputStream)
     */
    @Override
    public void doExport(final OutputStream out) throws IOException, JspException {
        try {
            final HSSFWorkbook wb = new HSSFWorkbook();
            this.getHssfTableWriter(wb).writeTable(this.model, "-1");
            wb.write(out);
            wb.close();
        } catch (final Exception e) {
            throw new HssfGenerationException(e);
        }
    }

    /**
     * Convenience method.
     *
     * @param wb
     *            the wb
     *
     * @return the hssf table writer
     */
    protected HssfTableWriter getHssfTableWriter(final HSSFWorkbook wb) {
        return new HssfTableWriter(wb);
    }

    /**
     * Sets the parameters.
     *
     * @param model
     *            the model
     * @param exportFullList
     *            the export full list
     * @param includeHeader
     *            the include header
     * @param decorateValues
     *            the decorate values
     *
     * @see org.displaytag.export.ExportView#setParameters(org.displaytag.model.TableModel, boolean, boolean, boolean)
     */
    @Override
    public void setParameters(final TableModel model, final boolean exportFullList, final boolean includeHeader,
            final boolean decorateValues) {
        this.model = model;
    }

    /**
     * Gets the mime type.
     *
     * @return "application/vnd.ms-excel"
     *
     * @see org.displaytag.export.BaseExportView#getMimeType()
     */
    @Override
    public String getMimeType() {
        return "application/vnd.ms-excel"; //$NON-NLS-1$
    }

    /**
     * Wraps POI-generated exceptions.
     */
    static class HssfGenerationException extends BaseNestableJspTagException {
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
        public HssfGenerationException(final Throwable cause) {
            super(DefaultHssfExportView.class, Messages.getString("DefaultHssfExportView.errorexporting"), cause); //$NON-NLS-1$
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
