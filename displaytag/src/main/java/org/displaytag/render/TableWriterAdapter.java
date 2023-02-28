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
package org.displaytag.render;

import org.displaytag.model.Column;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;

/**
 * Convenience abstract adapter for constructing a table view; contains only stub implementations. This class exists as
 * a convenience for creating table-writer objects. Extend this class to create a table writer and override the methods
 * of interest. This class also protects subclasses from future additions to TableWriterTemplate they may not be
 * interested in.
 *
 * @author Jorge L. Barroso
 *
 * @version $Id$
 */
public abstract class TableWriterAdapter extends TableWriterTemplate {

    /**
     * Write empty list message.
     *
     * @param emptyListMessage
     *            the empty list message
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeEmptyListMessage(java.lang.String)
     */
    @Override
    protected void writeEmptyListMessage(final String emptyListMessage) throws Exception {
    }

    /**
     * Write top banner.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTopBanner(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTopBanner(final TableModel model) throws Exception {
    }

    /**
     * Write table opener.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTableOpener(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableOpener(final TableModel model) throws Exception {
    }

    /**
     * Write caption.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeCaption(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeCaption(final TableModel model) throws Exception {
    }

    /**
     * Write table header.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTableHeader(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableHeader(final TableModel model) throws Exception {
    }

    /**
     * Write pre body footer.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writePreBodyFooter(org.displaytag.model.TableModel)
     */
    @Override
    protected void writePreBodyFooter(final TableModel model) throws Exception {
    }

    /**
     * Write table body opener.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyOpener(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableBodyOpener(final TableModel model) throws Exception {
    }

    /**
     * Write table body closer.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyCloser(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableBodyCloser(final TableModel model) throws Exception {
    }

    /**
     * Write post body footer.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writePostBodyFooter(org.displaytag.model.TableModel)
     */
    @Override
    protected void writePostBodyFooter(final TableModel model) throws Exception {
    }

    /**
     * Write table closer.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeTableCloser(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableCloser(final TableModel model) throws Exception {
    }

    /**
     * Write bottom banner.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeBottomBanner(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeBottomBanner(final TableModel model) throws Exception {
    }

    /**
     * Write decorated table finish.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedTableFinish(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedTableFinish(final TableModel model) throws Exception {
    }

    /**
     * Write decorated row start.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowStart(final TableModel model) throws Exception {
    }

    /**
     * Write row opener.
     *
     * @param row
     *            the row
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeRowOpener(org.displaytag.model.Row)
     */
    @Override
    protected void writeRowOpener(final Row row) throws Exception {
    }

    /**
     * Write column opener.
     *
     * @param column
     *            the column
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeColumnOpener(org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnOpener(final Column column) throws Exception {
    }

    /**
     * Write column value.
     *
     * @param value
     *            the value
     * @param column
     *            the column
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeColumnValue(Object,org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnValue(final Object value, final Column column) throws Exception {
    }

    /**
     * Write column closer.
     *
     * @param column
     *            the column
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeColumnCloser(org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnCloser(final Column column) throws Exception {
    }

    /**
     * Write row with no columns.
     *
     * @param string
     *            the string
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeRowWithNoColumns(java.lang.String)
     */
    @Override
    protected void writeRowWithNoColumns(final String string) throws Exception {
    }

    /**
     * Write row closer.
     *
     * @param row
     *            the row
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeRowCloser(org.displaytag.model.Row)
     */
    @Override
    protected void writeRowCloser(final Row row) throws Exception {
    }

    /**
     * Write decorated row finish.
     *
     * @param model
     *            the model
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowFinish(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowFinish(final TableModel model) throws Exception {
    }

    /**
     * Write empty list row message.
     *
     * @param message
     *            the message
     *
     * @throws Exception
     *             the exception
     *
     * @see org.displaytag.render.TableWriterTemplate#writeEmptyListRowMessage(java.lang.String)
     */
    @Override
    protected void writeEmptyListRowMessage(final String message) throws Exception {
    }
}
