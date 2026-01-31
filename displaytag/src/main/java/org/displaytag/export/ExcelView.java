/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.export;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

/**
 * Export view for excel exporting.
 */
public class ExcelView extends BaseExportView {

    /**
     * Gets the mime type.
     *
     * @return "application/vnd.ms-excel"
     *
     * @see org.displaytag.export.ExportView#getMimeType()
     */
    @Override
    public String getMimeType() {
        return "application/vnd.ms-excel"; //$NON-NLS-1$
    }

    /**
     * Gets the row end.
     *
     * @return the row end
     *
     * @see org.displaytag.export.BaseExportView#getRowEnd()
     */
    @Override
    protected String getRowEnd() {
        return "\n"; //$NON-NLS-1$
    }

    /**
     * Gets the cell end.
     *
     * @return the cell end
     *
     * @see org.displaytag.export.BaseExportView#getCellEnd()
     */
    @Override
    protected String getCellEnd() {
        return "\t"; //$NON-NLS-1$
    }

    /**
     * Gets the always append cell end.
     *
     * @return false
     *
     * @see org.displaytag.export.BaseExportView#getAlwaysAppendCellEnd()
     */
    @Override
    protected boolean getAlwaysAppendCellEnd() {
        return false;
    }

    /**
     * Gets the always append row end.
     *
     * @return false
     *
     * @see org.displaytag.export.BaseExportView#getAlwaysAppendRowEnd()
     */
    @Override
    protected boolean getAlwaysAppendRowEnd() {
        return false;
    }

    /**
     * Escaping for excel format.
     * <ul>
     * <li>Quotes inside quoted strings are escaped with a double quote</li>
     * <li>Fields are surrounded by " (should be optional, but sometimes you get a "Sylk error" without those)</li>
     * </ul>
     *
     * @param value
     *            the value
     *
     * @return the string
     *
     * @see org.displaytag.export.BaseExportView#escapeColumnValue(java.lang.Object)
     */
    @Override
    protected String escapeColumnValue(final Object value) {
        if (value != null) {
            // quotes around fields are needed to avoid occasional "Sylk format invalid" messages from excel
            return "\"" //$NON-NLS-1$
                    + Strings.CS.replace(StringUtils.trim(value.toString()), "\"", "\"\"") //$NON-NLS-1$ //$NON-NLS-2$
                    + "\""; //$NON-NLS-1$
        }

        return null;
    }

}
