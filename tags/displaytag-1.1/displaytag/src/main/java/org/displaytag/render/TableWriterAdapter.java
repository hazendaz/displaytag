/**
 * Licensed under the Artistic License; you may not use this file
 * except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://displaytag.sourceforge.net/license.html
 *
 * THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED
 * WARRANTIES OF MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
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
    protected void writeEmptyListMessage(String emptyListMessage) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTopBanner(org.displaytag.model.TableModel)
     */
    protected void writeTopBanner(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableOpener(org.displaytag.model.TableModel)
     */
    protected void writeTableOpener(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeCaption(org.displaytag.model.TableModel)
     */
    protected void writeCaption(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableHeader(org.displaytag.model.TableModel)
     */
    protected void writeTableHeader(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writePreBodyFooter(org.displaytag.model.TableModel)
     */
    protected void writePreBodyFooter(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyOpener(org.displaytag.model.TableModel)
     */
    protected void writeTableBodyOpener(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyCloser(org.displaytag.model.TableModel)
     */
    protected void writeTableBodyCloser(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writePostBodyFooter(org.displaytag.model.TableModel)
     */
    protected void writePostBodyFooter(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeTableCloser(org.displaytag.model.TableModel)
     */
    protected void writeTableCloser(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeBottomBanner(org.displaytag.model.TableModel)
     */
    protected void writeBottomBanner(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedTableFinish(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedTableFinish(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowStart(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeRowOpener(org.displaytag.model.Row)
     */
    protected void writeRowOpener(Row row) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeColumnOpener(org.displaytag.model.Column)
     */
    protected void writeColumnOpener(Column column) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeColumnValue(Object,org.displaytag.model.Column)
     */
    protected void writeColumnValue(Object value, Column column) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeColumnCloser(org.displaytag.model.Column)
     */
    protected void writeColumnCloser(Column column) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeRowWithNoColumns(java.lang.String)
     */
    protected void writeRowWithNoColumns(String string) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeRowCloser(org.displaytag.model.Row)
     */
    protected void writeRowCloser(Row row) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowFinish(org.displaytag.model.TableModel)
     */
    protected void writeDecoratedRowFinish(TableModel model) throws Exception
    {
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeEmptyListRowMessage(java.lang.String)
     */
    protected void writeEmptyListRowMessage(String message) throws Exception
    {
    }
}
