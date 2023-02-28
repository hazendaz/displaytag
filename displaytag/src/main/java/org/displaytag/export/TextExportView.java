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

import java.io.IOException;
import java.io.Writer;

import jakarta.servlet.jsp.JspException;

/**
 * Main interface for exportViews which need to output character data.
 *
 * @author Fabrizio Giustina
 *
 * @version $Revision$ ($Author$)
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
