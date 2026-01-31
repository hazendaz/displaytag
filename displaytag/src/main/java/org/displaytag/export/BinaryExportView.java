/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.export;

import jakarta.servlet.jsp.JspException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Main interface for exportViews which need to output binary data.
 */
public interface BinaryExportView extends ExportView {

    /**
     * Returns the exported content as a String.
     *
     * @param out
     *            output writer
     *
     * @throws IOException
     *             for exceptions in accessing the output stream
     * @throws JspException
     *             for other exceptions during export
     */
    void doExport(OutputStream out) throws IOException, JspException;
}
