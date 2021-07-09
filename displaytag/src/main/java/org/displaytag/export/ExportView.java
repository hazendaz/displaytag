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
package org.displaytag.export;

import org.displaytag.model.TableModel;

/**
 * Interface for export classes. ExportViewFactory is responsible for registering and initialization of export views. A
 * default, no parameters constructor is required. The <code>setParameters()</code> method is guarantee to be called
 * before any other operation.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
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
