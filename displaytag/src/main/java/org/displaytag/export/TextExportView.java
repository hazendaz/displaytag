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

/**
 * Main interface for exportViews which need to output character data.
 */
public interface TextExportView extends ExportView {

    /**
     * Returns the exported content as a String.
     *
     * @param out
     *            output writer
     * @param characterEncoding
     *            character encoding
     *
     * @throws IOException
     *             for exceptions in accessing the output stream
     * @throws JspException
     *             for other exceptions during export
     */
    void doExport(Writer out, String characterEncoding) throws IOException, JspException;

    /**
     * If <code>true</code> exported data will be included in the html page. <strong>actually not evaluated. Included
     * for future enhancements </strong>
     *
     * @return <code>true</code> if exported data should be included in the html page
     */
    boolean outputPage();
}
