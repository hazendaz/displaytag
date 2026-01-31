/*
 * SPDX-License-Identifier: MIT
 * See LICENSE file for details.
 *
 * Copyright 2002-2026 Fabrizio Giustina, the Displaytag team
 */
package org.displaytag.export;

import org.displaytag.model.TableModel;

/**
 * Interface for export classes. ExportViewFactory is responsible for registering and initialization of export views. A
 * default, no parameters constructor is required. The <code>setParameters()</code> method is guarantee to be called
 * before any other operation.
 */
public interface ExportView {

    /**
     * initialize the parameters needed for export. The method is guarantee be called before <code>doExport()</code> and
     * <code>getMimeType()</code>. Classes implementing ExportView should reset any instance field previously set when
     * this method is called, in order to support instance reusing.
     *
     * @param tableModel
     *            TableModel to render
     * @param exportFullList
     *            boolean export full list?
     * @param includeHeader
     *            should header be included in export?
     * @param decorateValues
     *            should output be decorated?
     */
    void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader, boolean decorateValues);

    /**
     * MimeType to return.
     *
     * @return String mime type
     */
    String getMimeType();

}
