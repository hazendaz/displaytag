/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.export;

import jakarta.servlet.jsp.JspException;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;

/**
 * Export view for comma separated value exporting.
 */
public class CsvView extends BaseExportView {

    /** The Constant UTF8_BOM. */
    private static final String UTF8_BOM = "\uFEFF";

    /**
     * Do export.
     *
     * @param out
     *            the out
     * @param characterEncoding
     *            the character encoding
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws JspException
     *             the jsp exception
     */
    @Override
    public void doExport(final Writer out, final String characterEncoding) throws IOException, JspException {
        if (Strings.CI.equals(characterEncoding, StandardCharsets.UTF_8.name())) {
            out.write(CsvView.UTF8_BOM);
        }
        super.doExport(out, characterEncoding);
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
        return ","; //$NON-NLS-1$
    }

    /**
     * Gets the always append cell end.
     *
     * @return the always append cell end
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
     * @return the always append row end
     *
     * @see org.displaytag.export.BaseExportView#getAlwaysAppendRowEnd()
     */
    @Override
    protected boolean getAlwaysAppendRowEnd() {
        return true;
    }

    /**
     * Gets the mime type.
     *
     * @return the mime type
     *
     * @see org.displaytag.export.ExportView#getMimeType()
     */
    @Override
    public String getMimeType() {
        return "text/csv"; //$NON-NLS-1$
    }

    /**
     * Escaping for csv format.
     * <ul>
     * <li>Quotes inside quoted strings are escaped with a /</li>
     * <li>Fields containings newlines or , are surrounded by "</li>
     * </ul>
     * Note this is the standard CVS format and it's not handled well by excel.
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
        final String stringValue = StringUtils.trim(value.toString());

        if (!StringUtils.containsNone(stringValue, '\r', '\n', ',')) {
            return "\"" + //$NON-NLS-1$
                    Strings.CS.replace(stringValue, "\"", "\\\"") + "\""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }

        return stringValue;
    }

}
