/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.export;

import org.apache.commons.text.StringEscapeUtils;

/**
 * Export view for xml exporting.
 */
public class XmlView extends BaseExportView {

    /**
     * Gets the row start.
     *
     * @return the row start
     *
     * @see org.displaytag.export.BaseExportView#getRowStart()
     */
    @Override
    protected String getRowStart() {
        return "<row>\n"; //$NON-NLS-1$
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
        return "</row>\n"; //$NON-NLS-1$
    }

    /**
     * Gets the cell start.
     *
     * @return the cell start
     *
     * @see org.displaytag.export.BaseExportView#getCellStart()
     */
    @Override
    protected String getCellStart() {
        return "<column>"; //$NON-NLS-1$
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
        return "</column>\n"; //$NON-NLS-1$
    }

    /**
     * Gets the document start.
     *
     * @return the document start
     *
     * @see org.displaytag.export.BaseExportView#getDocumentStart()
     */
    @Override
    protected String getDocumentStart() {
        return "<?xml version=\"1.0\"?>\n<table>\n"; //$NON-NLS-1$
    }

    /**
     * Gets the document end.
     *
     * @return the document end
     *
     * @see org.displaytag.export.BaseExportView#getDocumentEnd()
     */
    @Override
    protected String getDocumentEnd() {
        return "</table>\n"; //$NON-NLS-1$
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
        return true;
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
        return "text/xml"; //$NON-NLS-1$
    }

    /**
     * Escape column value.
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
        return StringEscapeUtils.escapeXml10(value.toString());
    }

}
