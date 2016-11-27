/**
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

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.model.TableModel;


/**
 * Export view for comma separated value exporting.
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class CsvView extends BaseExportView
{

    /** The Constant UTF8_BOM. */
    private static final String UTF8_BOM = "\uFEFF";

    @Override
    public void doExport(Writer out, String characterEncoding) throws IOException, JspException
    {
        if (StringUtils.equalsIgnoreCase(characterEncoding, "UTF-8"))
        {
            out.write(UTF8_BOM);
        }
        super.doExport(out, characterEncoding);
    }

    /**
     * @see org.displaytag.export.BaseExportView#setParameters(TableModel, boolean, boolean, boolean)
     */
    @Override
    public void setParameters(TableModel tableModel, boolean exportFullList, boolean includeHeader,
        boolean decorateValues)
    {
        super.setParameters(tableModel, exportFullList, includeHeader, decorateValues);
    }

    /**
     * @see org.displaytag.export.BaseExportView#getRowEnd()
     */
    @Override
    protected String getRowEnd()
    {
        return "\n"; //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.export.BaseExportView#getCellEnd()
     */
    @Override
    protected String getCellEnd()
    {
        return ","; //$NON-NLS-1$
    }

    /**
     * @see org.displaytag.export.BaseExportView#getAlwaysAppendCellEnd()
     */
    @Override
    protected boolean getAlwaysAppendCellEnd()
    {
        return false;
    }

    /**
     * @see org.displaytag.export.BaseExportView#getAlwaysAppendRowEnd()
     */
    @Override
    protected boolean getAlwaysAppendRowEnd()
    {
        return true;
    }

    /**
     * @see org.displaytag.export.ExportView#getMimeType()
     */
    @Override
    public String getMimeType()
    {
        return "text/csv"; //$NON-NLS-1$
    }

    /**
     * Escaping for csv format.
     * <ul>
     * <li>Quotes inside quoted strings are escaped with a /</li>
     * <li>Fields containings newlines or , are surrounded by "</li>
     * </ul>
     * Note this is the standard CVS format and it's not handled well by excel.
     * @see org.displaytag.export.BaseExportView#escapeColumnValue(java.lang.Object)
     */
    @Override
    protected String escapeColumnValue(Object value)
    {
        String stringValue = StringUtils.trim(value.toString());

        if (!StringUtils.containsNone(stringValue, new char[]{'\r', '\n', ','}))
        {
            return "\"" + //$NON-NLS-1$
                StringUtils.replace(stringValue, "\"", "\\\"") + "\""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }

        return stringValue;
    }

}
