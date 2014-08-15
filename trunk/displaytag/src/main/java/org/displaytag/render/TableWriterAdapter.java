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
package org.displaytag.render;

import org.displaytag.model.Column;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;


/**
 * Convenience abstract adapter for constructing a table view; contains only stub implementations. This class exists as
 * a convenience for creating table-writer objects. Extend this class to create a table writer and override the methods
 * of interest. This class also protects subclasses from future additions to TableWriterTemplate they may not be
 * interested in.
 * @author Jorge L. Barroso
 * @version $Id$
 */
public abstract class TableWriterAdapter extends TableWriterTemplate
{

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeEmptyListMessage(java.lang.String)
     */
    @Override
    protected void writeEmptyListMessage(String emptyListMessage) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTopBanner(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTopBanner(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableOpener(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableOpener(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeCaption(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeCaption(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableHeader(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableHeader(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writePreBodyFooter(org.displaytag.model.TableModel)
     */
    @Override
    protected void writePreBodyFooter(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyOpener(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableBodyOpener(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyCloser(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableBodyCloser(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writePostBodyFooter(org.displaytag.model.TableModel)
     */
    @Override
    protected void writePostBodyFooter(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableCloser(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableCloser(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeBottomBanner(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeBottomBanner(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedTableFinish(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedTableFinish(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowStart(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeRowOpener(org.displaytag.model.Row)
     */
    @Override
    protected void writeRowOpener(Row row) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeColumnOpener(org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnOpener(Column column) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeColumnValue(Object,org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnValue(Object value, Column column) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeColumnCloser(org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnCloser(Column column) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeRowWithNoColumns(java.lang.String)
     */
    @Override
    protected void writeRowWithNoColumns(String string) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeRowCloser(org.displaytag.model.Row)
     */
    @Override
    protected void writeRowCloser(Row row) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowFinish(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowFinish(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeEmptyListRowMessage(java.lang.String)
     */
    @Override
    protected void writeEmptyListRowMessage(String message) throws Exception
    {
    }
}
