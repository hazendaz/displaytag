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

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang3.StringUtils;
import org.displaytag.exception.DecoratorException;
import org.displaytag.exception.ObjectLookupException;
import org.displaytag.exception.WrappedRuntimeException;
import org.displaytag.model.Column;
import org.displaytag.model.HeaderCell;
import org.displaytag.model.Row;
import org.displaytag.model.TableModel;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.pagination.SmartListHelper;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.SortOrderEnum;
import org.displaytag.properties.TableProperties;
import org.displaytag.tags.CaptionTag;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.Anchor;
import org.displaytag.util.Href;
import org.displaytag.util.HtmlAttributeMap;
import org.displaytag.util.ParamEncoder;
import org.displaytag.util.PostHref;
import org.displaytag.util.TagConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A table writer that formats a table in HTML and writes it to a JSP page.
 * @author Fabrizio Giustina
 * @author Jorge L. Barroso
 * @version $Id$
 * @see org.displaytag.render.TableWriterTemplate
 * @since 1.1
 */
public class HtmlTableWriter extends TableWriterAdapter
{

    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(HtmlTableWriter.class);

    /**
     * <code>TableProperties</code>
     */
    private TableProperties properties;

    /**
     * Output destination.
     */
    private JspWriter out;

    /**
     * The param encoder used to generate unique parameter names. Initialized at the first use of encodeParameter().
     */
    private ParamEncoder paramEncoder;

    /**
     * base href used for links.
     */
    private Href baseHref;

    /**
     * add export links.
     */
    private boolean export;

    private CaptionTag captionTag;

    /**
     * The paginated list containing the external pagination and sort parameters The presence of this paginated list is
     * what determines if external pagination and sorting is used or not.
     */
    private PaginatedList paginatedList;

    /**
     * Used by various functions when the person wants to do paging.
     */
    private SmartListHelper listHelper;

    /**
     * page size.
     */
    private int pagesize;

    private HtmlAttributeMap attributeMap;

    /**
     * Unique table id.
     */
    private String uid;

    /**
     * This table writer uses a <code>TableTag</code> and a <code>JspWriter</code> to do its work.
     * @param tableTag <code>TableTag</code> instance called back by this writer.
     * @param out The output destination.
     */
    public HtmlTableWriter(
        TableProperties tableProperties,
        Href baseHref,
        boolean export,
        JspWriter out,
        CaptionTag captionTag,
        PaginatedList paginatedList,
        SmartListHelper listHelper,
        int pagesize,
        HtmlAttributeMap attributeMap,
        String uid)
    {
        this.properties = tableProperties;
        this.baseHref = baseHref;
        this.export = export;
        this.out = out;
        this.captionTag = captionTag;
        this.paginatedList = paginatedList;
        this.listHelper = listHelper;
        this.pagesize = pagesize;
        this.attributeMap = attributeMap;
        this.uid = uid;
    }

