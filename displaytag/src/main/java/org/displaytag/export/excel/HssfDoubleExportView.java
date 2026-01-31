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
import org.displaytag.model.HeaderCell;
import org.displaytag.render.HssfTableWriter;

/**
 * If you are doing an export, and do not know if the user would like a view that is grouped or a view that is raw, just
 * use this one; it will export one tab that is grouped, and one tab that is not. Override the getHssfTableWriter method
 * to change which HssfTableWriter is actually used.
 *
 * @see DefaultHssfExportView#getHssfTableWriter(org.apache.poi.hssf.usermodel.HSSFWorkbook)
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
