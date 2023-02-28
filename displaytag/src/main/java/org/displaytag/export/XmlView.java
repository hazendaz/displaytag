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

import org.apache.commons.text.StringEscapeUtils;

/**
 * Export view for xml exporting.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
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