    /**
     * Writes a banner containing search result and paging navigation above an HTML table to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTopBanner(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTopBanner(TableModel model)
    {
        if (model.getForm() != null)
        {

            String js = "<script type=\"text/javascript\">\n"
                + "function displaytagform(formname, fields){\n"
                + "    var objfrm = document.forms[formname];\n"
                + "    for (j=fields.length-1;j>=0;j--){var f= objfrm.elements[fields[j].f];if (f){f.value=fields[j].v};}\n"
                + "    objfrm.submit();\n"
                + "}\n"
                + "</script>";
            writeFormFields(model);
            write(js);
        }

        // Put the page stuff there if it needs to be there...
        if (properties.getAddPagingBannerTop())
        {
            writeSearchResultAndNavigation(model);
        }

        // add export links (only if the table is not empty)
        if (this.export && this.properties.getAddExportBannerTop() && model.getRowListPage().size() != 0)
        {
            // generate export link
            writeExportLinks(model);
        }
    }

    /**
     * Writes an HTML table's opening tags to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTableOpener(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableOpener(TableModel model)
    {
        this.write(getOpenTag());
    }

    private void writeFormFields(TableModel model)
    {
        Map<String, String[]> parameters = baseHref.getParameterMap();

        ParamEncoder pe = new ParamEncoder(model.getId());

        addIfMissing(parameters, pe.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
        addIfMissing(parameters, pe.encodeParameterName(TableTagParameters.PARAMETER_PAGE));
        addIfMissing(parameters, pe.encodeParameterName(TableTagParameters.PARAMETER_SORT));

        for (Iterator<String> it = parameters.keySet().iterator(); it.hasNext();)
        {
            String key = it.next();
            Object value = parameters.get(key);

            if (value != null & value.getClass().isArray())
            {
                Object[] arr = (Object[]) value;
                for (int j = 0; j < arr.length; j++)
                {
                    writeField(key, arr[j]);
                }
            }
            else
            {
                writeField(key, value);
            }
        }
    }

    /**
     * @param key
     * @param value
     */
    private void writeField(String key, Object value)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<input type=\"hidden\" name=\"");
        buffer.append(esc(key));
        buffer.append("\" value=\"");
        buffer.append(value);
        buffer.append("\"/>");

        write(buffer.toString());
    }

    private String esc(Object value)
    {
        String valueEscaped = StringUtils.replace(value != null ? value.toString() : StringUtils.EMPTY, "\"", "\\\"");
        return valueEscaped;
    }

    /**
     * Adds an element to the given map if empty (use an empty string as value)
     * @param parameters Map of parameters
     * @param key param key
     */
    private void addIfMissing(Map<String, String[]> parameters, String key)
    {
        if (!parameters.containsKey(key))
        {
            parameters.put(key, new String[]{StringUtils.EMPTY});
        }
    }

    /**
     * Writes an HTML table's caption to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeCaption(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeCaption(TableModel model)
    {
        this.write(captionTag.getOpenTag() + model.getCaption() + captionTag.getCloseTag());
    }

    /**
     * Writes an HTML table's footer to a JSP page; HTML requires tfoot to appear before tbody.
     * @see org.displaytag.render.TableWriterTemplate#writeFooter(org.displaytag.model.TableModel)
     */
    @Override
    protected void writePreBodyFooter(TableModel model)
    {
        this.write(TagConstants.TAG_TFOOTER_OPEN);
        this.write(model.getFooter());
        this.write(TagConstants.TAG_TFOOTER_CLOSE);
    }

    /**
     * Writes the start of an HTML table's body to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyOpener(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableBodyOpener(TableModel model)
    {
        this.write(TagConstants.TAG_TBODY_OPEN);

    }

    /**
     * Writes the end of an HTML table's body to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTableBodyCloser(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableBodyCloser(TableModel model)
    {
        this.write(TagConstants.TAG_TBODY_CLOSE);
    }

    /**
     * Writes the closing structure of an HTML table to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTableCloser(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableCloser(TableModel model)
    {
        this.write(TagConstants.TAG_OPENCLOSING);
        this.write(TagConstants.TABLE_TAG_NAME);
        this.write(TagConstants.TAG_CLOSE);
    }

    /**
     * Writes a banner containing search result, paging navigation, and export links below an HTML table to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeBottomBanner(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeBottomBanner(TableModel model)
    {
        writeNavigationAndExportLinks(model);
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedTableFinish(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedTableFinish(TableModel model)
    {
        model.getTableDecorator().finish();
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowStart(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowStart(TableModel model)
    {
        this.write(model.getTableDecorator().startRow());
    }

    /**
     * Writes an HTML table's row-opening tag to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeRowOpener(org.displaytag.model.Row)
     */
    @Override
    protected void writeRowOpener(Row row)
    {
        this.write(row.getOpenTag());
    }

    /**
     * Writes an HTML table's column-opening tag to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeColumnOpener(org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnOpener(Column column) throws ObjectLookupException, DecoratorException
    {
        this.write(column.getOpenTag());
    }

    /**
     * Writes an HTML table's column-closing tag to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeColumnCloser(org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnCloser(Column column)
    {
        this.write(column.getCloseTag());
    }

    /**
     * Writes to a JSP page an HTML table row that has no columns.
     * @see org.displaytag.render.TableWriterTemplate#writeRowWithNoColumns(java.lang.String)
     */
    @Override
    protected void writeRowWithNoColumns(String rowValue)
    {
        this.write(TagConstants.TAG_TD_OPEN);
        this.write(rowValue);
        this.write(TagConstants.TAG_TD_CLOSE);
    }

    /**
     * Writes an HTML table's row-closing tag to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeRowCloser(org.displaytag.model.Row)
     */
    @Override
    protected void writeRowCloser(Row row)
    {
        this.write(row.getCloseTag());
    }

    /**
     * @see org.displaytag.render.TableWriterTemplate#writeDecoratedRowFinish(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeDecoratedRowFinish(TableModel model)
    {
        this.write(model.getTableDecorator().finishRow());
    }

    /**
     * Writes an HTML message to a JSP page explaining that the table model contains no data.
     * @see org.displaytag.render.TableWriterTemplate#writeEmptyListMessage(java.lang.String)
     */
    @Override
    protected void writeEmptyListMessage(String emptyListMessage)
    {
        this.write(emptyListMessage);
    }

    /**
     * Writes a HTML table column value to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeColumnValue(java.lang.String,org.displaytag.model.Column)
     */
    @Override
    protected void writeColumnValue(Object value, Column column)
    {
        this.write(value);
    }

    /**
     * Writes an HTML message to a JSP page explaining that the row contains no data.
     * @see org.displaytag.render.TableWriterTemplate#writeEmptyListRowMessage(java.lang.String)
     */
    @Override
    protected void writeEmptyListRowMessage(String message)
    {
        this.write(message);
    }

    /**
     * Writes an HTML table's column header to a JSP page.
     * @see org.displaytag.render.TableWriterTemplate#writeTableHeader(org.displaytag.model.TableModel)
     */
    @Override
    protected void writeTableHeader(TableModel model)
    {

        if (log.isDebugEnabled())
        {
            log.debug("[" + model.getId() + "] getTableHeader called");
        }

        // open thead
        write(TagConstants.TAG_THEAD_OPEN);

        // open tr
        write(TagConstants.TAG_TR_OPEN);

        // no columns?
        if (model.isEmpty())
        {
            write(TagConstants.TAG_TH_OPEN);
            write(TagConstants.TAG_TH_CLOSE);
        }

        // iterator on columns for header
        Iterator<HeaderCell> iterator = model.getHeaderCellList().iterator();

        while (iterator.hasNext())
        {
            // get the header cell
            HeaderCell headerCell = iterator.next();

            if (headerCell.getSortable())
            {
                String cssSortable = this.properties.getCssSortable();
                headerCell.addHeaderClass(cssSortable);
            }

            // if sorted add styles
            if (headerCell.isAlreadySorted())
            {
                // sorted css class
                headerCell.addHeaderClass(this.properties.getCssSorted());

                // sort order css class
                headerCell.addHeaderClass(this.properties.getCssOrder(model.isSortOrderAscending()));
            }

            // append th with html attributes
            write(headerCell.getHeaderOpenTag());

            // title
            String header = headerCell.getTitle();

            // column is sortable, create link
            if (headerCell.getSortable())
            {
                // creates the link for sorting
                Anchor anchor = new Anchor(getSortingHref(headerCell, model), header);

                // append to buffer
                header = anchor.toString();
            }

            write(header);
            write(headerCell.getHeaderCloseTag());
        }

        // close tr
        write(TagConstants.TAG_TR_CLOSE);

        // close thead
        write(TagConstants.TAG_THEAD_CLOSE);

        if (log.isDebugEnabled())
        {
            log.debug("[" + model.getId() + "] getTableHeader end");
        }
    }

    /**
     * Generates the link to be added to a column header for sorting.
     * @param headerCell header cell the link should be added to
     * @return Href for sorting
     */
    private Href getSortingHref(HeaderCell headerCell, TableModel model)
    {
        // costruct Href from base href, preserving parameters
        Href href = (Href) this.baseHref.clone();

        if (model.getForm() != null)
        {
            href = new PostHref(href, model.getForm());
        }

        if (this.paginatedList == null)
        {
            // add column number as link parameter
            if (!model.isLocalSort() && (headerCell.getSortName() != null))
            {
                href.addParameter(encodeParameter(TableTagParameters.PARAMETER_SORT, model), headerCell.getSortName());
                href.addParameter(encodeParameter(TableTagParameters.PARAMETER_SORTUSINGNAME, model), "1");
            }
            else
            {
                href.addParameter(
                    encodeParameter(TableTagParameters.PARAMETER_SORT, model),
                    headerCell.getColumnNumber());
            }

            boolean nowOrderAscending = true;

            if (headerCell.getDefaultSortOrder() != null)
            {
                boolean sortAscending = SortOrderEnum.ASCENDING.equals(headerCell.getDefaultSortOrder());
                nowOrderAscending = headerCell.isAlreadySorted() ? !model.isSortOrderAscending() : sortAscending;
            }
            else
            {
                nowOrderAscending = !(headerCell.isAlreadySorted() && model.isSortOrderAscending());
            }

            int sortOrderParam = nowOrderAscending ? SortOrderEnum.ASCENDING.getCode() : SortOrderEnum.DESCENDING
                .getCode();
            href.addParameter(encodeParameter(TableTagParameters.PARAMETER_ORDER, model), sortOrderParam);

            // If user want to sort the full table I need to reset the page number.
            // or if we aren't sorting locally we need to reset the page as well.
            if (model.isSortFullTable() || !model.isLocalSort())
            {
                href.addParameter(encodeParameter(TableTagParameters.PARAMETER_PAGE, model), 1);
            }
        }
        else
        {
            if (properties.getPaginationSkipPageNumberInSort())
            {
                href.removeParameter(properties.getPaginationPageNumberParam());
            }

            String sortProperty = headerCell.getSortProperty();
            if (sortProperty == null)
            {
                sortProperty = headerCell.getBeanPropertyName();
            }

            href.addParameter(properties.getPaginationSortParam(), sortProperty);
            String dirParam;
            if (headerCell.isAlreadySorted())
            {
                dirParam = model.isSortOrderAscending() ? properties.getPaginationDescValue() : properties
                    .getPaginationAscValue();
            }
            else
            {
                dirParam = properties.getPaginationAscValue();
            }
            href.addParameter(properties.getPaginationSortDirectionParam(), dirParam);
            if (paginatedList.getSearchId() != null)
            {
                href.addParameter(properties.getPaginationSearchIdParam(), paginatedList.getSearchId());
            }
        }

        return href;
    }

    /**
     * encode a parameter name to be unique in the page using ParamEncoder.
     * @param parameterName parameter name to encode
     * @return String encoded parameter name
     */
    private String encodeParameter(String parameterName, TableModel model)
    {
        // paramEncoder has been already instantiated?
        if (this.paramEncoder == null)
        {
            // use the id attribute to get the unique identifier
            this.paramEncoder = new ParamEncoder(model.getId());
        }

        return this.paramEncoder.encodeParameterName(parameterName);
    }

    /**
     * Generates table footer with links for export commands.
     */
    protected void writeNavigationAndExportLinks(TableModel model)
    {
        // Put the page stuff there if it needs to be there...
        if (this.properties.getAddPagingBannerBottom())
        {
            writeSearchResultAndNavigation(model);
        }

        // add export links (only if the table is not empty)
        if (this.export && this.properties.getAddExportBannerBottom() && model.getRowListPage().size() != 0)
        {
            writeExportLinks(model);
        }
    }

    /**
     * generates the search result and navigation bar.
     */
    protected void writeSearchResultAndNavigation(TableModel model)
    {
        if ((this.paginatedList == null && this.pagesize != 0 && this.listHelper != null)
            || (this.paginatedList != null))
        {
            // create a new href
            Href navigationHref = (Href) this.baseHref.clone();

            if (model.getForm() != null)
            {
                navigationHref = new PostHref(navigationHref, model.getForm());
            }

            write(this.listHelper.getSearchResultsSummary());

            String pageParameter;
            if (paginatedList == null)
            {
                pageParameter = encodeParameter(TableTagParameters.PARAMETER_PAGE, model);
            }
            else
            {
                pageParameter = properties.getPaginationPageNumberParam();
                if ((paginatedList.getSearchId() != null)
                    && (!navigationHref.getParameterMap().containsKey(properties.getPaginationSearchIdParam())))
                {
                    navigationHref.addParameter(properties.getPaginationSearchIdParam(), paginatedList.getSearchId());
                }
            }
            write(this.listHelper.getPageNavigationBar(navigationHref, pageParameter));
        }
    }

    /**
     * Writes the formatted export links section.
     */
    private void writeExportLinks(TableModel model)
    {
        // Figure out what formats they want to export, make up a little string
        Href exportHref = (Href) this.baseHref.clone();

        StringBuffer buffer = new StringBuffer(200);
        Iterator<MediaTypeEnum> iterator = MediaTypeEnum.iterator();

        while (iterator.hasNext())
        {
            MediaTypeEnum currentExportType = iterator.next();

            if (this.properties.getAddExport(currentExportType))
            {

                if (buffer.length() > 0)
                {
                    buffer.append(this.properties.getExportBannerSeparator());
                }

                exportHref.addParameter(
                    encodeParameter(TableTagParameters.PARAMETER_EXPORTTYPE, model),
                    currentExportType.getCode());

                // export marker
                exportHref.addParameter(TableTagParameters.PARAMETER_EXPORTING, "1");

                String exportBannerItem = StringUtils.defaultString(
                    this.properties.getExportBannerItem(),
                    "<a href=\"{0}\">{1}</a>");

                Anchor anchor = new Anchor(exportHref, this.properties.getExportLabel(currentExportType));
                buffer.append(MessageFormat.format(
                    exportBannerItem,
                    new Object[]{exportHref, this.properties.getExportLabel(currentExportType)}));
            }
        }

        Object[] exportOptions = {buffer.toString()};
        write(new MessageFormat(this.properties.getExportBanner(), this.properties.getLocale()).format(exportOptions));
    }

    /**
     * create the open tag containing all the attributes.
     * @return open tag string: <code>%lt;table attribute="value" ... ></code>
     */
    public String getOpenTag()
    {

        if (this.uid != null && attributeMap.get(TagConstants.ATTRIBUTE_ID) == null)
        {
            // we need to clone the attribute map in order to "fix" the html id when using only the "uid" attribute
            Map<String, String> localAttributeMap = (Map<String, String>) attributeMap.clone();
            localAttributeMap.put(TagConstants.ATTRIBUTE_ID, this.uid);

            StringBuffer buffer = new StringBuffer();
            buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TABLE_TAG_NAME);
            buffer.append(localAttributeMap);
            buffer.append(TagConstants.TAG_CLOSE);

            return buffer.toString();

        }

        // fast, no clone
        StringBuffer buffer = new StringBuffer();

        buffer.append(TagConstants.TAG_OPEN).append(TagConstants.TABLE_TAG_NAME);
        buffer.append(attributeMap);
        buffer.append(TagConstants.TAG_CLOSE);

        return buffer.toString();
    }

    /**
     * Utility method.
     * @param string String
     */
    public void write(String string)
    {
        if (string != null)
        {
            try
            {
                out.write(string);
            }
            catch (IOException e)
            {
                throw new WrappedRuntimeException(getClass(), e);
            }
        }

    }

    @Override
    public void writeTable(TableModel model, String id) throws JspException
    {
        super.writeTable(model, id);
    }

    /**
     * Utility method.
     * @param string String
     */
    public void write(Object string)
    {
        if (string != null)
        {
            try
            {
                out.write(string.toString());
            }
            catch (IOException e)
            {
                throw new WrappedRuntimeException(getClass(), e);
            }
        }

    }

}
