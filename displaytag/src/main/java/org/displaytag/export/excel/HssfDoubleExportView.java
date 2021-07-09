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
package org.displaytag.export.excel;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.jsp.JspException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.displaytag.model.HeaderCell;
import org.displaytag.render.HssfTableWriter;

/**
 * If you are doing an export, and do not know if the user would like a view that is grouped or a view that is raw, just
 * use this one; it will export one tab that is grouped, and one tab that is not. Override the getHssfTableWriter method
 * to change which HssfTableWriter is actually used.
 *
 * @author andy
 *
 * @see DefaultHssfExportView#getHssfTableWriter(org.apache.poi.hssf.usermodel.HSSFWorkbook) Date: Nov 13, 2010 Time:
 *      8:46:29 AM
 */
public class HssfDoubleExportView extends DefaultHssfExportView {

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
     */
    @Override
    public void doExport(final OutputStream out) throws IOException, JspException {
        try {
            final HSSFWorkbook wb = new HSSFWorkbook();
            final HssfTableWriter writer = this.getHssfTableWriter(wb);
            writer.setSetSheetName("Export");
            writer.writeTable(this.model, "-1");

            boolean hasGroups = false;
            for (final HeaderCell cell : this.model.getHeaderCellList()) {
                if (cell.getGroup() > 0) {
                    hasGroups = true;
                    cell.setGroup(0);
                }
            }
            if (hasGroups) {
                writer.setSetSheetName("Data");
                this.model.reset();
                writer.writeTable(this.model, "-1");
            }
            wb.write(out);
            wb.close();
        } catch (final Exception e) {
            throw new HssfGenerationException(e);
        }
    }
}
