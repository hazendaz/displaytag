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
