/**
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
 *
 * @author Jorge L. Barroso
 * @version $Revision$ ($Author$)
 */
public class DefaultHssfExportView implements BinaryExportView
{
    /**
     * TableModel to render.
     */
    protected TableModel model;

    /**
     * @see org.displaytag.export.BinaryExportView#doExport(java.io.OutputStream)
     */
    @Override
    public void doExport(OutputStream out) throws IOException, JspException
    {
        try
        {
            HSSFWorkbook wb = new HSSFWorkbook();
            getHssfTableWriter(wb).writeTable(this.model, "-1");
            wb.write(out);
            wb.close();
        }
        catch (Exception e)
        {
            throw new HssfGenerationException(e);
        }
    }

    /**
     * Convenience method.
     *
     * @param wb the wb
     * @return the hssf table writer
     */
    protected HssfTableWriter getHssfTableWriter(HSSFWorkbook wb)
    {
        return new HssfTableWriter(wb);
    }

    /**
     * @see org.displaytag.export.ExportView#setParameters(org.displaytag.model.TableModel, boolean, boolean, boolean)
     */
    @Override
    public void setParameters(TableModel model, boolean exportFullList,
            boolean includeHeader, boolean decorateValues)
    {
        this.model = model;
    }

    /**
     * @see org.displaytag.export.BaseExportView#getMimeType()
     * @return "application/vnd.ms-excel"
     */
    @Override
    public String getMimeType()
    {
        return "application/vnd.ms-excel"; //$NON-NLS-1$
    }

    /**
     * Wraps POI-generated exceptions.
     * @author Fabrizio Giustina
     * @version $Revision$ ($Author$)
     */
    static class HssfGenerationException extends BaseNestableJspTagException
    {
        /**
         * D1597A17A6.
         */
        private static final long serialVersionUID = 899149338534L;

        /**
         * Instantiate a new PdfGenerationException with a fixed message and the given cause.
         * @param cause Previous exception
         */
        public HssfGenerationException(Throwable cause)
        {
            super(DefaultHssfExportView.class, Messages.getString("DefaultHssfExportView.errorexporting"), cause); //$NON-NLS-1$
        }

        /**
         * @see org.displaytag.exception.BaseNestableJspTagException#getSeverity()
         */
        @Override
        public SeverityEnum getSeverity()
        {
            return SeverityEnum.ERROR;
        }
    }
}
